(ns playground-coffeeshop.db)

(def default-db
  {:cms-events []
   :filtered-events []
   :on-mailer-process-event nil
   :details-render-id nil
   :on-event-details-render nil
   :on-about-entry-render nil
   :on-menus-entry-render nil
   :on-slide-show-images nil
   :on-consignment-entry-render nil
   :on-news-entries-received nil
   :on-news-entries-total-received 0
   :on-news-entries-loading false})
