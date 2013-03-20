(defproject org.clojars.sandropadin/ticlj-on-jerver "0.1.0-SNAPSHOT"
  :description "Ticlj Tic Tac Toe game running on the Jerver Java-based Web Server"
  :url "https://github.com/spadin/ticlj-on-jerver"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [hiccup "1.0.2"]
                 [org.clojars.sandropadin/ticlj "0.1.0-SNAPSHOT"]
                 [local/jerver "0.0.1-SNAPSHOT"]]
  :repositories {"project" "file:lib"}
  :profiles {:dev {:dependencies [[speclj "2.5.0"]]}}
  :plugins [[speclj "2.5.0"]]
  :test-paths ["spec"]
  :main ticlj-on-jerver.core)
