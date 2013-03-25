(ns ticlj-on-jerver.view.helper
  (:use
    [hiccup.page]
    [hiccup.form]
    [ticlj.player]
    [ticlj.game]
    [ticlj.game.protocol :only [empty-board-state]]
    [clojure.string :only [split]]))

(def ^:dynamic *view-body* "")
(def ^:dynamic *view-context* {})

(defn get-game-types []
  (map (fn [game-type]
         (assoc game-type :uri-value (second (split (:value game-type) #"\/"))))
       available-game-types))

(defn get-player-types []
  (map (fn [player-type]
         (assoc player-type :uri-value (second (split (:value player-type) #"\/"))))
       available-player-types))

(defn get-game-type []
  (:game-type *view-context*))

(defn get-board-str []
  (:board-str *view-context*))

(defn get-player-name [player-type-str]
  (let [player-types (get-player-types)]
    (:name (first (filter (fn [player-type]
                            (= (:uri-value player-type) player-type-str))
                          player-types)))))

(defn dashes-to-underscore [s]
  (clojure.string/replace s #"-" "_"))

(defn get-board-partial [game-type-str]
  (str "game/" (dashes-to-underscore game-type-str) "_board"))

(defn state-or-move-link [interactive-move? idx state]
  (if (= (str state) "-")
    (if interactive-move?
      [:a {:href (str "move?choice=" idx)} "choose"]
      [:span "&nbsp;"])
    [:span state]))

(defn get-player-name [player-type-str]
  (let [player-types (get-player-types)]
    (:name (first (filter (fn [player-type] (= (:uri-value player-type) player-type-str)) player-types)))))
