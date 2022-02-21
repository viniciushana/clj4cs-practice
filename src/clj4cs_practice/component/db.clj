(ns clj4cs-practice.component.db
  (:require [com.stuartsierra.component :as component])
  (:import (java.util UUID)))

(defrecord Db []

  component/Lifecycle
  (start [component]
    (assoc component :state (atom [])))

  (stop [component]
    (dissoc component :state)))

(defn new-db []
  (map->Db {}))

(defn store [component entity]
  (->> (UUID/randomUUID)
       str
       (assoc entity :id)
       (swap! (:state component) conj))
  component)

(defn query [{state :state} criteria]
  (filter criteria @state))
