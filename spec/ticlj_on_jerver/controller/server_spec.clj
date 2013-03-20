(ns ticlj-on-jerver.controller.server-spec
  (:use [speclj.core]
        [ticlj-on-jerver.controller.server]))

(describe "ticlj-on-jerver.controller.server"
  (with sample-server (reify com.jerver.http.server.Server
                         (setPublicDirectory [_ directory]
                           (println directory))
                         (run [_]
                           (println "run"))))

  (it "returns a ServerImpl instance"
    (should= com.jerver.http.server.ServerImpl
             (.getClass server)))

  (it "calls ServerImpl#setPublicDirectory"
    (let [server @sample-server]
      (should-contain "test-directory"
                      (with-out-str (-> server
                                        (set-public-directory "test-directory"))))))

  (it "calls ServerImpl#run"
    (let [server @sample-server]
      (should-contain "run"
                      (with-out-str (run server)))))

  (it "calls ServerImpl#setPublicDirectory then ServerImpl#run"
    (let [server @sample-server]
      (should= "test-directory\nrun\n"
               (with-out-str (-> server
                                 (set-public-directory "test-directory")
                                 (run)))))))
