(ns clj-curl.client.http
  (:require [clj-curl.easy :as curl-easy]
            [clj-curl.opts :as opts]
            [clojure.data.json :as json])
  (:import clj_curl.Handlers.MemHandler))

(defn post [url & opts]
  (let [curl (curl-easy/init)
        opts (first opts)
        mem (MemHandler.)]
    (when curl
      (curl-easy/setopt curl opts/url url)
      (curl-easy/setopt curl opts/postfields "name=daniel")
      (curl-easy/setopt curl opts/writefunction mem)
      (curl-easy/perform curl)
      (curl-easy/cleanup curl)
      (cond
        (= (:accept opts) :json) (json/read-json @mem)
        :default
        @mem))))

#_(post "http://httpbin.org/post"
        {:body "{\"json\": \"input\"}"
         :headers {"X-Api-Version" "2" "API-Token" "Valor!"}
         :accept :json})
