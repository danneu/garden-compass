(ns garden-compass.helpers.colors-test
  (:require [expectations :refer :all]
            [garden-compass.helpers.colors :refer :all]
            [garden.color :as color :refer [hsl as-hex]]))


;; Compass test suite ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; https://github.com/chriseppstein/compass/blob/9ee6b422262668e8a7e154d2ac34f350ee331dc0/test/units/sass_extensions_test.rb

(expect 75 (-> (hsl 50 50 50) (scale-lightness 50) :lightness))
(expect 25 (-> (hsl 50 50 50) (scale-lightness -50) :lightness))

(expect 75 (-> (hsl 50 50 50) (adjust-lightness 25) :lightness))
(expect 25 (-> (hsl 50 50 50) (adjust-lightness -25) :lightness))
(expect 100 (-> (hsl 50 50 50) (adjust-lightness 500) :lightness))
(expect 0  (-> (hsl 50 50 50) (adjust-lightness -500) :lightness))

(expect 75 (-> (hsl 50 50 50) (scale-saturation 50) :saturation))
(expect 25 (-> (hsl 50 50 50) (scale-saturation -50) :saturation))

(expect 75 (-> (hsl 50 50 50) (adjust-saturation 25) :saturation))
(expect 25 (-> (hsl 50 50 50) (adjust-saturation -25) :saturation))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(expect "aabbcc" (decrown-hex "#aabbcc"))

(expect "aabbcc" (expand-hex "#abc"))
(expect "aabbcc" (expand-hex "#abc"))

(expect "#000000" (weighted-mix "#000" "#fff" 0))
