(ns ext.mongo
  (:require [monger.core :as mg]
            [environ.core :refer [env]]
            [monger.collection :as mc])
  (:import org.bson.types.ObjectId))

(mount.core/defstate m-conn
                     :start (mg/connect-via-uri (env :mongo))
                     :stop (mg/disconnect (:conn m-conn)))

(defn save-session [se]
  (mc/insert (:db m-conn) "session" (dissoc (assoc se :_id (ObjectId.)) :command)))

(defn save-warn [ac]
  (mc/insert (:db m-conn) "warn" (assoc ac :_id (ObjectId.))))