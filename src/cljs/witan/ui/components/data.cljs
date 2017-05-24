(ns witan.ui.components.data
  (:require [reagent.core :as r]
            [sablono.core :as sab :include-macros true]
            [witan.ui.data :as data]
            [witan.ui.components.shared :as shared]
            [witan.ui.components.icons :as icons]
            [witan.ui.strings :refer [get-string]]
            [witan.ui.controller :as controller]
            [witan.ui.utils :as utils]
            [witan.ui.time :as time]
            [goog.string :as gstring])
  (:require-macros [cljs-log.core :as log]
                   [devcards.core :as dc :refer [defcard]]))

(defn reverse-group->activity-map
  [all-activities sharing]
  (let [sharing-sets (zipmap (keys sharing)
                             (map set (vals sharing)))
        all-groups (set (apply concat (vals sharing)))]
    (reduce
     (fn [group->activities group]
       (assoc group->activities
              group
              {:values
               (reduce
                (fn [a activity]
                  (assoc a
                         activity
                         (not (nil? ((get sharing-sets activity #{})
                                     group)))))
                {}
                all-activities)}))
     {}
     all-groups)))

(defn lock-activities
  [sharing user-sg]
  (reduce (fn [a [group activities]]
            (let [new-activities
                  (merge activities
                         (when (= (:kixi.group/id group) user-sg)
                           {:locked (set (data/get-in-app-state
                                          :app/datastore :ds/locked-activities))}))]
              (assoc a group new-activities))) {} sharing))

(defn format-description
  [description-str]
  (let [substrings (clojure.string/split description-str #"\n")]
    [:div
     (doall
      (mapcat
       #(vector [:span {:key (str "string-" %1)} %2]
                [:br {:key (str "br-" %1)}])
       (range (count substrings))
       substrings))]))

(defn editable-field
  [obj obj-key render-fn icon-fn input-class validate-fn on-complete-fn]
  (let [state (r/atom :idle)
        current-value (r/atom nil)
        focus-interval (r/atom nil)
        random-id (random-uuid)
        give-focus (fn []
                     (when-let [input (.getElementById js/document random-id)]
                       (.focus input)
                       (js/clearInterval @focus-interval)
                       (reset! focus-interval nil)))]
    (fn [obj oby-key render-fn icon-fn input-class validate-fn on-complete-fn]
      (let [value (get obj obj-key)]
        [:div
         {:class (str "editable-field "
                      (when (= :editing @state) "editable-field-editing"))
          :on-mouse-over #(when-not (= :editing @state)
                            (reset! state :hovered))
          :on-mouse-leave #(when-not (= :editing @state)
                             (reset! state :idle))}
         (icon-fn (if (= :editing @state) :error :success))
         (if (= :editing @state)
           [:div.editable-field-input
            [:input
             {:class input-class
              :autofocus true
              :id random-id
              :value @current-value
              :on-focus #(set! (.-value (.-target %)) value)
              :on-blur #(let [v (.-value (.-target %))
                              v (if (validate-fn v) v value)]
                          (reset! current-value v)
                          (reset! state :idle)
                          (when on-complete-fn
                            (on-complete-fn v)))
              :on-change #(reset! current-value (.-value (.-target %)))}]]
           [:div.editable-field-content
            (render-fn value)
            (when (= :hovered @state)
              [:span.clickable-text
               {:on-click #(do
                             (reset! state :editing)
                             (reset! current-value value)
                             (reset! focus-interval (js/setInterval give-focus 100)))}
               (get-string :string/edit)])])]))))

(defn title
  [file on-complete-fn]
  [editable-field file :kixi.datastore.metadatastore/name
   #(vector :h2.file-title %)
   icons/title
   "file-title-input"
   (complement clojure.string/blank?)
   on-complete-fn])

(defn description
  [file on-complete-fn]
  [editable-field file :kixi.datastore.metadatastore/description
   #(vector :span.file-description %)
   icons/description
   "file-description-input"
   (complement clojure.string/blank?)
   on-complete-fn])

