(ns playground-coffeeshop.views
  (:require [re-frame.core :as re-frame]
            [playground-coffeeshop.views.about :refer [about-view]]
            [playground-coffeeshop.views.events :refer [events-view]]
            [playground-coffeeshop.views.home :refer [home-view]]
            [playground-coffeeshop.views.gallery :refer [gallery-view]]
            [playground-coffeeshop.views.contact :refer [contact-view]]))

(defmulti views identity)
(defmethod views :home-view [] [home-view])
(defmethod views :about-view [] [about-view])
(defmethod views :events-view [] [events-view])
(defmethod views :gallery-view [] [gallery-view])
(defmethod views :contact-view [] [contact-view])
(defmethod views :default [] [:div "404"])

(defn show-view
  [view-name]
  [views view-name])

(defn main-view []
  (let [active-view (re-frame/subscribe [:active-view])]
    (fn []
      [show-view @active-view])))
