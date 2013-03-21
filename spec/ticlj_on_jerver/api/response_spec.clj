(ns ticlj-on-jerver.api.response-spec
  (:use [speclj.core]
        [ticlj-on-jerver.api.response]))

(describe "ticlj-on-jerver.api.response"
  (with sample-response (reify com.jerver.http.response.Response
                          (setStatusCode [_ status-code]
                            (println "set-status-code"))
                          (appendHeader [_body header]
                            (println "add-header: " header))
                          (setBody [_ body]
                            (println "set-body"))))

  (it "returns an instance of a Response"
    (should (instance? com.jerver.http.response.Response
                       response)))

  (it "calls #setStatusCode"
    (let [response @sample-response]
      (should-contain "set-status"
                      (with-out-str (-> response (set-status-code 200))))))

  (it "calls #appendHeader with 'Location: http://google.com'"
    (let [response @sample-response]
      (should-contain "Location: http://google.com"
                      (with-out-str (-> response (add-header "Location" "http://google.com"))))))

  (it "calls #appendHeader with 'Set-Cookie: cookieName=cookieValue"
    (let [response @sample-response]
      (should-contain "Set-Cookie: cookieName=cookieValue"
                      (with-out-str (-> response (set-cookie "cookieName" "cookieValue"))))))

  (it "calls #setBody"
    (let [response @sample-response]
      (should-contain "set-body"
                      (with-out-str (-> response (set-body "response-body")))))))
