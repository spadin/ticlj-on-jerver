(ns ticlj-on-jerver.api.render
  (:use [ticlj-on-jerver.api.resolver :only [resolver]]
        [ticlj-on-jerver.api.response :only [set-body set-cookie
                                             set-status-code add-header]]
        [hiccup.core])
  (:require [ticlj-on-jerver.view.helper]))

(def ^:dynamic *view-directory* "src/ticlj_on_jerver/view")

(defn load-view [view-name]
  (slurp (str *view-directory* "/" view-name ".hiccup.clj")))

(defn read-view [view-name]
  (read-string (load-view view-name)))

(defn eval-view [view-name]
  (let [body (read-view view-name)]
    (require 'ticlj-on-jerver.view.helper)
    (binding [*ns* (the-ns 'ticlj-on-jerver.view.helper)
              ticlj-on-jerver.view.helper/*view-body* body]
      (eval (read-view "layout")))))

(defn render-view [view-name]
  (let [view (eval-view view-name)]
    (html view)))

(defn render
  ([response view-name view-context]
   (binding [ticlj-on-jerver.view.helper/*view-context* view-context]
     (-> response (render view-name))))
  ([response view-name]
    (let [rendered-view (render-view view-name)]
      (-> response
          (set-status-code 200)
          (add-header "Content-Type" "text/html")
          (set-body rendered-view)))))
