(ns core
  (:require
    [ajax.core :refer [GET]]
    [helix.core :refer [defnc $]]
    [helix.dom :as d]
    [helix.hooks :as hooks]
    ["react-dom/client" :as rdom]))

(defnc nav []
       (d/nav {:class '[py-4 shadow]}
              (d/div {:class '[container]}
                     (d/h2 {:class '[text-xl]} "Contact Book"))))

(defnc app []
       (hooks/use-effect
         :once
         (GET "http://localhost:4000/api"
              {:handler (fn [response]
                            (.log js/console response))}))
       (d/div ($ nav)))

(defonce root (rdom/createRoot (js/document.getElementById "app")))

(defn ^:export ^:dev/after-load init []
  (.render root ($ app)))