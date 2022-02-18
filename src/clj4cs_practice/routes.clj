(ns clj4cs-practice.routes
  (:require [ring.util.response :as ring-resp]
            [io.pedestal.http.body-params :as body-params]
            [io.pedestal.http :as http]))

(defn home-page [{json :json-params}]
  (ring-resp/response {:entity (assoc json :responded "ok")}))

(def common-interceptors [(body-params/body-params) http/json-body])

(def routes #{["/" :post (conj common-interceptors `home-page) :route-name :greet]})