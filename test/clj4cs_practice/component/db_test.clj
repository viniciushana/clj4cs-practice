(ns clj4cs-practice.component.db-test
  (:require [clojure.test :refer :all]
            [clj4cs-practice.component.db :as db]
            [com.stuartsierra.component :as component]
            [matcher-combinators.test :refer [match?]]
            [matcher-combinators.matchers :as m]))


(deftest db-compnent
  (testing "db component is stateful"
    (let [comp (component/start (db/new-db))]
      (db/store comp {:coisa 1})
      (db/store comp {:coisa 2})
      (db/store comp {:coisa 3})
      (db/store comp {:coisa 4})
      (is (match? [{:coisa 1} {:coisa 2} {:coisa 3} {:coisa 4}]
                  (db/query comp identity)))
      (is (match? [{:coisa 2} {:coisa 4}]
                  (db/query comp #(-> % :coisa even?)))))))