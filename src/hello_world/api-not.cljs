(ns hello-world.api)

(def request-url "https://api.openweathermap.org/data/2.5/weather?id=")

(def api-key "a241533f43495fc655870b43a8f9d457")

(defn build-request [city-id]
  (str request-url city-id "&appid=" api-key))

(defn fetch+ [request]
  (-> (js/fetch request)
      (.then #(.json %))
      (.then #(js->clj % :keywordize-keys true))))

(defn m->feels-like [m]
  (get-in m [:main :feels_like]))

(defn return-temp+ "Will take city id and return temperature" [city-id]
  (-> (build-request city-id)
      fetch+
      (.then m->feels-like)))