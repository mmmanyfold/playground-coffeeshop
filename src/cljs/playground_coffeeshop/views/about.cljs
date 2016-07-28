(ns playground-coffeeshop.views.about)

;; about

(defn about-view []
  (fn []
    [:div "This is the About Page."
     [:div [:a {:href "/events"} "go to Events Page"]]]))