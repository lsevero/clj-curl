(ns clj-curl.easy
  (:import [com.sun.jna NativeLibrary Pointer Memory NativeLong]
           [com.sun.jna.ptr PointerByReference DoubleByReference LongByReference]
           [clj_curl.Handlers MemHandler FileHandler])
  (:require [clj-curl.opts :as opts]))

(def ^:private libcurl (com.sun.jna.NativeLibrary/getInstance "curl"))

(def url 10002)
(def handler 20011)

(defn init
  "https://curl.haxx.se/libcurl/c/curl_easy_init.html"
  ^Pointer 
  []
  (.invoke (.getFunction libcurl "curl_easy_init") Pointer (to-array [])))

(defn perform 
  "https://curl.haxx.se/libcurl/c/curl_easy_perform.html"
  ^Integer 
  [^Pointer curl]
  (.invoke (.getFunction libcurl "curl_easy_perform") Integer (to-array [curl])))

(defn cleanup
  "https://curl.haxx.se/libcurl/c/curl_easy_cleanup.html"
  [^Pointer curl]
  (.invoke (.getFunction libcurl "curl_easy_cleanup") Void/TYPE (to-array [])))

(defn getinfo
  "https://curl.haxx.se/libcurl/c/curl_easy_getinfo.html"
  ^Integer 
  [^Pointer curl ^Integer opt param]
  (.invoke (.getFunction libcurl "curl_easy_getinfo") Integer (to-array [curl opt param])))

(defn getinfo-double 
  ^Double
  [^Pointer curl ^Integer opt]
  (let [p-double (DoubleByReference.)]
    (do
      (.invoke (.getFunction libcurl "curl_easy_getinfo") Integer (to-array [curl opt p-double]))
      (.getValue p-double))))

(defn getinfo-long 
  ^Long
  [^Pointer curl ^Integer opt]
  (let [p-long (LongByReference.)]
    (do
      (.invoke (.getFunction libcurl "curl_easy_getinfo") Integer (to-array [curl opt p-long]))
      (.getValue p-long))))

(defn getinfo-string 
  ^String
  [^Pointer curl ^Integer opt]
  (let [p-str (PointerByReference.)]
    (do
      (.invoke (.getFunction libcurl "curl_easy_getinfo") Integer (to-array [curl opt p-str]))
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
  (.invoke (.getFunction libcurl "curl_easy_reset") Void/TYPE (to-array [curl])))

(defn slist-append
  "https://curl.haxx.se/libcurl/c/curl_slist_append.html"
  ^Pointer
  [^Pointer slist ^String s]
  (.invoke (.getFunction libcurl "curl_slist_append") Pointer (to-array [slist s])))

(defn slist-free-all
  "https://curl.haxx.se/libcurl/c/curl_slist_free_all.html"
  ^Pointer
  [^Pointer slist]
  (.invoke (.getFunction libcurl "curl_slist_free_all") Void/TYPE (to-array [slist])))

(defn setopt 
  "https://curl.haxx.se/libcurl/c/curl_easy_setopt.html"
  ^Integer
  [^Pointer curl ^Integer opt param]
  (if (= (type param) clojure.lang.PersistentVector)
    (let [slist (Memory. NativeLong/SIZE)]
      (do
        (doseq [s param]
          ;TODO: raise a exception if the reeturned ptr is NULL
          (slist-append slist s))
        (recur curl opt slist)))
    (.invoke (.getFunction libcurl "curl_easy_setopt") Integer (to-array [curl opt param]))))

(def c (init))
(def m (clj_curl.Handlers.MemHandler.))
(def f (clj_curl.Handlers.FileHandler. "/tmp/pudim.html"))
