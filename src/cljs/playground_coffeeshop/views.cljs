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
        [:div.flex-row
          [nav-component]
          [:div.main
           [:div.embed-container
            [:iframe {:src "https://www.youtube.com/embed/maRzI4b_CbA?rel=0&autoplay=1&controls=0&showinfo=0"
                      :frameBorder 0
                      :allowFullScreen true}]]
           [:div.flex-row-wrap.players
            [:div [:a {:href "https://soundcloud.com/playground_youth"} "Soundcloud"]]
            [:div "*"]
            [:div [:a {:href "http://mixlr.com/playgroundradio/" } "Mixlr Radio"]]
            [:iframe {:width "48%", :scrolling "no", :frameborder "no", :src "https://w.soundcloud.com/player/?url=https%3A//api.soundcloud.com/users/541285623&color=%23ff5500&auto_play=false&hide_related=false&show_comments=true&show_user=true&show_reposts=false&show_teaser=true&visual=true"}]
            [:iframe {:src "https://mixlr.com/playgroundradio/embed", :width "48%", :scrolling "no", :frameborder "no", :marginheight "0", :marginwidth "0"}]]]]
        [footer-component]]
       [:div
        [:div.flex-row
         [nav-component]
         [:div.main
          [:div.content
           [show-view @active-view]]]]
        [footer-component]])])))
