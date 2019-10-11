(ns clj-curl.easy
  (:refer-clojure :exclude [send])
  (:import [com.sun.jna NativeLibrary Pointer Memory NativeLong]
           [com.sun.jna.ptr PointerByReference DoubleByReference LongByReference]
           [clj_curl.Exceptions CurlEasyError])
  (:require [clj-curl.opts :as opts]))

(def libcurl (com.sun.jna.NativeLibrary/getInstance "curl"))

(defn init
  "https://curl.haxx.se/libcurl/c/curl_easy_init.html"
  ^Pointer 
  []
  (.invoke (.getFunction libcurl "curl_easy_init") Pointer (to-array [])))

(defn perform 
  "https://curl.haxx.se/libcurl/c/curl_easy_perform.html"
  ^Integer 
  [^Pointer curl]
  (let [return (.invoke (.getFunction libcurl "curl_easy_perform") Integer (to-array [curl]))]
    (if (> return opts/e-ok)
      (throw (CurlEasyError. return))
      return)))

(defn cleanup
  "https://curl.haxx.se/libcurl/c/curl_easy_cleanup.html"
  [^Pointer curl]
  (.invoke (.getFunction libcurl "curl_easy_cleanup") Void (to-array [curl])))

(defn getinfo
  "https://curl.haxx.se/libcurl/c/curl_easy_getinfo.html"
  ^Integer 
  [^Pointer curl ^Integer opt param]
  (let [return (.invoke (.getFunction libcurl "curl_easy_getinfo") Integer (to-array [curl opt param]))]
    (if (> return opts/e-ok)
      (throw (CurlEasyError. return))
      return)))

(defn getinfo-double 
  ^Double
  [^Pointer curl ^Integer opt]
  (let [p-double (DoubleByReference.)
        return (.invoke (.getFunction libcurl "curl_easy_getinfo") Integer (to-array [curl opt p-double]))]
    (if (> return opts/e-ok)
      (throw (CurlEasyError. return))
      (.getValue p-double))))

(defn getinfo-long 
  ^Long
  [^Pointer curl ^Integer opt]
  (let [p-long (LongByReference.)
        return (.invoke (.getFunction libcurl "curl_easy_getinfo") Integer (to-array [curl opt p-long]))]
    (if (> return (CurlEasyError. return))
      (throw (CurlEasyError. return))
      (.getValue p-long))))

(defn getinfo-string 
  ^String
  [^Pointer curl ^Integer opt]
  (let [p-str (PointerByReference.)
        return (.invoke (.getFunction libcurl "curl_easy_getinfo") Integer (to-array [curl opt p-str]))]
    (if (> return opts/e-ok)
      (throw (CurlEasyError. return))
      (let [value (.getValue p-str)]
        (if (= value Pointer/NULL) "" value)))))

(defn duphandle
  "https://curl.haxx.se/libcurl/c/curl_easy_duphandle.html"
  ^Pointer
  [^Pointer curl]
  (.invoke (.getFunction libcurl "curl_easy_duphandle") Pointer (to-array [curl])))

(defn reset
  "https://curl.haxx.se/libcurl/c/curl_easy_reset.html"
  [^Pointer curl]
  (.invoke (.getFunction libcurl "curl_easy_reset") Void (to-array [curl])))

(defn slist-append
  "https://curl.haxx.se/libcurl/c/curl_slist_append.html"
  ^Pointer
  [^Pointer slist ^String s]
  (.invoke (.getFunction libcurl "curl_slist_append") Pointer (to-array [slist s])))

(defn slist-free-all
  "https://curl.haxx.se/libcurl/c/curl_slist_free_all.html"
  ^Pointer
  [^Pointer slist]
  (.invoke (.getFunction libcurl "curl_slist_free_all") Void (to-array [slist])))

(defn setopt 
  "https://curl.haxx.se/libcurl/c/curl_easy_setopt.html"
  ^Integer
  [^Pointer curl ^Integer opt param]
  (if (= (type param) clojure.lang.PersistentVector)
    (let [slist (Memory. NativeLong/SIZE)]
      (do
        (doseq [s param]
          (slist-append slist s))
        (let [return (.invoke (.getFunction libcurl "curl_easy_setopt") Integer (to-array [curl opt slist]))]
          (slist-free-all slist)
          (if (> return opts/e-ok)
            (throw (CurlEasyError. return))
            return))))
    (let [return (.invoke (.getFunction libcurl "curl_easy_setopt") Integer (to-array [curl opt param]))]
      (if (> return opts/e-ok)
        (throw (CurlEasyError. return))
        return))))

(defn escape
  "https://curl.haxx.se/libcurl/c/curl_easy_escape.html"
  ^String
  ([^Pointer curl ^String s]
   (escape curl s (count s)))
  ([^Pointer curl ^String s ^Integer length]
   (.invoke (.getFunction libcurl "curl_easy_escape") String (to-array [curl s length]))))

(defn unescape
  "https://curl.haxx.se/libcurl/c/curl_easy_unescape.html"
  ^String
  [^Pointer curl ^String s]
  (.invoke (.getFunction libcurl "curl_easy_unescape") String (to-array [curl s 0 Pointer/NULL])))

(defn getdate
  "https://curl.haxx.se/libcurl/c/curl_getdate.html"
  ^Integer
  [^String date-str]
  (.invoke (.getFunction libcurl "curl_getdate") String (to-array [date-str Pointer/NULL])))

(defn version
  "https://curl.haxx.se/libcurl/c/curl_version.html"
  ^String
  []
  (.invoke (.getFunction libcurl "curl_version") String (to-array [])))

(defn pause
  "https://curl.haxx.se/libcurl/c/curl_easy_pause.html"
  ^Integer
  [^Pointer curl ^Integer bitmask]
  (let [return (.invoke (.getFunction libcurl "curl_easy_pause") Integer (to-array [curl bitmask]))]
    (if (> return opts/e-ok)
      (throw (CurlEasyError. return))
      return)))

(defn send
  "https://curl.haxx.se/libcurl/c/curl_easy_send.html"
  ^Integer
  ([^Pointer curl buffer ^LongByReference n]
   (send curl buffer (count buffer) n))
  ([^Pointer curl buffer ^Integer buflen ^LongByReference n]
   (let [return (.invoke (.getFunction libcurl "curl_easy_send") Integer (to-array [curl buffer buflen n]))]
     (if (> return opts/e-ok)
       (throw (CurlEasyError. return))
       return))))

(defn recv
  "https://curl.haxx.se/libcurl/c/curl_easy_recv.html"
  ^Integer
  [^Pointer curl buffer ^Integer buflen ^LongByReference n]
  (let [return (.invoke (.getFunction libcurl "curl_easy_recv") Integer (to-array [curl buffer buflen n]))]
    (if (> return opts/e-ok)
      (throw (CurlEasyError. return))
      return)))

(defn strerror
  "https://curl.haxx.se/libcurl/c/curl_easy_strerror.html"
  ^String
  [^Integer errornum]
  (.invoke (.getFunction libcurl "curl_easy_strerror") String (to-array [errornum])))

(defn upkeep
  "https://curl.haxx.se/libcurl/c/curl_easy_upkeep.html"
  ^Integer
  [^Pointer curl]
  (let [return (.invoke (.getFunction libcurl "curl_easy_upkeep") Integer (to-array [curl]))]
    (if (> return opts/e-ok)
      (throw (CurlEasyError. return))
      return)))
