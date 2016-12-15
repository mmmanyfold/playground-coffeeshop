(ns playground-coffeeshop.views.news
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            [playground-coffeeshop.components.news-article :refer [article]]))

(defn news-view []
  (let [news (re-frame/subscribe [:on-news-entries-received])]
    (reagent/create-class
      {:component-will-mount
       (fn []
         (when (empty? @news)
           (re-frame/dispatch [:get-news-cms-data])))
       :reagent-render
       (fn []
         (when @news
           (let [articles @news]
             [:div
              [:h2 "News"]
              [:hr]
              (for [a articles
                    :let [createdAt (get-in a [:sys :createdAt])
                          title (get-in a [:fields :title])
                          author (get-in a [:fields :author])
                          body (get-in a [:fields :body])]]
                ^{:key (gensym "article-")}
                [article {:createdAt createdAt
                          :title     title
                          :author    author
                          :body      body}])
              [:div.footer
               [:a {:on-click #(re-frame/dispatch [:get-news-cms-data 5])}
                [:i.fa.fa-angle-double-right.fa-3 "next-5"]]]])))})))
