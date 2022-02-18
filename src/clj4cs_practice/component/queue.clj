(ns clj4cs-practice.component.queue
  (:require [com.stuartsierra.component :as component])
  (:import (clojure.lang PersistentQueue)))

(def queue (atom PersistentQueue/EMPTY))

(defrecord Queue []

  component/Lifecycle
  (start [component]
    (assoc component :state queue))

  (stop [component]
    (dissoc component :state)))

(defn new-producer []
  (map->Queue {}))

(defn produce [{state :state} data]
  (swap! queue conj data)
  state)

(defn consume [{state :state}]
  (let [val (peek @queue)]
    (swap! queue pop)
    val))

(comment
  (-> (new-producer)
      (component/start)
      (produce {:b 2})
      (produce {:a 1})
      (consume)
      (consume)))