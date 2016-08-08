(ns playground-coffeeshop.views.bookings
  (:require [re-frame.core :as re-frame]))

;; bookings

(defn bookings-view []
  [:div
   [:p "The space can be converted into a gallery, party, performance venue, etc."]
   [:p "Prices contingent upon the idea."]
   [:p "----Photos----"]
   [:form.pure-form
    {:action "/" :method "post"}
    [:h4 "Contact us today and get reply with in 24 hours!"]
    [:fieldset
     [:input {:placeholder "Your name" :type "text" :tabIndex "1" :required true :autofocus true}]]
    [:fieldset
     [:input {:placeholder "Your Email Address" :type "email" :tabIndex "2" :required true}]]
    [:fieldset
     [:input {:placeholder "Event Type" :type "text" :tabIndex "3" :required true}]]
    [:fieldset
     [:input {:placeholder "How many people?" :type "number" :tabIndex "4" :required true}]]
    [:fieldset
     [:textarea {:placeholder "Date & Time" :type "datetime" :tabIndex "5" :required true}]]
    [:fieldset
     [:button.pure-button.pure-button-primary
      {:name "submit" :type "submit" :data-submit "...Sending"} "Submit"]]]
   [:p "Send to events@playgroundcoffeeshop.com --> \"Thank you! Allow 24 hours for a full response.\""]])
