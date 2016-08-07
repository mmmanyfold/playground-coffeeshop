(ns playground-coffeeshop.views.events
  (:require [re-frame.core :as re-frame]
            [playground-coffeeshop.components.event :refer [event-component]]))

(defn events-view []
  (let [_ (re-frame/dispatch [:get-cms-data])
        events (re-frame/subscribe [:filtered-events])]
    (fn []
      [:div.events
       [:div "Events:"
        [:p "Upcoming events" [:br]
         "- Title" [:br]
         "- Date & time" [:br]
         "- Description" [:br]
         "- Image or flier"]
        [:p "Past events"]]
       [:div
        (for [item @events]
          ^{:key (gensym "event-")}
          [event-component item])]])))
