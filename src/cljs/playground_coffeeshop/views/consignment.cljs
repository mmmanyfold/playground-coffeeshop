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
         (when @consignment
           (let [title (get-in @consignment [:fields :title])
                 details (get-in @consignment [:fields :details])
                 link (@consignment :consignment-asset)
                 gallery (@consignment :consignment-images)]
             [:div
              [:h2 title]
              [:p {:style {:margin "27px 0"}}
               [:a.button-link {:href link :target "_blank"}
                [:b "Download Form"]]]
              [:div {"dangerouslySetInnerHTML"
                     #js{:__html (js/marked details)}}]
              [:p "Below are some of the items that we sell instore:"]
              [:div.flex-row-wrap
               (for [image gallery
                     :let [id (gensym "key-")]]
                 ^{:key id}
                 [:div.consignment-image.text-center
                   [:img {:width "100%"
                          :src (image :image-url)}]
                   [:p (image :image-title)]])]
              [:div.text-center
               [:p "Feel free to contact us if you any have any questions and/or wish to sell your work at Playground."]]])))})))
