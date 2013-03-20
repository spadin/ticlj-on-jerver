(ns ticlj-on-jerver.controller.server-spec
  (:use [speclj.core]
        [ticlj-on-jerver.controller.server]))

(describe "ticlj-on-jerver.controller.server"
  (it "calls ServerImpl#setPublicDirectory"
    (let [server (reify com.jerver.http.server.Server
                    (setPublicDirectory [_ directory]
                      (print directory)))]
      (should= "test-directory" (with-out-str (-> server (set-public-directory "test-directory"))))))

  (it "calls ServerImpl#run"
    (let [server (reify com.jerver.http.server.Server
                    (run [_]
                      (print "stubbed-run")))]
      (should= "stubbed-run" (with-out-str (run server)))))

  (it "calls ServerImpl#setPublicDirectory then ServerImpl#run"
    (let [server (reify com.jerver.http.server.Server
                    (setPublicDirectory [_ directory]
                      (println "setPublicDirectory"))
                    (run [_]
                      (println "run")))]
      (should= "setPublicDirectory\nrun\n" (with-out-str (-> server
                                               (set-public-directory "test-directory")
                                               (run)))))))
