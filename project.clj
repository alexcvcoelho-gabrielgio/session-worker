(defproject session-worker "0.0.1"
  :description "Session worker"
  :url "http://example.com/FIXME"
  :repositories {"my.datomic.com" {:url   "https://my.datomic.com/repo"
                                   :creds :gpg}}
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/core.async "0.3.442"]
                 [commons-codec "1.10"]
                 [environ "1.1.0"]
                 [mount "0.1.11"]
                 [mysql/mysql-connector-java "5.1.6"]
                 [com.datomic/datomic-pro "0.9.5561"]
                 [com.taoensso/carmine "2.16.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [org.clojure/data.json "0.2.6"]
                 [com.novemberain/monger "3.1.0"]
                 [org.clojure/core.async "0.3.442"]
                 [spootnik/kinsky "0.1.16"]]
  :plugins [[lein-environ "1.1.0"]]
  :main ^:skip-aot session-worker.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
