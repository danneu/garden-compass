(ns garden-compass.utilities.general.float-test
  (:require [expectations :refer :all]
            [garden-compass.utilities.general.float :refer :all]
            [garden-compass.css3 :refer [*options*]]
            [garden.compiler :refer [compile-css]]))

(expect
"img {
  float: left;
  display: inline;
}" (compile-css [:img (float)]))

(expect
"img {
  display: block;
  float: none;
}" (compile-css [:img (reset-float)]))
