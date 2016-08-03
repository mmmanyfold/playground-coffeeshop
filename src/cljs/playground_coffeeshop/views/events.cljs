(ns playground-coffeeshop.views.events
  (:require [re-frame.core :as re-frame]
            [playground-coffeeshop.components.header :refer [header-component]]
            [playground-coffeeshop.components.menu :refer [menu-component]]
            [playground-coffeeshop.components.event :refer [event-component]]))

;; about

(defn events-view []
  (let [cms-data (re-frame/subscribe [:cms-data])
        _ (re-frame/dispatch [:get-cms-data])]
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
          [:p "Past events"]]
         [:div
          (for [item (:items @cms-data)]
            ^{:key (gensym "event-")}
            [event-component item])]]]])))
