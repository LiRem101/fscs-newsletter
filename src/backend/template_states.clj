(ns backend.template-states)


(def templates (atom {}))


(defn retrieve-from-files
  [get-templates]
  (reset! templates (get-templates)))


(defn get-map-by-type
  [type]
  (let [maps-with-type (filter #(= (:type %) type) @templates)]
    (if (not-empty maps-with-type)
      (first maps-with-type) nil)))
