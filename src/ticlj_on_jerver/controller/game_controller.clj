(ns ticlj-on-jerver.controller.game-controller
  (:use [ticlj-on-jerver.api.router   :only [GET POST]]
        [ticlj-on-jerver.api.response :only [redirect]]
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

(defn defroutes []
  (POST "/game/create" handle-game-create))
