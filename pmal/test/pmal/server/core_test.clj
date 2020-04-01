;; Copyright 2020, Di Sera Luca
;;       Contacts:   disera.luca@gmail.com
;;                   https://github.com/diseraluca
;;                   https://www.linkedin.com/in/luca-di-sera-200023167
;;
;; This code is licensed under the MIT License.
;; More information can be found in the LICENSE file in the root of this repository

(ns pmal.server.core-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [clojure.data.json :as json]
            [pmal.backend.mock :refer [do-with-mocked-package do-with-mocked-unknown-package]]
            [pmal.server.core :refer :all]))

(defn package-request [pkgname] (app (request :get (str "/api/details/" pkgname))))

(def parse-json #(json/read-str % :key-fn clojure.core/keyword))

(deftest test-details-package
  (do-with-mocked-unknown-package

    (is (= 404 (:status (package-request "testpkg")))
        "Asking for the details of a package that is not known to the system produces a 404 html-code response."))

  (do-with-mocked-package

    (is (= 200 (:status (package-request "testpkg")))
        "Asking for the details of a package that is known to the system produces a 200 html-code response.")

    (testing "The /api/details/:pkgname route provides a json-formatted mapped structure of the details of the package named :pkgname."
      (let [pkgname "testpkg"
            body (parse-json (:body (package-request pkgname)))]

        (is (contains? body :package)
            "The response contains a 'package' key in its body.")

        (is (map? (:package body))
            "The 'package' key in the response body refers to a mapped structure.")

        (testing "The mapped structure referred to by the 'package' key describes a package's detailed information."

          (is (every? #(contains? (:package body) %)
                      [:name :description])
              "The structure contains the keys [ 'name' 'description' ].")

          (let [{:keys [name description]} (:package body)]

            (is (string? name)
                "The 'name' key of the mapped structure refers to a string.")

            (is (= pkgname name)
                "The string referred to by the 'name' key is :pkgname.")

            (is (string? description)
                "The 'description' key refers to a string.")))))))

(deftest test-uknown-routes
  (is (= 404 (:status (app (request :get "/uknown/route"))))
      "Requesting a route that is unknown to the server will return a 404 html-code response."))
