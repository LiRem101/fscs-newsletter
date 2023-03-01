(ns components.list
  (:require [helix.core :refer [defnc $]]
            [helix.dom :as d]
            [state :refer [use-app-state]]))

(defnc newsletter-list-item [{:keys [news]}]
       (d/li (d/div (d/p (:headline news)))))

(defnc newsletter-list []
       (let [[state _] (use-app-state)
             newsletter (:newsletter state)]
            (d/div
              (d/ul
                (map-indexed (fn [i news] ($ newsletter-list-item {:key i :news news}))
                             newsletter)))))