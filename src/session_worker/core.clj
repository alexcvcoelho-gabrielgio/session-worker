(ns session-worker.core
  (:gen-class)
  (:require [ext.source :as kf]
            [clojure.core.async :refer [go thread chan <!!]]
            [ext.router :as rt]
            [clojure.data.json :as json]
            [clojure.tools.logging :as log]))

(defn pull [item]
  (println (:command item) " " (:uuid item))
  (case (:command item)
    "create_session" (rt/save-session item)
    "warn" (rt/save-warn item)
    nil))

(defn loop-through [c]
  (loop []
    (try
      (println "cycle")
      (kf/pop-session-async c)
      (doseq [i (<!! c)]
        (pull i))
      (catch Exception e
        (-> e print)))
    (recur)))

(defn -main [& args]
  (mount.core/start)
  (let [c (chan)]
    (loop-through c)))

