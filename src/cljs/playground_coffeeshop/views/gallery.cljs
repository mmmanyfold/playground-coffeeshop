(ns playground-coffeeshop.views.gallery
  (:require [re-frame.core :as re-frame]))

;; gallery

(defn gallery-view []
  [:div "Gallery:"
   [:p "About the gallery space?"]
   [:p "Photos of the space, stage, etc.?"]
   [:p "Rental info and expectations? (+ link to " [:a {:href "/contact"} "contact"] ")"]])
