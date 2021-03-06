(ns simple-multi
  (:require [clj-curl.easy :as easy]
            [clj-curl.multi :as multi]
            [clj-curl.opts :as opts])
  (:import [clj_curl.Handlers MemHandler]
           [com.sun.jna.ptr LongByReference]))

(defn simple-multi
  []
  (let [curls (take 5 (repeatedly #(easy/init)))
        urls ["ftp://speedtest.tele2.net/512KB.zip"
              "ftp://speedtest.tele2.net/1MB.zip"
              "ftp://speedtest.tele2.net/1KB.zip"
              "http://speedtest.tele2.net/1MB.zip"
              "http://speedtest.tele2.net/10MB.zip"]
        handlers (take 5 (repeatedly #(MemHandler.)))
        multi (multi/init)
        int-ptr (LongByReference.)]
    (doall (map easy/setopt curls (repeat opts/url) urls))
    (doall (map easy/setopt curls (repeat opts/writefunction) handlers))
    (doseq [curl curls]
      (multi/add-handle multi curl))
    (loop []
      (if (= opts/e-call-multi-perform (multi/perform multi int-ptr))
        (recur)
        (if (= 0 (.getValue int-ptr))
          (println "Finished to download all files")
          (do
            (println "Downloads remaining " (.getValue int-ptr))
            (Thread/sleep 100)
            (recur)))))))
