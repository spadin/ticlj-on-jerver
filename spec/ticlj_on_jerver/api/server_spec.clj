(ns ticlj-on-jerver.api.server-spec
  (:use [speclj.core]
        [ticlj-on-jerver.api.server]))

(describe "ticlj-on-jerver.api.server"
  (with sample-server (reify com.jerver.http.server.Server
                         (setPublicDirectory [_ directory]
                           (println directory))
                         (run [_]
                           (println "run"))))

  (it "returns a ServerImpl instance"
    (should (instance? com.jerver.http.server.Server
                       server)))

  (it "calls ServerImpl#setPublicDirectory"
    (let [server @sample-server]
      (should-contain "test-directory"
                      (with-out-str (-> server
                                        (set-public-directory "test-directory"))))))

  (it "calls ServerImpl#run"
    (let [server @sample-server]
      (should-contain "run"
                      (with-out-str (run server))))))
