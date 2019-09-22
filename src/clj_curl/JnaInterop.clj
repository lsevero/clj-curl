(ns clj-curl.JnaInterop
    (:import [com.sun.jna Callback Pointer]
             [java.io ByteArrayOutputStream IOException]))

(gen-interface :name clj_curl.JnaInterop.Handler 
               :extends [com.sun.jna.Callback]
               :methods [[handler [com.sun.jna.Pointer int int com.sun.jna.Pointer] int]])

(gen-class
 :name clj_curl.JnaInterop.MemHandler
 ;:extends [com.sun.jna.Callback]
 :implements [com.sun.jna.Callback]
 :init init
 :constructors {[] []}
 :state state
 :prefix "memhandler-"
 :method [[handler [com.sun.jna.Pointer int int com.sun.jna.Pointer] int]
          [getString [] String]
          [getBytes [] bytes]]
          [getSize [] int]
          [reset [] void]) 

(defn memhandler-init
  []
  [[] (ByteArrayOutputStream.)])

(defn memhandler-handler
  [this contents size nmemb userp]
  (let [^int s (* size nmemb)
        ^bytes data (.getByteArray contents 0 s)]
    (try
      (.write (.state this) data)
      (catch IOException e
        (.printStackTrace e)))))

(defn memhandler-getData
  [this]
  (.toByteArray (.state this)))

(defn memhandler-getString
  [this]
  (String. (.toByteArray (.state this))))

(defn memhandler-reset
  [this]
  (.reset (.state this)))

(defn memhandler-close
  [this]
  (try
    (.close (.state this))
    (catch Exception e
      (.printStackTrace e))))
