(ns ticlj-on-jerver.api.request-spec
  (:use [speclj.core]
        [ticlj-on-jerver.api.request]))

(describe "ticlj-on-jerver.api.request"
  (with sample-request (reify com.jerver.http.request.Request
                         (getParam [_ param-key]
                           (println "get-param:" param-key))
                         (getHeader [_ header-key]
                           (println "get-header:" header-key))))

  (it "returns an instance of Request"
    (should (instance? com.jerver.http.request.Request
                       request)))

  (it "calls #getParam with the var1 key"
    (let [request @sample-request]
      (should-contain "get-param: var1"
                      (with-out-str (-> request (get-param "var1"))))))

  (it "calls #getHeader with the Content-Type key"
    (let [request @sample-request]
      (should-contain "get-header: Cookie"
                      (with-out-str (-> request (get-header "Cookie"))))))

  (it "gets a cookie value from a key"
    (let [request (reify com.jerver.http.request.Request
                    (getHeader [_ header-key]
                      "cName=cValue; cName2=sub1=subVal1&sub2=subVal2"))]
      (should= "cValue"
               (-> request (get-cookie :cName))))))
