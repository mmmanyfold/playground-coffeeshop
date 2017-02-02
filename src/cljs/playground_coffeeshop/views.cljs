(ns playground-coffeeshop.views
  (:require [re-frame.core :as re-frame]
            [playground-coffeeshop.components.header :refer [header-component]]
            [playground-coffeeshop.components.nav :refer [nav-component]]
            [playground-coffeeshop.components.footer :refer [footer-component]]
            [playground-coffeeshop.views.about :refer [about-view]]
            [playground-coffeeshop.views.news :refer [news-view]]
            [playground-coffeeshop.views.events :refer [events-view]]
            [playground-coffeeshop.views.bookings :refer [bookings-view]]
            [playground-coffeeshop.views.contact :refer [contact-view]]
            [playground-coffeeshop.components.header :refer [header-component]]
            [playground-coffeeshop.views.menus :refer [menus-view]]
            [playground-coffeeshop.views.consignment :refer [consignment-view]]
            [playground-coffeeshop.views.event :refer [event-view]]))

(defmulti views identity)
(defmethod views :about-view [] [about-view])
(defmethod views :news-view [] [news-view])
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


(defn main-view []
 (let [active-view (re-frame/subscribe [:active-view])]
  (fn []
    [:div
     [header-component]
     (if (= @active-view :home-view)
       [:div
        [:div.flex-row {:style {:position "absolute"}}
          [nav-component]
          [:div.main
           [:div.content
            [:img {:src "img/timelapse_clip.gif"
                   :width "100%"}]]]]
        [:div.embed-container
          [:iframe {:src "https://www.youtube.com/embed/wqdTWsI9N0k?autoplay=1;rel=0&amp;controls=0&amp;showinfo=0"
                    :frameBorder 0
                    :allowFullScreen true}]]
        [:br]
        [:hr]
        [footer-component]]
       [:div
        [:div.flex-row
         [nav-component]
         [:div.main
          [:div.content
           [show-view @active-view]]]]
        [:br]
        [:hr]
        [footer-component]])])))
