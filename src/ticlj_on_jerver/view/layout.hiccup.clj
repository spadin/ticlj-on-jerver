[:html
 [:head
  [:meta {:http-equiv "Content-Type" :content "text/html" :charset "iso-8859-1"}]
  (if-let [meta-refresh (:meta-refresh *view-context*)]
    [:meta {:http-equiv "refresh" :content (str (:seconds meta-refresh) ";URL=" (:url meta-refresh))}])
  [:title "Ticlj on Jerver"]
  (include-css "/stylesheets/master.css")
 [:body
  [:h1 {:class "title"} "Ticlj on Jerver"
    [:em [:small "- tic tac toe"]]]

  (eval *view-body*)

]]]
