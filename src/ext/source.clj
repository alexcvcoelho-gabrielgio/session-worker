(ns ext.source
  (:require [clojure.data.json :as json]
            [kinsky.client :as client]
            [clojure.core.async :as async]
            [environ.core :refer [env]]))

(defn filter-result [r]
  (if (nil? r)
    nil
    (let [v (get (:by-topic r) "session")]
      (map #(:value %) v))))

(defn pop-session []
  (let [c (client/consumer {:bootstrap.servers (env :kafka)
                            :group.id          "consumerman"}
                           (client/keyword-deserializer)
                           (client/edn-deserializer))]
    (client/subscribe! c "session")
    (filter-result (client/poll! c 1000))))

(defn pop-session-async [ch]
  (async/go
    (let [c (client/consumer {:bootstrap.servers (env :kafka)
                              :group.id          "consumer-man"}
                               (client/keyword-deserializer)
                             (client/edn-deserializer))]
      (client/subscribe! c "session")
      (async/>!! ch (filter-result (client/poll! c 1000)))
      (client/close! c))))

