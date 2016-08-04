(ns playground-coffeeshop.components.menu
  (:require [re-frame.core :as re-frame]))

(defn menu-component []
  (fn []
    [:div.menu
     [:a {:href "/about"} [:div [:span.menu-about "About"]]]
     [:a {:href "/bookings"} [:div [:span.menu-bookings "Bookings"]]]
     [:a {:href "/events"} [:div [:span.menu-events "Events"]]]
     [:a {:href "/contact"} [:div [:span.menu-contact "Contact"]]]]))
