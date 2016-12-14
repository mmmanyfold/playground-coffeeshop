(ns playground-coffeeshop.components.news-article
  (:require [cljsjs.marked]))

(defn article
  "Renders one of articles"
  [{:keys [author title body createdAt]}]
  [:article
   [:h3 title]
   [:small (str "Posted by " author " on " createdAt)]
   [:div.article-body {"dangerouslySetInnerHTML"
                       #js{:__html (js/marked body)}}]
   [:hr]])
