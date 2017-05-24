(ns witan.ui.style.components.data
  (:require [garden.units :refer [px em percent]]
            [witan.ui.style.colour :as colour]
            [witan.ui.style.values :as values]
            [witan.ui.style.fonts :as fonts]
            [witan.ui.style.util :refer [transition]]))

(def style [[:#create-data
             {:height (percent 100)}
             [:.container
              {:position :relative
               :height (percent 100)}
              [:.content
               {:padding [[(em 1) (em 1)]]
                :display :flex
                :position :absolute
                :top values/app-peripheral-height
                :right 0
                :left 0
                :bottom 0
                :overflow-y 'auto}
               [:h2
                [:em
                 {:font-size (px 12)
                  :margin-left (px 5)
                  :font-style :normal}]]
               [:h3
                {:font-size (px 14)
                 :margin [[(em 0.4) (em 0)]]
                 :color 'gray}]
               [:button.button-success
                {:background-color colour/button-create
                 :color colour/body-bg}]
               [:#submit-button
                {:margin-bottom (px 20)}]
               [:.pure-form
                [:i
                 {:font-size (px 20)}]
                [:.button-container
                 {:margin-top (px 6)}
                 [:button {:margin (px 0)}]]]
               [:.upload-phases]
               [:.uploading :.upload-error
                {:text-align :center}
                [:.pure-button
                 {:margin 0}]
                [:.error
                 {:margin (em 1)}]
                [:.progress-bar
                 {:width (percent 60)
                  :display :block
                  :margin [[0 :auto]]
                  :margin-top (em 2)}]]
               [:.upload-phase
                {:margin-top (em 2)}
                [:.upload-phase-heading
                 {:display :flex
                  :align-items :center}
                 [:.number-circle
                  {:flex "0 0 auto"}]
                 [:strong
                  {:margin-left (em 0.5)
                   :font-size (em 1.2)
                   :vertical-align :middle}]]
                [:.upload-phase-heading-disabled
                 [:.number-circle
                  {:background-color 'silver}]
                 [:strong
                  {:color 'silver}]]
                [:.upload-phase-content
                 {:margin-left (em 2)
                  :margin-top (em 0.5)}
                 [:input.hidden-file-input
                  {:position :fixed
                   :top (em -100)}]
                 [:div.selected-file-name
                  {:margin-top (em 1)}
                  [:.size
                   {:margin-left (em 0.2)
                    :font-size (px 10)
                    :font-style :italic}]]]
                [:&#step-3 :&#step-4
                 [:label
                  {:margin-left (em 0.3)
                   :line-height (em 1.6)
                   :vertical-align :bottom}]
                 [:.shared-schema-search-area
                  :.shared-group-search-area
                  {:padding-top (px 10)}]]
                [:&#step-2
                 [:input :textarea
                  {:width (percent 100)}]]
                [:&#step-4
                 [:label
                  {:font-style :italic}]
                 [:button.data-upload
                  {:background-color colour/button-create
                   :color colour/body-bg}]]]]]]

            [:#data-dash
             [:.data-name
              [:span
               {:vertical-align :top
                :line-height 2.1
                :margin-left (px 10)}]]]

            [:#data-view
             {:width (percent 100)
              :height (percent 100)
              :overflow-y :auto}
             [:.container
              {:position :relative
               :max-width (px 1024)}]
             [:.field-entry
              {:margin [[(em 0.5) (em 0)]]}]
             [:.sharing-controls
              [:.sharing-activity
               [:.selected-groups
                {:margin [[(em 0.5) (em 0)]]}]]]]

            [:.file-title
             {:margin [[(em 0.0) (em 0.2)]]
              :height (px 30)
              ;;:display :inline
              :white-space :nowrap
              :overflow :hidden
              :text-overflow :ellipsis}]
            [:.file-title-input
             {:margin [[(em 0.0) (em 0.2)]]
              :height (px 30)
              :width (percent 100)
              :font-family fonts/title-fonts
              :color colour/title-fonts-colour
              :font-size (em 1.5)
              :display :inline-block
              :padding (px 0)
              :border (px 0)
              :background-color colour/editing-bg}
             ^:prefix {:box-sizing :content-box}
             [:&:focus
              {:outline :none}]]

            [:.editable-field
             {:padding [[(em 0.2) (em 0.4)]]
              :line-height (em 1.7)
              :border [[(px 1) 'solid 'transparent]]}
             [:&:hover
              {:border [[(px 1) 'dashed colour/gutter]]}]
             [:span
              {:font-size (px 12)
               :height (em 0.75)
               :line-height (em 0.75)}]

             [:.editable-field-content
              {:display :flex
               :justify-content :space-between
               :vertical-align :bottom
               :align-items :flex-end}]]
            [:.editable-field-editing
             {:border [[(px 1) 'dashed colour/gutter]]
              :background-color colour/editing-bg}]])
