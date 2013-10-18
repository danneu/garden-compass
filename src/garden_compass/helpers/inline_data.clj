(ns garden-compass.helpers.inline-data
  (:require [garden-compass.util :as util]
            [garden-compass.css-functions :refer [url]])
  (:import [java.io File]
  ))

(comment
  ;; url('data:#{mime_type};base64,#{data}')
)

(defn inline-image [path]
;; url(data:image/jpeg;base64,...)
  (let [f (File. path)]
    (if-not (.exists f)
      (ex-info "File not found:" {:path path})
      (let [mime-type (util/path-to-mime path)
            encoded-data (util/base64-encode-string
                          (util/get-file-bytes f))]
        (url (str "data:" mime-type ";base64," encoded-data))))))

(defn inline-font-files [font format])
