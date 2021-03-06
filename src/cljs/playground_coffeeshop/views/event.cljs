(ns playground-coffeeshop.views.event
  (:require [re-frame.core :as re-frame]
            [cljsjs.moment]
            [reagent.core :as reagent]
            [clojure.string :as str]
            [cljsjs.marked]))

(defn event-view []
  (let [event (re-frame/subscribe [:on-event-details-render])]

    (reagent/create-class

      {:component-will-mount
       (fn []
         (let [id (.replace (.-pathname js/location) "/" "")]
           (when (empty? @event)
             (do
               (re-frame/dispatch [:register-event-details-id id])
               (re-frame/dispatch [:get-event-cms-data])))))

       :reagent-render
       (fn []
         (if @event
           (let [{:keys [title start end img-src cost description photo-urls]} @event
                 formatted-start-date (.format (js/moment start)
                                               (if (= (first (str/split start #"T")) (first (str/split end #"T")))
                                                 "MMM D, YYYY" "MMM D"))
                 formatted-end-date (.format (js/moment end) "MMM D, YYYY")
                 formatted-start-time (.format (js/moment start) "LT")
                 formatted-end-time (.format (js/moment end) "LT")]
             (fn []
               [:div.event-details
                [:h3 title]
                [:div
                 (if (= formatted-start-date formatted-end-date)
                   formatted-start-date
                   [:span formatted-start-date " – " formatted-end-date])
                 [:br]
                 formatted-start-time " – " formatted-end-time [:br]
                 cost [:br][:br]]
                [:img {:src img-src}]

                [:div {"dangerouslySetInnerHTML"
                       #js{:__html (js/marked description)}}]

                [:div.flex-row-wrap
                  (map (fn [url]
                        ^{:key (gensym "event-")}
                        [:div.event-photo
                          [:img {:src url}]])
                       photo-urls)]]))
           [:div "loading..."]))})))
