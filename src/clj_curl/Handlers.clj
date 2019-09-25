(ns clj-curl.Handlers
    (:import [com.sun.jna Callback Pointer]
             [java.io ByteArrayInputStream ByteArrayOutputStream File FileOutputStream]))

(gen-class
  ;This class opens a buffer and write all the received data to it.
  ;Made to be used with WRITEFUNCTION.
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
  [[] (ByteArrayOutputStream.)])

(defn memhandler-callback
  [this contents size nmemb userp]
  (try
    (let [^int s (* size nmemb)
          ^bytes data (.getByteArray contents 0 s)]
      (.write (.state this) data)
      s)
    (catch Exception e
      (.printStackTrace e))))

(defn memhandler-getBytes
  [this]
  (.toByteArray (.state this)))

(defn memhandler-getString
  [this]
  (.toString (.state this)))

(defn memhandler-getSize
  [this]
  (count (.getBytes this)))

(defn memhandler-deref
  [this]
  (.getString this))

(gen-class
  ;This class opens a file and write all the received data to it.
  ;It was made to be used with WRITEFUNCTION.
  ;FileHandler will append all of the data to the file named filename even if it already exists. 
  :name clj_curl.Handlers.FileHandler
  :implements [com.sun.jna.Callback]
  :init init
  :constructors {[String] []}
  :state state
  :prefix "filehandler-"
  :methods [[^{Override {}} callback [com.sun.jna.Pointer int int com.sun.jna.Pointer] int]
            [getFilename [] String]])

(defn filehandler-init
  [filename]
  [[] filename])

(defn filehandler-callback
  [this contents size nmemb userp]
  (try
    (let [^int s (* size nmemb)
          ^bytes data (.getByteArray contents 0 s)]
      (do
        (with-open [file (FileOutputStream. (File. (.getFilename this)) true)]
          (.write file data))
        s))
    (catch Exception e
      (.printStackTrace e))))

(defn filehandler-getFilename
  [this]
  (.state this))

(gen-class
  ;This class opens a populated buffer and waits for something consume its data.
  ;Made to be used with READFUNCTION
  :name clj_curl.Handlers.MemReader
  :implements [com.sun.jna.Callback]
  :init init
  :constructors {[bytes] []
                 [String] []}
  :state state
  :prefix "memreader-"
  :methods [[^{Override {}} callback [com.sun.jna.Pointer int int com.sun.jna.Pointer] int]])

(defn memreader-init 
  [array]
  [[] (ByteArrayInputStream. (byte-array (map byte array)))])

(defn memreader-callback
  [this contents size nmemb userp]
  (try
    (let [^int s (* size nmemb)
          ^bytes buffer (byte-array s nil)
          ^int n-read-bytes (.read (.state this) buffer 0 s)]
      (if (= -1 n-read-bytes)
        0
        (do
          (.write contents 0 buffer 0 n-read-bytes)
          n-read-bytes)))
    (catch Exception e
      (.printStackTrace e))))
