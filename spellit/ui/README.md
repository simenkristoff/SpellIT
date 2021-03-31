
# UI

Denne modulen inneholder brukergrensesnittet til SpellIT.

[[_TOC_]]

## Beskrivelse
UI-modulen representerer appplikasjonens brukergrensesnitt og er bygget med JavaFX. Modulen inneholder controllers for koordinering mellom [domenelaget](spellit/core/README.md) og brukerinteraksjon, og views for å representere brukergrensesnittet. 

Ved oppstart av applikasjonen vises en **Splashscreen** mens applikasjonen initialiserer nødvendige ressurser. Deretter blir brukeren tatt til hovedmenyen, hvor man kan enten laste inn en tidligere lagret instans eller starte en ny. Selve spillet viser frem brettet og spillerens tilgjengelige brikker, samt. gir en oversikt over spillernes poengsum og rundenummer.

![Application preview](resources/spellit_preview.jpg)

## Arkitektur

### Klassediagram
Nedenfor vises et forenklet klassediagram over UI-modulen
 ![UI classdiagram](architecture/ui-classdiagram.png)

### Sekvensdiagram

 #### Lagring av spillinstans

 Følgende sekvensdiagram gir en oversikt over hvordan applikasjonen lagrer en spillinstans.
 ![Save game sequencediagram](architecture/savegame-sequencediagram.png)
