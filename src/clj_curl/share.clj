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
  ^Integer
  [^Pointer share-handle]
  (let [return (.invoke (.getFunction libcurl "curl_share_cleanup") Integer (to-array [share-handle]))]
    (if (> return opts/e-ok)
      (throw (CurlShareError. return))
      return)))

(defn strerror
  "https://curl.haxx.se/libcurl/c/curl_share_strerror.html"
  ^String
  [^Integer errornum]
  (.invoke (.getFunction libcurl "curl_share_strerror") String (to-array [errornum])))

(defn setopt
  "https://curl.haxx.se/libcurl/c/curl_share_setopt.html"
  ^Integer
  [^Pointer share ^Integer option parameter]
  (let [return (.invoke (.getFunction libcurl "curl_share_setopt") Integer (to-array [share option parameter]))]
    (if (> return opts/e-ok)
      (throw (CurlShareError. return))
      return)))
