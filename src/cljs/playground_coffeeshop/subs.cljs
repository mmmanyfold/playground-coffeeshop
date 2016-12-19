(ns playground-coffeeshop.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]))

(re-frame/register-sub
  :cms-events
  (fn [db]
    (reaction (:cms-events @db))))

(re-frame/register-sub
  :filtered-events
  (fn [db]
    (reaction (:filtered-events @db))))

(re-frame/register-sub
 :active-view
 (fn [db _]
   (reaction (:active-view @db))))

(re-frame/register-sub
  :on-mailer-process-event
  (fn [db _]
    (reaction (:on-mailer-process-event @db))))

(re-frame/register-sub
  :on-event-details-render
  (fn [db _]
    (reaction (:on-event-details-render @db))))

(re-frame/register-sub
  :on-about-entry-render
  (fn [db _]
    (reaction (:on-about-entry-render @db))))

(re-frame/register-sub
  :on-consignment-entry-render
  (fn [db _]
    (reaction (:on-consignment-entry-render @db))))

(re-frame/register-sub
  :on-menus-entry-render
  (fn [db _]
    (reaction (:on-menus-entry-render @db))))

(re-frame/register-sub
  :on-slide-show-images
  (fn [db _]
    (reaction (:on-slide-show-images @db))))

(re-frame/register-sub
  :on-news-entries-received
  (fn [db _]
    (reaction (:on-news-entries-received @db))))

(re-frame/register-sub
  :on-news-entries-total-received
  (fn [db _]
    (reaction (:on-news-entries-total-received @db))))

(re-frame/register-sub
  :on-news-entries-loading
  (fn [db _]
    (reaction (:on-news-entries-loading @db))))
