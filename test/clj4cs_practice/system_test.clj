(ns clj4cs-practice.system-test
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.test :refer [response-for]]
            [com.stuartsierra.component :as component]
            [clojure.test :refer :all]
            [clj4cs-practice.routes :as routes]
            [clj4cs-practice.system :as system]
            [clj4cs-practice.component.pedestal :refer :all]
            [cheshire.core :as json]))

(def url-for (route/url-for-routes
               (route/expand-routes routes/routes)))

(defn service-fn
  [system]
  (get-in system [:pedestal :service ::http/service-fn]))

(defmacro with-system
  [[bound-var binding-expr] & body]
  `(let [~bound-var (component/start ~binding-expr)]
     (try
       ~@body
       (finally
         (component/stop ~bound-var)))))

(deftest healthcheck-test
  (with-system [sut (system/new-system :test)]
               (let [service               (service-fn sut)
                     {:keys [status body]} (response-for service
                                                         :post
                                                         (url-for :healthcheck)
                                                         :headers {"Content-Type" "application/json"}
                                                         :body (json/encode {:a 1}))]
                 (is (= status 200))
                 (is (= body "{\"requested\":{\"a\":1},\"db\":[]}")))))