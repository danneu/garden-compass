(ns garden-compass.css3
  (:require [garden.core :refer :all]
            [garden.units :refer []]
            [clojure.set :as set])
  (:import [garden.types CSSFunction]))

(def cssfn (fn [fn-name]
             (fn [& args]
               (CSSFunction. fn-name args))))

;; Utils ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn merge-with-set
  "Merges maps but concats the values of dupe keys into sets.
   (merge-with-set {:a 1 :b 2} {:a 3})
   -> {:a #{1 3}, :b 2}"
  [& maps]
  (apply merge-with
         (fn [a b]
           (if (set? a)
             (conj a b)
             (conj #{a} b)))
         maps))

(defn all-vendors
  "Returns map of all vendor-prefix version of {prop arg}."
  [prop args]
  {:-webkit {prop args}
   :-khtml {prop args}
   :-moz {prop args}
   :-ms {prop args}
   :-o {prop args}
   prop args})

(defn vendor
  ([prop args]
     (all-vendors prop args))
  ([prop args keys]
     (select-keys (all-vendors prop args) (conj keys prop))))

(def option-to-vendor-prefix
  "Translates the vendor-related *options* keys into their
   vendor prefixes."
  {:experimental-support-for-webkit    :-webkit
   :experimental-support-for-khtml     :-khtml
   :experimental-support-for-mozilla   :-moz
   :experimental-support-for-microsoft :-ms
   :experimental-support-for-opera     :-o})

;; Options ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;; TODO: Extract options.

(defn ie-options
  ([] (ie-options {}))
  ([{:keys [legacy-support-for-ie
            legacy-support-for-ie6
            legacy-support-for-ie7
            legacy-support-for-ie8]
     :as opts}]
     (let [base (:legacy-support-for-ie opts true)]
       (merge {:legacy-support-for-ie base
               :legacy-support-for-ie6 (or legacy-support-for-ie6
                                           base)
               :legacy-support-for-ie7 (or legacy-support-for-ie7
                                           base)
               :legacy-support-for-ie8 (or legacy-support-for-ie8
                                           base)}
              {:legacy-support-for-ie (or legacy-support-for-ie6
                                          legacy-support-for-ie7
                                          legacy-support-for-ie8
                                          base)}))))

(defn build-options
  ([] (build-options {}))
  ([overrides]
     (merge (ie-options)
            {:legacy-support-for-mozilla true
             :experimental-support-for-mozilla true
             :experimental-support-for-webkit true
             :support-for-original-webkit-gradients true
             :experimental-support-for-opera true
             :experimental-support-for-microsoft true
             :experimental-support-for-khtml false
             :experimental-support-for-svg false
             :experimental-support-for-pie false
             :inline-block-alignment :middle
             :default-background-clip :padding-box
             :default-background-origin :content-box
             :default-background-size "100% auto"
             :default-border-radius "5px"
             :default-box-orient :horizontal
             :default-box-align :stretch
             :default-box-flex 0
             :default-box-flex-group 1
             :default-box-direction :normal
             :default-box-lines :single
             :default-box-pack :start

             :contrasted-dark-default        "black"
             :contrasted-light-default       "white"
             :contrasted-lightness-threshold 30  ; (%)

             :default-has-layout-approach :zoom
             ;; ...
             ;; TODO
             ;; ...
             }
            overrides)))

(def ^:dynamic *options* (build-options))

(defn options-enabled-vendors
  "Returns set of vendor-prefixes.
   Ex: #{:-ms :-webkit}"
  []
  (->> (filter second *options*)
       (map first)
       (map option-to-vendor-prefix)
       (remove nil?)
       (set)))

;; Compass API ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; compass/css3/shared

(defn experimental
  ([property value]
     (vendor property value (options-enabled-vendors)))
  ([property value overrides]
     (if (empty? overrides)
       (vendor property value [])
       (let [vendor-keys (set/intersection
                          (set overrides)
                          (options-enabled-vendors))]
         (vendor property value vendor-keys)))))

(defn experimental-value
  ([prop val]
     (apply merge-with-set
            {prop (name val)}
            (for [prefix (options-enabled-vendors)]
              {prop (str (name prefix) "-" (name val))})))
  ([prop val overrides]
     (let [vendor-keys (set/intersection
                        (set overrides)
                        (options-enabled-vendors))]
       (apply merge-with-set
              {prop (name val)}
              (for [prefix vendor-keys]
                {prop (str (name prefix) "-" (name val))})))))

;; compass/css3/Appearance ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn appearance [ap]
  (experimental :appearance ap [:-moz :-webkit]))

;; compass/css3/Background Clip ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn background-clip
  ([] (background-clip (:default-background-clip *options*)))
  ([clip]
     (let [deprecated (case clip
                        :padding-box :padding
                        :border-box  :border
                        clip)]
       (merge (experimental :background-clip
                            deprecated
                            [:-moz :-webkit])
              (experimental :background-clip clip [:-khtml])))))

;; compass/css3/Background Origin ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn background-origin
  "Possible `origin` values: :padding-box :border-box :content-box.
   Browser defaults to :padding-box.
   Mixin defaults to :content-box."
  ([] (background-origin (:default-background-origin *options*)))
  ([origin]
     (let [deprecated (case origin
                        :padding-box :padding
                        :border-box  :border
                        :content-box :content)]
       (merge (experimental :background-origin
                            deprecated
                            [:-moz :-webkit])
              (experimental :background-origin
                            origin
                            [:-o :-ms :-khtml])))))

