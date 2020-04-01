(defproject pmal "0.1.0-SNAPSHOT"

  :dependencies [[org.clojure/clojure "1.10.1"]
                 [ring/ring-core "1.6.3"]
                 [ring/ring-jetty-adapter "1.6.3"]
                 [ring/ring-json "0.5.0"]
                 [metosin/reitit-core "0.4.2"]
                 [metosin/reitit-ring "0.4.2"]
                 [metosin/ring-http-response "0.9.1"]
                 [environ "1.0.0"]]

  :source-paths ["src"]
  :test-paths ["test"]
  :main pmal.core

  :profiles {:dev [:project/dev]

             :project/dev {:dependencies [[org.clojure/data.json "1.0.0"]
                                          [ring/ring-devel "1.6.3"]
                                          [ring/ring-mock "0.4.0"]]

                           :repl-options {:init-ns interactive.core}
                           :plugins [[lein-environ "1.0.0"]]
                           :env {:manager :dpkg}}})
