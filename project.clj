(defproject clj-curl "0.1.0"
  :description "curl for clojure"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[net.java.dev.jna/jna "5.4.0"]]
  :aot [clj-curl.Handlers]
  :profiles {:dev {:dependencies [[org.clojure/clojure "1.10.0"]]
                   :plugins [[cider/cider-nrepl "0.22.3"]
                             [lein-cloverage "1.1.1"]]
                   :source-paths ["src" "test"]}
             :test {:dependencies [[org.clojure/clojure "1.10.0"]]}}
  :repl-options {:init-ns clj-curl.core})
