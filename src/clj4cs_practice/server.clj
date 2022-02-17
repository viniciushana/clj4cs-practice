(ns clj4cs-practice.server
  (:gen-class)
  (:require [io.pedestal.http :as server]
            [clj4cs-practice.system :as system]))

(defn -main
  "The entry-point for 'lein run'"
  [& args]
  (println "\nCreating your server...")
  (com.stuartsierra.component/start-system (system/new-system :prod)))
