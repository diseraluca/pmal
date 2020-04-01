;; Copyright 2020, Di Sera Luca
;;       Contacts:   disera.luca@gmail.com
;;                   https://github.com/diseraluca
;;                   https://www.linkedin.com/in/luca-di-sera-200023167
;;
;; This code is licensed under the MIT License.
;; More information can be found in the LICENSE file in the root of this repository

(ns mock.core-test
  (:require [clojure.test :refer :all]
            [mock.core :refer :all]))

(deftest test-mock

  (is (function? (mock #'prn (constantly nil)))
      "mock called with two argument returns a function.")

  (let [result "mocked!"
        mocked #'prn
        mockf (constantly result)
        callee #(mocked :args)]

    (is (= result
           ((mock mocked mockf) callee))
        "Calling the function returned by mock with a function as argument, will call the passed in function with each call to mocked substituted with mockf.")

    ;; TODO: This property must have a name. find it and clean the description.
    (is (= (mock mocked mockf callee)
           ((mock mocked mockf) callee))
        "Calling mock with three arguments, let them be arg1,arg2 and arg3, is equivalent to applying mock to arg1 and arg2 and then calling the result with arg3.")))
