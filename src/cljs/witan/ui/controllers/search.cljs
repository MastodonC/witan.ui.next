(ns witan.ui.controllers.search
  (:require [schema.core :as s]
            [witan.ui.ajax :as ajax]
            [witan.ui.data :as data]
            [witan.ui.activities :as activities]
            [witan.ui.utils :as utils]
            [witan.ui.time :as time]
            [cljs-time.core :as t]
            [inflections.core :as i]
            [cljs-time.format :as tf]
            [witan.ui.route :as route]
            [witan.ui.strings :refer [get-string]]
            [witan.ui.title :refer [set-title!]]
            [goog.string :as gstring]
            [cljs.core.async :refer [chan <! >! timeout pub sub unsub unsub-all put! close!]]
            [cljsjs.toastr])
  (:require-macros [cljs-log.core :as log]
                   [cljs.core.async.macros :refer [go go-loop]]
                   [witan.ui.env :as env :refer [cljs-env]]))

(declare on-query-response)

(defn set-expand-lock
  [v]
  (data/swap-app-state! :app/search
                        assoc
                        :ks/datapack-files-expand-in-progress
                        v))

(defn get-expand-lock
  []
  (data/get-in-app-state :app/search :ks/datapack-files-expand-in-progress))

(defn datapack-files-search
  []
  (data/get-in-app-state :app/search :ks/datapack-files :ks/current-search))

(defn update-datapack-files-search-name
  [search-term]
  (data/swap-app-state-in! [:app/search :ks/datapack-files
                            :ks/current-search :query :kixi.datastore.metadatastore.query/name]
                           assoc
                           :match search-term))

(defn update-datapack-files-search-from
  [from]
  (data/swap-app-state-in! [:app/search :ks/datapack-files :ks/current-search]
                           assoc
                           :from from))

(defn update-dashboard-search-name
  [search-term]
  (data/swap-app-state-in! [:app/search :ks/dashboard
                            :ks/current-search :query :kixi.datastore.metadatastore.query/name]
                           assoc
                           :match search-term))

(defn dashboard-search
  []
  (data/get-in-app-state :app/search :ks/dashboard :ks/current-search))

(defmulti handle
  (fn [event args] event))

(defmethod handle
  :dashboard
  [_ {:keys [search-term]}]
  (log/debug "Search: " search-term (dashboard-search))
  (when search-term
    (update-dashboard-search-name search-term))
  (let [current-search (dashboard-search)]
    (when-not (get (data/get-in-app-state :app/search :ks/dashboard :ks/search->result)
                   current-search)
      (data/query {:search/dashboard [[current-search]]}
                  on-query-response))))


(defmethod handle
  :datapack-files
  [event {:keys [search-term]}]
  (update-datapack-files-search-name search-term)
  (when-not (get (data/get-in-app-state :app/search :ks/datapack-files :ks/search->result)
                 (datapack-files-search))
    (data/query {:search/datapack-files [[(datapack-files-search)]]}
                on-query-response)))


(defmethod handle
  :datapack-files-expand
  [event _]
  (let [datapack-files (data/get-in-app-state :app/search :ks/datapack-files)
        current-search (:ks/current-search datapack-files)
        current-paging (get-in datapack-files [:ks/search->result current-search :paging])
        current-item-count (count (get-in datapack-files [:ks/search->result current-search :items]))]
    ;;This lock isn't very strong, but seems to be enough else where
    (when (and (> (:total current-paging) current-item-count)
               (not (get-expand-lock)))
      (set-expand-lock true)
      (data/query {:search/datapack-files-expand [[(assoc (datapack-files-search)
                                                          :from current-item-count)]]}
                  on-query-response))))

(defmethod handle
  :clear-datapack-files
  [event {:keys [search-term]}]
  (data/swap-app-state-in! [:app/search :ks/datapack-files]
                           assoc
                           :ks/current-search data/search-file-list-default)
  (set-expand-lock false))

(defmulti on-query-response
  (fn [[k v]] k))

(defmethod on-query-response
  :search/dashboard
  [[_ {:keys [search] :as resp}]]
  (log/debug "Dashboard Got: " (get-in resp [:search]))

  (comment "caching version"
           ;;TODO need to control number of autocompletes stored

           (data/swap-app-state-in! [:app/search :ks/dashboard :ks/search->result]
                                    assoc
                                    search
                                    resp))

  (data/swap-app-state-in! [:app/search :ks/dashboard]
                           assoc
                           :ks/search->result
                           {search resp}))

(defn datapacks-cache-search
  [search]
  (dissoc search :from))

(defmethod on-query-response
  :search/datapack-files
  [[_ {:keys [search] :as resp}]]
  (log/debug "Datapacks Got: " (get-in resp [:search]))
  (data/swap-app-state-in! [:app/search :ks/datapack-files :ks/search->result]
                           assoc
                           (datapacks-cache-search search)
                           resp))

(defmethod on-query-response
  :search/datapack-files-expand
  [[_ {:keys [search items paging] :as resp}]]
  (log/debug "Datapacks Got: " (get-in resp [:search]))
  (data/swap-app-state-in! [:app/search :ks/datapack-files :ks/search->result (datapacks-cache-search search) :items]
                           (comp vec concat)
                           (:items resp))
  (data/swap-app-state-in! [:app/search :ks/datapack-files :ks/search->result (datapacks-cache-search search) :paging :count]
                           +
                           (:count paging))
  (set-expand-lock false))

(defn send-dashboard-query!
  []
  (handle :dashboard {:search-term nil}))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; On Route Change

(defmulti on-route-change
  (fn [{:keys [args]}] (:route/path args)))

(defmethod on-route-change
  :default [_])

(def dash-page-query-param :page)

(defmethod on-route-change
  :app/data-dash
  [{:keys [args]}]
  (let [type-filter (get-in args [:route/query :metadata-type])
        requested-page (js/parseInt (or (get-in args [:route/query dash-page-query-param]) "1"))]
    (if type-filter
      (data/swap-app-state-in! [:app/search :ks/dashboard :ks/current-search :query] assoc :kixi.datastore.metadatastore.query/type {:equals type-filter})
      (data/swap-app-state-in! [:app/search :ks/dashboard :ks/current-search :query] dissoc :kixi.datastore.metadatastore.query/type))
    (data/swap-app-state-in! [:app/search :ks/dashboard :ks/current-search]
                             assoc
                             :from
                             (* (dec requested-page)
                                (data/get-in-app-state :app/search :ks/dashboard :ks/current-search :size))))
  (send-dashboard-query!)
  (set-title! (get-string :string/title-data-dashboard)))

(defonce subscriptions
  (do (data/subscribe-topic :data/route-changed on-route-change)))
