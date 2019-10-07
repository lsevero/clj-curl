(ns clj-curl.mime
  (:refer-clojure :exclude [name type])
  (:require [clj-curl.easy :refer [libcurl slist-append slist-free-all]]
            [com.sun.jna Pointer Memory NativeLong]))

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

(defn free
  "https://curl.haxx.se/libcurl/c/curl_mime_free.html"
  [^Pointer mime]
  (.invoke (.getFunction libcurl "curl_mime_free") Void (to-array [mime])))

(defn subparts
  "https://curl.haxx.se/libcurl/c/curl_mime_subparts.html"
  ^Integer
  [^Pointer part ^Pointer subparts]
  (.invoke (.getFunction libcurl "curl_mime_subparts") Integer (to-array [part subparts])))

(defn hearders
  "https://curl.haxx.se/libcurl/c/curl_mime_headers.html"
  ^Integer
  [^Pointer part headers take-ownership]
  (if (= clojure.lang.PersistentVector (type headers))
    (let [slist (Memory. NativeLong/SIZE)]
      (doseq [s param]
        (slist-append slist s))
      (let [ret (.invoke (.getFunction libcurl "curl_mime_headers") Integer (to-array [part headers take-ownership]))]
        (slist-free-all slist)
        ret))
    (throw (Exception. "headers should be a vector."))))

(defn filedata
  "https://curl.haxx.se/libcurl/c/curl_mime_filedata.html"
  ^Integer
  [^Pointer part ^String filename]
  (.invoke (.getFunction libcurl "curl_mime_filedata") Integer (to-array [part filename])))

(defn encoder
  "https://curl.haxx.se/libcurl/c/curl_mime_encoder.html"
  ^Integer
  [^Pointer part ^String encoding]
  (.invoke (.getFunction libcurl "curl_mime_encoder") Integer (to-array [part encoding])))

(defn mime-data-cb
  "https://curl.haxx.se/libcurl/c/curl_mime_data_cb.html"
  ^Integer
  ([^Pointer part ^Integer datasize readfunc]
   (mime-data-cb part datasize readfunc Pointer/NULL Pointer/NULL Pointer/NULL))
  ([^Pointer part ^Integer datasize readfunc arg]
   (mime-data-cb part datasize readfunc Pointer/NULL Pointer/NULL arg))
  ([^Pointer part ^Integer datasize readfunc seekfunc arg]
   (mime-data-cb part datasize readfunc seekfunc Pointer/NULL arg))
  ([^Pointer part ^Integer datasize readfunc seekfunc freefunc arg]
   (.invoke (.getFunction libcurl "curl_mime_data_cb") Integer
            (to-array [part datasize readfunc seekfunc freefunc arg]))))
