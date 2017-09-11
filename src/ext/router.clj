(ns ext.router
  (:require [datomic.api :as d]
            [monger.core :as mg]
            [ext.datomic :as db]
            [ext.mongo :as mo]
            [clojure.core.async :as a]
            [environ.core :refer [env]]))

(defn save-session [item]
  (let [cd (a/chan)
        cm (a/chan)]
    (a/go
      (a/>! cd (db/save-session item)))
    (a/go
      (a/>! cm (mo/save-session item)))
    (a/map vector [cm cd])))


(defn save-warn [item]
  (let [datomic-conn (d/connect (env :datomic))
        mongo-conn (mg/connect-via-uri (env :mongo))]
    (db/save-warn datomic-conn item)
    (mo/save-warn item)
    (mg/disconnect (:conn mongo-conn))))

(defn setup []
  (let [conn (d/connect (env :datomic))]
    (db/transact-schema conn)))
