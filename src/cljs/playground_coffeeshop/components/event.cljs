(ns playground-coffeeshop.components.event)

(defn event-component [data]
  (let [title (get-in data [:title])
        start (get-in data [:start])
        end (get-in data [:end])
        img-src (get-in data [:img-src])
        cost (get-in data [:cost])
        details (get-in data [:description])]
    [:div
     [:h3 title]
     [:div
      start [:br]
      end [:br]
      cost [:br]
      [:small details] [:br]]
     [:img {:src   img-src
            :width "100%"}]
     [:hr]]))