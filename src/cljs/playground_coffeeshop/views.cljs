(ns playground-coffeeshop.views
  (:require [re-frame.core :as re-frame]
            [playground-coffeeshop.components.header :refer [header-component]]
            [playground-coffeeshop.components.nav :refer [nav-component]]
            [playground-coffeeshop.components.footer :refer [footer-component]]
            [playground-coffeeshop.views.about :refer [about-view]]
            [playground-coffeeshop.views.blog :refer [blog-view]]
            [playground-coffeeshop.views.events :refer [events-view]]
            [playground-coffeeshop.views.bookings :refer [bookings-view]]
            [playground-coffeeshop.views.contact :refer [contact-view]]
            [playground-coffeeshop.components.header :refer [header-component]]
            [playground-coffeeshop.views.menus :refer [menus-view]]
            [playground-coffeeshop.views.consignment :refer [consignment-view]]
            [playground-coffeeshop.views.event :refer [event-view]]))

(defmulti views identity)
(defmethod views :about-view [] [about-view])
(defmethod views :blog-view [] [blog-view])
(defmethod views :events-view [] [events-view])
(defmethod views :bookings-view [] [bookings-view])
(defmethod views :contact-view [] [contact-view])
(defmethod views :event-view [] [event-view])
(defmethod views :menus-view [] [menus-view])
(defmethod views :consignment-view [] [consignment-view])
(defmethod views :default [] [:div])

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
            [:img.timelapse-mobile {:src "img/timelapse_clip.gif"
                                    :width "100%"}]]]]
        [:div.embed-container
          [:iframe {:src "https://www.youtube.com/embed/wqdTWsI9N0k?rel=0&autoplay=1&controls=0&showinfo=0"
                    :frameBorder 0
                    :allowFullScreen true}]]
        [:div {:style {:overflow "hidden" :text-align "right"}}
          [:iframe {:src "https://mixlr.com/users/2451151/embed?autoplay=true", :width "100%", :height "112px", :scrolling "no", :frameborder "no", :marginHeight "0", :marginWidth "0"}]
          [:br][:small [:i "You're Listening to 8Ball Radio"]]]
        [footer-component]]
       [:div
        [:div.flex-row
         [nav-component]
         [:div.main
          [:div.content
           [show-view @active-view]]]]
        [footer-component]])])))
