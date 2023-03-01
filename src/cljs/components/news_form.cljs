(ns components.news-form
  (:require [helix.core :refer [defnc $]]
            [helix.dom :as d]
            [helix.hooks :as hooks]))

(defnc newsletter-display [{:keys [newsletter]}]
       (d/p "Newsletter Display"))

(defnc newsletter-edit [{:keys [newsletter]}]
       (d/p "Newsletter Edit"))

(defnc newsletter-form [{:keys [newsletter]}]
       (let [[edit set-edit] (hooks/use-state false)]
            (d/div
              (d/h1 "Submit Form")
              (d/button {:on-click #(set-edit (not edit))}
                        "Toggle")
              (if edit
                ($ newsletter-edit {:newsletter newsletter})
                ($ newsletter-display {:newsletter newsletter})))))