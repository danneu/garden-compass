(ns garden-compass.helpers.image-size-test
  (:require [expectations :refer :all]
            [garden-compass.helpers.image-size :refer :all]
            [garden.compiler :refer [render-css]]))


(given [w h path] (expect [w h] [(render-css (image-width path))
                                 (render-css (image-height path))])
       "1px" "2px" "test/resources/dummy.gif"
       "1px" "2px" "test/resources/dummy.jpg"
       "1px" "2px" "test/resources/dummy.png")
