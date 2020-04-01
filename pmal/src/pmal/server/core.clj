;; Copyright 2020, Di Sera Luca
;;       Contacts:   disera.luca@gmail.com
;;                   https://github.com/diseraluca
;;                   https://www.linkedin.com/in/luca-di-sera-200023167
;;
;; This code is licensed under the MIT License.
;; More information can be found in the LICENSE file in the root of this repository

(ns pmal.server.core
  (:require [reitit.ring :as ring]
            [pmal.server.routes.core :refer [routes]]))

(defonce app
  (ring/ring-handler
   (ring/router routes)
   (ring/create-default-handler)))