(ns garden-compass.css3-test
  (:require [expectations :refer :all]
            [garden-compass.css3 :refer :all]))

;; experimental ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; (experimental a b)    -> only options enabled vendors
;; (experimental a b []) -> only {a b}

(expect {:foo :bar} (experimental :foo :bar []))

(expect {:-webkit {"abc" "xyz"}
         :-moz    {"abc" "xyz"}
         :-ms     {"abc" "xyz"}
         :-o      {"abc" "xyz"}
         "abc"    "xyz"}
        (binding [*options* (build-options)]
          (experimental "abc" "xyz")))

(expect {:-webkit {"abc" "xyz"}
         :-moz    {"abc" "xyz"}
         "abc"    "xyz"}
        (binding [*options* (build-options)]
          (experimental "abc" "xyz" [:-webkit :-moz])))

(expect {"abc" "xyz"}
        (binding [*options* {}]
          (experimental "abc" "xyz")))

;; Override and *options* must intersect (Compass does this)
(expect {"abc" "xyz"}
        (binding [*options*
                  {:experimental-support-for-webkit true}]
          (experimental "abc" "xyz" [:-moz])))

;; compass/css3/Appearance ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(expect {:-webkit {:appearance :foo}
         :-moz {:appearance :foo}
         :appearance :foo}
        (appearance :foo))

;; compass/css3/Background Clip ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(expect {:-webkit         {:background-clip :padding}
         :-moz            {:background-clip :padding}
         :background-clip :padding-box}
        (background-clip))

(expect {:-webkit         {:background-clip :padding}
         :-moz            {:background-clip :padding}
         :background-clip :padding}
        (background-clip :padding))

;; compass/css3/Background Origin ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(expect {:-webkit {:background-origin :content}
         :-moz    {:background-origin :content}
         :-ms     {:background-origin :content-box}
         :-o      {:background-origin :content-box}
         :background-origin :content-box}
        (background-origin))

;; compass/css3/Background Size ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(expect {:-webkit {:background-size ["100% auto"]}
         :-moz    {:background-size ["100% auto"]}
         :-o      {:background-size ["100% auto"]}
         :background-size           ["100% auto"]}
        (background-size))

(expect {:-webkit {:background-size ["1px" "2px" "3px"]}
         :-moz    {:background-size ["1px" "2px" "3px"]}
         :-o      {:background-size ["1px" "2px" "3px"]}
         :background-size           ["1px" "2px" "3px"]}
        (background-size "1px" "2px" "3px"))

;; compass/css3/Border Radius ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(expect {:-webkit {:border-radius "5px"}
         :-moz    {:border-radius "5px"}
         :-ms     {:border-radius "5px"}
         :-o      {:border-radius "5px"}
         :border-radius           "5px"}
        (border-radius))

(expect {:-webkit {:border-radius ["1px" "10px"]}
         :-moz {:border-radius ["1px" "2px" "/" "10px" "11px"]}
         :border-radius ["1px" "2px" "/" "10px" "11px"]}
        (border-radius ["1px" "2px"] ["10px" "11px"]))

;; compass/css3/Box ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(expect {:foo #{"-webkit-bar"
                "-moz-bar"
                "-ms-bar"
                "bar"}}
        (display-box :foo :bar))

(expect {:-webkit {:box-orient :horizontal}
         :-moz    {:box-orient :horizontal}
         :-ms     {:box-orient :horizontal}
         :box-orient           :horizontal}
        (box-orient))

(expect {:-webkit {:box-align :stretch}
         :-moz    {:box-align :stretch}
         :-ms     {:box-align :stretch}
         :box-align           :stretch}
        (box-align))

(expect {:-webkit {:box-flex 0}
         :-moz    {:box-flex 0}
         :-ms     {:box-flex 0}
         :box-flex           0}
        (box-flex))

(expect {:-webkit {:box-flex-group 1}
         :-moz    {:box-flex-group 1}
         :-ms     {:box-flex-group 1}
         :box-flex-group           1}
        (box-flex-group))

