(ns core
  (:require
    [ajax.core :refer [GET]]
    [components.list :refer [newsletter-list]]
    [components.nav :refer [nav]]
    [components.news-form :refer [newsletter-form]]
    [helix.core :refer [defnc $ provider]]
    [helix.dom :as d]
    [helix.hooks :as hooks]
    [state :refer [app-state]]
    ["react-dom/client" :as rdom]))

(defnc app []
       (let [[state set-state] (hooks/use-state nil)
             context-value (hooks/use-context app-state)]
         (hooks/use-effect
           :once
           (GET "http://localhost:4000/api"
              {:handler (fn [response]
                            (set-state response))}))
         (js/console.log context-value)
         (if state
           (d/div ($ nav)
                  (d/div {:class '[container pt-4]}
                         ($ newsletter-list {:newsletter state})
                         ($ newsletter-form {:newsletter (first (:newsletter state))})))
           (d/p "Loading..."))))

(defnc provided-app []
       (provider {:context app-state :value "app-state value"} ($ app)))

(defonce root (rdom/createRoot (js/document.getElementById "app")))

(defn ^:export ^:dev/after-load init []
  (.render root ($ provided-app)))