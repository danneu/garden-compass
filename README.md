# garden-compass

A port of [Compass](http://compass-style.org/) to Clojure for use with [Garden](https://github.com/noprompt/garden).

Namespaces:

    garden-compass.css3
    garden-compass.helpers
    garden-compass.layout
    garden-compass.reset
    garden-compass.typography
    graden-compass.utilities

## Progress

It's mostly unorganized/naive experimentation to feel things out since I did not know how to best organize it as I was writing it.

I have some better ideas now and nothing is set in stone.

## Goals

- Expose an API as similar to Compass as possible.
- Map namespace and variable names to Compass'.
- Remove or improve the API cruft that only exists because of Sass limitations.
- Reveal features that make more sense to be included in Garden.
- Expose all config-options (like `images-path`) and css-options (like `default-padding-size`) with two central maps that are easy to work with and to override.

## Tests

Run all tests:

    lein expectations

Run focused autotests:

    lein autoexpect
