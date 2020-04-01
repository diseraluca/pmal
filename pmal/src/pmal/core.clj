;; Copyright 2020, Di Sera Luca
;;       Contacts:   disera.luca@gmail.com
;;                   https://github.com/diseraluca
;;                   https://www.linkedin.com/in/luca-di-sera-200023167
;;
;; This code is licensed under the MIT License.
;; More information can be found in the LICENSE file in the root of this repository

(ns pmal.core
  (:gen-class)
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [pmal.server.core :refer [app]]
            [pmal.backend.core :refer [can-be-used?]]))

;; TODO: can-be-used will be changed to an Either-Monad-like flow and the error reporting will need to be changed accordingly.
(defn start []
  (if (can-be-used?)
    (jetty/run-jetty (-> #'app wrap-reload) { :port 8080 :join? false })
    (println "Unable to start server. Unknown or unavailable package manager.")))

(defn -main [] (start))
