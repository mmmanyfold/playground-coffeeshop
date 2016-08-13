(ns playground-coffeeshop.components.header
  (:require [reagent.core :as reagent]))

; module aliases
(def Engine (.-Engine js/Matter))
(def Render (.-Render js/Matter))
(def World (.-World js/Matter))
(def Bodies (.-Bodies js/Matter))

; constants
(defonce WIDTH 400)
(defonce HEIGHT 200)
(defonce HW (/ WIDTH 2))
(defonce HH (/ WIDTH 2))
(defonce color-const
         {:render {:fillStyle   "black"
                   :strokeStyle "black"
                   :lineWidth   3}})

; create an engine
(def engine (.create Engine))

; create object primitives
;;;;;;;;;;;;;;;;;;;;;;;;;;;; x, y, w, h, opts
(def boxA (.rectangle Bodies (- HW 75) 0 20 20))
(def boxB (.rectangle Bodies (- HW 75) 0 20 20))

(def projectile (.circle Bodies (- HW 75) 0 50))

(def catapult (.rectangle Bodies HW (- HH 100)
                          200 10 (clj->js color-const)))

(def triangle (.rectangle Bodies HW (- HH 25)
                          20 20 (clj->js (assoc color-const
                                           :isStatic true))))

(defn header-component []
  (let []
    (reagent/create-class
      {:component-did-mount
                     (fn []
                       (let [; create a renderer
                             render
                             (.create Render #js {:element (.querySelector js/document "#canvas")
                                                  :engine  engine
                                                  :options #js {:wireframes          false
                                                                :wireframeBackground "transparent"
                                                                :background          "transparent"
                                                                :width               WIDTH
                                                                :height              HEIGHT}})]

                         ; add all of the bodies to the world
                         (.add World (.-world engine)
                               #js [boxA boxB projectile catapult triangle])

                         ; run the engine
                         (.run Engine engine)
                         ; run the renderer
                         (.run Render render)))

       :display-name "header-component"

       :reagent-render
                     (fn [data]
                       [:div.header.flex-row
                        [:div.left
                         [:a {:href "/"}
                          [:img.logo {:src "img/header_logo.png"}]]]
                        [:div.right.flex-col
                         [:div
                          [:img.addy {:src "img/header_addy.png"}]]
                         [:div#canvas]]
                        ;  [:div
                        ;   [:img.phone {:src "img/header_phone.png"}]]]
                        (if (:error data)
                          [:span (:status-text (:error data))]
                          [:img {:src data}])])})))
