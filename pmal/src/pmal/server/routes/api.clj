;; Copyright 2020, Di Sera Luca
;;       Contacts:   disera.luca@gmail.com
;;                   https://github.com/diseraluca
;;                   https://www.linkedin.com/in/luca-di-sera-200023167
;;
;; This code is licensed under the MIT License.
;; More information can be found in the LICENSE file in the root of this repository

(ns pmal.server.routes.api
  (:require [ring.middleware.json :refer [wrap-json-response]]
            [ring.util.http-response :as response]
            [pmal.backend.core :as be]))

(def request-package-details
  "Retrieves and provides the details about the requested package.
   If the requested package is one that is known to the system, the body of the response will
   contain a mapped structure, under the package key in the json-encoded body, that presents
   the details of the package.

   In the case that the package cannot be retrieved, a not-found response will be sent to the client."
  ["/details/:pkgname" {:get (fn [{{:keys [pkgname]} :path-params}]
                               (if-let [package (be/package pkgname)]
                                 (response/ok {:package package})
                                 (response/not-found (format "Unknown package %s" pkgname))))}])

(def routes
  ["/api" {:middleware [wrap-json-response]}
    request-package-details])
