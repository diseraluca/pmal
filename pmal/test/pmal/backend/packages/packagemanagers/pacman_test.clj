;; Copyright 2020, Di Sera Luca
;;       Contacts:   disera.luca@gmail.com
;;                   https://github.com/diseraluca
;;                   https://www.linkedin.com/in/luca-di-sera-200023167
;;
;; This code is licensed under the MIT License.
;; More information can be found in the LICENSE file in the root of this repository

(ns pmal.backend.packages.packagemanagers.pacman-test
  (:require [clojure.test :refer :all]
            [clojure.string :refer [split-lines]]
            [pmal.backend.packages.packagemanagers.pacman :refer :all]
            [pmal.backend.packages.types :refer [PackageManager]])
  (:import (pmal.backend.packages.packagemanagers.pacman pacman)))

(def package-details
"Repository      : test
Name            : testpackage
Version         : 9.3.0-1
Description     : A test package
Architecture    : x86_64
URL             : htts://www.bogus.url/
Licenses        : Unknown
Groups          : Unknown
Provides        : None
Depends On      : None
Optional Deps   : None
Conflicts With  : None
Replaces        : None
Download Size   : 29.84 MiB
Installed Size  : 139.32 MiB
Packager        : Luca Di Sera
Build Date      : Thu 12 Mar 2020 05:55:10 PM CET
Validated By    : MD5 Sum  SHA-256 Sum  Signature")

;; TODO: Add tests that describe the format of a detail line.

(deftest test-parse-detail-line

  (is (= "data"
         (parse-detail-line "tag : data "))
      "parse-detail-line returns the data of the detail line, i.e the part of the string that is after the colon with trailing and leading spaces stripped."))

(deftest test-get-detail-line
  (let [detail-lines [
                      "tag1 : data1"
                      "tag2 : data2"
                      "tag3 : data3"
                      ]]

    (is (= (second detail-lines)
           (get-detail-line ::tag2 detail-lines))
        "get-detail-line returns the line that starts with the given tag.")))

(deftest test-parse-details
  (let [parsed (parse-details package-details)]

    (is (map? parsed)
        "Given a correctly formed details string, parse-details returns a mapped structure.")

    (testing "The mapped structure returned by parse-details provides information about the described package."

      (is (contains? parsed :name)
          "The mapped structure contains the 'name' key.")

      (is (= (parse-detail ::Name (split-lines package-details))
             (:name parsed))
          "The 'name' key contains the data of the line of the details string that has tag 'Name'.")

      (is (contains? parsed :description)
          "The mapped structure contains the 'description' key.")

      (is (= (parse-detail ::Description (split-lines package-details))
             (:description parsed))
          "The 'description' key contains the data of the line of the details string that has tag 'Description'."))))

(deftest test-pacman

  (is (satisfies? PackageManager (pacman.))
      "The pacman type satisfies the PackageManager protocol."))

(deftest test-package-manager

  (is (instance? pacman package-manager)
      "package-manager returns an instance of the pacman PackageManager."))
