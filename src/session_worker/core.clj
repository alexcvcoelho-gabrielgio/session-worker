(ns session-worker.core
  (:gen-class)
  (:require [ext.redis :as rd]
            [clojure.core.async :refer [go thread]]
            [ext.router :as rt]
            [clojure.data.json :as json]))

(defn pull []
  (let [item (rd/pop-session)]
    (case (:command item)
      "create_session" (rt/save-session item)
      "warn" (rt/save-warn item)
      nil)))

(defn loop-through []
  (loop []
    (try
      (pull)
      (catch Exception e
        (-> e print)))
    (recur)))

(defn -main []
  (loop-through))

