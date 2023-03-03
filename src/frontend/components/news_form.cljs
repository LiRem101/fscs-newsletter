(ns frontend.components.news-form
  (:require
    [frontend.state :refer [use-app-state]]
    [frontend.utils :refer [make-label-str newsletter-form-fields]]
    [helix.core :refer [defnc $]]
    [helix.dom :as d]
    [helix.hooks :as hooks]))


(defnc newsletter-display-item [{:keys [label value]}]
  (d/p (d/strong (make-label-str label)) value))


(defnc newsletter-display [{:keys [newsletter]}]
  (d/div
    (map-indexed
      (fn [i v] ($ newsletter-display-item {:label v :value (get newsletter (keyword v)) :key i}))
      newsletter-form-fields)))


(defnc newsletter-edit-item [{:keys [label value on-change]}]
  (d/div
    (d/label {:for label} (make-label-str label))
    (d/input {:id label :value value :on-change on-change})))


(defnc newsletter-edit [{:keys [newsletter]}]
  (let [[state set-state] (hooks/use-state newsletter)]
    (d/form
      (map-indexed
        (fn [i v]
          ($ newsletter-edit-item {:label v :value (get state (keyword v)) :key i
                                   :on-change #(set-state (assoc state (keyword v) (.. % -target -value)))}))
        newsletter-form-fields))))


(defnc newsletter-form []
  (let [[edit set-edit] (hooks/use-state false)
        [state _actions] (use-app-state)
        newsletter (:selected state)]
    (d/div
      (d/h1 "Submit Form")
      (d/button {:on-click #(set-edit (not edit))}
                "Toggle")
      (if edit
        ($ newsletter-edit {:newsletter newsletter})
        ($ newsletter-display {:newsletter newsletter})))))
