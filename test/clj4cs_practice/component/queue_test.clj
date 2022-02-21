(ns clj4cs-practice.component.queue-test
  (:require [clojure.test :refer :all]
            [clj4cs-practice.component.queue :as queue]
            [com.stuartsierra.component :as component]))

(deftest queue-test
  (testing "queue is stateful and FIFO"
    (let [comp (component/start (queue/new-queue))]
      (queue/produce comp {:a 1})
      (queue/produce comp {:b 2})
      (is (= {:a 1} (queue/consume comp)))
      (is (= {:b 2} (queue/consume comp))))))