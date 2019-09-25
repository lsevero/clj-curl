(ns simple-ftp
  (:require [clj-curl.easy :as curl-easy]
            [clj-curl.opts :as opts])
  (:import [clj_curl.Handlers FileHandler]))

(defn simple-ftp
  []
  ;FileHandler needs to be closed after you finished to use it.
  ;creating it with 'with-open' does that automatically for you.
  (with-open [filehldr (FileHandler. "/tmp/simple.ftp")]
    (let [curl (curl-easy/init)]
      (do 
        (println "simple ftp")
        (curl-easy/setopt curl opts/url "ftp://speedtest.tele2.net/512KB.zip")
        (curl-easy/setopt curl opts/writefunction filehldr)
        (curl-easy/perform curl)
        (println "speed download: " (curl-easy/getinfo-double curl opts/speed-download))
        (curl-easy/cleanup curl)))))
