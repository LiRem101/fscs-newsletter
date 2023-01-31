(ns core
  (:require
    [helix.core :refer [defnc $]]
    [helix.dom :as d]
    ["react-dom/client" :as rdom]))

(defnc nav []
       (d/nav {:class '[py-4 shadow]}
              (d/div {:class '[container]}
                     (d/h2 {:class '[text-xl]} "Contact Book"))))

(defnc app [] (d/div ($ nav)))

(defonce root (rdom/createRoot (js/document.getElementById "app")))

(defn ^:export init []
  (.render root ($ app)))