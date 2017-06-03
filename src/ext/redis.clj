(ns ext.redis
  (:require [taoensso.carmine :as car :refer (wcar)]
            [clojure.data.json :as json])
  (:import (datomic Util)
           (clojure.lang ArityException)))

(defmacro wcar* [& body] `(car/wcar {:pool {} :spec {:host "gabrielgio.com.br" :port 6379}} ~@body))

(defn pop [key]
  (wcar* (car/brpop key 100)))

(defn pop-session []
  (try (let [json (pop "session")]
         (json/read-str (last json) :key-fn keyword))
       (catch Exception e nil)))
