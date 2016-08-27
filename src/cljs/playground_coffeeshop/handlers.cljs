(ns playground-coffeeshop.handlers
  (:require [re-frame.core :as re-frame]
            [secretary.core :as secretary]
            [playground-coffeeshop.db :as db]
            [ajax.core :refer [GET POST]]
            [cljsjs.moment]
            [json-html.core :refer [edn->html]]))

(defonce events-space "vwupty4rcx24")
(defonce cdn-token "bfcc4593881abafaed07ce4b74f384cf82bf693a300fb1a4c0bffc05d6bfdaa9")

(defn- filter-items [comparador type items]
  "
  returns a vector of filter items base on its comparador(~comparator).
  --
  comparador: = !=
  type: about event etc
  items: collection of items from which to filter"
  (filterv #(comparador type
               (-> %
                   (get-in [:sys :contentType :sys :id]))) items))

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
    (let [event-items (filter-items = "event" (:items response))
          about-items (filter-items = "about" (:items response))
          assets (get-in response [:includes :Asset])
          items_mod (mapv (fn [k] (let [item-id (get-in k [:fields :image :sys :id])
                                        photos (get-in k [:fields :photos])
                                        photo-ids (mapv #(get-in % [:sys :id]) photos)

                                        img (some #(when
                                                    (= (get-in % [:sys :id]) item-id)
                                                    %)
                                                  assets)

                                        photo-urls (mapv (fn [id]
                                                           (some #(when (= id (get-in % [:sys :id]))
                                                                   (get-in % [:fields :file :url]))
                                                                 assets)) photo-ids)

                                        url (get-in img [:fields :file :url])]
                                    (assoc (:fields k)
                                      :img-src url :photo-urls photo-urls))) event-items)
          _response (assoc response :items items_mod)]
      (re-frame/dispatch [:set-about-entry about-items])
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
  :set-about-entry
  (fn
    [db [_ about-entry]]
    (-> db
        (assoc :on-about-entry-render about-entry))))

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
        (when-let [id (:details-render-id db)]
          (js/setTimeout #(re-frame/dispatch [:render-event-details id]) 1000))
        (-> db
            (assoc :filtered-events custom-events))))))

(re-frame/register-handler
  :render-event-details
  (fn [db [_ id]]
    (if-not (empty? (:filtered-events db))
      (let [filtered-events (:filtered-events db)
            ids (mapv #(get-in % [:image :sys :id]) filtered-events)
            match (some #(when (= id %) %) ids)
            match-event (some #(when (= match (get-in % [:image :sys :id])) %) (:filtered-events db))]
        (assoc db :on-event-details-render match-event))
      db)))

(re-frame/register-handler
  :register-event-details-id
  (fn [db [_ id]]
    (-> db
        (assoc :details-render-id id))))
