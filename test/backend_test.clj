(ns backend-test
  {:clj-kondo/config '{:linters {:unresolved-symbol {:exclude (test-get-headlines)}}}}
  (:require
    [backend.manage-news :as news]
    [clojure.test :as t]
    [clojure.test.check.clojure-test :refer [defspec]]
    [clojure.test.check.generators :as gen]
    [clojure.test.check.properties :as prop]))


(t/deftest addtwo (t/testing "Add two"
                    (t/testing "Zero"
                      (t/is (= 2 (+ 2 0))))
                    (t/testing "One"
                      (t/is (= 3 (+ 2 1))))))


(defn create-headline-map
  [strs prev-map]
  (map #(merge {:headline %} prev-map) strs))


(defspec test-get-headlines 10
  (prop/for-all
    [without-headline (gen/list (gen/such-that #(not (:headline %)) (gen/map gen/keyword gen/string-ascii)))
     headline-list (gen/list gen/string-ascii)
     map-for-headline (gen/such-that #(not (:headline %)) (gen/map gen/keyword gen/string-ascii))]
    (let [headline-map (create-headline-map headline-list map-for-headline)
          maps (concat headline-map without-headline)]
      (= headline-list (news/get-headlines maps)))))
