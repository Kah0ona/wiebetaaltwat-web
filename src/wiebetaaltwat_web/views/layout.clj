(ns wiebetaaltwat-web.views.layout
  (:require [hiccup.page :refer [html5 include-css include-js]]))

(defn common [& body]
  (html5
    [:head
     [:title "Wie Betaalt Wat - Door Marten Sytema"]
     (include-css "/css/screen.css")
     (include-js "http://code.jquery.com/jquery-2.1.1.min.js")
     (include-js  "/js/store-cookie.js")]
    [:body body]))
