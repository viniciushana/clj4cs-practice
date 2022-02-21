(ns clj4cs-practice.component.queue
  (:require [com.stuartsierra.component :as component])
  (:import (clojure.lang PersistentQueue)))

(defrecord Queue []

  component/Lifecycle
  (start [component]
    (assoc component :state (atom PersistentQueue/EMPTY)))
  (stop [component]
    (dissoc component :state)))

(defn new-queue []
  (map->Queue {}))

(defn produce [component data]
  (swap! (:state component) conj data)
  component)

(defn consume [{state :state}]
  (let [val (peek @state)]
    (swap! state pop)
    val))