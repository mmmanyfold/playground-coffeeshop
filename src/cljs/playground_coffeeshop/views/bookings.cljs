(ns playground-coffeeshop.views.bookings
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [reagent-forms.core :refer [bind-fields]]
            [playground-coffeeshop.components.alert :refer [alert-component]]))

(defonce msg-init-state
         {:alert {:display false
                  :message nil
                  :title   nil}})

(def msg-state (reagent/atom msg-init-state))

(defonce form-init-state
         {:how-many-ppl nil
          :name         nil
          :event-type   nil
          :email        nil
          :date         nil
          :time         nil})

(def form-state (reagent/atom form-init-state))

(def form-template
  [:form.pure-form
   [:h4 "Contact us today and get reply with in 24 hours!"]
   [:fieldset
    [:input {:field    :text :id :name :placeholder "Your name" :type "text"
             :tabIndex "1" :required true :autofocus true}]]
   [:fieldset
    [:input {:field       :email :id :email
             :placeholder "Your email" :type "email" :tabIndex "2"
             :required    true}]]
   [:fieldset
    [:input {:field :numeric :id :how-many-ppl
             :type  "number" :tabIndex "3" :required true :placeholder "How many people?"}]]
   [:fieldset
    [:input {:field       :text :id :event-type
             :placeholder "Event Type" :type "text" :tabIndex "4" :required true}]]
   [:fieldset
    [:textarea {:field       :datepicker :id :date
                :date-format "yyyy/mm/dd" :inline true :placeholder "Date"
                :type        "datetime" :tabIndex "5" :required true}]]
   [:fieldset
    [:input {:field       :text :id :time
             :placeholder "Time: 12 pm" :type "text" :tabIndex "6" :required true}]]])


(defn alert-update! [type title msg]
  (swap! msg-state assoc-in [:alert] {:display true
                                      :type    type
                                      :message msg
                                      :title   title}))

;; bookings

(defn bookings-view []
  (let [mailer-process-event (re-frame/subscribe [:on-mailer-process-event])]
    (fn []
      (if @mailer-process-event
        (case @mailer-process-event
          :success (do
                     (alert-update! :success "Success!" "Thank you! Allow 24 hours for a full response.")
                     (reset! form-state form-init-state)
                     (re-frame/dispatch [:reset-mailer-process-event])
                     (js/window.scrollTo 0 0))
          :error (do
                   (alert-update! :error "Error" "Please check the form fields.")
                   (re-frame/dispatch [:reset-mailer-process-event])
                   (js/window.scrollTo 0 0))
          "default"))
      [:div
       [alert-component msg-state 5000]
       [:p "The space can be converted into a gallery, party, performance venue, etc."]
       [:p "Prices contingent upon the idea."]
       [bind-fields form-template form-state]
       [:button.pure-button.pure-button-primary
        {:name     "submit" :type "button"
         :disabled (true? (some nil? (vals @form-state)))
         :on-click #(re-frame/dispatch [:post-email @form-state])} "Submit"]])))
