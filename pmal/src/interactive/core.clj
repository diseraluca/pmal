(ns interactive.core
  (:require [pmal.core :refer [start]]
            [pmal.server.core :refer [app]]
            [mock.core :refer :all]
            [ring.mock.request :refer [request]]
            [environ.core :refer [env]]))

(def server (atom nil))

(defn start-server []
  (when-not @server
    (reset! server (start))))

(defn stop-server []
  (when @server
    (.stop @server)
    (reset! server nil)))

(defn restart-server []
  (do
    (stop-server)
    (start-server)))

(defn request-to-server [route]
  (app (request :get route)))

(defn request-package [pkgname]
  (request-to-server (str "/api/details/" pkgname)))

(defmacro with-package-manager [manager & forms]
  `(do-with-mock (mock #'env {:manager ~manager}) ~@forms))

(defmacro request-with-pacman [request]
  `(with-package-manager :pacman ~request))

(defmacro request-with-dpkg [request]
  `(with-package-manager :dpkg ~request))
