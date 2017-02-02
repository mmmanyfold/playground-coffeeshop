(ns playground-coffeeshop.views.about
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            [cljsjs.jquery :as $]))

(defn about-view []
  (let [about (re-frame/subscribe [:on-about-entry-render])
        slide-show-images (re-frame/subscribe [:on-slide-show-images])]
    (reagent/create-class
      {:component-will-mount
       (fn []
         (when (empty? @about)
           (re-frame/dispatch [:get-site-cms-data])))
       :component-did-mount
       #(.carousel (js/$ ".carousel"))
       :reagent-render
       (fn []
         (let [{:keys [description]} (:fields @about)]
           [:div
            [:div.carousel.slide {:data-ride "carousel"}
             [:div.carousel-inner
              (let [first-image (first @slide-show-images)]
                (for [src @slide-show-images
                      :let [id (gensym "key-")
                            is-first? (= first-image src)]]
                  ^{:key id}
                  [:div.item {:class (when is-first? "active")}
                   [:img {:src src, :alt "Playground Coffee Shop"}]]))]]
            [:p description]]))})))
