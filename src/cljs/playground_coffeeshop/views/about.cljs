(ns playground-coffeeshop.views.about
  (:require [re-frame.core :as re-frame]
            [cljsjs.marked]
            [reagent.core :as reagent]))

(defn about-view []
  (let [about (re-frame/subscribe [:on-about-entry-render])]
    (reagent/create-class
      {:component-will-mount
       (fn []
         (when (empty? @about)
           (re-frame/dispatch [:get-cms-data])))
       :reagent-render
       (fn []
         (let [{:keys [title description]} (:fields (first @about))]
           [:div
            [:h4 title]
            [:p description]]))})))
