(ns ^:figwheel-hooks hello-world.core
  (:require
   [goog.dom :as gdom]
   [reagent.core :as reagent :refer [atom]]
   [hello-world.api :as api]))

;; define your app data so that it doesn't get over-written on reload
(def !app-state (atom {:today-temp nil, :ytd-temp 900}))
(defn diff-checker
  "Calculates differece between today's temp and yesterday's temperature. 
If difference > 0, returns 'warmer' if difference < 0, returns 'colder'. 
If difference == 0, returns 'same'."
  [today-temp ytd-temp]
  (assert (number? today-temp) "Today's temperature must be an integer")
  (assert (number? ytd-temp) "Yesterday's temperature must be an integer")

  (cond
    (< 0 (- today-temp ytd-temp)) "warmer than "
    (> 0 (- today-temp ytd-temp)) "colder than "
    :else "same as "))

(defn weather-app []
  (let [{today :today-temp ytd :ytd-temp} @!app-state]
    [:div
     (if (some? today)
       [:<> "Today will be " (diff-checker today ytd) " yesterday."]
       "Loading")]))

(defn get-app-element []
  (gdom/getElement "app"))

(defn mount [el]
  (reagent/render-component [weather-app] el))

(defn mount-app-element []
  (when-let [el (get-app-element)]
    (mount el)))

(defn init []
  (-> (api/return-temp+ "2172797")
      (.then #(swap! !app-state assoc :today-temp %)))
  (mount-app-element))

;; conditionally start your application based on the presence of an "app" element
;; this is particularly helpful for testing this ns without launching the app
(init)

;; specify reload hook with ^;after-load metadata
(defn ^:after-load on-reload []
  (mount-app-element)
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )
