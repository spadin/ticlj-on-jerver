(ns ticlj-on-jerver.controller.router)

(def router
  com.jerver.http.route.RouterImpl/INSTANCE)

(defn- add-route [router method uri route]
  (-> router (.addRoute method uri route)))

(defn GET [uri route]
  (-> router (add-route "GET" uri route)))

(defn POST [uri route]
  (-> router (add-route "POST" uri route)))

(defrecord GamePlayRoute []
  com.jerver.http.route.Routable
  (resolve [_ request response]
    (println "RESOLVE" request response)))

(def game-play-route (GamePlayRoute.))
