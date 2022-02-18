(ns clj4cs-practice.component.db
  (:require [com.stuartsierra.component :as component])
  (:import (java.util UUID)))

(def state (atom {}))

(defrecord Db []

  component/Lifecycle
  (start [component]
    (assoc component :state state))

  (stop [component]
    (dissoc component :state)))

(defn new-db []
  (map->Db {}))

(defn store [{state :state} entity]
  (let [id (str (UUID/randomUUID))]
    (swap! state #(assoc % (keyword id) entity))))

(comment
  (-> (new-db)
      (component/start)
      (store {:coisa 1})))