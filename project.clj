(defproject garden-compass "0.1.0-SNAPSHOT"
  :description "A port of Compass to Clojure for use with Garden."
  :url "http://github.com/danneu/garden-compass"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [garden "1.1.2"]
                 [expectations "1.4.56"]
                 [commons-codec "1.8"]
                 [commons-io "1.4"]]
  :plugins [[lein-expectations "0.0.7"]])
