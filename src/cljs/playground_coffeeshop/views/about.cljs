(ns playground-coffeeshop.views.about
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]))

(defn- generic-responsive-iframe
  "returns an responsive iframe"
  [iframe-code]
  (.replace iframe-code (js/RegExp. "/\\\"/g,'\\''")))

(defn about-view []
  (let [about (re-frame/subscribe [:on-about-entry-render])]
    (reagent/create-class
      {:component-will-mount
       (fn []
         (when (empty? @about)
           (re-frame/dispatch [:get-site-cms-data])))
       :reagent-render
       (fn []
         (let [{:keys [title description video]} (:fields @about)]
           [:div
            [:div.embed-container
             {"dangerouslySetInnerHTML"
              #js{:__html (generic-responsive-iframe video)}}]
            [:p description]]))})))
