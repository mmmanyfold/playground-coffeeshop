(ns playground-coffeeshop.components.header)

(defn header-component [data]
  (if (:error data)
    [:span (:status-text (:error data))]
    [:img {:src data}]))