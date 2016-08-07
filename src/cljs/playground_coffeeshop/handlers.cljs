(ns playground-coffeeshop.handlers
  (:require [re-frame.core :as re-frame]
            [playground-coffeeshop.db :as db]
            [ajax.core :refer [GET]]
            [cljsjs.moment]))

(defonce events-space "vwupty4rcx24")
(defonce cdn-token "bfcc4593881abafaed07ce4b74f384cf82bf693a300fb1a4c0bffc05d6bfdaa9")

(re-frame/register-handler
  :initialize-db
  (fn [_ _]
    db/default-db))

(re-frame/register-handler
  :get-cms-data
  (fn [db _]
    (GET (str "https://cdn.contentful.com/spaces/" events-space "/entries?access_token=" cdn-token)
         {:response-format :json
          :keywords?       true
          :handler         #(re-frame/dispatch [:process-response %1])
          :error-handler   #(re-frame/dispatch [:bad-response %1])})
    db))

(re-frame/register-handler
  :process-response
  (fn
    [db [_ response]]
    ;; normalize data
    ;; merge :includes :assets with :event :item data
    (let [items (:items response)
          assets (get-in response [:includes :Asset])
          items_mod (into [] (map-indexed (fn [i k] (let [item-id (get-in k [:fields :image :sys :id])
                                                          img (filterv #(= (get-in % [:sys :id]) item-id) assets)
                                                          url (get-in (first img) [:fields :file :url])]
                                                      (assoc (:fields k)
                                                        :img-src url))) items))
          _response (assoc response :items items_mod)]
      (-> db
          (assoc :cms-events _response)))))

(re-frame/register-handler
  :bad-response
  (fn
    [db [_ response]]
    (-> db
        (assoc :cms-events response))))

(re-frame/register-handler
  :set-active-view
  (fn [db [_ active-view]]
    (assoc db :active-view active-view)))

(re-frame/register-handler
  :display-filtered-events
  (fn
    [db [_ op]]
    (let [items (get-in db [:cms-events :items])]
      (let [nowish (.unix (js/moment))
            custom-events (filterv
                            (fn [e]
                              (let [start (:start e)
                                    unix-start (.unix (js/moment start))]
                                (if-not op
                                  db
                                  (apply op [unix-start nowish]))))
                            items)]
        (-> db
            (assoc :filtered-events custom-events))))))