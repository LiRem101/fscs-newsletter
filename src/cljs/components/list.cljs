(ns components.list
  (:require [helix.core :refer [defnc]]
            [helix.dom :as d]))

(defnc newsletter-list [{:keys [newsletter]}]
       (d/div
         (d/ul (:hello newsletter))))