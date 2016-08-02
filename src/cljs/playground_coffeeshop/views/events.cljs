(ns playground-coffeeshop.views.events
  (:require [re-frame.core :as re-frame]
            [playground-coffeeshop.components.header :refer [header-component]]
            [playground-coffeeshop.components.menu :refer [menu-component]]))

;; about

(defn events-view []
  (fn []
    [:div
     [header-component]
     [:div.flex-row
       [menu-component]
       [:div.content
        [:div "Events:"
              [:p "Upcoming events" [:br]
                  "- Title" [:br]
                  "- Date/time" [:br]
                  "- Description" [:br]
                  "- Image or flier"]
              [:p "Past events"]]]]]))
