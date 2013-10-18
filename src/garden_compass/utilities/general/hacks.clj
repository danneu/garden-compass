(ns garden-compass.utilities.general.hacks
  (:require [garden-compass.css3 :refer [*options*]]))

;; *options*
;;
;; :default-has-layout-approach :zoom

;; Mixins ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(declare has-layout-zoom has-layout-block)

(defn has-layout
  ([] (has-layout (:default-has-layout-approach *options*)))
  ([approach]
     (when (:legacy-support-for-ie *options*)
       (case approach
         :zoom (has-layout-zoom)
         :block (has-layout-block)
         ;; TODO: Warn
         (has-layout-zoom)))))

(defn has-layout-zoom []
  (if (or (:legacy-support-for-ie6 *options*)
          (:legacy-support-for-ie7 *options*))
    {:*zoom 1}))

(defn has-layout-block []
  (when (:legacy-support-for-ie *options*)
    {:display :inline-block
     :& {:display :block}}))

(defn bang-hack [property value ie6-value]
  (when (:legacy-support-for-ie6 *options*)
    [:& {property [[value :!important]]}
        {property ie6-value}]))
