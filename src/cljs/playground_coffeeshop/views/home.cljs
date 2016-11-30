(ns playground-coffeeshop.views.home
  (:require [cljsjs.jquery :as $]
            [re-frame.core :as re-frame]
            [reagent.core :as reagent]))

(defn home-view []
  (let [slide-show-images (re-frame/subscribe [:on-slide-show-images])]
    (reagent/create-class
      {:component-did-mount
       #(.carousel (js/$ ".carousel"))
       :component-will-mount
       #(when (empty? @slide-show-images)
         (re-frame/dispatch [:get-site-cms-data]))
       :reagent-render
       (fn []
         [:div.carousel.slide {:data-ride "carousel"}
          [:div.carousel-inner
           (let [first-image (first @slide-show-images)]
             (for [src @slide-show-images
                   :let [id (gensym "key-")
                         is-first? (= first-image src)]]
               ^{:key id}
               [:div.item {:class (when is-first? "active")}
                [:img {:src src, :alt "playground coffeeshop"}]]))]])})))
