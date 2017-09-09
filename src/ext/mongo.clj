(ns ext.mongo
  (:require [monger.core :as mg]
            [monger.collection :as mc])
  (:import org.bson.types.ObjectId))

(defn save-session [conn se]
  (mc/insert (:db conn) "session" (assoc se :_id (ObjectId.))))

(defn save-warn [conn ac]
  (mc/insert (:db conn) "warn" (assoc ac :_id (ObjectId.))))