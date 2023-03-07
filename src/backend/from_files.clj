(ns backend.from-files
  (:require
    [clojure.data.json :as json]
    [clojure.java.io :as io]))


(def template-path "resources/templates/")


(defn json-from-file
  [file]
  (json/read-str (slurp file) :key-fn keyword))


(defn get-files
  [path]
  (filter #(.isFile %) (file-seq (io/file path))))


;; retrieves content of the files in resources/templates in a seq of maps of the form
;; {:headline Headline, :content-html "Content"}
(defn get-templates
  []
  (map json-from-file (get-files template-path)))
