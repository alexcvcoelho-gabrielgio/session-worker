(ns session-worker.core
  (:gen-class)
  (:require [ext.redis :as rd]
            [ext.source :as kf]
            [clojure.core.async :refer [go thread chan <!!]]
            [ext.router :as rt]
            [clojure.data.json :as json]
            [clojure.tools.logging :as log]))

(defn pull [item]
  (println (:command item))
  (case (:command item)
    "create_session" (rt/save-session item)
    "warn" (rt/save-warn item)
    nil))

(defn loop-through [c]
  (loop []
    (try
      (kf/pop-session-async c)
      (doseq [i (<!! c)]
        (pull i))
      (catch Exception e
        (-> e print)))
    (recur)))

(defn -main [& args]
  (let [c (chan)]
    (loop-through c)))

