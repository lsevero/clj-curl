(ns clj-curl.Exceptions)

(def easy-errors {1 "CURLE_UNSUPPORTED_PROTOCOL"
                  2 "CURLE_FAILED_INIT"
                  3 "CURLE_URL_MALFORMAT"
                  4 "CURLE_NOT_BUILT_IN"
                  5 "CURLE_COULDNT_RESOLVE_PROXY"
                  6 "CURLE_COULDNT_RESOLVE_HOST"
                  7 "CURLE_COULDNT_CONNECT"
                  8 "CURLE_FTP_WEIRD_SERVER_REPLY"
                  9 "CURLE_REMOTE_ACCESS_DENIED"
                  10 "CURLE_FTP_ACCEPT_FAILED"
                  11 "CURLE_FTP_WEIRD_PASS_REPLY"
                  12 "CURLE_FTP_ACCEPT_TIMEOUT"
                  13 "CURLE_FTP_WEIRD_PASV_REPLY"
                  14 "CURLE_FTP_WEIRD_227_FORMAT"
                  15 "CURLE_FTP_CANT_GET_HOST"
                  16 "CURLE_HTTP2"
                  17 "CURLE_FTP_COULDNT_SET_TYPE"
                  18 "CURLE_PARTIAL_FILE"
                  19 "CURLE_FTP_COULDNT_RETR_FILE"
                  21 "CURLE_QUOTE_ERROR"
                  22 "CURLE_HTTP_RETURNED_ERROR"
                  23 "CURLE_WRITE_ERROR"
                  25 "CURLE_UPLOAD_FAILED"
                  26 "CURLE_READ_ERROR"
                  27 "CURLE_OUT_OF_MEMORY"
                  28 "CURLE_OPERATION_TIMEDOUT"
                  30 "CURLE_FTP_PORT_FAILED"
                  31 "CURLE_FTP_COULDNT_USE_REST"
                  33 "CURLE_RANGE_ERROR"
                  34 "CURLE_HTTP_POST_ERROR"
                  35 "CURLE_SSL_CONNECT_ERROR"
                  36 "CURLE_BAD_DOWNLOAD_RESUME"
                  37 "CURLE_FILE_COULDNT_READ_FILE"
                  38 "CURLE_LDAP_CANNOT_BIND"
                  39 "CURLE_LDAP_SEARCH_FAILED"
                  41 "CURLE_FUNCTION_NOT_FOUND"
                  42 "CURLE_ABORTED_BY_CALLBACK"
                  43 "CURLE_BAD_FUNCTION_ARGUMENT"
                  45 "CURLE_INTERFACE_FAILED"
                  47 "CURLE_TOO_MANY_REDIRECTS"
                  48 "CURLE_UNKNOWN_OPTION"
                  49 "CURLE_TELNET_OPTION_SYNTAX"
                  52 "CURLE_GOT_NOTHING"
                  53 "CURLE_SSL_ENGINE_NOTFOUND"
                  54 "CURLE_SSL_ENGINE_SETFAILED"
                  55 "CURLE_SEND_ERROR"
                  56 "CURLE_RECV_ERROR"
                  58 "CURLE_SSL_CERTPROBLEM"
                  59 "CURLE_SSL_CIPHER"
                  60 "CURLE_PEER_FAILED_VERIFICATION"
                  61 "CURLE_BAD_CONTENT_ENCODING"
                  62 "CURLE_LDAP_INVALID_URL"
                  63 "CURLE_FILESIZE_EXCEEDED"
                  64 "CURLE_USE_SSL_FAILED"
                  65 "CURLE_SEND_FAIL_REWIND"
                  66 "CURLE_SSL_ENGINE_INITFAILED"
                  67 "CURLE_LOGIN_DENIED"
                  68 "CURLE_TFTP_NOTFOUND"
                  69 "CURLE_TFTP_PERM"
                  70 "CURLE_REMOTE_DISK_FULL"
                  71 "CURLE_TFTP_ILLEGAL"
                  72 "CURLE_TFTP_UNKNOWNID"
                  73 "CURLE_REMOTE_FILE_EXISTS"
                  74 "CURLE_TFTP_NOSUCHUSER"
                  75 "CURLE_CONV_FAILED"
                  76 "CURLE_CONV_REQD"
                  77 "CURLE_SSL_CACERT_BADFILE"
                  78 "CURLE_REMOTE_FILE_NOT_FOUND"
                  79 "CURLE_SSH"
                  80 "CURLE_SSL_SHUTDOWN_FAILED"
                  81 "CURLE_AGAIN"
                  82 "CURLE_SSL_CRL_BADFILE"
                  83 "CURLE_SSL_ISSUER_ERROR"
                  84 "CURLE_FTP_PRET_FAILED"
                  85 "CURLE_RTSP_CSEQ_ERROR"
                  86 "CURLE_RTSP_SESSION_ERROR"
                  87 "CURLE_FTP_BAD_FILE_LIST"
                  88 "CURLE_CHUNK_FAILED"
                  89 "CURLE_NO_CONNECTION_AVAILABLE"
                  90 "CURLE_SSL_PINNEDPUBKEYNOTMATCH"
                  91 "CURLE_SSL_INVALIDCERTSTATUS"
                  92 "CURLE_HTTP2_STREAM"
                  93 "CURLE_RECURSIVE_API_CALL"
                  94 "CURLE_AUTH_ERROR"})

