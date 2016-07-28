(ns playground-coffeeshop.views.events)

(defn events-view []
  (fn []
    [:div "This is the Events Page."
     [:div [:a {:href "/"} "go to Home Page"]]]))