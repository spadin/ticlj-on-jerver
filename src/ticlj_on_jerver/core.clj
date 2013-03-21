(ns ticlj-on-jerver.core
  (:use [ticlj-on-jerver.api.server :only [server run set-public-directory]]
        [ticlj-on-jerver.api.router :only [GET]]))


(defn -main [& args]
  (-> server (set-public-directory "public"))
  (-> server (run)))
