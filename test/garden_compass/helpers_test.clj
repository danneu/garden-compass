;; (ns garden-compass.helpers-test
;;   (:require [garden.core :refer :all]
;;             [expectations :refer :all]
;;             [garden-compass.helpers :refer :all])
;;   (:import [clojure.lang ExceptionInfo]))

;; ;; Test helpers

;; (defn compile-css [input]
;;   (css {:pretty-print? false} input))

;; ;; Compass/Color Stops
;; ;; Compass/Colors
;; ;; Compass/Constants

;; (expect ExceptionInfo
;;         (opposite-position :unknown))

;; (expect :right
;;         (opposite-position :left))

;; (expect :center
;;         (opposite-position :center))

;; ;; Compass/Cross Browser
;; ;; Compass/Display Helpers
;; ;; Compass/Font Files
;; ;; Compass/Image Dimensions
;; ;; Compass/Inline Data
;; ;; Compass/Math
;; ;; Compass/Selectors
;; ;; Compass/Sprites
;; ;; Compass/URLs

;; ;; Shortcircuits when path is absolute
;; (expect "/style.css" (stylesheet-url "/style.css"))

;; (expect "/stylesheets/style.css" (stylesheet-url "style.css"))


;; ;; Shortcircuits when path is absolute
;; (expect "/test.tts" (font-url "/test.tts"))

;; (expect "/fonts/test.tts" (font-url "test.tts"))


;; ;; Shortcircuits when path is absolute
;; (expect "/test.jpg" (image-url "/test.jpg"))

;; (expect "/images/test.jpg" (image-url "test.jpg"))
