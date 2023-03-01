(ns core
  (:require
    [ajax.core :refer [GET]]
    [components.list :refer [newsletter-list]]
    [components.nav :refer [nav]]
    [helix.core :refer [defnc $]]
    [helix.dom :as d]
    [helix.hooks :as hooks]
    ["react-dom/client" :as rdom]))

(defnc app []
       (let [[state set-state] (hooks/use-state nil)]
         (hooks/use-effect
           :once
           (GET "http://localhost:4000/api"
              {:handler (fn [response]
                            (set-state response))}))
         (js/console.log state)
         (d/div ($ nav)
                (d/div {:class '[container pt-4]}
                       ($ newsletter-list)))))

(defonce root (rdom/createRoot (js/document.getElementById "app")))

(defn ^:export ^:dev/after-load init []
  (.render root ($ app)))