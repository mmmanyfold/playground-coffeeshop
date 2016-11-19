(ns playground-coffeeshop.views
  (:require [playground-coffeeshop.components.header :refer [header-component]]
            [playground-coffeeshop.components.nav :refer [nav-component]]
            [playground-coffeeshop.components.footer :refer [footer-component]]))

(defn main-view []
  (fn []
    [:div
     [header-component]
     [nav-component]
     [:br]
     [:hr]
     [footer-component]]))
