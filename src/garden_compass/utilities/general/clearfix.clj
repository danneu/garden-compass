(ns garden-compass.utilities.general.clearfix
  (:require [garden-compass.utilities.general.hacks
             :refer [has-layout]]))

;; Mixins ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn clearfix []
  [:& {:overflow :hidden}
      (has-layout)])

(defn legacy-pie-clearfix []
  [:& [:&:after {:content "\0020"
                 :display :block
                 :height 0
                 :clear :both
                 :overflow :hidden
                 :visibility :hidden}]
      [:& (has-layout)]])

(defn pie-clearfix []
  [:& [:&:after {:content "\"\""
                 :display :table
                 :clear :both}]
      [:& (has-layout)]])
