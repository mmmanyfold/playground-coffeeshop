(ns playground-coffeeshop.components.footer
  (:require [re-frame.core :as re-frame]))

(defn footer-component []
  (fn []
    [:div.footer.flex-row
      [:div.info
        [:b "Playground Coffee Shop"] [:br]
        "1114 Bedford Ave." [:br]
        "Brooklyn, NY 11216" [:br]
        "(718) 484-4833"]
      [:div.flex-row
        [:div.subscribe
          [:b "Mailing List"]
          [:div#mc_embed_signup
           [:form#mc-embedded-subscribe-form.validate
            {:novalidate "novalidate",
             :target "_blank",
             :name "mc-embedded-subscribe-form",
             :method "post",
             :action
             ""}
            [:div#mc_embed_signup_scroll
             [:div.mc-field-group
              [:input#mce-EMAIL.required.email
               {:name "EMAIL",
                :value "",
                :type "email"}]]
             [:div#mce-responses.clear
              [:div#mce-error-response.response]
              [:div#mce-success-response.response]]
             [:div.mc-hidden
              {:aria-hidden "true"}
              [:input
               {:value "",
                :tabindex "-1",
                :name "b_68464bd8133a7fa7bfd024a26_c54dec6e9d",
                :type "text"}]]
             [:div.clear
              [:input#mc-embedded-subscribe.button
               {:name "subscribe",
                :value "> Subscribe",
                :type "submit"}]]]]]]
        [:div.social
          [:div.socialbtns
            [:ul
              [:li
               [:a.fa.fa-sm.fa-facebook
                {:href "https://www.facebook.com/playgroundcoffeeshopbk/"}]]
              [:li
               [:a.fa.fa-sm.fa-instagram
                {:href "https://www.instagram.com/playgroundcoffeeshop/"}]]]]]]]))
