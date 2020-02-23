(ns hello-world.core-test
  (:require
   [cljs.test :refer-macros [deftest is testing]]
   [hello-world.core :as h]))
; (deftest weather-test 
;   (is (= "colder than " (h/diff-checker 3 4)))
;   (is (= "warmer than " (h/diff-checker 7 4)))
;   (is (= "same as " (h/diff-checker 4 4)))
; ;   (is (thrown-with-msg? js/Error #"Today's temperature must be an integer" (h/diff-checker 21.4 9)))
; ;   (is (thrown-with-msg? js/Error #"Yesterday's temperature must be an integer" (h/diff-checker 8 "hello"))))