(defn view
  []
  (let [{:keys [ds/current ds/download-pending? ds/error] :as ds}
        (data/get-in-app-state :app/datastore)
        activities->string (:ds/activities ds)
        md (data/get-in-app-state :app/datastore :ds/file-metadata current)
        user-sg (data/get-in-app-state :app/user :kixi.user/self-group)]
    (if error
      [:div.text-center.padded-content
       [:div
        (icons/error :dark :large)]
       [:div [:h3 (get-string error)]]]
      (if-not md
        [:div.loading
         (icons/loading :large)]
        (let [{:keys [kixi.datastore.metadatastore/file-type
                      kixi.datastore.metadatastore/name
                      kixi.datastore.metadatastore/description
                      kixi.datastore.metadatastore/sharing
                      kixi.datastore.metadatastore/provenance
                      kixi.datastore.metadatastore/size-bytes]} md
              sharing-groups (set (reduce concat [] (vals sharing)))]
          [:div#data-view
           [:div.container.padded-content
            [:h2 (get-string :string/file-name ":" name)]
            ;; ----------------------------------------------
            [:hr]
            [:div.field-entries
             [:div.field-entry
              [:strong (get-string :string/file-type ":")]
              [:span file-type]]
             [:div.field-entry
              [:strong (get-string :string/file-provenance-source ":")]
              [:span (:kixi.datastore.metadatastore/source provenance)]]
             [:div.field-entry
              [:strong (get-string :string/created-at ":")]
              [:span (time/iso-time-as-moment (:kixi.datastore.metadatastore/created provenance))]]
             [:div.field-entry
              [:strong (get-string :string/file-size ":")]
              [:span (js/filesize size-bytes)]]
             [:div.field-entry
              [:strong (get-string :string/file-uploader ":")]
              [:span (get-in provenance [:kixi/user :kixi.user/name])]]]
            (when description
              [:div.field-entry
               [:strong (get-string :string/file-description ":")]
               (format-description description)])
            ;; ----------------------------------------------
            [:hr]
            [:div.sharing-controls
             [:h2 (get-string :string/sharing)]
             [:div.sharing-activity
              [:div.selected-groups
               [shared/sharing-matrix activities->string
                (-> (keys activities->string)
                    (reverse-group->activity-map sharing)
                    (lock-activities user-sg))
                {:on-change
                 (fn [[group activities] activity target-state]
                   (controller/raise! :data/sharing-change
                                      {:current current
                                       :group group
                                       :activity activity
                                       :target-state target-state}))
                 :on-add
                 (fn [group]
                   (controller/raise! :data/sharing-add-group
                                      {:current current :group group}))}
                {:exclusions sharing-groups}]]]]
            ;; ----------------------------------------------
            [:hr]
            [:div.actions
             [:a {:href (str
                         (if (:gateway/secure? data/config) "https://" "http://")
                         (or (:gateway/address data/config) "localhost:30015")
                         "/download?id="
                         current)
                  :target "_blank"} (shared/button {:id :button-a
                                                    :icon icons/download
                                                    :txt :string/file-actions-download-file
                                                    :class "file-action-download"}
                                                   #())]]]])))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def example-file
  {:kixi.datastore.metadatastore/size-bytes 49
   :kixi.datastore.metadatastore/sharing
   {:kixi.datastore.metadatastore/file-read
    [{:kixi.group/id "cc4d9d25-5847-4f78-9331-c31c50544cd5",
      :kixi.group/name "Mastodon C",
      :kixi.group/type "group",
      :kixi.group/created-by "0c0d29af-4f9a-46b7-a8c6-29fde09c1f8e",
      :kixi.group/created "2017-04-24T16:35:50.581Z"}
     {:kixi.group/id "44a94379-9d4f-4d5d-8511-8c0f2ceccc24", :kixi.group/name "Test User", :kixi.group/type "user", :kixi.group/created-by "614c4e89-7e0e-4bd1-9dfb-ca115b005704", :kixi.group/created "2017-04-07T14:13:46.046Z"}],
    :kixi.datastore.metadatastore/meta-read
    [{:kixi.group/id "cc4d9d25-5847-4f78-9331-c31c50544cd5", :kixi.group/name "Mastodon C", :kixi.group/type "group", :kixi.group/created-by "0c0d29af-4f9a-46b7-a8c6-29fde09c1f8e", :kixi.group/created "2017-04-24T16:35:50.581Z"}
     {:kixi.group/id "44a94379-9d4f-4d5d-8511-8c0f2ceccc24", :kixi.group/name "Test User", :kixi.group/type "user", :kixi.group/created-by "614c4e89-7e0e-4bd1-9dfb-ca115b005704", :kixi.group/created "2017-04-07T14:13:46.046Z"}],
    :kixi.datastore.metadatastore/meta-update [{:kixi.group/id "cc4d9d25-5847-4f78-9331-c31c50544cd5", :kixi.group/name "Mastodon C", :kixi.group/type "group", :kixi.group/created-by "0c0d29af-4f9a-46b7-a8c6-29fde09c1f8e", :kixi.group/created "2017-04-24T16:35:50.581Z"}
                                               {:kixi.group/id "44a94379-9d4f-4d5d-8511-8c0f2ceccc24", :kixi.group/name "Test User", :kixi.group/type "user", :kixi.group/created-by "614c4e89-7e0e-4bd1-9dfb-ca115b005704", :kixi.group/created "2017-04-07T14:13:46.046Z"}]},
   :kixi.datastore.metadatastore/file-type "txt",
   :kixi.datastore.metadatastore/header false,
   :kixi.datastore.metadatastore/provenance {:kixi.datastore.metadatastore/source "upload",
                                             :kixi.datastore.metadatastore/created "20170524T031328.649Z",
                                             :kixi/user {:kixi.user/id "614c4e89-7e0e-4bd1-9dfb-ca115b005704", :kixi.user/name "Test User", :kixi.user/created "2017-04-07T14:13:46.020Z", :kixi.user/username "test@mastodonc.com"}},
   :kixi.datastore.metadatastore/name "Test File",
   :kixi.datastore.metadatastore/id "5f28728c-5cc7-485d-8563-345fd0a65f2f",
   :kixi.datastore.metadatastore/type "stored",
   :kixi.datastore.metadatastore/description "Test Description"})

(defcard title-display
  (fn [data _]
    (sab/html
     [:div
      {:style {:width "100%"}}
      (r/as-element [title @data #(swap! data assoc :kixi.datastore.metadatastore/name %)])]))
  (select-keys example-file [:kixi.datastore.metadatastore/name])
  {:inspect-data true
   :frame true
   :history false})

(defcard description-display
  (fn [data _]
    (sab/html
     [:div
      {:style {:width "100%"}}
      (r/as-element [description @data #(swap! data assoc :kixi.datastore.metadatastore/description %)])]))
  (select-keys example-file [:kixi.datastore.metadatastore/description])
  {:inspect-data true
   :frame true
   :history false})
