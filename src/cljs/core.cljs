(ns core
  (:require
    [helix.core :refer [defnc $]]
    [helix.dom :as d]
    ["react-dom/client" :as rdom]))


(defnc app [] (d/div "Hello World"))

(defonce root (rdom/createRoot (js/document.getElementById "app")))

(defn ^:export init []
  (.render root ($ app)))