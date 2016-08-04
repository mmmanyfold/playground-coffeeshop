(ns playground-coffeeshop.views.contact
  (:require [re-frame.core :as re-frame]))

;; about

(defn contact-view []
  [:div "General inquiries:"
   [:p [:a {:href "mailto:general@playgroundcoffeeshop.com"} "general@playgroundcoffeeshop.com"]]])
