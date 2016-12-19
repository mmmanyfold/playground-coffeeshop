(ns playground-coffeeshop.components.news-article
  (:require [cljsjs.marked]
            [cljsjs.moment]))

(defn article
  "Renders one of articles"
  [{:keys [author title body createdAt]}]
  (let [formatted-date (.format (js/moment createdAt) "MMM D, YYYY")]
    [:article
     [:h3 title]
     [:small (str "Posted by " author " on " formatted-date)]
     [:div.article-body {"dangerouslySetInnerHTML"
                         #js{:__html (js/marked body)}}]
     [:hr]]))
