(ns playground-coffeeshop.components.event-thumb
  (:require cljsjs.moment))

(defn event-thumb-component [data]
  (let [{:keys [title start end img-src cost description]} data
        formatted-start (.format (js/moment start) "LLLL")
        formatted-end (.format (js/moment end) "LLLL")]

    [:div.event-thumb
     [:img {:src   img-src
            :width "100%"}]
     [:h3 title]
     [:div
      formatted-start [:br]
      formatted-end [:br]
      cost [:br]]
     [:hr]]))
