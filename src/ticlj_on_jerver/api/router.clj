(ns ticlj-on-jerver.api.router
  (:use [ticlj-on-jerver.api.resolver :only [resolver]]))

(def router
  com.jerver.http.route.RouterImpl/INSTANCE)

(defn- add-route [router method uri route]
  (-> router (.addRoute method uri route)))

(defn GET [uri route]
  (-> router (add-route "GET" uri (resolver route))))

(defn POST [uri route]
  (-> router (add-route "POST" uri (resolver route))))
