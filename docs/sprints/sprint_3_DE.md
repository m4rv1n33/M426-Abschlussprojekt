# Sprint 3

## Sprint Goal

"Liefert ein vollständig spielbares End-to-End-Game: alle offenen Frontend-UI-Tasks abschliessen, mit dem Backend verbinden und alle verbleibenden offenen Issues schliessen"

## Sprint Duration

**1 Woche (5 Arbeitstage)**
Start: 11/06/2026
End: 17/06/2026

## Team Setup

Keine Änderungen gegenüber Sprint #2:

```
├── Frontend Supervisor: Finn
│   └── Gabriela, Laura
└── Backend Supervisor: Marvin
    └── Fenia, Lea
```

---

## Sprint Backlog - Technische Tasks

### Frontend Tasks (Supervisor: Finn)

**Team:** Finn, Gabriela, Laura

**Alle untenstehenden Tasks sind Carryover aus Sprint #2.**

- [ ] Shape-Upgrade Button UI implementieren (Gabriela unter Finns Guidance) - 3 SP - **[Lightwork]**
- [ ] Upgrades-Panel designen und programmieren (Finn + Laura Assistance) - 3 SP - **[Finn Lead]**
- [ ] Prestige-UI & Button erstellen (Laura unter Finns Guidance) - 2 SP - **[Lightwork]**
- [ ] Tutorial-Flow & State Management (Gabriela, Laura) - 3 SP - **[Minor]**
- [ ] Tutorial-Texte, Bilder & Content (Gabriela, Laura) - 2 SP - **[Lightwork]**
- [ ] Button Styling & UI Polish (Laura) - 2 SP - **[Lightwork]**
- [ ] Vollständiger Frontend-Backend Integration Smoke Test (Finn + Marvin) - 2 SP - **[Lead-Aufgabe]**

### Backend Tasks (Supervisor: Marvin)

**Team:** Marvin, Fenia, Lea

- [ ] Verbleibendes offenes Issue aus Sprint #2 schliessen (Marvin) - Priorität Tag 1 - **[Lead-Aufgabe]**
- [ ] Balance-Parameter verfeinern & Daten-getriebene Anpassungen (Marvin (ja, das machen wir wieder)) - 3 SP - **[Lead-Aufgabe]**
- [ ] Git-Grundlagen-Workshop (Marvin lead, alle Juniors nehmen teil) - 2 SP - **[Team goal]**
- [ ] Unit-Tests für Prestige-Tree schreiben (Fenia) - 2 SP - **[Lightwork]**
- [ ] UpgradeNode Edge-Case-Tests schreiben (Lea) - 2 SP - **[Lightwork]**

---

### Team-weite Tasks

- [ ] Git-Grundlagen-Workshop Verbesserungen oder Hands-on-Session bei Bedarf (Marvin lead, alle Juniors nehmen teil) - **2 SP**

---

**Gesamte Story Points: 27**

---

## Definition of Done

- [ ] Code-Review durchgeführt
- [ ] Unit-Tests geschrieben & grün
- [ ] Integration-Tests bestanden
- [ ] Pullrequest erstellt, Marvin oder Finn als Reviewer zugewiesen
- [ ] Keine kritischen Bugs
- [ ] Frontend und Backend sind vollständig verbunden und manuell getestet

---

## Risiken & Mitigationen

| Risiko | Wahrscheinlichkeit | Impact | Mitigation |
|--------|--------------------|--------|------------|
| Frontend-Carryover-Umfang zu gross für einen Sprint | Hoch | Hoch | Finn priorisiert und kürzt Tutorial-Umfang auf MVP falls nötig |
| Fenia liefert erneut keinen Output | Hoch | Mittel | Kleinstmöglichen, konkreten Task zuweisen; tägliches Check-in mit Marvin |
| Frontend-Backend-Integration deckt Bugs auf | Mittel | Hoch | Integration-Smoke-Test in Sprint-Mitte einplanen, nicht am Ende |
| Git-Workshop wird erneut verschoben | Mittel | Niedrig | Erste 2 Stunden von Sprint-Tag 1 dafür blocken |

---

## Notizen aus dem Sprint #2 Retrospective

- **Fenia** - zwei aufeinanderfolgende Sprints ohne Output; bekommt den kleinstmöglichen, sinnvollen Task (Prestige-Tree Unit-Tests, 2 SP) mit eindeutigem Scope.
- **Lea** - geringer Output in Sprint #2; bekommt alleinige Verantwortung für die UpgradeNode Edge-Case-Tests, um Accountability aufzubauen.
- **Frontend Recovery** - alle 6 Carryover-Tasks müssen diesen Sprint abgeschlossen werden; ohne sie ist das Spiel nicht shippable.
- **Git-Workshop** - eventuell zusätzliche Hands-on-Lektion, falls von den Juniors gewünscht.
- **Balance-Tuning** - mit der vollständigen Test-Suite können Balance-Parameter jetzt mit Vertrauen angepasst werden.

