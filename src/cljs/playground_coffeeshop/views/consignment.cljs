(ns playground-coffeeshop.views.consignment
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            [cljsjs.marked]))

(defn consignment-view []
  (let [consignment (re-frame/subscribe [:on-consignment-entry-render])]
    (reagent/create-class
      {:component-will-mount
       (fn []
         (when (empty? @consignment)
           (re-frame/dispatch [:get-site-cms-data])))
       :reagent-render
       (fn []
         (let [title (get-in @consignment [:fields :title])
               details (get-in @consignment [:fields :details])
               link (@consignment :consignment-asset)]
           [:div
            [:h3 title]

            [:div {"dangerouslySetInnerHTML"
                   #js{:__html (js/marked details)}}]

            [:p {:style {:margin-top "27px"}}
              [:a.button-link {:href link :target "_blank"}
                [:b "Download Form"]]]]))})))
