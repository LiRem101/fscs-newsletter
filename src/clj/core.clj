(ns clj.core
  (:require [org.httpkit.server :refer [run-server]]
            [reitit.ring :as ring]
            [reitit.ring.middleware.exception :refer [exception-middleware]]
            [reitit.ring.middleware.muuntaja :refer [format-negotiate-middleware
                                                     format-request-middleware
                                                     format-response-middleware]]
            [muuntaja.core :as m]))

(defonce server (atom nil))

(def app
  (ring/ring-handler
    (ring/router
      [["/api" {:get (fn [req]
                       {:status 200
                        :body {:hello "world"}})}]]
      {:data {:muuntaja m/instance
              :middleware [format-negotiate-middleware
                           format-response-middleware
                           exception-middleware
                           format-request-middleware]}})
    (ring/routes
      (ring/redirect-trailing-slash-handler)
      (ring/create-default-handler
        {:not-found (constantly {:status 404 :body "Route not found"})}))))

(defn -main []
  (println "Server started")
  (reset! server (run-server app {:port 4000})))

(defn stop-server []
  (reset! server nil))

(defn restart-server []
  (stop-server)
  (-main))