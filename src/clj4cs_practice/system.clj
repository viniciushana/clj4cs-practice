(ns clj4cs-practice.system
  [:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]
            [clj4cs-practice.component.pedestal :as pedestal]
            [clj4cs-practice.component.queue :as queue]
            [clj4cs-practice.component.db :as db]
            [clj4cs-practice.routes :as routes]])

(defn new-system [env]
  (component/system-map
    :db (db/new-db)

    :queue (queue/new-queue)

    :service-map
    {:env          env
     ::http/routes routes/routes
     ::http/type   :jetty
     ::http/port   8890
     ::http/join?  false}

    :pedestal
    (component/using                                        ; using permite definir interdependências entre componentes
      (pedestal/new-pedestal)                               ; aqui, cria-se a dependência em si
      [:service-map])))                                     ; aqui, o vector determina quais componentes são necessários