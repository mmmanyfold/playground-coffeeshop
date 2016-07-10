(ns playground-coffeeshop.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [playground-coffeeshop.core-test]))

(doo-tests 'playground-coffeeshop.core-test)
