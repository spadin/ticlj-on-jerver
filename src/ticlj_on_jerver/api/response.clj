(ns ticlj-on-jerver.api.response)

(def response (com.jerver.http.response.ResponseImpl.))

(defn set-status-code [response status-code]
  (-> response (.setStatusCode status-code))
  response)

(defn add-header [response header-key header-val]
  (-> response (.appendHeader (str header-key ": " header-val)))
  response)

(defn set-cookie [response cookie-name cookie-value]
  (-> response (add-header "Set-Cookie" (str cookie-name "=" cookie-value))))

(defn set-body [response body]
  (-> response (.setBody (.getBytes body)))
  response)
