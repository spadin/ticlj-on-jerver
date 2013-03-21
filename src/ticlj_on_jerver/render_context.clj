(ns ticlj-on-jerver.render-context
  (:use [hiccup.page]
        [hiccup.form]
        [ticlj-on-jerver.view.helper]))

(def ^:dynamic *view-body* "")
