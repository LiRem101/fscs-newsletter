(ns backend-test
  (:require
    [clojure.test :as t]))


(t/deftest addtwo (t/testing "Add two"
                    (t/testing "Zero"
                      (t/is (= 2 (+ 2 0))))
                    (t/testing "One"
                      (t/is (= 3 (+ 2 1))))))
