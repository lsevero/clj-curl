(defproject clj-curl "1.1.2"
  :description "curl for clojure"
  :url "https://github.com/lsevero/clj-curl"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[net.java.dev.jna/jna "5.5.0"]
                 [org.clojure/clojure "1.10.0"]]
  :aot [clj-curl.Handlers
        clj-curl.Exceptions]
  :java-source-paths ["java"]
  :profiles {:dev {:plugins [[cider/cider-nrepl "0.22.3"]
                             [lein-cloverage "1.1.1"]]
                   
                   :global-vars {*warn-on-reflection* true}
                   :main main
                   :repl-options {:init-ns clj-curl.easy}
                   :source-paths ["src" "test" "examples"]}
             :test {:dependencies [[org.clojure/clojure "1.10.0"]]}}
  :source-paths ["src"]
  :repl-options {:port 17020}
  )
