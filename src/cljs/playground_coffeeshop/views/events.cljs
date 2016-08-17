(ns playground-coffeeshop.views.events
  (:require [re-frame.core :as re-frame]
            [playground-coffeeshop.components.event_thumb :refer [event-thumb-component]]))

(defn events-view []
  (let [_ (re-frame/dispatch [:get-cms-data])
        events (re-frame/subscribe [:filtered-events])]
    (fn []
      [:div.events
       [:h2 "Events:"]
       [:div.flex-row-wrap
         (for [item @events
               :let [id  (get-in item [:image :sys :id])]]
           ^{:key (gensym "event-")}
           [:a.event-link {:href (str "/event/" id)}
            [event-thumb-component item]])]])))
