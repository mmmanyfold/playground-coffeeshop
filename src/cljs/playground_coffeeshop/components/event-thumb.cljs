(ns playground-coffeeshop.components.event-thumb)

(defn event-thumb-component [data]
  (let [title (get-in data [:title])
        start (get-in data [:start])
        end (get-in data [:end])
        img-src (get-in data [:img-src])
        cost (get-in data [:cost])
        details (get-in data [:description])]
    [:div.event-thumb
     [:img {:src   img-src
            :width "100%"}]
     [:h3 title]
     [:div
      start [:br]
      end [:br]
      cost [:br]]
     [:hr]]))