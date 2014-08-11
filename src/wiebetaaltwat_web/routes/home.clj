(ns wiebetaaltwat-web.routes.home
  (:require [compojure.core :refer :all]
            [wiebetaaltwat-web.views.layout :as layout]
            [wiebetaaltwat-web.models.engine :as engine]))

(def expl "Plak of type hieronder een lijst met declaraties.<br/><br/><b>Uitleg:</b>")

(def placeholder "jan piet klaas hein
; -----------------------------------------------------------------------
; Formaat van een declaratie:
; [naam], [bedrag], [omschrijving], [deelnemer1 deelnemer2 deelnemer3...]
; -----------------------------------------------------------------------
jan, 15.75, boodschappen, piet klaas
piet, 25.10, bier, iedereen
; .... etc.")

(defn home []
  (layout/common 
    [:div {:class "container"}
      [:h1 "WIE BETAALT WAT"]
      [:p expl]
      [:ul 
        [:li "Op de eerste regel dien je alle deelnemers te typen, door spatie gescheiden."]
        [:li "Gebruik 'iedereen' als het voor iedereen is."]
        [:li "Een regel die begint met ';' is commentaar, bijvoorbeeld voor een notitie/opmerking."]
        [:li "Bedragen met een punt, en 2 decimalen, dus: 10.00 of 25.50"]
        [:li "<b>NB:</b> Het systeem werkt regel-voor-regel, breek dus lange regels niet af maar type gewoon door."]]
      [:form {:action "/results" :method "post"} 
        [:textarea {:id "textarea" :name "textarea" :rows 25 :cols 100 
                    :placeholder placeholder :onchange "storeInLocalStorage()" :onload "restoreFromLocalStorage()"}]
        [:br]
        [:input {:type "submit" :value "Berekenen" :name "submit"}]]]))


(def errmsg "De input text bevat een fout. Let erop dat bedragen dit formaat hebben: 10.00, en dat elke regel 4 waarden bevat:
[declarant],[bedrag], [omschrijving], [deelnemers, spatie gescheiden].") 

(defn results [input balances who-pays-who]
  (layout/common
    [:div {:class "container"}
      [:h1 "Afrekening"]
      [:a {:class "backtooverview" :href "javascript:history.back()"} "&larr; terug"]
      [:hr]
      [:h2 "Ingevoerde declaraties"]
      [:pre input]
      [:h2 "Saldi"]
      [:pre balances]
      [:h2 "Wie betaalt wat?"]
      [:pre who-pays-who]
      [:hr]
      [:a {:class "backtooverview" :href "javascript:history.back()"} "&larr; terug"]
     ]))

(defn errorscreen [msg] 
  (layout/common
    [:div {:class "container"}
      [:h1 "Fout"]
      [:pre msg]
      [:a {:class "backtooverview" :href "javascript:history.back()"} "&larr; terug"]
     ]))


(engine/get-report (engine/process-input "jan piet klaas\njan,10.00,bier,iedereen"))
(engine/get-who-pays-who (engine/process-input "jan piet klaas\njan,10.00,bier,iedereen"))

(defroutes home-routes
  (GET "/" [] (home))
  (POST "/results" [textarea]
      (try  
       (let [balance (engine/process-input textarea) 
             report  (engine/get-report balance)
             who-pays-who (engine/get-who-pays-who balance)] 
        (results textarea report who-pays-who))
        (catch Exception e
          (errorscreen errmsg)))))
