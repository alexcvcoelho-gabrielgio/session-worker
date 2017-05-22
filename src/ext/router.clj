(ns ext.router
  (:require [datomic.api :as d]
            [monger.core :as mg]
            [ext.datomic :as db]
            [ext.mongo :as mo]))

(defn save-session [item]
  (let [datomic-conn (d/connect db/dev-db-uri)
        mongo-conn (mg/connect)]
    (db/save-session datomic-conn item)
    (mo/save-session mongo-conn item)
    (mg/disconnect mongo-conn)))

(defn save-warn [item]
  (let [datomic-conn (d/connect db/dev-db-uri)
        mongo-conn (mg/connect)]
    (db/save-warn datomic-conn item)
    (mo/save-warn mongo-conn item)
    (mg/disconnect mongo-conn)))

(defn setup []
  (let [conn (d/connect db/dev-db-uri)]
    (db/transact-schema conn)))
