;; Copyright 2020, Di Sera Luca
;;       Contacts:   disera.luca@gmail.com
;;                   https://github.com/diseraluca
;;                   https://www.linkedin.com/in/luca-di-sera-200023167
;;
;; This code is licensed under the MIT License.
;; More information can be found in the LICENSE file in the root of this repository

(ns pmal.backend.packages.core
  (:require [pmal.backend.packages.packagemanagers.core :as pkgmanager]
            [pmal.backend.packages.types :as types]
            [environ.core :refer [env]]))

(defn manager-setting
  "Retrieves and formats the :manager environment variable.
   Anything that isn't a keyword is not a valid :manager.
   A manager might no be available even if it is a valid setting."
  []
  (let [manager-keyword (env :manager)]
    (if (keyword? manager-keyword)
      (keyword (str (ns-name 'pmal.backend.packages.packagemanagers.core) "/" (name manager-keyword)))
      nil)))

(defn manager
  "Provides access to the current package manager abstraction.

  Returns nil if there is no package manager available under the current settings."
  []
  (pkgmanager/package-manager (manager-setting)))

(defn available?
  "Tests that there is an available package manager."
  []
  ((complement nil?) (manager)))

(defn package-details
  "Queries for the details of the package named pkgname under the current package manager.

  Returns nil if the package is unknown."
  [pkgname]
  (types/package-details (manager) pkgname))
