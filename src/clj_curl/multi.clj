(ns clj-curl.multi
  (:require [clj-curl.easy :refer [libcurl]]
            [clj-curl.opts :as opts])
  (:import [com.sun.jna Pointer]
           [com.sun.jna.ptr LongByReference]
           [clj_curl.Exceptions CurlMultiError]))

(defn init
  "https://curl.haxx.se/libcurl/c/curl_multi_init.html"
  ^Pointer
  []
  (.invoke (.getFunction libcurl "curl_multi_init") Pointer (to-array [])))

(defn add-handle
  "https://curl.haxx.se/libcurl/c/curl_multi_add_handle.html"
  ^Integer
  [^Pointer multi-handle ^Pointer easy-handle]
  (let [return (.invoke (.getFunction libcurl "curl_multi_add_handle") Integer (to-array [multi-handle easy-handle]))]
    (if (> return opts/e-ok)
      (throw (CurlMultiError. return))
      return)))

(defn perform
  "https://curl.haxx.se/libcurl/c/curl_multi_perform.html"
  ^Integer
  [^Pointer multi-handle ^LongByReference running-handles]
  (let [return (.invoke (.getFunction libcurl "curl_multi_perform") Integer (to-array [multi-handle running-handles]))]
    (if (> return opts/e-ok)
      (throw (CurlMultiError. return))
      return)))

(defn remove-handle
  "https://curl.haxx.se/libcurl/c/curl_multi_remove_handle.html"
  ^Integer
  [^Pointer multi-handle ^Pointer easy-handle]
  (let [return (.invoke (.getFunction libcurl "curl_multi_remove_handle") Integer (to-array [multi-handle easy-handle]))]
    (if (> return opts/e-ok)
      (throw (CurlMultiError. return))
      return)))

(defn cleanup
  "https://curl.haxx.se/libcurl/c/curl_multi_cleanup.html"
  ^Integer
  [^Pointer multi-handle]
  (let [return (.invoke (.getFunction libcurl "curl_multi_cleanup") Integer (to-array [multi-handle]))]
    (if (> return opts/e-ok)
      (throw (CurlMultiError. return))
      return)))

(defn strerror
  "https://curl.haxx.se/libcurl/c/curl_multi_strerror.html"
  ^String
  [^Integer errornum]
  (.invoke (.getFunction libcurl "curl_multi_strerror") String (to-array [errornum])))

(defn setopt
  "https://curl.haxx.se/libcurl/c/curl_multi_setopt.html"
  ^Integer
  [^Pointer multi-handle ^Integer option param]
  (let [return (.invoke (.getFunction libcurl "curl_multi_setopt") Integer (to-array [multi-handle option param]))]
    (if (> return opts/e-ok)
      (throw (CurlMultiError. return))
      return)))
