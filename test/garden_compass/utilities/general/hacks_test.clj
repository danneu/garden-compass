(ns garden-compass.utilities.general.hacks-test
  (:require [expectations :refer :all]
            [garden-compass.utilities.general.hacks :refer :all]
            [garden-compass.css3 :refer [*options*]]
            [garden.compiler :refer [compile-css]]))

;; Check default options
(expect (= :zoom (:default-has-layout-approach *options*)))

;; has-layout-zoom

(expect {:*zoom 1} (has-layout-zoom))

;; has-layout-block

(expect {:display :inline-block
         :& {:display :block}}
        (has-layout-block))

;; bang-hack

(expect "foo{color:white!important;color:lol}"
        (compile-css {:pretty-print? false}
                     [ :foo (bang-hack :color :white :lol)]))
