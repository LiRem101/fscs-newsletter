(ns backend.manage-news)


(defn get-types
  [template-map]
  (map :type (filter :type template-map)))
