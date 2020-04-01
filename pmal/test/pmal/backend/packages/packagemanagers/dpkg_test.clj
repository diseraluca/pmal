;; Copyright 2020, Di Sera Luca
;;       Contacts:   disera.luca@gmail.com
;;                   https://github.com/diseraluca
;;                   https://www.linkedin.com/in/luca-di-sera-200023167
;;
;; This code is licensed under the MIT License.
;; More information can be found in the LICENSE file in the root of this repository

(ns pmal.backend.packages.packagemanagers.dpkg-test
  (:require [clojure.test :refer :all]
            [clojure.string :refer [split-lines join]]
            [pmal.backend.packages.packagemanagers.dpkg :refer :all]
            [pmal.backend.packages.types :refer [PackageManager]])
  (:import (pmal.backend.packages.packagemanagers.dpkg dpkg)))

(def package-details "testpackage\nA Package that is used for testing\nThis is the description of this package.")

;; TODO: Remember to add tests for query-details as soon as the behavior regarding error checking is laid out.

;; TODO: Remember to add tests to show the form of the details string as soon as the format is more complete.

(deftest test-parse-details
  (let [parsed (parse-details package-details)]

    (is (map? parsed)
        "Given a correctly formed details string, parse-details returns a mapped structure.")

    (testing "The mapped structure returned by parse-details provides information about the described package."

      (is (contains? parsed :name)
          "The mapped structure contains the 'name' key.")

      (is (= (first (split-lines package-details))
             (:name parsed))
          "The 'name' key contains the content of the first line of the details string.")

      (is (contains? parsed :description)
          "The mapped structure contains the 'description' key.")

      (is (= (join "\n" (rest (split-lines package-details)))
             (:description parsed))
          "The 'description' key contains the newline-separated concatenation of the lines in the details string excluding the first."))))

(deftest test-dpkg

  (is (satisfies? PackageManager (dpkg.))
      "The dpkg type satisfies the PackageManager protocol."))

(deftest test-package-manager

  (is (instance? dpkg package-manager)
      "package-manager returns an instance of the dpkg PackageManager."))
