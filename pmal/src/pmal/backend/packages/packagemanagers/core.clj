;; Copyright 2020, Di Sera Luca
;;       Contacts:   disera.luca@gmail.com
;;                   https://github.com/diseraluca
;;                   https://www.linkedin.com/in/luca-di-sera-200023167
;;
;; This code is licensed under the MIT License.
;; More information can be found in the LICENSE file in the root of this repository

(ns pmal.backend.packages.packagemanagers.core
  (:require [pmal.backend.packages.packagemanagers.dpkg :as dpkg]
            [pmal.backend.packages.packagemanagers.pacman :as pacman]))

(defmulti package-manager identity)

(defmethod package-manager ::dpkg [_] (when (dpkg/available?) dpkg/package-manager))
(defmethod package-manager ::pacman [_] (when (pacman/available?) pacman/package-manager))

(defmethod package-manager :default [_] nil)
