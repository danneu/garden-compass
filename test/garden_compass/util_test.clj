(ns garden-compass.util-test
  (:require [expectations :refer :all]
            [garden-compass.util :refer :all]
            [clojure.java.io :as io]
            [garden.color :as color])
  (:import [java.io File]))

;; join-with-slash ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(expect "a/b" (join-with-slash "a" "b"))
(expect "a/b" (join-with-slash "a/" "/b"))

;; absolute-path? ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(expect (not (absolute-path? "foo")))
(expect (absolute-path? "/foo"))
(expect (absolute-path? "http://example.com/foo"))

;; get-extension ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(expect (nil? (get-extension "")))
(expect (nil? (get-extension " ")))
(expect (nil? (get-extension "foo ")))
(expect (nil? (get-extension "foo.")))
(expect "txt" (get-extension "foo.txt "))

;; extension-to-mime ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(expect "image/png" (extension-to-mime "png"))
(expect "image/foo" (extension-to-mime "foo"))
(expect (nil? (extension-to-mime "123")))
(expect (nil? (extension-to-mime "")))

(expect "image/png" (path-to-mime "foo/bar.png"))

;; color converterrs ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(expect "#f0e68c" (as-hex+ "khaki"))
(expect "#f0e68c" (color/as-hex (as-rgb+ "khaki")))
(expect "#f0e68c" (color/as-hex (as-hsl+ "khaki")))
