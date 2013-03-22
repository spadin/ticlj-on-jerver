(ns ticlj-on-jerver.api.render-spec
  (:use [speclj.core]
        [ticlj-on-jerver.api.render]))

(describe "ticlj-on-jerver.api.render"
  (with spec-view-directory "spec/resources/view")

  (around [it]
    (binding [*view-directory* @spec-view-directory]
      (it)))

  (context "#render"
    ;(it "returns a Routable instance"
      ;(should (instance? com.jerver.http.response.Response
                         ;(render "test"))))

    (it "can set the *view-directory*"
      (should= "spec/resources/view"
               *view-directory*))

    (it "loads a test template"
      (should= "[:div \"test\"]\n"
               (load-view "test")))

    (it "returns an html string for the template"
      (should-contain "<div>test</div>"
                      (render-view "test")))))
