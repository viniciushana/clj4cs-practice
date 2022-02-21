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

(defn set-components [db queue service-map]
  (-> service-map
      http/default-interceptors
      (update ::http/interceptors conj
              (interceptor/interceptor
                {:name  ::components
                 :enter #(update % :request assoc :db db :queue queue)}))))

(defrecord Pedestal [service-map db queue service]

  component/Lifecycle
  (start [this]
    (if service
      this
      (->> service-map
           (set-components db queue)
           http/create-server
           start-server
           (assoc this :service))))

  (stop [this]
    (when (and service (not (test? service-map)))
      (http/stop service))
    (assoc this :service nil)))

(defn new-pedestal []
  (map->Pedestal {}))