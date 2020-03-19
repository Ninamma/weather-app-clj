(ns hello-world.api
  (:require [clj-time.core :as t]
            [clj-time.coerce :as c]
            [clj-time.local :as l]
            [org.httpkit.client :as http]
            [clojure.data.json :as json]))

(def request-url "https://api.darksky.net/forecast/")

(def api-key "API KEY")

(def ytdtime "gets UNIX timestamp in seconds for 24 hours in the past"
  (int (/ (- (c/to-long (l/local-now)) 86400000) 1000)))

(defn build-ytd-request "builds request url for yesterdays daily info" [coords]
  (str request-url api-key "/" coords "," ytdtime "?exclude=currently,hourly&units=si"))

(defn get-ytd-temp "gets ytd's daytime highest temp" []
  (get-in (json/read-str
           (get-in @(http/get (build-ytd-request london-coords)) [:body]) :key-fn keyword) 
          [:daily :data 0 :apparentTemperatureHigh]))

(defn build-today-request "builds request url for today's daily info" [coords]
  (str request-url api-key "/" coords "?units=si"))

(defn get-today-temp "gets today's daytime highest temp" []
  (get-in (json/read-str
           (get-in @(http/get (build-today-request london-coords)) [:body]) :key-fn keyword)
          [:daily :data 0 :apparentTemperatureHigh]))

(defn get-temp-diff "gets temp diff between today and ytd, positive if today is hotter, negative if today is colder" []
  (- (get-today-temp) (get-ytd-temp)))

(defn hotter-or-colder []
  (if (< 0 (get-temp-diff)) (println "hotter") (println "colder")))

(hotter-or-colder)