---

## Sprint Backlog als GitHub Issues

Jeder Task oben wird als GitHub-Issue-Entwurf mit Akzeptanzkriterien aufbereitet, bereit zum Anlegen im Issue-Tracker.

### Frontend

#### Issue: Shape-Upgrade Button UI implementieren
- **Labels:** `frontend`, `sprint-3`, `carryover`
- **Assignee:** Gabriela (Guidance: Finn)
- **Story Points:** 3
- **Tag:** Lightwork

**Beschreibung**
Den/die Button(s) im Spiel hinzufügen, mit denen der Spieler Shape-Upgrades direkt aus der Hauptansicht kauft, verbunden mit der bestehenden `UpgradeNode`-Kauflogik.

**Akzeptanzkriterien**
- [ ] Für jedes kaufbare Shape-Upgrade wird ein Button mit Name und aktuellen Kosten angezeigt
- [ ] Button ist deaktiviert/ausgegraut, wenn `canPurchase()` `false` zurückgibt (zu wenig Währung, unerfüllte Voraussetzungen oder bereits gekauft und nicht wiederholbar)
- [ ] Klick auf einen aktiven Button ruft die Backend-Kauflogik auf und aktualisiert die Währungsanzeige sofort
- [ ] Button-Status (aktiv/deaktiviert, Kostenanzeige) aktualisiert sich automatisch bei Änderung der Währung
- [ ] Manuell gegen das laufende Backend verifiziert (keine gemockten Daten)

---

#### Issue: Upgrades-Panel designen und programmieren
- **Labels:** `frontend`, `sprint-3`, `carryover`
- **Assignee:** Finn (Assistance: Laura)
- **Story Points:** 3
- **Tag:** Finn Lead

**Beschreibung**
Den Tab "Upgrades" (`upgradesCanvas` in `nusian-view.fxml`) ausbauen, um den vollständigen Upgrade-Tree mit Namen, Beschreibungen, Kosten und Kaufstatus anzuzeigen.

**Akzeptanzkriterien**
- [ ] Alle `UpgradeNode`s aus dem Backend werden mit Name, Beschreibung und aktuellen Kosten dargestellt
- [ ] Knoten mit unerfüllten Voraussetzungen (`hasUnlockedPrerequisites() == false`) sind visuell als gesperrt erkennbar
- [ ] Gekaufte, nicht wiederholbare Knoten zeigen einen klaren "gekauft"-Status
- [ ] Unendlich kaufbare Knoten zeigen die aktuelle Kaufanzahl und die aktualisierten Kosten nach jedem Kauf
- [ ] Panel-Inhalt aktualisiert sich live, ohne dass die App neu gestartet werden muss
- [ ] Layout von Finn geprüft und freigegeben

---

#### Issue: Prestige-UI & Button erstellen
- **Labels:** `frontend`, `sprint-3`, `carryover`
- **Assignee:** Laura (Guidance: Finn)
- **Story Points:** 2
- **Tag:** Lightwork

**Beschreibung**
Den Tab "Prestige" implementieren und den bestehenden `prestigeButton` (in `nusian-view.fxml`) mit dem Reset-Flow des `PrestigeStateManager` verbinden.

**Akzeptanzkriterien**
- [ ] Prestige-Tab zeigt die aktuelle Prestige-Währung sowie die Knoten des Prestige-Trees an
- [ ] `prestigeButton` zeigt die korrekten Kosten/Belohnung an und ist deaktiviert, wenn die Prestige-Voraussetzungen nicht erfüllt sind
- [ ] Klick auf `prestigeButton` löst den Backend-Prestige-Reset aus, und die UI spiegelt den neuen Spielstand wider
- [ ] Vor dem Prestige-Reset wird ein Bestätigungsdialog angezeigt
- [ ] Anzeige der Prestige-Währung (`prestigeCurrencyDisplay`) aktualisiert sich sofort nach erfolgreichem Prestige

---

#### Issue: Tutorial-Flow & State Management
- **Labels:** `frontend`, `sprint-3`, `carryover`
- **Assignee:** Gabriela, Laura
- **Story Points:** 3
- **Tag:** Minor

**Beschreibung**
Die State-Machine implementieren, die das Tutorial steuert: aktuellen Schritt verfolgen, Fortschritt erlauben und den Abschluss speichern, damit das Tutorial nicht erneut angezeigt wird.

