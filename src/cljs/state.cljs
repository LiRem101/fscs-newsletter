(ns state
  (:require [helix.core :refer [create-context]]))

(def app-state (create-context nil))