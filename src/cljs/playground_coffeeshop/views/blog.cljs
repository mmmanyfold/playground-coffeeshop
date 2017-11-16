(ns playground-coffeeshop.views.blog
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            [playground-coffeeshop.components.news-article :refer [article]]))

(defn articles-wrapper [articles]
      (if articles
        [:div
         (for [a articles
               :let [createdAt (get-in a [:sys :createdAt])
                     title (get-in a [:fields :title])
                     author (get-in a [:fields :author])
                     body (get-in a [:fields :body])]]
              ^{:key (gensym "article-")}
              [article {:createdAt createdAt
                        :title     title
                        :author    author
                        :body      body}])]
        [:h3 "Please check back soon!"]))

(defn blog-view []
      (let [news (re-frame/subscribe [:on-news-entries-received])
            total (re-frame/subscribe [:on-news-entries-total-received])
            loading? (re-frame/subscribe [:on-news-entries-loading])]
           (reagent/create-class
             {:component-will-mount
              #(when (empty? @news)
                     (re-frame/dispatch [:get-news-cms-data]))
              :reagent-render
              (fn []
                  [:div.news-wrapper
                   [:h2 "Blog"]
                   [:hr]
                   [:div
                    [articles-wrapper @news]
                    [:div.footer
                     (when (> @total 5)
                           [:button {:disabled (if @loading? true false)
                                     :on-click #(re-frame/dispatch [:get-news-cms-data 5])}
                            [:i.fa.fa-angle-double-down.fa-5]])]]])})))
