(ns playground-coffeeshop.views.events
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            [playground-coffeeshop.components.event_thumb :refer [event-thumb-component]]))

(defn events-view []
  (let [filtered-events (re-frame/subscribe [:filtered-events])]

    (reagent/create-class

      {:component-will-mount
       (fn []
         (when (empty? @filtered-events)
           (re-frame/dispatch [:get-event-cms-data])))

       :reagent-render
       (fn []
         [:div.filtered-events
          [:h2 "Events"]
          [:div.flex-row-wrap
           (if (not (empty? @filtered-events))
             (for [item @filtered-events
                   :let [id (get-in item [:image :sys :id])]]
               ^{:key (gensym "event-")}
               [:a.event-link {:href id}
                [event-thumb-component item]])
             "No upcoming events / check back soon.")]])})))
