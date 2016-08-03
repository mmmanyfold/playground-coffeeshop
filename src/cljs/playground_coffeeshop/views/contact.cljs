(ns playground-coffeeshop.views.contact
  (:require [re-frame.core :as re-frame]))

;; about

(defn contact-view []
  [:div "Contact:"
   [:p "Rental form" [:br]
    "- Name" [:br]
    "- Email" [:br]
    "- Event date" [:br]
    "- Event description" [:br]
    "- Capacity"]
   [:p "General inquiry form" [:br]
    "- Name" [:br]
    "- Email" [:br]
    "- Message"]])
