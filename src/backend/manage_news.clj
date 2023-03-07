(ns backend.manage-news)


(defn get-headlines
  [template-map]
  (map :headline (filter :headline template-map)))