**Akzeptanzkriterien**
- [ ] Tutorial-State verfolgt den aktuellen Schritt und schreitet bei Nutzeraktion (weiter/überspringen) voran
- [ ] Spieler können das Tutorial vollständig überspringen oder Schritt für Schritt durchgehen
- [ ] Nach Abschluss oder Überspringen erscheint das Tutorial bei späteren App-Starts nicht mehr
- [ ] Tutorial-State beeinträchtigt oder blockiert nicht den normalen Spielzustand (Währung, Upgrades, Prestige)

---

#### Issue: Tutorial-Texte, Bilder & Content
- **Labels:** `frontend`, `sprint-3`, `carryover`
- **Assignee:** Gabriela, Laura
- **Story Points:** 2
- **Tag:** Lightwork

**Beschreibung**
Texte verfassen und Bilder/Icons für jeden Tutorial-Schritt liefern, die die Kernmechaniken des Spiels erklären.

**Akzeptanzkriterien**
- [ ] Tutorial-Inhalte decken ab: Shape-Klicken/Produktion, das Upgrades-Panel und die Prestige-Mechanik
- [ ] Alle Texte sind korrekturgelesen, in der korrekten Sprache (gemäss Projektsprache) und konsistent im Ton
- [ ] Für jeden Tutorial-Schritt ist ein Bild oder Icon vorhanden und wird in der Ziel-Fenstergrösse korrekt dargestellt
- [ ] Inhalte von Finn vor dem Merge geprüft und freigegeben

---

#### Issue: Button Styling & UI Polish
- **Labels:** `frontend`, `sprint-3`, `carryover`
- **Assignee:** Laura
- **Story Points:** 2
- **Tag:** Lightwork

**Beschreibung**
Konsistentes visuelles Styling für alle Buttons, Tabs und Panels anwenden, die in Sprint #2 und Sprint #3 hinzugefügt wurden.

**Akzeptanzkriterien**
- [ ] Einheitliches Farbschema und einheitliche Schriftart über alle Buttons und Panels hinweg
- [ ] Hover-, Disabled- und Active-States sind für jeden interaktiven Button gestylt
- [ ] Kein Layout-Overflow oder Clipping bei der Ziel-Fenstergrösse der Anwendung
- [ ] Finales Styling auf visuelle Konsistenz mit der bestehenden UI geprüft

---

#### Issue: Vollständiger Frontend-Backend Integration Smoke Test
- **Labels:** `frontend`, `backend`, `sprint-3`, `integration`
- **Assignee:** Finn, Marvin
- **Story Points:** 2
- **Tag:** Lead-Aufgabe

**Beschreibung**
Einen End-to-End-Manualtest durchführen, der Produktion, Währungsaktualisierung, Upgrade-Käufe und Prestige umfasst, um zu bestätigen, dass Frontend und Backend vollständig verbunden sind.

**Akzeptanzkriterien**
- [ ] Ein vollständiger Game-Loop (Währung produzieren -> Upgrades kaufen -> Prestige) ist ohne Abstürze spielbar
- [ ] Währung und Upgrade-Status bleiben durchgehend zwischen UI und Backend synchron
- [ ] Edge Cases werden getestet: keine Währung, alle Upgrades gekauft, wiederholtes Prestige
- [ ] Gefundene Probleme werden vor Sprint-Ende als neue GitHub-Issues angelegt

---

### Backend

#### Issue: Verbleibendes offenes Issue aus Sprint #2 schliessen
- **Labels:** `backend`, `sprint-3`, `carryover`, `priority:high`
- **Assignee:** Marvin
- **Story Points:** -
- **Tag:** Lead-Aufgabe (Priorität Tag 1)

**Beschreibung**
Das eine offene Issue aus Sprint #2 lösen, bevor neue Backend-Arbeit begonnen wird.

**Akzeptanzkriterien**
- [ ] Das übernommene Issue aus Sprint #2 ist identifiziert und mit diesem Issue verlinkt
- [ ] Ursache ist behoben und durch Unit-/Integration-Tests abgedeckt
- [ ] PR ist gemerged und das ursprüngliche Issue ist am ersten Sprint-Tag geschlossen

---

#### Issue: Balance-Parameter verfeinern & Daten-getriebene Anpassungen
- **Labels:** `backend`, `sprint-3`, `balance`
- **Assignee:** Marvin
- **Story Points:** 3
- **Tag:** Lead-Aufgabe

