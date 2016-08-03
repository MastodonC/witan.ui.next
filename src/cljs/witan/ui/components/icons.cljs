(ns witan.ui.components.icons
  (:require [sablono.core :as sab :include-macros true])
  (:require-macros
   [devcards.core :as dc :refer [defcard]]
   [cljs-log.core :as log]))

(defn decode-keys [& args]
  (let [lookup {:error "md-error"
                :dark "md-dark"
                :light "md-light"
                :disabled "md-inactive"
                :small "md-s"
                :medium "md-m"
                :large "md-l"
                :x-large "md-xl"
                :spin "anim-spin"}]
    (reduce (fn [a r]
              (apply str a
                     (if-let [value (get lookup r)]
                       (str "." value)))) "" args)))

(defn create-key
  [icon-name & args]
  (let [key-str (->>
                 (apply decode-keys args)
                 (str "i.material-icons")
                 (keyword))]
    [key-str icon-name]))

;;

(defn cog
  [& args]
  (apply create-key "settings" args))

(defn upload
  [& args]
  (apply create-key "file_upload" args))

(defn padlock
  [& args]
  (apply create-key "lock" args))

(defn star
  [& args]
  (apply create-key "star" args))

(defn workspace
  [& args]
  (apply create-key "assessment" args))

(defn data
  [& args]
  (apply create-key "subject" args))

(defn help
  [& args]
  (apply create-key "live_help" args))

(defn logout
  [& args]
  (apply create-key "power_settings_new" args))

(defn search
  [& args]
  (apply create-key "search" args))

(defn person
  [& args]
  (apply create-key "person" args))

(defn visualisation
  [& args]
  (apply create-key "show_chart" args))

(defn topology
  [& args]
  (apply create-key "transform" args))

(defn plus
  [& args]
  (apply create-key "add" args))

(defn open
  [& args]
  (apply create-key "open_in_browser" args))

(defn error
  [& args]
  (apply create-key "error" args))

(defn clipboard
  [& args]
  (apply create-key "content_paste" args))

(defn close
  [& args]
  (apply create-key "close" args))

;;

(defn loading
  [& args]
  (apply cog :spin :dark args))

;;

(defcard loading-card
  (sab/html [:div
             (loading :small)
             (loading :large )
             (loading :x-large)]))

(defcard icons-card
  (sab/html
   (let [variations '([]
                      [:dark]
                      [:dark :disabled]
                      [:light]
                      [:light :disabled]
                      [:small]
                      [:medium]
                      [:large]
                      [:x-large])
         all-icons [["cog"           cog]
                    ["upload"        upload]
                    ["padlock"       padlock]
                    ["star"          star]
                    ["workspace"     workspace]
                    ["data"          data]
                    ["help"          help]
                    ["logout"        logout]
                    ["search"        search]
                    ["visualisation" visualisation]
                    ["person"        person]
                    ["plus"          plus]
                    ["open"          open]
                    ["error"         error]
                    ["clipboard"     clipboard]
                    ["close"         close]]]
     [:div {:style {:background-color "#fff"}}
      (for [[title icon-fn] all-icons]
        [:div
         {:key title}
         [:h2 title]
         (for [v variations]
           (let [icon (apply icon-fn v)]
             [:div
              {:key (str title icon)}
              icon
              [:span {:style {:float "right"}} (dc/edn icon)]]))])])))
