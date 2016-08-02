(ns playground-coffeeshop.views.about
  (:require [re-frame.core :as re-frame]
            [playground-coffeeshop.components.header :refer [header-component]]
            [playground-coffeeshop.components.menu :refer [menu-component]]))

;; about

(defn about-view []
  (fn []
    [:div
     [header-component]
     [:div.flex-row
       [menu-component]
       [:div.content
        [:div "About Playground?"
              [:p "Hours?"]
              [:p "etc."]]]]]))
