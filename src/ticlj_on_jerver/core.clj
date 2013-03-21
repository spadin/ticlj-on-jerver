(ns ticlj-on-jerver.core
  (:use [ticlj-on-jerver.api.server   :only [server run set-public-directory]]
        [ticlj-on-jerver.api.render   :only [render]]
        [ticlj-on-jerver.api.router   :only [GET POST]]
        [ticlj-on-jerver.api.resolver :only [redirect]]))

(defn render-index []
  (render "index"))

(defn render-game-create []
  (redirect "/"))

(defn -main [& args]
  (-> server (set-public-directory "public"))

  (GET "/" (render-index))
  (POST "/game/create" (render-game-create))

  (-> server (run)))
