(ns ext.router
  (:require [datomic.api :as d]
            [monger.core :as mg]
            [ext.datomic :as db]
            [ext.mongo :as mo]
            [environ.core :refer [env]]))

(defn save-session [item]
  (let [datomic-conn (d/connect (env :datomic))
        mongo-conn (mg/connect-via-uri (env :mongo))]
    (db/save-session datomic-conn item)
    (mo/save-session mongo-conn item)
    (mg/disconnect (:conn mongo-conn))))

(defn save-warn [item]
  (let [datomic-conn (d/connect (env :datomic))
        mongo-conn (mg/connect-via-uri (env :mongo))]
    (db/save-warn datomic-conn item)
    (mo/save-warn mongo-conn item)
    (mg/disconnect (:conn mongo-conn))))

(defn setup []
  (let [conn (d/connect (env :datomic))]
    (db/transact-schema conn)))
