(ns state
  (:require [helix.core :refer [create-context]]
            [helix.hooks :as hooks]))

(def initial-state {:selected nil :newsletter []})

(def app-state (create-context nil))

(defmulti app-reducer
          (fn [_ action] (first action)))

(defmethod app-reducer
           ; (app-reducer state [:set-newsletter [{data}]]) sets :newsletter value of state to [{:data}]
           ::set-newsletter [state [_ payload]]
           (assoc state :newsletter payload))

(defmethod app-reducer
           ::set-selected [state [_ payload]]
           (assoc state :selected payload))

(defmethod app-reducer
           ::add-newsletter [state [_ payload]]
           (let [prev (:newsletter state)
                 next (conj prev payload)]
                (assoc state :newsletter next)))

(defmethod app-reducer
           ::update-newsletter [state [_ payload]]
           (let [prev (:newsletter state)
                 update-contact #(if (= (:id %) (:id payload)) payload %)
                 next (map update-contact prev)]
                (assoc state :newsletter next)))

(defmethod app-reducer
           ::remove-newsletter [state [_ payload]]
           (let [prev (:newsletter state)
                 not-matching #(not (= (:id %) payload))
                 next (filter not-matching prev)]
                (assoc state :newsletter next)))

(defn use-app-state []
      (let [[state dispatch] (hooks/use-context app-state)]
           [state {:init (fn [response]
                             (dispatch [::set-newsletter (:newsletter response)])
                             (dispatch [::set-selected (first (:newsletter response))]))
                   :select #(dispatch [::set-selected %])
                   :new-newsletter #(dispatch [::set-selected nil])
                   :add-newsletter #(dispatch [::add-newsletter %])
                   :update-newsletter #(dispatch [::update-newsletter %])
                   :remove-newsletter #(dispatch [::remove-newsletter %])}]))