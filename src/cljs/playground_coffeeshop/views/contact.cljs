(ns playground-coffeeshop.views.contact
  (:require [re-frame.core :as re-frame]))

;; about

(defn contact-view []
  [:div
   [:h3 "General Inquiries"]
   [:p [:a {:href "mailto:general@playgroundcoffeeshop.com"} "general@playgroundcoffeeshop.com"]]])
