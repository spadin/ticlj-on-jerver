(ns ticlj-on-jerver.controller.game-controller-spec
  (:use [speclj.core]
        [ticlj-on-jerver.controller.game-controller]))

(describe "ticlj-on-jerver.controller.game-controller"
  (with sample-response (reify com.jerver.http.response.Response
                          (setStatusCode [_ status-code]
                            (println "et-status-code"))
                          (appendHeader [_ header]
                            (println "add-header: " header))
                          (setBody [_ body]
                            (println "set-body"))))

  (with proper-request
    (reify com.jerver.http.request.Request
      (getParam [_ param-key]
        (case param-key
          "game-type" "three-by-three-game"
          "x-player" "human-player"
          "o-player" "human-player"))))

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
    (it "sets the game cookie"
      (let [game {:game-type "a"
                  :x-player  "b"
                  :o-player  "c"
                  :board-str "d"}
            response @sample-response]
        (with-redefs [ticlj-on-jerver.api.response/set-cookie
                      (fn [response k v] (print k))]
          (should= "game"
                   (with-out-str (-> response (set-game-cookie game))))))))

  (context "#create-game-from-params"
    (it "returns a game object"
      (let [request @proper-request]
        (should= {:game-type "three-by-three-game"
                  :x-player  "human-player"
                  :o-player  "human-player"
                  :board-str "---------"}
                 (create-game-from-params request))))

    (it "returns nil"
      (let [request @improper-request]
        (should= nil
                 (create-game-from-params request)))))

  (context "#empty-board-str"
    (it "returns a board with 9 spots for a three-by-three game-type"
      (should= 9
               (count (empty-board-str "three-by-three"))))

    (it "returns a board with 16 spots for a four-by-four game-type"
      (should= 16
               (count (empty-board-str "four-by-four")))))

  (context "#get-game-of-uri-value"
    (it "returns an instance of a game"
      (should (instance? ticlj.game.protocol.Game
                         (get-game-of-uri-value "three-by-three-game")))))

  (context "#get-player-of-uri-value"
    (it "returns an instance of a player"
      (should (instance? ticlj.player.protocol.Player
                         (get-player-of-uri-value "human-player")))))

  (context "#board-str->state"
    (it "converts a string of an empty board to a board-state"
      (should= [:# :# :# :# :# :# :# :# :#]
               (board-str->state "---------")))

    (it "converts a second string board to a board-state"
      (should= [:X :O :# :# :# :# :# :# :#]
               (board-str->state "XO-------")))))
