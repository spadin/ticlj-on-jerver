[:div {:class "game"}
  [:div {:class "players"}
    [:h2 "Players 2"]
    [:div
      [:span {:class "X"} [:strong "X"] [:br] (get-player-name (:x-player *view-context*))]
      [:span {:class "O"} [:strong "O"] [:br] (get-player-name (:o-player *view-context*))]]]

  ;[:div {:class "winning-message-holder"}
   ;(if (:winning-message *view-context*)
     ;[:div {:class "winning-message"} (:winning-message *view-context*)])]

  [:div {:class "board-container"}
    [:ul {:class (str "board board-type-" (get-game-type))}
      (map-indexed
        (fn [idx state]
          [:li (state-or-move-link (:interactive-move? *view-context*) idx state)])
        (get-board-str))]]

  [:a {:href "/game/reset" :class "new-game"} "Start over"]]
