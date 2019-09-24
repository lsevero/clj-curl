(ns clj-curl.client-http-test
  (:require [clj-curl.client.http :as http]
            [clojure.test :refer [deftest is testing]]))

(deftest posting-httpbin
  (testing "send a post request to HTTPBIN and get a string response back"
    (let [resp (http/post "http://httpbin.org/post")]
      (is (= (type resp) java.lang.String)))))
