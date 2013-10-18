(ns garden-compass.helpers.urls-test
  (:require [expectations :refer :all]
            [garden-compass.helpers.urls :refer :all]
            [garden-compass.config :refer [*config*]]
            [garden.compiler :refer [render-css]]))

;; Relative
(expect "url(/stylesheets/foo.css)"
        (render-css (stylesheet-url "foo.css")))

;; Absolute
(expect "url(/foo.css)"
        (render-css (stylesheet-url "/foo.css")))

;; Absolute
(expect "url(http://bar.com/foo.css)"
        (render-css (stylesheet-url "http://bar.com/foo.css")))
