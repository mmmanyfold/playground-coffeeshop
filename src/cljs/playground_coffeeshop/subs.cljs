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
