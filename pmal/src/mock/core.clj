;; Copyright 2020, Di Sera Luca
;;       Contacts:   disera.luca@gmail.com
;;                   https://github.com/diseraluca
;;                   https://www.linkedin.com/in/luca-di-sera-200023167
;;
;; This code is licensed under the MIT License.
;; More information can be found in the LICENSE file in the root of this repository

(ns mock.core)

(defn mock
  "Mocks the given function and replaces it with a user-defined result.
   If two arguments are given, mock will return a function that accepts any function
   and runs it in an environment where any call to mocked is replaced by mockf.
   The three argument version directly applies f in an environment where mocked is
   replaced by mockf.

   Example:
     user> (mock #'prn (constantly 5) (fn [] (prn 10)))
     5"
  ([mocked mockf]
   (fn [f] (with-redefs-fn {mocked mockf} f)))
  ([mocked mockf f]
   ((mock mocked mockf) f)))

(defmacro do-with-mock
  "Executes a sequence of forms in the mocked environment mock.

  Example:
  user> (do-with-mock (mock #'vector (fn [& elements] (prn elements))) (vector 6) (vector 7 8) (vector 9))
  (6)
  (7 8)
  (9)
  nil
  "
  [mock & forms]
  `(do ~@(map (fn [form#] `(~mock (fn [] ~form#))) forms)))
