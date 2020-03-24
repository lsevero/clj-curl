(ns simple-http
  (:require [clj-curl.easy :as curl-easy]
            [clj-curl.opts :as opts]
            [clj-curl.global :as curl-global])
  (:import [clj_curl.Handlers MemHandler]))


(defn simple-http []
  (let [curl (curl-easy/init)
        mem (MemHandler.)]
    (do
      (curl-global/init opts/global-all)
      (println "Simple http")
      ;clj-curl work exactly the same as the C api
      ;this library will try to expose all of the functionality of libcurl as possible
      ;even if is not the "clojure" way of doing things
      (curl-easy/setopt curl opts/url "http://www.duckduckgo.com")
      ;There is no slists in clj-curl, use vectors instead
      (curl-easy/setopt curl opts/httpheader ["Shoesize: 1234"
                                              "Omg: lol"
                                              "Another: header"])
      (curl-easy/setopt curl opts/writefunction mem)
      (curl-easy/perform curl)
      ;the normal getinfo function will return the result through pointer reference
      ;using the JNA this is done with the DoubleByReference class, but it is very ugly
      ;I have added these getinfo wrapper functions to make things a little bit easier
      ;without going down too much on the low level stuff
      (println "speed download: " (curl-easy/getinfo-double curl opts/speed-download))
      ;deref-ing a MemHandler will extract the handler contents and convert it to a string
      ;another little helper
      (println "contents: " @mem)
      (println "Curl version: " (curl-easy/version))
      (curl-easy/cleanup curl))))
