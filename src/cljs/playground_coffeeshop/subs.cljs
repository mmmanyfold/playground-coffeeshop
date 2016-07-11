(ns playground-coffeeshop.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]))

(re-frame/register-sub
 :name
 (fn [db]
   (reaction (:name @db)))

 (re-frame/register-sub
  :cms-data
  (fn [db]
    (reaction (:cms-data @db)))))

(re-frame/register-sub
 :active-view
 (fn [db _]
   (reaction (:active-view @db))))
