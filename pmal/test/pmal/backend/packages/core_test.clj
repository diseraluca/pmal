;; Copyright 2020, Di Sera Luca
;;       Contacts:   disera.luca@gmail.com
;;                   https://github.com/diseraluca
;;                   https://www.linkedin.com/in/luca-di-sera-200023167
;;
;; This code is licensed under the MIT License.
;; More information can be found in the LICENSE file in the root of this repository

(ns pmal.backend.packages.core-test
  (:require [clojure.test :refer :all]
            [mock.core :refer :all]
            [pmal.backend.packages.core :refer :all]
            [pmal.backend.packages.packagemanagers.dpkg :as dpkg]
            [pmal.backend.packages.types :refer [PackageManager]]
            [environ.core :refer [env]]))

(deftest test-manager

  (do-with-mock (mock #'env {:manager :dpkg})
                (do-with-mock (mock #'dpkg/available? (fn [] true))
                (is (satisfies? PackageManager (manager))
                    "If the environment contains a :manager variable that is known and available, manager returns a PackageManager.")))

  (do-with-mock (mock #'env {:manager :unknown})
                (is (nil? (manager))
                    "If the environment contains a :manager variable that is unknown, manager returns nil.")))
