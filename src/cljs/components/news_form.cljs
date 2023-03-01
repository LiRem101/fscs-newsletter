(ns components.news-form
  (:require [helix.core :refer [defnc $]]
            [helix.dom :as d]
            [helix.hooks :as hooks]))

(def newsletter-form-fields ["headline"])

(defn make-label-str [s]
      (str (-> s
               (clojure.string/replace "_" " ")
               clojure.string/capitalize)
           ": "))

(defnc newsletter-display-item [{:keys [label value]}]
       (d/p (d/strong (make-label-str label)) value))

(defnc newsletter-display [{:keys [newsletter]}]
       (d/div
         (map-indexed
           (fn [i v] ($ newsletter-display-item {:label v :value (get newsletter (keyword v)) :key i}))
           newsletter-form-fields)))

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