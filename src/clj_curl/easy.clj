(ns clj-curl.easy
  (:import [com.sun.jna NativeLibrary Pointer]
           [com.sun.jna.ptr PointerByReference DoubleByReference LongByReference]
           [clj_curl.Handlers MemHandler FileHandler]))

(def ^:private libcurl (com.sun.jna.NativeLibrary/getInstance "curl"))

(def url 10002)
(def handler 20011)

(defn init ^Pointer
  []
  (.invoke (.getFunction libcurl "curl_easy_init") Pointer (to-array [])))

(defn setopt ^Integer
  [^Pointer curl ^Integer opt param]
  (.invoke (.getFunction libcurl "curl_easy_setopt") Integer (to-array [curl opt param])))

(defn perform ^Integer
  [^Pointer curl]
  (.invoke (.getFunction libcurl "curl_easy_perform") Integer (to-array [curl])))

(defn cleanup
  [^Pointer curl]
  (.invoke (.getFunction libcurl "curl_easy_cleanup") Void/TYPE (to-array [])))

(defn getinfo ^Integer
  [^Pointer curl ^Integer opt param]
  (.invoke (.getFunction libcurl "curl_easy_getinfo") Integer (to-array [curl opt param])))

(defn getinfo-double ^Double
  [^Pointer curl ^Integer opt]
  (let [p-double (DoubleByReference.)]
    (do
      (.invoke (.getFunction libcurl "curl_easy_getinfo") Integer (to-array [curl opt p-double]))
      (.getValue p-double))))

(defn getinfo-long ^Long
  [^Pointer curl ^Integer opt]
  (let [p-long (LongByReference.)]
    (do
      (.invoke (.getFunction libcurl "curl_easy_getinfo") Integer (to-array [curl opt p-long]))
      (.getValue p-long))))

(defn getinfo-string ^String
  [^Pointer curl ^Integer opt]
  (let [p-str (PointerByReference.)]
    (do
      (.invoke (.getFunction libcurl "curl_easy_getinfo") Integer (to-array [curl opt p-str]))
      (let [value (.getValue p-str)]
        (if (= value Pointer/NULL) "" value)))))

(defn duphandle
  [^Pointer curl]
  (.invoke (.getFunction libcurl "curl_easy_duphandle") Pointer (to-array [curl])))

(def c (init))
(def m (clj_curl.Handlers.MemHandler.))
(def f (clj_curl.Handlers.FileHandler. "/tmp/pudim.html"))
