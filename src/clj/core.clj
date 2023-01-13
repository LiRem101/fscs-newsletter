(ns clj.core
  (:require [org.httpkit.server :refer [run-server]]
            [reitit.ring :as ring]))

(defonce server (atom nil))

(def app
  (ring/ring-handler
    (ring/router
      [["/api" {:get (fn [req]
                       {:status 200
                        :body "ok"})}]])))

(defn -main []
  (println "Server started")
  (reset! server (run-server app {:port 4000})))

(defn stop-server []
  (reset! server nil))

(defn restart-server []
  (stop-server)
  (-main))