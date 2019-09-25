(ns copy-file
  (:require [clj-curl.easy :as curl-easy]
            [clj-curl.opts :as opts])
  (:import [clj_curl.Handlers FileReader]))

(defn copy-file
  []
  (let [filename "/tmp/origin.txt"]
    (spit filename (str (range 100000)))
    (with-open [filereader (FileReader. filename)]
      (let [curl (curl-easy/init)]
        (do 
          (println "copy file")
          (curl-easy/setopt curl opts/url "file:///tmp/destination.txt")
          (curl-easy/setopt curl opts/upload true)
          (curl-easy/setopt curl opts/readfunction filereader)
          (curl-easy/perform curl)
          (println "speed upload: " (curl-easy/getinfo-double curl opts/speed-upload))
          (curl-easy/cleanup curl))))))
