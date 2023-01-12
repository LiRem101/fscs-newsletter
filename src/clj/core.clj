(ns clj.core
  (:require [org.httpkit.server :refer [run-server]]
            [reitit.ring :as ring]))

(def app
  (ring/ring-handler
    (ring/router
      [["/api" {:get (fn [req]
                       {:status 200
                        :body "ok"})}]])))

(defn -main []
  (println "Server started")
  (run-server app {:port 4000}))