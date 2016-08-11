(ns playground-coffeeshop.views
  (:require [playground-coffeeshop.components.header :refer [header-component]]
            [playground-coffeeshop.components.menu :refer [menu-component]]
            [playground-coffeeshop.components.footer :refer [footer-component]]))

(defn main-view []
  (fn []
    [:div
     [header-component]
     [menu-component]
     [:hr]
     [footer-component]]))
