(ns clj-curl.global
  (:require [clj-curl.easy :refer [libcurl]]))

(defn init
  "https://curl.haxx.se/libcurl/c/curl_global_init.html"
  ^Long
  [^Long flags]
  (.invoke (.getFunction libcurl "curl_global_init") Long (to-array [flags])))

(defn cleanup
  "https://curl.haxx.se/libcurl/c/curl_global_cleanup.html"
  []
  (.invoke (.getFunction libcurl "curl_global_cleanup") Void (to-array [])))
