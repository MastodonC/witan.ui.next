(ns ^:figwheel-always witan.ui.fixtures.menu.view-model
    (:require [om.core :as om :include-macros true]
              [venue.core :as venue]
              [witan.ui.util :as util])
    (:require-macros [cljs-log.core :as log]
                     [witan.ui.macros :as wm]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn on-initialise
  [owner cursor]
  (util/inline-subscribe!
   :api/user-identified
   #(om/update! cursor :user %)))

(wm/create-standard-view-model! {:on-initialise on-initialise})

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defmethod response-handler
  [:logout :success]
  [_ _ _ _])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defmethod event-handler
  :event/logout
  [owner _ args cursor]
  (venue/request! {:owner owner
                   :service :service/api
                   :request :logout}))
