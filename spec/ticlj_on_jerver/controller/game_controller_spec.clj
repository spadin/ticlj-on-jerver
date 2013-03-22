(ns ticlj-on-jerver.controller.game-controller-spec
  (:use [speclj.core]
        [ticlj-on-jerver.controller.game-controller]))

(describe "ticlj-on-jerver.controller.game-controller"
  (with proper-request
    (reify com.jerver.http.request.Request
      (getParam [_ param-key]
        "some-value")))

  (with improper-request
    (reify com.jerver.http.request.Request
      (getParam [_ param-key]
        nil)))

  (it "defines routes on the router"
    (with-redefs [ticlj-on-jerver.api.router/POST
                  (fn [uri callback]
                    (println "POST" uri))]
      (should-contain "POST /"
                      (with-out-str (defroutes)))))

  (it "determines that a header param is empty"
    (let [request @proper-request]
      (should= false
               (empty-param? request "game-type"))))

  (it "determines that a header param is not empty"
    (let [request @improper-request]
      (should= true
               (empty-param? request "game-type"))))

  (it "determines that new game parameters have been passed"
    (let [request @proper-request]
      (should= true
               (game-create-params-set? request)))))
