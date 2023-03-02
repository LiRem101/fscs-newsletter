(ns frontend.utils
  (:require [clojure.string :refer [replace capitalize]]))

(def newsletter-form-fields
  ["headline"])

(defn make-label-str [s]
      (str (-> s
               (replace "_" " ")
               capitalize)
           ": "))