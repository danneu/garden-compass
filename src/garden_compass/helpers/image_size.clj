(ns garden-compass.helpers.image-size
  (:require [garden.units :refer [px]]
            [garden-compass.util :as util])
  (:import [java.io File]))

(defn image-height [path]
  (let [f (File. path)]
    (if (.exists f)
      (px (:height (util/get-image-dimensions f)))
      (ex-info "File not found:" {:path path}))))

(defn image-width [path]
  (let [f (File. path)]
    (if (.exists f)
      (px (:width (util/get-image-dimensions f)))
      (ex-info "File not found:" {:path path}))))
