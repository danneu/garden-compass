(ns garden-compass.config)

;;http://compass-style.org/help/tutorials/configuration-reference/

(def ^:dynamic *config*
  {:environment :production  ; or :development
   ;; Path to project root. Can be inferred from context
   :project-path :TODO
   ;; Path to project when running iwthin web server.
   :http-path "/"
   ;; Relative path to dir where stylesheets are kept.
   ;; Relative to `project_path`.
   :css-dir "stylesheets"
   ;; Full path to where css stylesheets are kept.
   :css-path "/stylesheets"  ; <project-path>/<css-dir>
   ;;The full http path to stylesheets on the web server.
   :http-stylesheets-path :TODO  ; http-path "/" css-dir
   :fonts-dir "fonts"
   :fonts-path "/fonts"
   :images-dir "images"
   :images-path "/images"
   :relative-assets? true
   ;; ...
   ;; TODO
   ;; ...

   })
