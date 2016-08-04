(ns playground-coffeeshop.views.bookings
  (:require [re-frame.core :as re-frame]))

;; bookings

(defn bookings-view []
  [:div "Bookings:"
   [:p "The space can be converted into a gallery, party, performance venue, etc."]
   [:p "Prices contingent upon the idea."]
   [:p "Photos?"]
   [:p "Booking form:"
    [:br] "- name"
    [:br] "- email"
    [:br] "- what kind of event"
    [:br] "- how many ppl"
    [:br] "- date"
    [:br] "- time"
    [:br] "Send to events@playgroundcoffeeshop.com --> \"Thank you! Allow 24 hours for a full response.\""]])
