(ns playground-coffeeshop.views.consignment
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]))

(defn consignment-view []
  (let [consignment (re-frame/subscribe [:on-consignment-entry-render])]
    (reagent/create-class
      {:component-will-mount
       (fn []
         (when (empty? @consignment)
           (re-frame/dispatch [:get-site-cms-data])))
       :reagent-render
       (fn []
         (let [link (first @consignment)]
           [:div
            [:h3 "Sell with us!"]
            [:p "We are accepting zines, cassettes, vinyl records, vhs tapes, cds, prints, etc for consignment."]
            [:p "Please send completed consignment form to "
             [:a {:href "mailto:general@playgroundcoffeeshop.com"} "general@playgroundcoffeeshop.com"]
             " along with images of your work."]
            [:br]
            [:a.button-link {:href link :target "_blank"}
             [:b "Download Form"]]]))})))
