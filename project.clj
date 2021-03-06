(defproject playground-coffeeshop "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.8.51"]
                 [reagent "0.5.1"]
                 [binaryage/devtools "0.8.0"]
                 [re-frame "0.7.0"]
                 [secretary "1.2.3"]
                 [compojure "1.5.0"]
                 [yogthos/config "0.8"]
                 [cljs-ajax "0.5.8"]
                 [ring "1.4.0"]
                 [venantius/accountant "0.1.7"]
                 [cljsjs/moment "2.10.6-4"]
                 [reagent-forms "0.5.24"]
                 [json-html "0.4.0"]
                 [reanimated "0.4.0"]
                 [cljsjs/jquery "2.2.2-0"]
                 [cljsjs/marked "0.3.5-0"]]

  :plugins [[lein-cljsbuild "1.1.3"]
            [lein-less "1.7.5"]
            [lein-s3-static-deploy "0.1.0"]]

  :aws {:access-key       ~(System/getenv "AWS_ACCESS_KEY_ID")
        :secret-key       ~(System/getenv "AWS_SECRET_ACCESS_KEY")
        :s3-static-deploy {:bucket     "dev.playgroundcoffeeshop.com"
                           :local-root "resources/public"}}

  :min-lein-version "2.5.3"

  :source-paths ["src/clj"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"
                                    "test/js"]

  :figwheel {:css-dirs     ["resources/public/css"]
             :ring-handler playground-coffeeshop.handler/dev-handler}

  :less {:source-paths ["less"]
         :target-path  "resources/public/css"}

  :profiles
  {:dev
   {:dependencies []

    :plugins      [[lein-figwheel "0.5.8"]
                   [lein-doo "0.1.6"]]}}


  :cljsbuild
  {:builds
   [{:id           "dev"
     :source-paths ["src/cljs"]
     :figwheel     {:on-jsload "playground-coffeeshop.core/mount-root"}
     :compiler     {:main                 playground-coffeeshop.core
                    :output-to            "resources/public/js/compiled/app.js"
                    :output-dir           "resources/public/js/compiled/out"
                    :asset-path           "js/compiled/out"
                    :source-map-timestamp true}}
    {:id           "min"
     :source-paths ["src/cljs"]
     :jar          true
     :compiler     {:main            playground-coffeeshop.core
                    :output-to       "resources/public/js/compiled/app.js"
                    :optimizations   :advanced
                    :closure-defines {goog.DEBUG false}
                    :pretty-print    false
                    :externs ["externs.js"]}}
    {:id           "test"
     :source-paths ["src/cljs" "test/cljs"]
     :compiler     {:output-to     "resources/public/js/compiled/test.js"
                    :main          playground-coffeeshop.runner
                    :optimizations :none}}]}


  :main playground-coffeeshop.server

  :aot [playground-coffeeshop.server]

  :prep-tasks [["cljsbuild" "once" "min"] "compile"])
