;; Copyright 2020, Di Sera Luca
;;       Contacts:   disera.luca@gmail.com
;;                   https://github.com/diseraluca
;;                   https://www.linkedin.com/in/luca-di-sera-200023167
;;
;; This code is licensed under the MIT License.
;; More information can be found in the LICENSE file in the root of this repository

(ns pmal.backend.packages.packagemanagers.dpkg
  (:require [pmal.backend.packages.types :refer :all]
            [clojure.java.shell :refer [sh]]
            [clojure.string :refer [split-lines join]]))

(defn query-details
  "Queries the details of the package named pkgname in the dpkg database.
   If the query is successful, a detail string is returned with the following format:

   pkgname\nMultilineDescription...

  Returns an empty string if the package is unknown or if any other error is encountered."
  [pkgname]
  (:out (sh "dpkg-query" "-W" "-f=${Package}\n${Description}" pkgname)))

(defn parse-details
  "Parses a detail string, formatted as described in [[query-details]], and builds a PMAL-Package from it.

  Example;
  user> (parse-details \"gcc\nBest\nC\nCompiler\")
  {:name \"gcc\", :description \"Best\nC\nCompiler\"}
  "
  [details]
  (let [[name & description] (split-lines details)]
    (->PMAL-Package name (join "\n" description))))

(deftype dpkg []
  PackageManager
  (package-details [_ pkgname]
    (when-let [details
               (not-empty (query-details pkgname))]
      (parse-details details))))

(defn available? []
  (try
    (do (sh "dpkg") true)
    (catch java.io.IOException e false)))

(defonce package-manager (dpkg.))
