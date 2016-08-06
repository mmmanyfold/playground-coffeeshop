(ns playground-coffeeshop.components.menu
  (:require [re-frame.core :as re-frame]
            [playground-coffeeshop.views.about :refer [about-view]]
            [playground-coffeeshop.views.events :refer [events-view]]
            [playground-coffeeshop.views.home :refer [home-view]]
            [playground-coffeeshop.views.bookings :refer [bookings-view]]
            [playground-coffeeshop.views.contact :refer [contact-view]]
            [playground-coffeeshop.components.header :refer [header-component]]))

(defmulti views identity)
(defmethod views :home-view [] [home-view])
(defmethod views :about-view [] [about-view])
(defmethod views :events-view [] [events-view])
(defmethod views :bookings-view [] [bookings-view])
(defmethod views :contact-view [] [contact-view])
(defmethod views :default [] [:div "404"])

(defn show-view
  [view-name]
  [views view-name])

(defn menu-component []
  (let [active-view (re-frame/subscribe [:active-view])]
    (fn []
      ;[:ul
      ; [:li [:a {:href "/events"} [:div.menu-events]]]
      ; [:li [:a {:on-click #(re-frame/dispatch [:display-upcoming-events])}
      ;       [:span "upcoming"]]]
      ; [:li [:a {:on-click #(re-frame/dispatch [:display-past-events])}
      ;       [:span "past-events"]]]]


      ; .menu-item-divided.pure-menu-selected

      [:div#layout
        [:a#menuLink.menu-link
          {:href "#menu"}
          [:span]]
        [:div#menu
          [:div.pure-menu
           [:ul.pure-menu-list
            [:li.pure-menu-item [:a.pure-menu-link {:href "/about"} "About"]]
            [:li.pure-menu-item [:a.pure-menu-link {:href "/bookings"} "Bookings"]]
            [:li.pure-menu-item [:a.pure-menu-link {:href "/events"} "Events"]]
            [:li.pure-menu-item [:a.pure-menu-link {:href "/merch"} "Merch"]]
            [:li.pure-menu-item [:a.pure-menu-link {:href "/contact"} "Contact"]]]]]
        [:div.main
          [:div.content
            [show-view @active-view]]]])))
