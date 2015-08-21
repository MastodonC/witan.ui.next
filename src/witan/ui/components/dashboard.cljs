(ns ^:figwheel-always witan.ui.components.dashboard
  (:require [om.core :as om :include-macros true]
            [om-tools.dom :as dom :include-macros true]
            [om-tools.core :refer-macros [defcomponent]]
            [sablono.core :as html :refer-macros [html]]
            [inflections.core :as i]
            [schema.core :as s :include-macros true]
              ;;
            [witan.ui.widgets :as widgets]
            [witan.schema.core :refer [Projection]]
            [witan.ui.util :refer [get-string]]
            [witan.ui.async :refer [raise!]]
            [witan.ui.refs :as refs]))

(defcomponent
  dash-header
  [cursor owner]
  (render [_]
          (let [selected (:selected (om/observe owner (refs/projections-meta)))
                selected-id (second selected)]
            (html
             [:div.pure-menu.pure-menu-horizontal.witan-dash-heading
              [:h1
               (i/capitalize (get-string :projections))]
              (om/build widgets/search-input (str (get-string :filter) " " (get-string :projections)))
              [:ul.pure-menu-list
               [:li.witan-menu-item.pure-menu-item
                ;; single button
                [:a {:href "#/new-projection"}
                 [:button.pure-button.button-success
                  [:i.fa.fa-plus]]]]
               ;;
               (if (not-empty selected)
                 [:li.witan-menu-item.pure-menu-item
                  [:a {:href (str "#/projection/" selected-id)}
                   [:button.pure-button.button-warning
                    [:i.fa.fa-pencil]]]])
               (if (not (empty? selected))
                 [:li.witan-menu-item.pure-menu-item
                  [:a {:href (str "#/projection/" selected-id "/download")}
                   [:button.pure-button.button-primary
                    [:i.fa.fa-download]]]])
               (if (not (empty? selected))
                 [:li.witan-menu-item.pure-menu-item
                  [:a {:href (str "#/share/" selected-id)}
                   [:button.pure-button.button-primary
                    [:i.fa.fa-share-alt]]]])]]))))

(defcomponent view
  [cursor owner args]
  (render [_]
          (html
           [:div
            (om/build dash-header {})
            [:table.pure-table.pure-table-horizontal#witan-dash-projection-list
             [:thead
              [:th] ;; empty, for the tree icon
              [:th (get-string :projection-name)]
              (for [x [:projection-type
                       :projection-owner
                       :projection-version
                       :projection-lastmodified]]
                [:th.text-center (get-string x)])]
             [:tbody
              (om/build-all widgets/projection-tr
                            (:projections cursor)
                            {:key :id
                             :opts {:on-click #(raise! %1 %2 %3)}})]]])))