**Beschreibung**
Mit der nun vollständigen Test-Suite die Spielbalance (Kosten, Wachstumsraten, Multiplikatoren) für eine flüssigere Progressionskurve anpassen und hartkodierte Werte wo sinnvoll in ein datengetriebenes Format überführen.

**Akzeptanzkriterien**
- [ ] Balance-relevante Parameter (z.B. `UpgradeCost`-Werte, Wachstumsraten) sind überprüft und angepasst
- [ ] Bestehende Unit- und Integration-Tests sind mit den neuen Werten grün; fehlende Abdeckung wird durch neue Tests ergänzt
- [ ] Geänderte Werte und deren Begründung sind dokumentiert
- [ ] Manueller Playtest bestätigt eine verbesserte Progressionskurve (keine übermässig langen Plateaus oder ausufernde Skalierung)

---

#### Issue: Git-Grundlagen-Workshop
- **Labels:** `backend`, `sprint-3`, `team-goal`, `onboarding`
- **Assignee:** Marvin (Lead), Fenia, Lea (Teilnehmende)
- **Story Points:** 2
- **Tag:** Team goal

**Beschreibung**
Einen praxisorientierten Git-Grundlagen-Workshop für die Junior-Teammitglieder durchführen, der Branching, Committen, Pushen und das Lösen von Merge-Konflikten abdeckt.

**Akzeptanzkriterien**
- [ ] Workshop-Session findet statt, beide Juniors nehmen teil
- [ ] Jeder Junior erstellt während der Session einen Branch, committet eine Änderung und eröffnet einen PR
- [ ] Mindestens ein Merge-Konflikt wird im Rahmen der Übung hands-on gelöst
- [ ] Workshop-Notizen/Cheat-Sheet werden unter `docs/` für zukünftige Referenz hinzugefügt

---

#### Issue: Unit-Tests für Prestige-Tree schreiben
- **Labels:** `backend`, `sprint-3`, `testing`
- **Assignee:** Fenia
- **Story Points:** 2
- **Tag:** Lightwork

**Beschreibung**
`PrestigeTreeTest` um Tests für Knotensuche, Tracking gekaufter Knoten und Referenzauflösung in `PrestigeTree` erweitern.

**Akzeptanzkriterien**
- [ ] `getNode(name)` wird sowohl für existierende als auch für nicht existierende Knotennamen getestet
- [ ] `getPurchasedNodes()` wird mit null, einem und mehreren gekauften Knoten getestet
- [ ] `resolveReferences()` verknüpft `previousNodes` korrekt anhand von `previousNodeNames`
- [ ] Alle neuen Tests laufen lokal und in der CI grün

---

#### Issue: UpgradeNode Edge-Case-Tests schreiben
- **Labels:** `backend`, `sprint-3`, `testing`
- **Assignee:** Lea
- **Story Points:** 2
- **Tag:** Lightwork

**Beschreibung**
`UpgradeNodeTest` um Edge Cases für Kaufberechtigung, Wiederholungskauf-Regeln und Fortschritts-Reset bei `UpgradeNode` erweitern.

**Akzeptanzkriterien**
- [ ] `canPurchase()` wird getestet für: falscher benötigter Shape-Type, unerfüllte Voraussetzungen, zu wenig Währung und einen bereits gekauften, nicht wiederholbaren Knoten
- [ ] `recordPurchase()` wirft `IllegalStateException`, wenn es ein zweites Mal auf einem nicht wiederholbaren Knoten aufgerufen wird
- [ ] `resetProgress()` setzt `purchaseCount` auf 0 zurück und ermöglicht den Kauf wieder, wo zutreffend
- [ ] `getCurrentCost()` wird über mehrere Kaufanzahlen hinweg für einen unendlich kaufbaren Knoten verifiziert
- [ ] Alle neuen Tests laufen lokal und in der CI grün

---

### Team-weit

#### Issue: Git-Grundlagen-Workshop Follow-up / Hands-on-Session
- **Labels:** `team-goal`, `sprint-3`, `onboarding`
- **Assignee:** Marvin (Lead), Fenia, Lea (Teilnehmende)
- **Story Points:** 2

**Beschreibung**
Optionale zusätzliche Hands-on-Git-Session für die Juniors, die bei Bedarf eingeplant wird, falls der erste Workshop Lücken aufzeigt.

**Akzeptanzkriterien**
- [ ] Feedback zum ersten Git-Workshop wird von beiden Juniors eingeholt
- [ ] Eine Follow-up-Session wird nur eingeplant, wenn das Feedback dies nahelegt
- [ ] Falls durchgeführt: Juniors können danach selbständig einen Branch erstellen, committen, pushen und einen PR eröffnen, ohne Unterstützung der Supervisors

---
