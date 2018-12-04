(ns playground-coffeeshop.components.nav
  (:require [re-frame.core :as re-frame]))

(defn nav-component []
  (let [active-view (re-frame/subscribe [:active-view])]
    (fn []
     [:div#nav-wrapper
       [:ul.nav
        [:li [:a {:href "/about"} "About"]]
        [:li [:a {:href "http://blog.playgroundcoffeeshop.com"} "Blog"]]
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
        [:li [:a {:href "/bookings"} "Bookings"]]
        [:li [:a {:href "http://shop.playgroundcoffeeshop.com"} "Shop"]]
        [:li [:a {:href "/contact"} "Contact"]]]])))
