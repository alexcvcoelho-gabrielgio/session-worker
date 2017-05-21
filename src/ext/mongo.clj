(ns ext.mongo
  (:require [monger.core :as mg]
            [monger.collection :as mc]))

(defn save-session [conn se]
  (let [db (mg/get-db conn "main")]
    (mc/insert db "session" se)))

(defn save-warn [conn ac]
  (let [db (mg/get-db conn "main")]
    (mc/insert db "warn" ac)))