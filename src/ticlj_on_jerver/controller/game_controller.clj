(ns ticlj-on-jerver.controller.game-controller
  (:use [ticlj-on-jerver.api.router   :only [GET POST]]
        [ticlj-on-jerver.api.response :only [redirect set-cookie]]
        [ticlj-on-jerver.api.request  :only [get-param]]))

(defn empty-param? [request param]
  (empty? (-> request (get-param param))))

(defn game-create-params-set? [request]
  (and
    (not (-> request (empty-param? "game-type")))
    (not (-> request (empty-param? "x-player")))
    (not (-> request (empty-param? "o-player")))))

(defn handle-game-create [request response]
  (if (game-create-params-set? request)
    (-> response (redirect "/game/play"))
    (-> response (redirect "/"))))

(defn game-cookie-str [game]
  (str "game-type=" (:game-type game) "&"
       "x-player="  (:x-player  game) "&"
       "o-player="  (:o-player  game) "&"
       "board-str=" (:board-str game)))

(defn set-game-cookie [response game]
  (-> response (set-cookie "game" (game-cookie-str game))))

(defn defroutes []
  (POST "/game/create" handle-game-create))