;; missing: box-ordinal-group

(expect {:-webkit {:box-direction :normal}
         :-moz    {:box-direction :normal}
         :-ms     {:box-direction :normal}
         :box-direction           :normal}
        (box-direction))

(expect {:-webkit {:box-lines :single}
         :-moz    {:box-lines :single}
         :-ms     {:box-lines :single}
         :box-lines           :single}
        (box-lines))

(expect {:-webkit {:box-pack :start}
         :-moz    {:box-pack :start}
         :-ms     {:box-pack :start}
         :box-pack           :start}
        (box-pack))

;; compass/css3/Box Shadow ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; compass/css3/Box Sizing ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; compass/css3/CSS Regions ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(expect {:-webkit {:flow-into :foo}
         :-ms {:flow-into :foo}}
        (flow-into :foo))

(expect {:-webkit {:flow-from :foo}
         :-ms {:flow-from :foo}}
        (flow-from :foo))

;; compass/css3/CSS3 Pie ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; compass/css3/Columns ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; compass/css3/Filter ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; compass/css3/Font Face ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; compass/css3/Hyphenation ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; word-break

;; When break-all
(expect {:word-break #{:break-all :break-word}
         :-ms {:word-break :break-all}}
        (word-break :break-all))

;; Else
(expect {:word-break :keep-all
         :-ms {:word-break :keep-all}}
        (word-break :keep-all))

;; Default is :normal
(expect {:word-break :normal
         :-ms {:word-break :normal}}
        (word-break))

;; hyphens

;; default is :auto
(expect {:hyphens :auto
         :-webkit {:hyphens :auto}
         :-moz {:hyphens :auto}}
        (hyphens))

;; hyphenation

(expect {:-ms {:word-break :break-all}
         :word-break #{:break-all :break-word}
         :-webkit {:hyphens :auto}
         :-moz {:hyphens :auto}
         :hyphens :auto}
        (hyphenation))

;; compass/css3/Images ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; compass/css3/Inline Block ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(expect {:display :inline-block}
        (binding [*options* {}]
          (inline-block)))

;; Custom alignment
(expect {:display :inline-block
         :vertical-align :_}
        (binding [*options* {:inline-block-alignment :_}]
          (inline-block)))

;; Mozilla legacy
(expect {:display #{:-moz-inline-stack :inline-block}}
        (binding [*options* {:legacy-support-for-mozilla true}]
          (inline-block)))

;; IE legacy
(expect {:display :inline-block
         :*vertical-align :auto
         :zoom 1
         :*display :inline}
        (binding [*options* {:legacy-support-for-ie true}]
          (inline-block)))

;; Default
(expect {:display #{:-moz-inline-stack :inline-block}
         :vertical-align :middle
         :*vertical-align :auto
         :zoom 1
         :*display :inline}
        (inline-block))

;; compass/css3/Opacity ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(expect {:filter {:function
                  "progid:DXImageTransform.Microsoft.Alpha"
                  :args
                  '("Opacity=50")}
         :opacity 0.5}
        (opacity 0.5))

(expect {:filter {:function
                  "progid:DXImageTransform.Microsoft.Alpha"
                  :args
                  '("Opacity=0")}
         :opacity 0}
        (transparent))

(expect {:filter {:function
                  "progid:DXImageTransform.Microsoft.Alpha"
                  :args
                  '("Opacity=100")}
         :opacity 1}
        (opaque))

;; compass/css3/Shared Utilities ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; compass/css3/Text Shadow ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; compass/css3/Transform ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; compass/css3/Transform (legacy) ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; compass/css3/Transition ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; compass/css3/User Interface ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(expect {:-webkit {:user-select :foo}
         :-moz {:user-select :foo}
         :-khtml {:user-select :foo}
         :user-select :foo}
        (binding [*options*
                  (build-options
                   {:experimental-support-for-khtml true})]
          (user-select :foo)))

(expect {:-webkit {:user-select :foo}
         :-moz {:user-select :foo}
         :user-select :foo}
        (user-select :foo))
