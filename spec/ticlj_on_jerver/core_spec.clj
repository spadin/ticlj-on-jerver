(ns ticlj-on-jerver.core-spec
  (:require [speclj.core :refer :all]
            [ticlj-on-jerver.core :refer :all]))

(describe "ticlj-on-jerver.core"
  (context "-main"
    (it "sets public directory then runs server"
      (with-redefs [ticlj-on-jerver.api.server/set-public-directory (fn [server dir] (print "set-public-directory:" dir ""))
                    ticlj-on-jerver.api.server/run (fn [server] (print "run "))]
        (should-contain "set-public-directory: public run"
                        (with-out-str (-main)))))))
