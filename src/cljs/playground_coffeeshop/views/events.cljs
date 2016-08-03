(ns playground-coffeeshop.views.events
  (:require [re-frame.core :as re-frame]
            [playground-coffeeshop.components.header :refer [header-component]]
            [playground-coffeeshop.components.menu :refer [menu-component]]))

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
           (for [item (doall (:items @cms-data))
                 :let [id (get-in item [:sys :id])
                       _ (prn id)
                       title (get-in item [:fields :title])
                       start (get-in item [:fields :start])
                       end (get-in item [:fields :end])
                       cost (get-in item [:fields :cost])
                       details (get-in item [:fields :description])]]
             ^{:key id}[:h3 title])]]]])))
