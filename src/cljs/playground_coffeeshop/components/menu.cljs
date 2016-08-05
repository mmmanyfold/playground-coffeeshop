(ns playground-coffeeshop.components.menu
  (:require [re-frame.core :as re-frame]))

(defn menu-component []
  (fn []
    ;[:ul
    ; [:li [:a {:href "/events"} [:div.menu-events]]]
    ; [:li [:a {:on-click #(re-frame/dispatch [:display-upcoming-events])}
    ;       [:span "upcoming"]]]
    ; [:li [:a {:on-click #(re-frame/dispatch [:display-past-events])}
    ;       [:span "past-events"]]]]
    [:div.menu
     [:a {:href "/about"} [:div [:span "About"]]]
     [:a {:href "/bookings"} [:div [:span "Bookings"]]]
     [:a {:href "/events"} [:div [:span "Events"]]]
     [:a {:href "http://shop.playgroundcoffeeshop.com/"} [:div [:span "Merch"]]]
     [:a {:href "/contact"} [:div [:span "Contact"]]]]))
