(ns ticlj-on-jerver.controller.game-controller
  (:use [ticlj-on-jerver.api.router   :only [GET POST]]
        [ticlj-on-jerver.api.response :only [redirect set-cookie]]
        [ticlj-on-jerver.api.request  :only [get-param get-cookie
                                             build-key-val-map]]
        [ticlj-on-jerver.api.render   :only [render]]
        [ticlj-on-jerver.view.helper  :only [*view-context*]]
        [ticlj.game.protocol          :only [empty-board-state
                                             set-mark-at-index
                                             next-possible-mark
                                             gameover? winner]]
        [ticlj.player                 :only [available-player-types
                                             get-player-of-type]]
        [ticlj.game                   :only [available-game-types
                                             get-game-of-type]]
        [clojure.string               :only [split]]

        [ticlj.game.protocol :only [empty-board-state
                                    set-mark-at-index
                                    next-possible-mark
                                    gameover? winner]]
        [ticlj.player.protocol :only [move]]))

(defn empty-param? [request param]
  (empty? (-> request (get-param param))))

(defn- find-first-that-contains [s coll]
  (first (filter #(.contains (:value %) s) coll)))

(defn get-game-of-uri-value [s]
  (let [game-str (:value (find-first-that-contains s available-game-types))]
    (get-game-of-type game-str)))

(defn get-player-of-uri-value [s]
  (let [player-str (:value (find-first-that-contains s available-player-types))]
    (get-player-of-type player-str)))

(defn board-str->state [s]
  (reduce (fn [memo val]
            (conj memo (keyword (if (= "-" (str val)) "#" (str val)))))
          [] s))

(defn board-state->str [board-state]
  (reduce #(str %1 (clojure.string/replace (name %2) #"#" "-")) "" board-state))

(defn empty-board-str [game-type]
  (let [game (get-game-of-uri-value game-type)
        board-state (empty-board-state game)]
    (board-state->str board-state)))

(defn game-create-params-set? [request]
  (and
    (not (-> request (empty-param? "game-type")))
    (not (-> request (empty-param? "x-player")))
    (not (-> request (empty-param? "o-player")))))

(defn create-game-from-params [request]
  (if (-> request (game-create-params-set?))
    (let [game-type-str (-> request (get-param "game-type"))]
      {:game-type game-type-str
       :x-player  (-> request (get-param "x-player"))
       :o-player  (-> request (get-param "o-player"))
       :board-str (empty-board-str game-type-str)})))

(defn game-cookie-str [game]
  (str "game-type=" (:game-type game) "&"
       "x-player="  (:x-player  game) "&"
       "o-player="  (:o-player  game) "&"
       "board-str=" (:board-str game)))

(defn set-game-cookie [response game]
  (-> response (set-cookie "game" (game-cookie-str game)))
  response)

(defn set-game-in-view-context [view-context game]
  (merge view-context {:game game}))

(defn get-game-from-cookie [request]
  (let [cookie-str (-> request (get-cookie :game))]
    (if-not (empty? cookie-str)
      (let [cookie-pairs (split cookie-str #"&")]
        (build-key-val-map cookie-pairs)))))

; Route handlers
; ==============

; POST /game/create
(defn handle-game-create [request response]
  (if-let [game (create-game-from-params request)]
    (-> response
        (set-game-cookie game)
        (redirect "/game/play"))
    (-> response (redirect "/"))))

(defn handle-game-play [request response]
  (if-let [game-cookie (-> request (get-game-from-cookie))]
    (let [game (get-game-of-uri-value (:game-type game-cookie))
          board-state (board-str->state (:board-str game-cookie))
          gameover? (gameover? game board-state)
          winner (winner game board-state)
          player-str-coll {:X (:x-player game-cookie) :O (:o-player game-cookie)}
          next-mark (next-possible-mark game board-state)
          player-str (get player-str-coll next-mark)
          meta-refresh (if (and (not gameover?) (not= "human-player" player-str)) {:seconds 1 :url "/game/move"} nil)
          interactive-move? (if (and (not gameover?) (nil? meta-refresh)) true false)
          winning-message (if (and gameover? winner)
                            (str "Game over, " (name winner) " has won.")
                            (if gameover?
                              (str "Game over, tied game")
                              nil))]
      (-> response (render "play" (assoc game-cookie
                                         :meta-refresh meta-refresh
                                         :interactive-move? interactive-move?
                                         :gameover? gameover?
                                         :winner winner
                                         :x-player (:x-player game-cookie)
                                         :o-player (:o-player game-cookie)
                                         :next-mark (if-not gameover? next-mark nil)
                                         :winning-message winning-message))))
    (-> response (redirect "/"))))

(defn handle-game-move [request response]
  (if-let [game-cookie (get-game-from-cookie request)]
    (let [game (get-game-of-uri-value (:game-type game-cookie))
          board-state (board-str->state (:board-str game-cookie))
          gameover? (gameover? game board-state)
          player-str-coll {:X (:x-player game-cookie) :O (:o-player game-cookie)}
          player-str (get player-str-coll (next-possible-mark game board-state))
          player (get-player-of-uri-value player-str)
          move (if-not (nil? (-> request (get-param "choice")))
                 (Integer/parseInt (-> request (get-param "choice")))
                 (move player game board-state))
          new-board-state (if gameover? board-state (set-mark-at-index game board-state move))
          new-board-str (board-state->str new-board-state)]
      (-> response
          (set-game-cookie (assoc game-cookie
                                  :board-str new-board-str))
          (redirect "/game/play")))
    (-> response (redirect "/"))))

(defn handle-game-reset [request response]
  (-> response
      (set-game-cookie "")
      (redirect "/")))

(defn defroutes []
  (POST "/game/create" handle-game-create)
  (GET  "/game/play"   handle-game-play)
  (GET  "/game/move"   handle-game-move)
  (GET  "/game/reset"  handle-game-reset))
