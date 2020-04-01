;; Copyright 2020, Di Sera Luca
;;       Contacts:   disera.luca@gmail.com
;;                   https://github.com/diseraluca
;;                   https://www.linkedin.com/in/luca-di-sera-200023167
;;
;; This code is licensed under the MIT License.
;; More information can be found in the LICENSE file in the root of this repository

(ns pmal.backend.packages.packagemanagers.pacman
  (:require [pmal.backend.packages.types :refer :all]
            [clojure.java.shell :refer [sh]]
            [clojure.string :refer [split split-lines starts-with? trim]]))

(defn query-details
  "Queries the details of the package named pkgname in pacman's sync database.
   If the query is successful, a detail string is returned with the following format:

   tag1 : data1\ntag2 : data2\n...

   A life of the form 'tag1 : data1' is called a detail line.
   A line tagged 'Name' and a line tagged 'Description' is present in any well-formed detail string.

   Returns an empty string if the package is unknown or if any other error is encountered."
  [pkgname]
  (:out (sh "pacman" "-Si" pkgname)))

;; TODO: Modify the 'parser' such that it is less shaky ( has more domain knowledge about the format it parses )
;;       and avoids doing some expensive operations more than once

(defn parse-detail-line
  "Parses a single line of the details string formatted as in [[query-details]], returning the data it contains.
   The data is stripped of its leading and trailing whitespace.

  Example:
  user> (parse-detail-line \"Name : gcc\")
  \"gcc\""
  [detail-line]
  ((comp trim second split) detail-line #":"))

(defn get-detail-line
  "Retrieves the detail line tagged tag from a vector of lines formatted as one of the lines described in [[query-details]].

  Example:
  user> (get-detail-line :tag2 [\"tag1 : data1\" \"tag2 : data2\" \"tag3 : data3\"])
  \"tag2 : data2\""
  [tag detail-lines]
  (first (filter #(starts-with? % (name tag)) detail-lines)))

(defn parse-detail 
  "Retrieves the data tagged tag from a vector of detail lines formatted as described in [[query-details]].

  Example:
  user> (parse-detail :Description [\"Name : gcc\" \"Description : The best c compiler!!\"])
  \"The best c compiler!!\""
  [tag detail-lines]
  (parse-detail-line (get-detail-line tag detail-lines)))

(defn parse-details
  "Builds a PMAL-Package from a detail string formatted as in [[query-details]].

  Example:
  user> (parse-details \"Name : gcc\nDescription : The best c compiler!!\")
  {:name \"gcc\", :description \"The best c compiler!!\"}"
  [details]
  (let [detail-lines (split-lines details)]
    (->PMAL-Package (parse-detail ::Name detail-lines) (parse-detail ::Description detail-lines))))

(deftype pacman []
  PackageManager
  (package-details [_ pkgname]
    (when-let [details
               (not-empty (query-details pkgname))]
      (parse-details details))))

(defn available? []
  (try
    (do (sh "pacman") true)
    (catch java.io.IOException e false)))

(defonce package-manager (pacman.))
