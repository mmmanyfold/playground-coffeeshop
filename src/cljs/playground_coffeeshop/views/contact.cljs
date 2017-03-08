(ns playground-coffeeshop.views.contact
  (:require [re-frame.core :as re-frame]))

;; about

(defn contact-view []
  [:div
   [:h2 "General Inquiries:"]
   [:h3 [:a {:href "mailto:general@playgroundcoffeeshop.com"} "general@playgroundcoffeeshop.com"]]])
