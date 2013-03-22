(ns ticlj-on-jerver.core
  (:use [ticlj-on-jerver.api.server   :only [server run set-public-directory]]
        [ticlj-on-jerver.api.render   :only [render]]
        [ticlj-on-jerver.api.router   :only [GET POST]])
  (:require [ticlj-on-jerver.controller.game-controller :as game-controller]))

(defn handle-index [request response]
  (-> response (render "index")))

(defn -main [& args]
  (-> server (set-public-directory "public"))

  (GET "/" handle-index)
  (game-controller/defroutes)

  (-> server (run)))
