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
(defonce WIDTH 550)
(defonce HEIGHT 250)
(defonce HW (/ WIDTH 2))
(defonce HH (/ WIDTH 2))
(defonce BLACK-COLOR
         {:render {:fillStyle   "black"
                   :strokeStyle "black"
                   :lineWidth   1}})
(defonce WHITE-COLOR
         {:render {:fillStyle   "transparent"
                   :strokeStyle "black"
                   :lineWidth   1}})

; create an engine
(def engine (.create Engine))

; create object primitives
;;;;;;;;;;;;;;;;;;;;;;;;;;;; x, y, w, h, opts

(def stack (.. Composites
               (stack 440 110                               ;; x y
                      5 7                                   ;; col row
                      0 0                                   ;; dx dy
                      (fn [x y]
                        (let [r (rand-int 3)
                              bc (clj->js BLACK-COLOR)
                              wc (clj->js WHITE-COLOR)]
                          (case (mod r 3)
                            0 (.rectangle Bodies x y 10 10 bc)
                            1 (.circle Bodies x y 8 wc)
                            2 (.polygon Bodies x y 3 8 wc)))))))  ;; triangle


(def weight (.circle Bodies (+ HW 60) -300 50 (clj->js (assoc BLACK-COLOR
                                                         :density 1))))

(def catapult (.rectangle Bodies (+ HW 160) (- HH 60) 200 8 (clj->js (assoc BLACK-COLOR
                                                                       :density 0.0025))))

(def counter-balance (.rectangle Bodies (+ HW 235) (- HH 45) 20 20
                                 (clj->js (assoc BLACK-COLOR
                                            :isStatic true))))

(defn header-component []
  (let []
    (reagent/create-class
      {:component-did-mount
                     (fn [])
                       ;(let [; create a renderer
                       ;      render
                       ;      (.create Render #js {:element (.querySelector js/document "#canvas")
                       ;                           :engine  engine
                       ;                           :options #js {:wireframes false
                       ;                                         :background "transparent"
                       ;                                         :width      WIDTH
                       ;                                         :height     HEIGHT}})]
                       ;
                       ;  ; add all of the bodies to the world
                       ;  (.add World (.-world engine)
                       ;        #js [stack
                       ;             catapult
                       ;             counter-balance
                       ;             weight
                       ;             (.create Constraint (clj->js {:bodyA catapult :pointB {:x (+ HW 140) :y (+ HH 20)}}))
                       ;             (.create Constraint (clj->js {:bodyA catapult :pointB {:x (+ HW 180) :y (+ HH 20)}}))])
                       ;
                       ;  ; run the engine
                       ;  (.run Engine engine)
                       ;  ; run the renderer
                       ;  (.run Render render)))

       :display-name "header-component"

       :reagent-render
                     (fn [data]
                       [:div
                        ;[:div#canvas]
                        [:div.header.flex-row
                         [:div.left.flex-col
                          [:a {:href "/"}
                           [:img.logo {:src    "img/header_logo.png"
                                       :height "200px"}]]]
                         [:div.right
                          [:div
                           [:img.addy {:src "img/header_addy.png"}]]]
                         ;  [:div
                         ;   [:img.phone {:src "img/header_phone.png"}]]]
                         (if (:error data)
                           [:span (:status-text (:error data))]
                           [:img {:src data}])]])})))

