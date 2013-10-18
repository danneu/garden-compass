(ns garden-compass.utilities.color.contrast
  (:require [garden-compass.css-functions]
            [garden-compass.util :as util]
            [garden-compass.css3 :refer [*options*]]
            [garden.color :as color]))

;; http://compass-style.org/reference/compass/utilities/color/contrast/

;; *options*
;;
;; - :contrasted-dark-default        "black"
;; - :contrasted-light-default       "white"
;; - :contrasted-lightness-threshold "30%"

;; Functions ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn contrast-color
  "Returns the `light` color when `color` is dark
   Returns the `dark` color when `color` is light.
   - `threshold` is a percent integer 0 to 100 that determines
     when the lightness of `color` changes from dark to light.
   - `color` is Garden HSL. Ex: (hsl 180 60 50)"
  [{:keys [color dark light threshold]
    :or {dark (:contrasted-dark-default *options*)
         light (:contrasted-light-default *options*)
         threshold (:contrasted-lightness-threshold *options*)}}]
  (if (< (:lightness color) threshold)
    light
    dark))

;; Mixins ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn contrasted
  [{:keys [background-color dark light threshold]
    :or {dark (:contrasted-dark-default *options*)
         light (:contrasted-light-default *options*)
         threshold (:contrasted-lightness-threshold *options*)}}]
  {:background-color background-color
   :color (contrast-color {:color (util/as-hsl+ background-color)
                           :dark dark
                           :light light
                           :threshold threshold})})
