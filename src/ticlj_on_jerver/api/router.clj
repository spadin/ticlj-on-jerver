(ns ticlj-on-jerver.api.router)

(def router
  com.jerver.http.route.RouterImpl/INSTANCE)

(defn- add-route [router method uri route]
  (-> router (.addRoute method uri route)))

(defn GET [uri route]
  (-> router (add-route "GET" uri route)))

(defn POST [uri route]
  (-> router (add-route "POST" uri route)))
