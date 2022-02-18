(ns clj4cs-practice.component.db
  (:require [com.stuartsierra.component :as component]))

(def db (atom {}))

(defrecord Db []

  component/Lifecycle
  (start [component]
    (assoc component :db db))

  (stop [component]
    (dissoc component :db)))

(defn new-db []
  (map->Db {}))

(defn store [db id entity]
  (swap! db #(assoc % (keyword id) entity)))

(comment
  (-> (new-db)
      (component/start)
      :db
      (store "123" {:coisa 1})
      prn)
  )