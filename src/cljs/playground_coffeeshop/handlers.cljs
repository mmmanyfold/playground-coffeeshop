(ns playground-coffeeshop.handlers
    (:require [re-frame.core :as re-frame]
              [playground-coffeeshop.db :as db]
              [ajax.core :refer [GET]]))

(defonce events-space "vwupty4rcx24")
(defonce cdn-token "bfcc4593881abafaed07ce4b74f384cf82bf693a300fb1a4c0bffc05d6bfdaa9")

(re-frame/register-handler
 :initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/register-handler
  :get-cms-data
  (fn [db _]
    (GET (str "https://cdn.contentful.com/spaces/" events-space "/entries?access_token=" cdn-token)
     {:response-format :json
      :keywords?       true
      :handler #(re-frame/dispatch [:process-response %1])
      :error-handler #(re-frame/dispatch [:bad-response %1])})
    db))

(re-frame/register-handler
  :process-response
  (fn
   [db [_ response]]
    
   (-> db
       (assoc :cms-data response))))


(re-frame/register-handler
  :bad-response
  (fn
   [db [_ response]]
   (-> db
       (assoc :cms-data response))))

(re-frame/register-handler
 :set-active-view
 (fn [db [_ active-view]]
   (assoc db :active-view active-view)))
