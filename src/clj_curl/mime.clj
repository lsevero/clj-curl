(ns clj-curl.mime
  (:refer-clojure :exclude (name type))
  (:require [clj-curl.easy :refer [libcurl]]
            [com.sun.jna Pointer]))

(defn init
  "https://curl.haxx.se/libcurl/c/curl_mime_init.html"
  ^Pointer
  [^Pointer curl]
  (.invoke (.getFunction libcurl "curl_mime_init") Pointer (to-array [curl])))

(defn addpart
  "https://curl.haxx.se/libcurl/c/curl_mime_addpart.html"
  ^Pointer
  [^Pointer mime]
  (.invoke (.getFunction libcurl "curl_mime_addpart") Pointer (to-array [curl])))

(defn name
  "https://curl.haxx.se/libcurl/c/curl_mime_name.html"
  ^Integer
  [^Pointer part ^String s]
  (.invoke (.getFunction libcurl "curl_mime_name") Integer (to-array [part s])))

(defn data
  "https://curl.haxx.se/libcurl/c/curl_mime_data.html"
  ^Integer
  ([^Pointer part ^String data]
   (data part data (count data)))
  ([^Pointer part ^String data ^Integer size]
   (.invoke (.getFunction libcurl "curl_mime_data") Integer (to-array [part s size]))))

(defn type
  "https://curl.haxx.se/libcurl/c/curl_mime_type.html"
  ^Integer
  [^Pointer part ^String mimetype]
  (.invoke (.getFunction libcurl "curl_mime_type") Integer (to-array [part s])))

(defn filename
  "https://curl.haxx.se/libcurl/c/curl_mime_filename.html"
  ^Integer
  [^Pointer part ^String filename]
  (.invoke (.getFunction libcurl "curl_mime_filename") Integer (to-array [part filename])))

;TODO curl_mime_headers, curl_mime_subparts, curl_mime_free, curl_mime_data_cb, curl_mime_encoder, curl_mime_filedata

