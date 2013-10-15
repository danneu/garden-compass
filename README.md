# garden-compass

A port of [Compass](http://compass-style.org/) to Clojure datastructures for use with [Garden](https://github.com/noprompt/garden).

## Implementation

My idea so far is to round up all of Compass' scattered variables into a single dynamic-var `*options*` map that mixins can see.

``` clojure
(css [:div
      [:&
       {:color "white"}
       (opacity 0)]])
; div {
;   color: white;
;   opacity: 0;
;   filter: progid:DXImageTransform.Microsoft.Alpha(Opacity=0);
; }
```

And customizations can be made by rebinding `*options*`:

``` clojure
(def ^:dynamic *options*
  (build-options {:legacy-support-for-ie false
                  :default-border-radius "5px"
                  :inline-block-alignment :middle}))
```

Or perhaps only temporarily:

``` clojure
(binding [*options* (build-options {:legacy-support-for-ie false})]
  (css [:div
        [:&
         {:color "white"}
         (opacity 0)]]))
; div {
;   color: white;
;   opacity: 0;
; }
```

Otherwise, `*options*` will default to Compass' default options.
