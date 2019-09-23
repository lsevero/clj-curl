(ns clj-curl.core
  (:import [com.sun.jna NativeLibrary Pointer]
           [clj_curl.Handlers MemHandler FileHandler]))

(def ^:private libcurl (com.sun.jna.NativeLibrary/getInstance "curl"))

(def url 10002)
(def handler 20011)

(defn easy-init ^Pointer
  []
  (.invoke (.getFunction libcurl "curl_easy_init") Pointer (to-array [])))

(defn easy-setopt ^Integer
  [^Pointer curl ^Integer i ^Object param]
  (.invoke (.getFunction libcurl "curl_easy_setopt") Integer (to-array [curl i param])))

(defn easy-perform ^Integer
  [^Pointer curl]
  (.invoke (.getFunction libcurl "curl_easy_perform") Integer (to-array [curl])))

(def c (easy-init))
(def m (clj_curl.Handlers.MemHandler.))
(def f (clj_curl.Handlers.FileHandler. "/tmp/pudim.html"))
