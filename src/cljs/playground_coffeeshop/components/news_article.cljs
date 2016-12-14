(ns playground-coffeeshop.components.news-article)

(defn article
  "Renders one of articles"
  [{:keys [author title body createdAt]}]
  [:div
   [:h3 title]
   [:h3 author]
   [:h3 createdAt]
   [:div.content body]])
