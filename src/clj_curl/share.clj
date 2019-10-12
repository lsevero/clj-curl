(ns clj-curl.share
  (:require [clj-curl.easy :refer [libcurl]]
            [clj-curl.opts :as opts])
  (:import [clj_curl.Exceptions CurlShareError]
           [com.sun.jna Pointer]))

(defn init
  "https://curl.haxx.se/libcurl/c/curl_share_init.html"
  ^Pointer
  []
  (.invoke (.getFunction libcurl "curl_share_init") Pointer (to-array [])))

(defn cleanup
  "https://curl.haxx.se/libcurl/c/curl_share_cleanup.html"
  ^Long
  [^Pointer share-handle]
  (let [return (.invoke (.getFunction libcurl "curl_share_cleanup") Long (to-array [share-handle]))]
    (if (> return opts/e-ok)
      (throw (CurlShareError. return))
      return)))

(defn strerror
  "https://curl.haxx.se/libcurl/c/curl_share_strerror.html"
  ^String
  [^Long errornum]
  (.invoke (.getFunction libcurl "curl_share_strerror") String (to-array [errornum])))

(defn setopt
  "https://curl.haxx.se/libcurl/c/curl_share_setopt.html"
  ^Long
  [^Pointer share ^Long option parameter]
  (let [return (.invoke (.getFunction libcurl "curl_share_setopt") Long (to-array [share option parameter]))]
    (if (> return opts/e-ok)
      (throw (CurlShareError. return))
      return)))
