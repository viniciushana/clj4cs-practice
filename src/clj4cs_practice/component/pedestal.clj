(ns clj4cs-practice.component.pedestal
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]
            [io.pedestal.interceptor :as interceptor]))

(defn test?
  [service-map]
  (= :test (:env service-map)))

(defn start-server
  [service-map]
  (prn service-map)
  (if (test? service-map)
    service-map
    (http/start service-map)))

(defn set-components [db service-map]
  (-> service-map
      http/default-interceptors
      (update ::http/interceptors conj
              (interceptor/interceptor
                {:name  ::components
                 :enter #(assoc-in % [:request :db] db)}))))

(defrecord Pedestal [service-map db service]

  component/Lifecycle
  (start [this]
    (if service
      this
      (->> service-map
           (set-components db)
           http/create-server
           start-server
           (assoc this :service))))

  (stop [this]
    (when (and service (not (test? service-map)))
      (http/stop service))
    (assoc this :service nil)))

(defn new-pedestal []
  (map->Pedestal {}))