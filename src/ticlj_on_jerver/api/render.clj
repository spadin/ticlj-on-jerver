(ns ticlj-on-jerver.api.render
  (:use [ticlj-on-jerver.api.resolver :only [resolver]]
        [ticlj-on-jerver.api.response :only [set-body set-cookie set-status-code add-header]]
        [hiccup.core])
  (:require [ticlj-on-jerver.view.helper]))

(def ^:dynamic *view-directory* "src/ticlj_on_jerver/view")

(defn load-view [view-name]
  (slurp (str *view-directory* "/" view-name ".hiccup.clj")))

(defn eval-view [view-name]
  (let [view-str (load-view view-name)
        view-ns 'ticlj-on-jerver.render-context]
    (require view-ns)
    (binding [*ns* (the-ns view-ns)]
      (eval (read-string view-str)))))


(defn render-view [view-name]
  (let [view (eval-view view-name)]
    (html view)))

(defn render [view-name]
  (let [rendered-view (render-view view-name)]
    (resolver (fn [request response]
      (-> response
          (set-status-code 200)
          (add-header "Content-Type" "text/html")
          (set-body rendered-view))))))
