(ns playground-coffeeshop.components.menu
  (:require [re-frame.core :as re-frame]))

(defn menu-component []
  (fn []
    [:div.menu
     [:a {:href "/about"} [:div [:span "About"]]]
     [:a {:href "/bookings"} [:div [:span "Bookings"]]]
     [:a {:href "/events"} [:div [:span "Events"]]]
     [:a {:href "http://shop.playgroundcoffeeshop.com/"} [:div [:span "Merch"]]]
     [:a {:href "/contact"} [:div [:span "Contact"]]]]))
