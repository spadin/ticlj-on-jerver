(ns ticlj-on-jerver.controller.server)

(def ^:dynamic *port* 11111)

(def server
  (com.jerver.http.server.ServerImpl. *port*))

(defn set-public-directory [server directory]
  (-> server (.setPublicDirectory directory))
  server)

(defn run [server]
  (-> server (.run))
  server)

(defn run-with-public-directory [server directory]
  (-> server
      (set-public-directory directory)
      (run)))
