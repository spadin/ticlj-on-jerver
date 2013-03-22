(ns ticlj-on-jerver.api.request
  (:use [clojure.string :only [split]]))

(def request (com.jerver.http.request.RequestImpl.))

(defn get-param [request param-key]
  (-> request (.getParam param-key)))

(defn get-header [request header-key]
  (-> request (.getHeader header-key)))

(defn build-key-val-map [arr]
  (loop [cookie-pairs arr
         cookie-map {}]
    (let [pair (split (first cookie-pairs) #"=" 2)
          c-key (first pair)
          c-val (second pair)
          c-map (assoc cookie-map (keyword c-key) c-val)]
      (if-let [others (rest cookie-pairs)]
        (if-not (empty? others) (recur others c-map) c-map)))))

(defn get-cookies [request]
  (if-let [cookies-str (-> request (get-header "Cookie"))]
    (let [cookie-pairs (split cookies-str #"; ")]
      (build-key-val-map cookie-pairs))))

(defn get-cookie [request cookie-key]
  (get (get-cookies request) cookie-key))
