(ns garden-compass.helpers.colors
  (:require [garden.color :as color :refer [hsl]]
            [clojure.string :as str]))

;; Internal helpers ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn scale-color-value [value amount]
  (+ value (if (pos? amount)
             (* (- 100 value) (/ amount 100))
             (/ (* value amount) 100))))

(defn decrown-hex [hex]
  (str/replace hex #"^#" ""))

(defn crown-hex [hex]
  (if (re-find #"^#" hex)
    hex
    (str "#" hex)))

(defn expand-hex
  "(expand-hex \"#abc\") -> \"aabbcc\"
   (expand-hex \"333333\") -> \"333333\""
  [hex]
  (as-> (decrown-hex hex) _
        (cond
         (= 3 (count _)) (str/join (mapcat vector _ _))
         (= 1 (count _)) (str/join (repeat 6 _))
         :else _)))

(defn hex->long
  "(hex->long \"#abc\") -> 11189196"
  [hex]
  (-> hex
      (str/replace #"^#" "")
      (expand-hex)
      (Long/parseLong 16)))

(defn long->hex
  "(long->hex 11189196) -> \"aabbcc\""
  [long]
  (Integer/toHexString long))

;; Adjust-* functions add `amount` to the value ;;;;;;;;;;;;

(defn adjust-lightness
  [color amount]
  (if (pos? amount)
    (color/lighten color amount)
    (color/darken color (- 0 amount))))

(defn adjust-saturation
  [color amount]
  (if (pos? amount)
    (color/saturate color amount)
    (color/desaturate color (- 0 amount))))

;; Scale-* functions adjust the value as a percentage ;;;;;;

(defn scale-lightness [color amount]
  (#'color/update-color color :lightness scale-color-value amount))


(defn scale-saturation [color amount]
  (#'color/update-color color :saturation scale-color-value amount))

;; Mixer ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn weighted-mix
  "`weight` is number 0 to 100 (%).
   At 0, it weighs color-1 at 100%.
   At 100, it weighs color-2 at 100%.
   Returns hex string."
  [color-1 color-2 weight]
  (let [[weight-1 weight-2] (map #(/ % 100) [(- 100 weight) weight])
        [long-1 long-2] (map (comp hex->long color/as-hex)
                             [color-1 color-2])]
    (-> (+ (* long-1 weight-1) (* long-2 weight-2))
        (long->hex)
        (expand-hex)
        (crown-hex))))
