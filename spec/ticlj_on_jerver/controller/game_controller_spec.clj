(ns ticlj-on-jerver.controller.game-controller-spec
  (:use [speclj.core]
        [ticlj-on-jerver.controller.game-controller]))

(describe "ticlj-on-jerver.controller.game-controller"
  (with sample-response (reify com.jerver.http.response.Response
                          (setStatusCode [_ status-code]
                            (println "set-status-code"))
                          (appendHeader [_ header]
                            (println "add-header: " header))
                          (setBody [_ body]
                            (println "set-body"))))

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

  (context "#empty-param?"
    (it "determines that a header param is empty"
      (let [request @proper-request]
        (should= false
                 (empty-param? request "game-type"))))

    (it "determines that a header param is not empty"
      (let [request @improper-request]
        (should= true
                 (empty-param? request "game-type")))))

  (context "#game-create-params-set?"
    (it "determines that new game parameters have been passed"
      (let [request @proper-request]
        (should= true
                 (game-create-params-set? request)))))

  (context "#generate-game-cookie-string"
    (it "returns a cookie-ready string"
      (let [game {:game-type "a"
                  :x-player  "b"
                  :o-player  "c"
                  :board-str "d"}]
        (should= "game-type=a&x-player=b&o-player=c&board-str=d"
                 (game-cookie-str game)))))

  (context "#set-game-cookie"
    (it "sets the game cookiie"
      (let [game {:game-type "a"
                  :x-player  "b"
                  :o-player  "c"
                  :board-str "d"}
            response @sample-response]
        (with-redefs [ticlj-on-jerver.api.response/set-cookie
                      (fn [response k v] k)]
          (should= "game"
                   (-> response (set-game-cookie game))))))))
