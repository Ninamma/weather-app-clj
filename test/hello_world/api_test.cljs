(ns hello-world.api-test
  (:require
   [cljs.test :refer-macros [deftest is testing]]
   [hello-world.api :as api]))

; (deftest test-correct-string-for-request
;   (is (= "https://api.darksky.net/forecast/e0034d02222a086c6ea4259831f0299e/1,2" (api/build-request 1 2))))
