(ns playground-coffeeshop.handlers
  (:require [re-frame.core :as re-frame]
            [secretary.core :as secretary]
            [playground-coffeeshop.db :as db]
            [ajax.core :refer [GET POST]]
            [cljsjs.moment]
            [json-html.core :refer [edn->html]]))

(defonce events-space "vwupty4rcx24")
(defonce site-space "3tvj6ug2yrmi")
(defonce news-space "ybsyvzjbcqye")
(defonce event-cdn-token "bfcc4593881abafaed07ce4b74f384cf82bf693a300fb1a4c0bffc05d6bfdaa9")
(defonce site-cdn-token "9e839c4c137d65e026cce9aa3a7f0be8a954055b25a969474cd0bd202bf703c4")
(defonce news-cdn-token "227fb3b8c2ba3127dc03f5ba17e9a5258de77a52b68b8718353e5bc7cae76900")


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

(defn contentful-cdn-query
  ([space token]
   (str "https://cdn.contentful.com/spaces/" space "/entries?access_token=" token))
  ([space token limit & [skip]]
   (str "https://cdn.contentful.com/spaces/" space "/entries?access_token=" token
        "&limit=" limit
        "&skip=" (or skip 0))))

(defn comp-unix-end [a b]
  (let [a-unix (:unix-end-time a)
        b-unix (:unix-end-time b)]
    (if (> a-unix b-unix) true false)))


(re-frame/register-handler
  :initialize-db
  (fn [_ _]
    db/default-db))

(re-frame/register-handler
  :get-event-cms-data
  (fn [db _]
    (GET (contentful-cdn-query events-space event-cdn-token)
         {:response-format :json
          :keywords?       true
          :handler         #(re-frame/dispatch [:process-contentful-events-ok-response %1])
          :error-handler   #(re-frame/dispatch [:process-contenful-error-response %1])})
    db))

(re-frame/register-handler
  :get-site-cms-data
  (fn [db _]
    (GET (contentful-cdn-query site-space site-cdn-token)
         {:response-format :json
          :keywords?       true
          :handler         #(re-frame/dispatch [:process-contentful-site-ok-response %1])
          :error-handler   #(re-frame/dispatch [:process-contenful-error-response %1])})
    db))

(re-frame/register-handler
  :get-news-cms-data
  (fn [db [_ skip]]
    (let [many 5]
      (re-frame/dispatch [:on-news-entries-loading true])
      (GET (contentful-cdn-query news-space news-cdn-token many skip)
           {:response-format :json
            :keywords?       true
            :handler         #(re-frame/dispatch [:process-contentful-news-ok-response %1])
            :error-handler   #(re-frame/dispatch [:process-contenful-error-response %1])}))
    db))

(re-frame/register-handler
  :on-news-entries-loading
  (fn [db [_ loading?]]
    (-> db
        (assoc :on-news-entries-loading loading?))))

(re-frame/register-handler
  :post-email
  (fn [db [_ body]]
    (POST "https://mmmanyfold-api.herokuapp.com/api/mail"
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
  :process-contentful-news-ok-response
  (fn [db [_ response]]
    (re-frame/dispatch [:on-news-entries-loading false])
    (let [items (:items response)
          total (:total response)]
      (if (empty? (:on-news-entries-received db))
        (-> db
            (assoc :on-news-entries-received items
                   :on-news-entries-total-received total))
        (-> db
            (assoc :on-news-entries-received
                   (concat (:on-news-entries-received db) items)))))))

(re-frame/register-handler
  :process-contentful-events-ok-response
  (fn
    [db [_ response]]
    ;; normalizes data
    ;; merge :includes :assets with :event :item data
    (let [event-items (filter-items = "event" (:items response))
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
                                      :unix-end-time (.unix (js/moment (get-in k [:fields :end])))
                                      :img-src (str url "?w=600")
                                      :photo-urls photo-urls))) event-items)
          sorted-items (sort comp-unix-end items_mod)
          _response (assoc response :items sorted-items)]

      (when (empty? (:filtered-events db))
        (re-frame/dispatch [:display-filtered-events]))
      (-> db
          (assoc :cms-events _response)))))

(re-frame/register-handler
  :process-contenful-error-response
  (fn
    [db [_ response]]
    (re-frame/dispatch [:on-news-entries-loading false])
    (-> db
        (assoc :cms-events response))))

(re-frame/register-handler
  :process-contentful-site-ok-response
  (fn [db [_ response]]
    (let [response-items (:items response)

          about-item (-> (filter-items = "about" response-items)
                         first)

          menu-items (filter-items = "menu" response-items)

          consignment-item (-> (filter-items = "consignment" response-items)
                               first)

          slide-show-items (-> (filter-items = "slideShow" response-items)
                               first
                               :fields
                               :images)

          consignment-pdf-asset-id (get-in consignment-item [:fields :pdf :sys :id])

          consignment-gallery-items (get-in consignment-item [:fields :imageGallery])

          consignment-gallery-image-ids (->> consignment-gallery-items
                                             (map :sys)
                                             (map :id))

          slide-show-image-ids (->> slide-show-items
                                    (map :sys)
                                    (map :id))

          assets (get-in response [:includes :Asset])

          match-slide-show-assets (mapv (fn [id]
                                          (some #(when (= id (get-in % [:sys :id]))
                                                   (get-in % [:fields :file :url])) assets))
                                        slide-show-image-ids)

          match-consignment-assets (some #(when (= consignment-pdf-asset-id (get-in % [:sys :id]))
                                            (get-in % [:fields :file :url])) assets)

          match-consignment-images (mapv (fn [id]
                                           (some #(when (= id (get-in % [:sys :id]))
                                                    (hash-map
                                                      :image-title (get-in % [:fields :title])
                                                      :image-url (get-in % [:fields :file :url]))) assets))
                                         consignment-gallery-image-ids)

          final-consigment-data (assoc consignment-item
                                  :consignment-asset match-consignment-assets
                                  :consignment-images match-consignment-images)]

      (-> db
          (assoc :on-slide-show-images match-slide-show-assets
                 :on-about-entry-render about-item
                 :on-consignment-entry-render final-consigment-data)))))

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
                              (let [end (:end e)
                                    unix-end (.unix (js/moment end))]
                                (if-not op
                                  db
                                  (apply op [unix-end nowish]))))
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