;; compass/css3/Background Size ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn background-size
  ([] (background-size (:default-background-size *options*)))
  ([& sizes]
     (experimental :background-size sizes [:-moz :-webkit :-o])))

;; compass/css3/Border Radius ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn border-radius
  ([] (border-radius (:default-border-radius *options*) nil))
  ([radius vertical-radius]
     (if vertical-radius
       (merge (experimental :border-radius
                            [(first radius)
                             (first vertical-radius)]
                            [:-webkit])
              (experimental :border-radius
                            (concat radius ["/"] vertical-radius)
                            [:-moz :-khtml]))
       (experimental :border-radius radius))))

;; compass/css3/Box ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn display-box [prop val]
  (experimental-value prop val [:-moz :-webkit :-ms]))

(defn box-orient
  ([] (box-orient (:default-box-orient *options*)))
  ([orientation]
     (experimental :box-orient orientation [:-moz :-webkit :-ms])))

(defn box-align
  ([] (box-align (:default-box-align *options*)))
  ([alignment]
     (experimental :box-align alignment [:-moz :-webkit :-ms])))

(defn box-flex
  ([] (box-flex (:default-box-flex *options*)))
  ([flex]
     (experimental :box-flex flex [:-moz :-webkit :-ms])))

(defn box-flex-group
  ([] (box-flex-group (:default-box-flex-group *options*)))
  ([group]
     (experimental :box-flex-group group [:-moz :-webkit :-ms])))

(defn box-ordinal-group
  ;; TODO no test
  ;; TODO Is Compass right about :default-ordinal-flex-group?
  ([] (box-ordinal-group (:default-ordinal-flex-group *options*)))
  ([group]
     (experimental :box-ordinal-group
                   group
                   [:-moz :-webkit :-ms])))

(defn box-direction
  ([] (box-direction (:default-box-direction *options*)))
  ([direction]
     (experimental :box-direction
                   direction
                   [:-moz :-webkit :-ms])))

(defn box-lines
  ([] (box-lines (:default-box-lines *options*)))
  ([lines]
     (experimental :box-lines lines [:-moz :-webkit :-ms])))

(defn box-pack
  ([] (box-pack (:default-box-pack *options*)))
  ([pack]
     (experimental :box-pack pack [:-moz :-webkit :-ms])))

;; compass/css3/Box Shadow ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; compass/css3/Box Sizing ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; compass/css3/CSS Regions ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn flow-into [target]
  (dissoc (experimental :flow-into target [:-webkit :-ms])
          :flow-into))

(defn flow-from [target]
  (dissoc (experimental :flow-from target [:-webkit :-ms])
          :flow-from))

;; compass/css3/CSS3 Pie ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; compass/css3/Columns ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; compass/css3/Filter ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; compass/css3/Font Face ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; compass/css3/Hyphenation ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn word-break
  ([] (word-break :normal))
  ([value]
     (if (= value :break-all)
       (merge-with-set (experimental :word-break value [:-ms])
                       (experimental :word-break :break-word []))
       (experimental :word-break (or value :normal) [:-ms]))))

(defn hyphens
  ([] (hyphens :auto))
  ([value] (experimental :hyphens value [:-moz :-webkit])))

(defn hyphenation []
  (merge (word-break :break-all)
         (hyphens)))

;; compass/css3/Images ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; compass/css3/Inline Block ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Option: :inline-block-alignment :middle
(defn inline-block
  ([] (inline-block (:inline-block-alignment *options*)))
  ([alignment]
      (let [{:keys [legacy-support-for-mozilla
                    legacy-support-for-ie]}
            *options*]
        (apply merge-with
               (fn [a b]
                 (if (set? a)
                   (conj a b)
                   (conj #{a} b)))
               [{:display :inline-block}
                (when alignment
                  {:vertical-align alignment})
                (when legacy-support-for-mozilla
                  {:display :-moz-inline-stack})
                (when legacy-support-for-ie
                  {:*vertical-align :auto
                   :zoom 1
                   :*display :inline})]))))

;; compass/css3/Opacity ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn- microsoft-alpha
  "Opacity 0-100 integer."
  [opacity]
  (let [arg (str "Opacity=" opacity)]
    ((cssfn "progid:DXImageTransform.Microsoft.Alpha") arg)))

(defn opacity
  "Opacity 0.0-1.0"
  [opacity]
  (merge {:opacity opacity}
         (when (or (:legacy-support-for-ie6 *options*)
                   (:legacy-support-for-ie7 *options*)
                   (:legacy-support-for-ie8 *options*))
           {:filter
            (microsoft-alpha (int (* 100 opacity)))})))

(defn transparent []
  (opacity 0))

(defn opaque []
  (opacity 1))

;; compass/css3/Shared Utilities ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; compass/css3/Text Shadow ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; compass/css3/Transform ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; compass/css3/Transform (legacy) ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; compass/css3/Transition ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; compass/css3/User Interface ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn user-select [select]
  (experimental :user-select select [:-moz :-webkit :-khtml]))
