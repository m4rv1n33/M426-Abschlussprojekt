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
