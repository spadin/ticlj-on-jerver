[:div {:id "start-game"}
  [:h3 "Start a new game"]

  [:form {:action "/game/create" :id "new-game" :method "post"}
    [:label {:for "game-type"} "Choose game type"] [:br]
    [:select {:name "game-type" :id "game-type"}
      (for [game-type (get-game-types)]
        [:option {:value (:uri-value game-type)} (:name game-type)])]

    [:div
      [:p "Choose player types"]

      [:div {:class "label-group"}
        [:strong [:label {:for "x-player"} "X"]]
        [:select {:name "x-player" :id "x-player"}
          (for [player-type (get-player-types)]
            [:option {:value (:uri-value player-type)} (:name player-type)])]]

      [:div {:class "label-group"}
        [:strong [:label {:for "o-player"} "O"]]
        [:select {:name "o-player" :id "o-player"}
          (for [player-type (get-player-types)]
            [:option {:value (:uri-value player-type)} (:name player-type)])]]

      [:input {:type "submit"
               :value "Start game"
               :class "submit"}]]]]
