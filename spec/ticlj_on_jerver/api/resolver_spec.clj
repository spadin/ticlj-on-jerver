(ns ticlj-on-jerver.api.resolver-spec
  (:use [speclj.core]
        [ticlj-on-jerver.api.resolver]))

(describe "ticlj-on-jerver.api.resolver"
  (with sample-resolver-callback
    (fn [request response]
      (println "sample-resolver-callback" request response)))

  (it "returns an instance that implements Routable"
    (should (instance? com.jerver.http.route.Routable
                       (resolver @sample-resolver-callback)))))
