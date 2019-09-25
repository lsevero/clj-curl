(ns ftp-upload
  (:require [clj-curl.easy :as curl-easy]
            [clj-curl.opts :as opts])
  (:import [clj_curl.Handlers MemReader]))

(defn ftp-upload
  []
  (let [curl (curl-easy/init)
        memreader (MemReader. (str "Text that will go inside the uploaded file.\n" (range 100000)))]
    (do 
      (println "ftp upload")
      (curl-easy/setopt curl opts/url "ftp://speedtest.tele2.net/upload/clj-curl-ftp-upload.txt")
      (curl-easy/setopt curl opts/upload true)
      (curl-easy/setopt curl opts/readfunction memreader)
      (curl-easy/perform curl)
      (println "speed upload: " (curl-easy/getinfo-double curl opts/speed-upload))
      (curl-easy/cleanup curl))))
