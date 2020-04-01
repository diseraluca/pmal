;; Copyright 2020, Di Sera Luca
;;       Contacts:   disera.luca@gmail.com
;;                   https://github.com/diseraluca
;;                   https://www.linkedin.com/in/luca-di-sera-200023167
;;
;; This code is licensed under the MIT License.
;; More information can be found in the LICENSE file in the root of this repository

(ns pmal.backend.mock
  (:require [pmal.backend.core]
            [pmal.backend.packages.types :refer [->PMAL-Package]]
            [mock.core :refer [mock do-with-mock]]))

(defmacro do-with-mocked-package
  "Process the given forms in a sequence, mocking pmal.backend.core/package to always return the details of a package with the requested name.

  Example:
  user> (do-with-mocked-package (pmal.backend.core/package \"bogus-package!\"))
  {:name \"bogus-package!\", :description \"A mocked package.\"}"
  [& forms]
  `(do-with-mock (mock #'pmal.backend.core/package (fn [pkgname#] (->PMAL-Package pkgname# "A mocked package."))) ~@forms))

(defmacro do-with-mocked-unknown-package 
  "Process the given forms in a sequence, mocking pmal.backend.core/package to always return nil.

  Example:
  user> (do-with-mocked-unknown-package (pmal.backend.core/package \"gcc\"))
  nil"
  [& forms]
  `(do-with-mock (mock #'pmal.backend.core/package (fn [pkgname#] nil)) ~@forms))
