(ns clj4cs-practice.routes
  (:require [ring.util.response :as ring-resp]
            [io.pedestal.http.body-params :as body-params]
            [io.pedestal.http :as http]))

(defn home-page [_]
  (ring-resp/response {:entity {:id 1}}))

(def common-interceptors [(body-params/body-params) http/json-body])

(def routes #{["/" :get (conj common-interceptors `home-page) :route-name :greet]})