(ns clj-curl.Handlers
    (:import [com.sun.jna Callback Pointer]
             [java.io ByteArrayOutputStream IOException]))

(gen-class
  :name clj_curl.Handlers.MemHandler
  :implements [com.sun.jna.Callback clojure.lang.IDeref]
  :init init
  :constructors {[] []}
  :state state
  :prefix "memhandler-"
  :methods [[^{Override {}} callback [com.sun.jna.Pointer int int com.sun.jna.Pointer] int]
            [getString [] String]
            [getBytes [] bytes]
            [getSize [] int]
            [deref [] String]])

(defn memhandler-init
  []
  [[] (atom "")])

(defn memhandler-callback
  [this contents size nmemb userp]
  (try
    (let [^int s (* size nmemb)
          ^bytes data (.getByteArray contents 0 s)]
      (reset! (.state this) data)
      s)
    (catch Exception e
      (.printStackTrace e))))

(defn memhandler-getBytes
  [this]
  @(.state this))

(defn memhandler-getString
  [this]
  (String. @(.state this)))

(defn memhandler-getSize
  [this]
  (count @(.state this)))

(defn memhandler-deref
  [this]
  (.getString this))


(gen-class
  :name clj_curl.Handlers.FileHandler
  :implements [com.sun.jna.Callback clojure.lang.IDeref]
  :init init
  :constructors {[String] []}
  :state state
  :prefix "filehandler-"
  :methods [[^{Override {}} callback [com.sun.jna.Pointer int int com.sun.jna.Pointer] int]
            [getString [] String]
            [getBytes [] bytes]
            [getSize [] int]
            [getFilename [] String]
            [deref [] String]])

(defn filehandler-init
  [filename]
  [[] (atom {:filename filename :data ""})])

(defn filehandler-callback
  [this contents size nmemb userp]
  (try
    (let [^int s (* size nmemb)
          ^bytes data (.getByteArray contents 0 s)]
      (do
        (swap! (.state this) assoc :data data)
        (spit (-> @(.state this) :filename) (String. (-> @(.state this) :data)))
        s))
    (catch Exception e
      (.printStackTrace e))))

(defn filehandler-getBytes
  [this]
  (-> @(.state this) :data))

(defn filehandler-getString
  [this]
  (String. (-> @(.state this) :data)))

(defn filehandler-getSize
  [this]
  (count (-> @(.state this) :data)))

(defn filehandler-getFilename
  [this]
  (-> @(.state this) :filename))

(defn filehandler-deref
  [this]
  (.getString this))

