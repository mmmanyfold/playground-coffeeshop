(ns playground-coffeeshop.components.event-thumb
  (:require cljsjs.moment))

(defn event-thumb-component [data]
  (let [{:keys [title start end img-src cost description]} data
        formatted-start-date (.format (js/moment start) "MMM D, YYYY")
        formatted-end-date (.format (js/moment end) "MMM D, YYYY")
        formatted-start-time (.format (js/moment start) "LT")
        formatted-end-time (.format (js/moment end) "LT")]

    [:div.event-thumb
     [:img {:src   img-src
            :width "100%"}]
     [:h3 title]
     [:div
      formatted-start-date " – " formatted-end-date [:br]
      formatted-start-time " – " formatted-end-time [:br]
      cost [:br]]
     [:hr]]))
