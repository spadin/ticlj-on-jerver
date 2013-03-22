[:html
 [:head
  [:meta {:http-equiv "Content-Type" :content "text/html" :charset "iso-8859-1"}]
  [:title "Ticlj on Jerver"]
  (include-css "/stylesheets/master.css")
 [:body
  [:h1 {:class "title"} "Ticlj on Jerver"
    [:em [:small "- tic tac toe"]]]

  (eval *view-body*)

]]]
