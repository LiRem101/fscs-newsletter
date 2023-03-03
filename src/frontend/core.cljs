(ns frontend.core
  (:require
    ["react-dom/client" :as rdom]
    [ajax.core :refer [GET]]
    [frontend.components.list :refer [newsletter-list]]
    [frontend.components.nav :refer [nav]]
    [frontend.components.news-form :refer [newsletter-form]]
    [frontend.state :refer [app-reducer app-state initial-state use-app-state]]
    [helix.core :refer [defnc $ provider]]
    [helix.dom :as d]
    [helix.hooks :as hooks]))


(defnc app []
  (let [[state actions] (use-app-state)
        init (:init actions)]
    (hooks/use-effect
      :once
      (GET "http://localhost:4000/api"
           {:handler init}))
    (js/console.log state)
    (if state
      (d/div ($ nav)
             (d/div {:class '[container pt-4]}
                    ($ newsletter-list)
                    ($ newsletter-form)))
      (d/p "Loading..."))))


(defnc provided-app []
  (provider {:context app-state :value (hooks/use-reducer app-reducer initial-state)} ($ app)))


(defonce root (rdom/createRoot (js/document.getElementById "app")))


(defn ^:export ^:dev/after-load init
  []
  (.render root ($ provided-app)))
