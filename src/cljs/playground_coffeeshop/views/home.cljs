(ns playground-coffeeshop.views.home
  (:require [re-frame.core :as re-frame]
            [playground-coffeeshop.components.header :refer [header-component]]
            [playground-coffeeshop.components.menu :refer [menu-component]]))

(defn home-view []
  (let [name (re-frame/subscribe [:name])
        cms-data (re-frame/subscribe [:cms-data])]
    (fn []
      [:div
       [header-component (get-in (first (:Asset (:includes @cms-data))) [:fields :file :url])]
       [:div.flex-row
         [menu-component]
         [:div.content
           [:img.main {:src "img/home1.jpg"}]]]])))
          
          ;  [:button {:on-click #(re-frame/dispatch [:get-cms-data])} "get-data-cms"]]]])))
