(ns ticlj-on-jerver.api.router-spec
  (:use [speclj.core]
        [ticlj-on-jerver.api.router :only [router]]))

(describe "ticlj-on-jerver.api.router"
  (with sample-router (reify com.jerver.http.route.Router
                       (^void addRoute [_ ^String method ^String uri ^com.jerver.http.route.Routable route]
                         (println "add-route:" method uri))
                       (^void addRoute [_ ^String method ^String uri ^String route]
                         (println "add-route:" method uri))))

  (with sample-route (reify com.jerver.http.route.Routable
                       (resolve [_ request response]
                         (println "RENDER" request response))))

  (it "returns an instance of Router"
    (should (instance? com.jerver.http.route.Router
                       router)))

  (it "calls Router#addRoute with a String"
    (let [router @sample-router
          add-route @#'ticlj-on-jerver.api.router/add-route]
      (should-contain "add-route: GET /"
                      (with-out-str (-> router (add-route "GET" "/" "Hello World"))))))

  (it "calls Router#addRoute with a Routable"
    (let [router @sample-router
          route  @sample-route
          add-route @#'ticlj-on-jerver.api.router/add-route]
      (should-contain "add-route: GET /"
                      (with-out-str (-> router (add-route "GET" "/" route)))))))
