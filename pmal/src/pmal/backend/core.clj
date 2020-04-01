;; Copyright 2020, Di Sera Luca
;;       Contacts:   disera.luca@gmail.com
;;                   https://github.com/diseraluca
;;                   https://www.linkedin.com/in/luca-di-sera-200023167
;;
;; This code is licensed under the MIT License.
;; More information can be found in the LICENSE file in the root of this repository

(ns pmal.backend.core
  (:require [pmal.backend.packages.core :as packages]))

;; TODO: When more than one initialization failure is implemented change to an Either-Monad-like approach.
(defn can-be-used?
  "Tests the backend to check that the needed external resources are available."
  []
  (packages/available?))

(defn package
  "Retrieves the details of the package with the given name.

   Returns nil if the package is unknown."
  [pkgname]
  (packages/package-details pkgname))
