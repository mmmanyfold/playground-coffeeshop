(ns playground-coffeeshop.views.home
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]))

(defn home-view []
  (let [slideshow (re-frame/subscribe [:on-slide-show-entry-render])]
    (reagent/create-class
      {:component-will-mount
       (fn []
         (when (empty? @slideshow)
           (re-frame/dispatch [:get-site-cms-data])))
       :reagent-render
       (fn []
         [:div
          (str @slideshow)
          [:img.main {:src "img/home1.jpg"}]]
         )})))

