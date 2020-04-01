;; Copyright 2020, Di Sera Luca
;;       Contacts:   disera.luca@gmail.com
;;                   https://github.com/diseraluca
;;                   https://www.linkedin.com/in/luca-di-sera-200023167
;;
;; This code is licensed under the MIT License.
;; More information can be found in the LICENSE file in the root of this repository

(ns pmal.backend.packages.types)

(defprotocol PackageManager
  "The abstraction protocol over the feature provided by a system's package manager."
  (package-details [this pkgname] "Retrieves the details of the package named pkgname."))

(defrecord PMAL-Package [name description])
