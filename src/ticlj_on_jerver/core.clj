(ns ticlj-on-jerver.core
  (:use [ticlj-on-jerver.controller.server :only [server run set-public-directory]]
        [ticlj-on-jerver.controller.router :only [GET]]))

(defn -main [& args]
  (-> server (set-public-directory "public"))
  (-> server (run)))
