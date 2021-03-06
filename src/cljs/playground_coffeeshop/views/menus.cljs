(ns playground-coffeeshop.views.menus
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]))

(defn menus-view []
  (let [menus (re-frame/subscribe [:on-menus-entry-render])]
    (reagent/create-class
      {:component-will-mount
       (fn []
         (when (empty? @menus)
           (re-frame/dispatch [:get-site-cms-data])))
       :reagent-render
       (fn []
         [:div
          [:h3 "Menus"]
          (for [m @menus
                :let [title (first m)
                      link (second m)]]
            ^{:key (gensym "event-")}
            [:p {:style {:margin-bottom "27px"}} [:a.button-link {:href link :target "_blank"}
                                                  [:b title]]])])})))
