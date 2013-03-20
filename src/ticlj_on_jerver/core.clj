(ns ticlj-on-jerver.core
  (:use [ticlj-on-jerver.controller.server :only [server run-with-public-directory]]))

(defn -main [& args]
  (-> server (run-with-public-directory "public")))
