(ns ticlj-on-jerver.api.resolver
  (:use [ticlj-on-jerver.api.response :only [set-status-code add-header]]))

(defrecord Resolver [callback]
  com.jerver.http.route.Routable
  (resolve [this request response]
    (let [callback (:callback this)]
      (callback request response))))

(defn resolver [callback]
  (Resolver. callback))
