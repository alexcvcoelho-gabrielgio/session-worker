  (ns ext.datomic
  (:require [cprop.core :refer [load-config]]
            [clojure.java.io :as io]
            [datomic.api :as d]
            [cprop.source :as source])
  (:import (datomic Util)))

(def dev-db-uri "datomic:sql://main?jdbc:mysql://gabrielgio.com.br:3306/datomic?user=remote&password=remote")

(defn get-session [{:keys [uid brand model hid]}]
  [{:session/uid   uid
    :session/brand brand
    :session/model model
    :session/hid   hid}])

(defn get-warn [{:keys [action session-id]}]
  [{:action/action    action
    :action/session-id session-id}])

(def schema (io/resource "schema.edn"))

(defn save-session [conn se]
  (d/transact conn (get-session se)))

(defn save-warn [conn ac]
  (d/transact conn (get-warn ac)))

(defn read-txs
  [tx-resource]
  (with-open [tf (io/reader tx-resource)]
    (Util/readAll tf)))

(defn transact-all
  ([conn txs]
   (transact-all conn txs nil))
  ([conn txs res]
   (if (seq txs)
     (transact-all conn (rest txs) @(d/transact conn (first txs)))
     res)))

(defn transact-schema [conn]
  (transact-all conn (read-txs schema)))