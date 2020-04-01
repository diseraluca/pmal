;; Copyright 2020, Di Sera Luca
;;       Contacts:   disera.luca@gmail.com
;;                   https://github.com/diseraluca
;;                   https://www.linkedin.com/in/luca-di-sera-200023167
;;
;; This code is licensed under the MIT License.
;; More information can be found in the LICENSE file in the root of this repository

(ns pmal.backend.packages.packagemanagers.core-test
  (:require [clojure.test :refer :all]
            [pmal.backend.packages.packagemanagers.core :as pkgmanager]
            [pmal.backend.packages.packagemanagers.dpkg :as dpkg]
            [pmal.backend.packages.packagemanagers.pacman :as pacman]
            [mock.core :refer :all])
  (:import (pmal.backend.packages.packagemanagers.dpkg dpkg))
  (:import (pmal.backend.packages.packagemanagers.pacman pacman)))

(deftest test-package-manager

  (do-with-mock (mock #'dpkg/available? (fn [] true))
    (is (instance? dpkg (pkgmanager/package-manager ::pkgmanager/dpkg))
        "Calling package-manager with the dpkg keyword returns a dpkg instance, when it is available."))

  (do-with-mock (mock #'dpkg/available? (fn [] false))
                (is (nil? (pkgmanager/package-manager ::pkgmanager/dpkg))
                    "Calling package-manager with the dpkg keyword returns nil when dpkg is not available."))

  (do-with-mock (mock #'pacman/available? (fn [] true))
    (is (instance? pacman (pkgmanager/package-manager ::pkgmanager/pacman))
        "Calling package-manager with the pacman keyword returns a pacman instance, when it is available."))

  (do-with-mock (mock #'pacman/available? (fn [] false))
                (is (nil? (pkgmanager/package-manager ::pkgmanager/pacman))
                    "Calling package-manager with the pacman keyword returns nil when pacman is not available."))

  (is (= nil (pkgmanager/package-manager :uknown-value))
      "Calling package-manager with an unknown package manager keyword will return nil."))
