(ns main
  (:gen-class)
  (:require [simple-http :refer :all]
            [simple-ftp :refer :all]
            [ftp-upload :refer :all]
            [copy-file :refer :all]
            [simple-multi :refer :all]))

(defn -main
  []
  (simple-http)
  (simple-ftp)
  (ftp-upload)
  (copy-file)
  (simple-multi))

