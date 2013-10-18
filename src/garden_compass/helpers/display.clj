(ns garden-compass.helpers.display
  (:require [garden-compass.css3 :refer [*options*]]
            [garden.compiler :refer [compile-css]]))

(defn elements-of-type [element-type]
  (element-type {:block
                 [:address :article :aside :blockquote :center :dd :details :dir :div :dl :dt :fieldset :figcaption :figure :footer :form :frameset :h1 :h2 :h3 :h4 :h5 :h6 :header :hgroup :hr :isindex :menu :nav :noframes :noscript :ol :p :pre :section :summary :ul]

                 :inline
                 [:a :abbr :acronym :audio :b :basefont :bdo :big :br :canvas :cite :code :command :datalist :dfn :em :embed :font :i :img :input :kbd :keygen :label :mark :meter :output :progress :q :rp :rt :ruby :s :samp :select :small :span :strike :strong :sub :sup :textarea :time :tt :u :var :video :wbr]

                 :inline-block
                 [:img]

                 :table
                 [:table]

                 :list-item
                 [:li]

                 :table-row-group
                 [:tbody]

                 :table-header-group
                 [:thead]

                 :table-footer-group
                 [:tfoot]

                 :table-row
                 [:tr]

                 :table-cell
                 [:table-cell]

                 :html5-block
                 [:article :aside :details :figcaption :figure :footer :header :hgroup :menu :nav :section :summary]

                 :html5-inline
                 [:audio :canvas :command :datalist :embed :keygen :mark :meter :output :progress :rp :rt :ruby :time :video :wbr]

                 :html5
                 [:article :aside :audio :canvas :command :datalist :details :embed :figcaption :figure :footer :header :hgroup :keygen :mark :menu :meter :nav :output :progress :rp :rt :ruby :section :summary :time :video :wbr]}
                (ex-info "Unknown element type."
                         {:element-type element-type})))
