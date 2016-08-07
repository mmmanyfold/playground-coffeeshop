(ns playground-coffeeshop.views
  (:require [playground-coffeeshop.components.header :refer [header-component]]
            [playground-coffeeshop.components.menu :refer [menu-component]]))

(defn main-view []
  (fn []
    [:div
     [header-component]
     [menu-component]]))
