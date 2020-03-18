(ns hello-world.api
  (:require [clj-time.core :as t]
            [clj-time.coerce :as c]
            [clj-time.local :as l]
            [clj-http.client :as client]))

(def request-url "https://api.darksky.net/forecast/")

(def api-key "API KEY")

(def ytdtime "gets UNIX timestamp in seconds for 24 hours in the past"
  (int (/ (- (c/to-long (l/local-now)) 86400000) 1000)))

(defn build-ytdrequest "builds request url for yesterdays daily info" [coords]
  (str request-url api-key "/" coords "," ytdtime "?exclude=currently,hourly&units=si"))

(def london-coords (str 51.4786 "," -0.158))

(println (build-ytdrequest london-coords))
(clojure.pprint/pprint (get-in (client/get (build-ytdrequest london-coords) {:as :json})
                               [:body :daily :data 0 :apparentTemperatureHigh]))
; (defn fetch+ [request]
;   (-> (js/fetch request)
;       (.then #(.json %))
;       (.then #(js->clj % :keywordize-keys true))))

; (defn m->feels-like [m]
;   (get-in m [:main :feels_like]))

; (defn return-temp+ "Will take city id and return temperature" [city-id]
;   (-> (build-request city-id)
;       fetch+
;       (.then m->feels-like)))