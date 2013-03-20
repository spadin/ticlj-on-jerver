(ns ticlj-on-jerver.core
  (import com.jerver.http.server.Server))

(def server
  (Server. 11111))

(defn run-server []
  (.run server))

(defn run-with-public-directory [directory]
  (.setPublicDirectory server directory)
  (run-server))
