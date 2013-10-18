(ns garden-compass.helpers.urls
  (:require [expectations :refer :all]
            [garden-compass.util :as util]
            [garden-compass.config :refer [*config*]]
            [garden-compass.css-functions :refer [url]]))

;; https://github.com/chriseppstein/compass/blob/9ee6b422262668e8a7e154d2ac34f350ee331dc0/lib/compass/sass_extensions/functions/urls.rb

(comment
  ;; If input to these functions is a relative path, then they
  ;; are relative to :css-dir, :font-dir, and :image-dir.
  ;;
  ;; Those directories should exist in resources/public/.

  (stylesheet-url ...)
  "foo.css"                 ; -> url(/stylesheets/foo.css)
  "/foo.css"                ; -> url(/foo.css)
  "http://bar.com/foo.css"  ; -> url(http://bar.com/foo.css)

  (font-url ...)
  "foo.ttf"                 ; -> url(/fonts/foo.ttf)
  "/foo.ttf"                ; -> url(/foo.ttf)
  "http://bar.com/foo.ttf"  ; -> url(http://bar.com/foo.ttf)

  (image-url ...)
  "foo.jpg"                 ; -> url(/fonts/foo.jpg)
  "/foo.jpg"                ; -> url(/foo.jpg)
  "http://bar.com/foo.jpg"  ; -> url(http://bar.com/foo.jpg)
)

(defn stylesheet-url
  [path]
  (if (util/absolute-path? path)
    (url path)
    (let [base (if (:relative-assets? *config*)
                 (:css-path *config*)
                 ;; TODO
                 )]
      (url (util/join-with-slash base path)))))

(defn font-url
  [path]
  (if (util/absolute-path? path)
    (url path)
    (let [base (if (:relative-assets? *config*)
                 (:fonts-path *config*)
                 ;; TODO
                 )]
      (url (util/join-with-slash base path)))))

(defn image-url
  [path]
  (if (util/absolute-path? path)
    (url path)
    (let [base (if (:relative-assets? *config*)
                 (:images-path *config*)
                 ;; TODO
                 )]
      (url (util/join-with-slash base path)))))
