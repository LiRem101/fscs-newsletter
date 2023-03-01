(ns state
  (:require [helix.core :refer [create-context]]))

(def initial-state {:selected nil :newsletter []})

(def app-state (create-context nil))

(defn app-reducer [state _] state)