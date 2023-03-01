(ns core
  (:require
    [ajax.core :refer [GET]]
    [components.list :refer [newsletter-list]]
    [components.nav :refer [nav]]
    [components.news-form :refer [newsletter-form]]
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
         (if state
           (d/div ($ nav)
                  (d/div {:class '[container pt-4]}
                         ($ newsletter-list {:newsletter state})
                         ($ newsletter-form {:newsletter (first (:newsletter state))})))
           (d/p "Loading..."))))

(defonce root (rdom/createRoot (js/document.getElementById "app")))

(defn ^:export ^:dev/after-load init []
  (.render root ($ app)))