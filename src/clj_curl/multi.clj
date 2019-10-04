(ns clj-curl.multi
  (:require [clj-curl.easy :refer [libcurl]]
            [com.sun.jna Pointer]
            [com.sun.jna.ptr LongByReference]))

(defn init
  "https://curl.haxx.se/libcurl/c/curl_multi_init.html"
  ^Pointer
  []
  (.invoke (.getFunction libcurl "curl_multi_init") Pointer (to-array [])))

(defn add-handle
  "https://curl.haxx.se/libcurl/c/curl_multi_add_handle.html"
  ^Integer
  [^Pointer multi-handle ^Pointer easy-handle]
  (.invoke (.getFunction libcurl "curl_multi_add_handle") Integer (to-array [multi-handle easy-handle])))

(defn perform
  "https://curl.haxx.se/libcurl/c/curl_multi_perform.html"
  ^Integer
  [^Pointer multi-handle ^LongByReference running-handles]
  (.invoke (.getFunction libcurl "curl_multi_perform") Integer (to-array [multi-handle running-handles])))

(defn remove-handle
  "https://curl.haxx.se/libcurl/c/curl_multi_remove_handle.html"
  ^Integer
  [^Pointer multi-handle ^Pointer easy-handle]
  (.invoke (.getFunction libcurl "curl_multi_remove_handle") Integer (to-array [multi-handle easy-handle])))

(defn cleanup
  "https://curl.haxx.se/libcurl/c/curl_multi_cleanup.html"
  ^Integer
  [^Pointer multi-handle]
  (.invoke (.getFunction libcurl "curl_multi_cleanup") Integer (to-array [multi-handle])))

(defn strerror
  "https://curl.haxx.se/libcurl/c/curl_multi_strerror.html"
  ^String
  [^Integer errornum]
  (.invoke (.getFunction libcurl "curl_multi_strerror") String (to-array [errornum])))

(defn setopt
  "https://curl.haxx.se/libcurl/c/curl_multi_setopt.html"
  ^Integer
  [^Pointer multi-handle ^Integer option param]
  (.invoke (.getFunction libcurl "curl_multi_setopt") Integer (to-array [multi-handle option param])))
