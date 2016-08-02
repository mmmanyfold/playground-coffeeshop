(ns playground-coffeeshop.views.contact
  (:require [re-frame.core :as re-frame]
            [playground-coffeeshop.components.header :refer [header-component]]
            [playground-coffeeshop.components.menu :refer [menu-component]]))

;; about

(defn contact-view []
  (fn []
    [:div
     [header-component]
     [:div.flex-row
       [menu-component]
       [:div.content
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
                  "- Message"]]]]]))
