(ns playground-coffeeshop.components.menu
  (:require [re-frame.core :as re-frame]))

(defn menu-component []
  (fn []
    [:div.menu
     [:a {:href "/about"} [:div.menu-about]]
     [:a {:href "/gallery"} [:div.menu-gallery]]
     [:a {:href "/events"} [:div.menu-events]]
     [:a {:href "/contact"} [:div.menu-contact]]]))
