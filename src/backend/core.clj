(ns backend.core
  (:require
    [backend.from-files :as ff]
    [backend.manage-news :as news]
    [backend.template-states :as state]
    [muuntaja.core :as m]
    [org.httpkit.server :refer [run-server]]
    [reitit.ring :as ring]
    [reitit.ring.middleware.exception :refer [exception-middleware]]
    [reitit.ring.middleware.muuntaja :refer [format-negotiate-middleware
                                             format-request-middleware
                                             format-response-middleware]]
    [ring.middleware.cors :refer [wrap-cors]]))


(defonce server (atom nil))


(def app
  (ring/ring-handler
    (ring/router
      [["/api" {:get (fn [_req]
                       {:status 200
                        :body {:newsletter [{:headline "Konsolenabend"}
                                            {:headline "Spieleabend"}]}})}]]
      {:data {:muuntaja m/instance
              :middleware [[wrap-cors
                            :access-control-allow-origin [#"http://localhost:4200"]
                            :access-control-allow-methods [:get :post :put :delete]]
                           format-negotiate-middleware
                           format-response-middleware
                           exception-middleware
                           format-request-middleware]}})
    (ring/routes
      (ring/redirect-trailing-slash-handler)
      (ring/create-default-handler
        {:not-found (constantly {:status 404 :body "Route not found"})}))))


(defn -main
  []
  (println "Server started")
  (state/retrieve-from-files ff/get-templates)
  (println "Loaded information from template files.")
  (println (news/get-headlines @state/templates))
  (reset! server (run-server app {:port 4000})))


(defn stop-server
  []
  (reset! server nil))


(defn restart-server
  []
  (stop-server)
  (-main))
