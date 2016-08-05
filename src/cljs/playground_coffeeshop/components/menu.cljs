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


    ; .menu-item-divided.pure-menu-selected

    [:div#layout.menu
      [:a#menuLink.menu-link
        {:href "#menu"}
        [:span]]
      [:div#menu
        [:div.pure-menu
         [:ul.pure-menu-list
          [:li.pure-menu-item [:a.pure-menu-link {:href "/about"} "About"]]
          [:li.pure-menu-item [:a.pure-menu-link {:href "/bookings"} "Bookings"]]
          [:li.menu-item-divided.pure-menu-selected [:a.pure-menu-link {:href "/events"} "Events"]]
          [:li.pure-menu-item [:a.pure-menu-link {:href "http://shop.playgroundcoffeeshop.com/"} "Merch"]]
          [:li.pure-menu-item [:a.pure-menu-link {:href "/contact"} "Contact"]]]]]]))
