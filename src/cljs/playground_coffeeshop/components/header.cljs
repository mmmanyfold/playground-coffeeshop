(ns playground-coffeeshop.components.header
  (:require [reagent.core :as reagent]))

; module aliases
(def Engine (.-Engine js/Matter))
(def Render (.-Render js/Matter))
(def World (.-World js/Matter))
(def Bodies (.-Bodies js/Matter))
(def Composites (.-Composites js/Matter))
(def Constraint (.-Constraint js/Matter))

; constants
(defonce WIDTH 400)
(defonce HEIGHT 200)
(defonce HW (/ WIDTH 2))
(defonce HH (/ WIDTH 2))
(defonce COLOR
         {:render {:fillStyle   "transparent"
                   :strokeStyle "black"
                   :lineWidth   1}})

; create an engine
(def engine (.create Engine))

; create object primitives
;;;;;;;;;;;;;;;;;;;;;;;;;;;; x, y, w, h, opts

(def stack (.stack Composites 15 0 12 10 0 0
                   (fn [x y]
                     (.rectangle Bodies x y 10 10 (clj->js COLOR)))))

(def projectile (.circle Bodies (+ HW 75) 0 50 (clj->js COLOR)))

(def catapult (.rectangle Bodies HW (- HH 45) 200 10 (clj->js COLOR)))

(def counter-balance (.rectangle Bodies (- HW 100) (- HH 30)
                          20 20 (clj->js (assoc COLOR
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
                                                  :options #js {:wireframes false
                                                                :background "transparent"
                                                                :width      WIDTH
                                                                :height     HEIGHT}})]

                         ; add all of the bodies to the world
                         (.add World (.-world engine)
                               #js [stack
                                    catapult
                                    counter-balance
                                    projectile
                                    (.create Constraint (clj->js {:bodyA catapult :pointB {:x (- HW 20) :y (+ HH 10)}}))
                                    (.create Constraint (clj->js {:bodyA catapult :pointB {:x (+ HW 20) :y (+ HH 10)}}))])

                         ; run the engine
                         (.run Engine engine)
                         ; run the renderer
                         (.run Render render)))

       :display-name "header-component"

       :reagent-render
                     (fn [data]
                       [:div.header.flex-row
                        [:div.left.flex-col
                         [:a {:href "/"}
                          [:img.logo {:src "img/header_logo.png"
                                      :width "80%"}]]]
                        [:div.right
                         [:div
                          [:img.addy {:src "img/header_addy.png"}]]
                         [:div#canvas]]
                        ;  [:div
                        ;   [:img.phone {:src "img/header_phone.png"}]]]
                        (if (:error data)
                          [:span (:status-text (:error data))]
                          [:img {:src data}])])})))
