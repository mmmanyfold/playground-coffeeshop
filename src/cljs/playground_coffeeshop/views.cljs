(ns playground-coffeeshop.views
    (:require [re-frame.core :as re-frame]))


;; home

(defn home-view []
  (let [name (re-frame/subscribe [:name])]
    (fn []
      [:div (str "Hola from " @name ". This is the Home Page.")
       [:div [:a {:href "#/about"} "go to About Page"]]])))


;; about

(defn about-view []
  (fn []
    [:div "This is the About Page."
     [:div [:a {:href "#/events"} "go to Events Page"]]]))

;; events

(defn events-view []
 (fn []
   [:div "This is the Events Page."
    [:div [:a {:href "#/"} "go to Home Page"]]]))

;; main

(defmulti views identity)
(defmethod views :home-view [] [home-view])
(defmethod views :about-view [] [about-view])
(defmethod views :events-view [] [events-view])
(defmethod views :default [] [:div])

(defn show-view
  [view-name]
  [views view-name])

(defn main-view []
  (let [active-view (re-frame/subscribe [:active-view])]
    (fn []
      [show-view @active-view])))
