(ns playground-coffeeshop.components.nav
  (:require [re-frame.core :as re-frame]
            [playground-coffeeshop.views.about :refer [about-view]]
            [playground-coffeeshop.views.events :refer [events-view]]
            [playground-coffeeshop.views.home :refer [home-view]]
            [playground-coffeeshop.views.bookings :refer [bookings-view]]
            [playground-coffeeshop.views.contact :refer [contact-view]]
            [playground-coffeeshop.components.header :refer [header-component]]
            [playground-coffeeshop.views.menus :refer [menus-view]]
            [playground-coffeeshop.views.consignment :refer [consignment-view]]
            [playground-coffeeshop.views.event :refer [event-view]]))

(defmulti views identity)
(defmethod views :home-view [] [home-view])
(defmethod views :about-view [] [about-view])
(defmethod views :events-view [] [events-view])
(defmethod views :bookings-view [] [bookings-view])
(defmethod views :contact-view [] [contact-view])
(defmethod views :event-view [] [event-view])
(defmethod views :menus-view [] [menus-view])
(defmethod views :consignment-view [] [consignment-view])
(defmethod views :default [] [:div "404"])

(defn show-view
  [view-name]
  [views view-name])

(defn nav-component []
  (let [active-view (re-frame/subscribe [:active-view])]
    (fn []
      [:div.flex-row
       [:div#nav-wrapper
         [:ul.nav
          [:li [:a {:href "/about"} "About"]]
          [:li [:a {:href "/menus"} "Menus"]]
          [:li [:a {:href "/bookings"} "Bookings"]]
          [:ul.events-menu [:span [:a
                                   {:href     "/events"
                                    :on-click #(re-frame/dispatch [:display-filtered-events])} "Events"]]
           [:li
            {:class (if (= @active-view :events-view)
                      "events-submenu-show"
                      "events-submenu-hide")}
            [:a {:href     "/events"
                 :on-click #(re-frame/dispatch [:display-filtered-events >])}
             [:span "Upcoming"]]]
           [:li.last
            {:class (if (= @active-view :events-view)
                      "events-submenu-show"
                      "events-submenu-hide")}
            [:a {:href     "/events"
                 :on-click #(re-frame/dispatch [:display-filtered-events <])}
             [:span "Past"]]]]
          [:li [:a {:href "/consignment"} "Consignment"]]
          [:li [:a {:href "/contact"} "Contact"]]]]
       [:div.main
        [:div.content
         [show-view @active-view]]]])))
