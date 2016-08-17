(ns playground-coffeeshop.handlers
  (:require [re-frame.core :as re-frame]
            [secretary.core :as secretary]
            [playground-coffeeshop.db :as db]
            [ajax.core :refer [GET POST]]
            [cljsjs.moment]
            [json-html.core :refer [edn->html]]))

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
          :handler         #(re-frame/dispatch [:process-contentful-ok-response %1])
          :error-handler   #(re-frame/dispatch [:process-contenful-error-response %1])})
    db))

(re-frame/register-handler
  :post-email
  (fn [db [_ body]]
    (POST "http://playground-coffeeshop-mailer.apps.aterial.org/api/mail"
          {:response-format :json
           :keywords?       true
           :params          {:from    (:email body)
                             :to      "events@playgroundcoffeeshop.com"
                             :subject "new booking request"
                             :body    (edn->html body)}
           :handler         #(re-frame/dispatch [:process-mailer-ok-response %])
           :error-handler   #(re-frame/dispatch [:process-mailer-error-response %])})
    db))

(re-frame/register-handler
  :process-mailer-ok-response
  (fn
    [db [_ _]]
    (-> db
        (assoc :on-mailer-process-event :success))))

(re-frame/register-handler
  :process-mailer-error-response
  (fn
    [db [_ _]]
    (-> db
        (assoc :on-mailer-process-event :error))))

(re-frame/register-handler
  :reset-mailer-process-event
  (fn
    [db [_ _]]
    (-> db
        (assoc :on-mailer-process-event nil))))

(re-frame/register-handler
  :process-contentful-ok-response
  (fn
    [db [_ response]]
    ;; normalizes data
    ;; merge :includes :assets with :event :item data
    (let [items (:items response)
          assets (get-in response [:includes :Asset])
          items_mod (mapv (fn [k] (let [item-id (get-in k [:fields :image :sys :id])
                                        img (some #(when
                                                    (= (get-in % [:sys :id]) item-id)
                                                    %)
                                                  assets)
                                        url (get-in img [:fields :file :url])]
                                    (assoc (:fields k)
                                      :img-src url))) items)
          _response (assoc response :items items_mod)]
      (when (empty? (:filtered-events db))
        (re-frame/dispatch [:display-filtered-events]))
      (-> db
          (assoc :cms-events _response)))))

(re-frame/register-handler
  :process-contenful-error-response
  (fn
    [db [_ response]]
    (-> db
        (assoc :cms-events response))))

(re-frame/register-handler
  :set-active-view
  (fn [db [_ active-view id]]
    (when (= active-view :event-view)
      (re-frame/dispatch [:render-event-details id]))
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

(re-frame/register-handler
 :render-event-details
 (fn [db [action id]]
   (let [ids (mapv #(get-in % [:image :sys :id]) (:filtered-events db))
         match (some #(when (= id %) %) ids)
         match-event (some #(when (= match (get-in % [:image :sys :id])) %) (:filtered-events db))]
     (assoc db :on-event-details-render match-event))))
