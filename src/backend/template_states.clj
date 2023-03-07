(ns backend.template-states)


(def templates (atom {}))


(defn retrieve-from-files
  [get-templates]
  (reset! templates (get-templates)))