(def share-errors {1 "CURLSHE_BAD_OPTION"
                   2 "CURLSHE_IN_USE"
                   3 "CURLSHE_INVALID"
                   4 "CURLSHE_NOMEM"
                   5 "CURLSHE_NOT_BUILT_IN"})

(def multi-errors {1 "CURLM_BAD_HANDLE"
                   2 "CURLM_BAD_EASY_HANDLE"
                   3 "CURLM_OUT_OF_MEMORY"
                   4 "CURLM_INTERNAL_ERROR"
                   5 "CURLM_BAD_SOCKET"
                   6 "CURLM_UNKNOWN_OPTION"
                   7 "CURLM_ADDED_ALREADY"
                   8 "CURLM_RECURSIVE_API_CALL"})

(gen-class
  :name clj_curl.Exceptions.CurlEasyError
  :extends RuntimeException
  :init init
  :constructors {[Long] [String]}
  :state state
  :prefix "easy-error-" 
  :methods [[getCURLcode [] Long]
            [getCURLcodeName [] String]])

(defn easy-error-init
  [errornum]
  [[(str "CURL EASY ERROR: " (easy-errors errornum))] errornum])

(defn easy-error-getCURLcode
  [this]
  (.state this))

(defn easy-error-getCURLcodeName
  [this]
  (easy-errors (.state this)))

(gen-class
  :name clj_curl.Exceptions.CurlShareError
  :extends RuntimeException
  :init init
  :constructors {[Long] [String]}
  :state state
  :prefix "share-error-" 
  :methods [[getCURLcode [] Long]
            [getCURLcodeName [] String]])

(defn share-error-init
  [errornum]
  [[(str "CURL SHARE ERROR: " (share-errors errornum))] errornum])

(defn share-error-getCURLSHcode
  [this]
  (.state this))

(defn share-error-getCURLSHcodeName
  [this]
  (share-errors (.state this)))

(gen-class
  :name clj_curl.Exceptions.CurlMultiError
  :extends RuntimeException
  :init init
  :constructors {[Long] [String]}
  :state state
  :prefix "multi-error-" 
  :methods [[getCURLcode [] Long]
            [getCURLcodeName [] String]])

(defn multi-error-init
  [errornum]
  [[(str "CURL MULTI ERROR: " (multi-errors errornum))] errornum])

(defn multi-error-getCURLSHcode
  [this]
  (.state this))

(defn multi-error-getCURLSHcodeName
  [this]
  (multi-errors (.state this)))
