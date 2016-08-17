(ns playground-coffeeshop.views.event
  (:require [re-frame.core :as re-frame]
            [cljsjs.moment]
            [clojure.string :as str]
            [cljsjs.marked]))

(defn event-view []
  (let [events (re-frame/subscribe [:on-event-details-render])
        {:keys [title start end img-src cost description]} @events
        formatted-start-date (.format (js/moment start)
                                      (if (= (first (str/split start #"T")) (first (str/split end #"T")))
                                          "MMM D, YYYY" "MMM D"))
        formatted-end-date (.format (js/moment end) "MMM D, YYYY")
        formatted-start-time (.format (js/moment start) "LT")
        formatted-end-time (.format (js/moment end) "LT")]
    [:div.event-details
     [:h3 title]
     [:img {:src   img-src
            :width "100%"}]
     [:div {"dangerouslySetInnerHTML"
            #js{:__html (js/marked description)}}]
     [:div
      (if (= formatted-start-date formatted-end-date)
        formatted-start-date
        [:span formatted-start-date " – " formatted-end-date])
      [:br]
      formatted-start-time " – " formatted-end-time [:br]
      cost [:br]]
     [:hr]]))
