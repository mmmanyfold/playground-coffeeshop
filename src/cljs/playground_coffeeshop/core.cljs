(ns playground-coffeeshop.core
    (:require [reagent.core :as reagent]
              [re-frame.core :as re-frame]
              [devtools.core :as devtools]
              [playground-coffeeshop.handlers]
              [playground-coffeeshop.subs]
              [playground-coffeeshop.routes :as routes]
              [playground-coffeeshop.views :as views]
              [playground-coffeeshop.config :as config]))


(defn dev-setup []
  (when config/debug?
    (println "dev mode")
    (devtools/install!)))

(defn mount-root []
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (routes/app-routes)
  (re-frame/dispatch-sync [:initialize-db])
  (dev-setup)
  (mount-root))
