(ns clj-curl.mime
  (:refer-clojure :exclude [name type])
  (:require [clj-curl.easy :refer [libcurl slist-append slist-free-all]]
            [clj-curl.opts :as opts])
  (:import [clj_curl.Exceptions CurlEasyError]
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
  (.invoke (.getFunction libcurl "curl_mime_addpart") Pointer (to-array [mime])))

(defn name
  "https://curl.haxx.se/libcurl/c/curl_mime_name.html"
  ^Long
  [^Pointer part ^String s]
  (let [return (.invoke (.getFunction libcurl "curl_mime_name") Long (to-array [part s]))]
    (if (> return opts/e-ok)
      (throw (CurlEasyError. return))
      return)))

(defn data
  "https://curl.haxx.se/libcurl/c/curl_mime_data.html"
  ^Long
  ([^Pointer part ^String data]
   (data part data (count data)))
  ([^Pointer part ^String data ^Long size]
   (let [return (.invoke (.getFunction libcurl "curl_mime_data") Long (to-array [part s size]))]
     (if (> return opts/e-ok)
       (throw (CurlEasyError. return))
       return))))

(defn type
  "https://curl.haxx.se/libcurl/c/curl_mime_type.html"
  ^Long
  [^Pointer part ^String mimetype]
  (let [return (.invoke (.getFunction libcurl "curl_mime_type") Long (to-array [part s]))]
    (if (> return opts/e-ok)
      (throw (CurlEasyError. return))
      return)))

(defn filename
  "https://curl.haxx.se/libcurl/c/curl_mime_filename.html"
  ^Long
  [^Pointer part ^String filename]
  (let [return (.invoke (.getFunction libcurl "curl_mime_filename") Long (to-array [part filename]))]
    (if (> return opts/e-ok)
      (throw (CurlEasyError. return))
      return)))

(defn free
  "https://curl.haxx.se/libcurl/c/curl_mime_free.html"
  [^Pointer mime]
  (.invoke (.getFunction libcurl "curl_mime_free") Void (to-array [mime])))

(defn subparts
  "https://curl.haxx.se/libcurl/c/curl_mime_subparts.html"
  ^Long
  [^Pointer part ^Pointer subparts]
  (let [return (.invoke (.getFunction libcurl "curl_mime_subparts") Long (to-array [part subparts]))]
    (if (> return opts/e-ok)
      (throw (CurlEasyError. return))
      return)))

(defn hearders
  "https://curl.haxx.se/libcurl/c/curl_mime_headers.html"
  ^Long
  [^Pointer part headers take-ownership]
  (if (= clojure.lang.PersistentVector (type headers))
    (let [slist (Memory. NativeLong/SIZE)]
      (doseq [s param]
        (slist-append slist s))
      (let [return (.invoke (.getFunction libcurl "curl_mime_headers") Long (to-array [part headers take-ownership]))]
        (slist-free-all slist)
        (if (> return opts/e-ok)
          (throw (CurlEasyError. return))
          return)))
    (throw (Exception. "headers should be a vector."))))

(defn filedata
  "https://curl.haxx.se/libcurl/c/curl_mime_filedata.html"
  ^Long
  [^Pointer part ^String filename]
  (let [return (.invoke (.getFunction libcurl "curl_mime_filedata") Long (to-array [part filename]))]
    (if (> return opts/e-ok)
      (throw (CurlEasyError. return))
      return)))

(defn encoder
  "https://curl.haxx.se/libcurl/c/curl_mime_encoder.html"
  ^Long
  [^Pointer part ^String encoding]
  (let [return (.invoke (.getFunction libcurl "curl_mime_encoder") Long (to-array [part encoding]))]
    (if (> return opts/e-ok)
      (throw (CurlEasyError. return))
      return)))

(defn mime-data-cb
  "https://curl.haxx.se/libcurl/c/curl_mime_data_cb.html"
  ^Long
  ([^Pointer part ^Long datasize readfunc]
   (mime-data-cb part datasize readfunc Pointer/NULL Pointer/NULL Pointer/NULL))
  ([^Pointer part ^Long datasize readfunc arg]
   (mime-data-cb part datasize readfunc Pointer/NULL Pointer/NULL arg))
  ([^Pointer part ^Long datasize readfunc seekfunc arg]
   (mime-data-cb part datasize readfunc seekfunc Pointer/NULL arg))
  ([^Pointer part ^Long datasize readfunc seekfunc freefunc arg]
   (let [return (.invoke (.getFunction libcurl "curl_mime_data_cb") Long
                         (to-array [part datasize readfunc seekfunc freefunc arg]))]
     (if (> return opts/e-ok)
       (throw (CurlEasyError. return))
       return))))
