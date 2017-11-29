(ns ext.datomic
  (:require [clojure.java.io :as io]
            [datomic.api :as d]
            [environ.core :refer [env]]
            [mount.core :as mount])
  (:import (datomic Util)))

(mount/defstate d-conn
                :start (-> env :datomic d/connect)
                :stop (-> d-conn .release))

(defn get-session [{:keys [uuid brand model hd-id lat long]}]
  [{:session/uid         uuid
    :session/brand       brand
    :session/model       model
    :session/hd-id       hd-id
    :session/destination #{{:location/x long
                            :location/y lat}}}])

(defn get-warn [{:keys [action session-id]}]
  [{:action/action     action
    :action/session-id session-id}])

(def schema (io/resource "schema.edn"))

(defn save-session [se]
  (d/transact d-conn (get-session se)))

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