(ns main
  (:gen-class)
  (:require [simple-http :refer :all]
            [simple-ftp :refer :all]))

(defn -main
  []
  (simple-http)
  (simple-ftp))

