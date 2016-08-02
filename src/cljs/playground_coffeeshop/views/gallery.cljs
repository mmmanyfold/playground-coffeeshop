(ns playground-coffeeshop.views.gallery
  (:require [re-frame.core :as re-frame]
            [playground-coffeeshop.components.header :refer [header-component]]
            [playground-coffeeshop.components.menu :refer [menu-component]]))

;; gallery

(defn gallery-view []
  (fn []
    [:div
     [header-component]
     [:div.flex-row
       [menu-component]
       [:div.content
        [:div "Gallery:"
              [:p "About the gallery space?"]
              [:p "Photos of the space, stage, etc.?"]
              [:p "Rental info and expectations? (+ link to " [:a {:href "/contact"} "contact"] ")"]]]]]))
