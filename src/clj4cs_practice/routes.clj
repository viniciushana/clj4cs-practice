(ns clj4cs-practice.routes
  (:require [ring.util.response :as ring-resp]
            [io.pedestal.http.body-params :as body-params]
            [io.pedestal.http :as http]))

(def common-interceptors [(body-params/body-params) http/json-body])

(defn healthcheck [request]
  (ring-resp/response {:requested (:json-params request)
                       :db        (-> request :db :state deref)}))

(def routes #{["/healthcheck" :post (conj common-interceptors `healthcheck) :route-name :healthcheck]})