(ns core
  (:require
    [helix.core :refer [defnc $]]
    [helix.dom :as d]
    ["react-dom" :as dom]))


(defnc app [] (d/div "Hello World"))

(defn ^:export init []
  (dom/render ($ app) (js/document.getElementById "app")))