(ns playground-coffeeshop.components.menu)

(defn menu-component []
  (fn []
    [:div.menu
     [:a {:href "/about"} [:div.menu-about]]
     [:a {:href "/gallery"} [:div.menu-gallery]]
     [:a {:href "/events"} [:div.menu-events]]
     [:a {:href "/contact"} [:div.menu-contact]]]))
