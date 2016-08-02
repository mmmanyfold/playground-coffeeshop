(ns playground-coffeeshop.components.header
  (:require [reagent.core :as reagent]))

; module aliases
(def Engine (.-Engine js/Matter))
(def Render (.-Render js/Matter))
(def World (.-World js/Matter))
(def Bodies (.-Bodies js/Matter))

; create an engine
(def engine (.create Engine))

; create two boxes and a ground
(def boxA (.rectangle Bodies 400, 200, 80, 80))
(def boxB (.rectangle Bodies 450, 50, 80, 80))
(def ground (.rectangle Bodies 400, 610, 810, 60, #js {:isStatic true}))

(defn header-component []
  (let []
    (reagent/create-class
      {:component-did-mount
                     (fn []
                       #_(let []; create a renderer
                             render (.create Render #js {:element (.querySelector js/document "#canvas")
                                                         :engine  engine}))


                         ; add all of the bodies to the world
                         (.add World (.-world engine) #js [boxA, boxB, ground])
                         ; run the engine
                         (.run Engine engine)
                         ; run the renderer
                         (.run Render render))


       :display-name "header-component"

       :reagent-render
                     (fn [data]
                       [:div.header.flex-row
                        [:div.left
                         [:img.logo {:src "img/header_logo.png"}]]
                        [:div.right.flex-col
                         [:div
                          [:img.phone {:src "img/header_phone.png"}]]
                         [:div#canvas]
                         [:div
                          [:img.addy {:src "img/header_addy.png"}]]]
                        (if (:error data)
                          [:span (:status-text (:error data))]
                          [:img {:src data}])])})))
