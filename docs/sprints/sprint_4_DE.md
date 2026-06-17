# Sprint 4

## Sprint Goal

"Liefert ein vollständiges, spielbares End-to-End-Spiel, indem das ganze Team auf das Frontend-Carryover-Backlog fokussiert und es mit dem Backend integriert"

## Sprint Duration

**1 Woche (5 Arbeitstage)**
Start: 18/06/2026
End: 24/06/2026

## Team Setup (All-Hands Frontend Push)

Nach dem Sprint-#3-Retrospective und als drastische Massnahme, um das Frontend-Backlog abzuarbeiten, wechseln alle Teammitglieder ausser Marvin ins Frontend. Marvin bleibt im Backend, übernimmt die Integration und agiert als Gesamt-Lead:

```
├── Frontend (all hands, Supervisor: Finn)
│   └── Gabriela, Laura, Fenia, Lea
└── Backend / Integration / Lead: Marvin
```

---

## Sprint Backlog - Technische Tasks

### Frontend Tasks (Supervisor: Finn)

**Team:** Finn, Gabriela, Laura, Fenia, Lea

**Alle Feature-Tasks unten sind Carryover aus Sprint #3.**

- [ ] Ausstehende Arbeit mergen & aufräumen: Lauras Branch `feature/prestige_UI` reviewen & mergen, dann die gemergten Branches löschen - Priorität für Tag 1 - **[Lead-Aufgabe]**
- [ ] Upgrades-Panel designen und programmieren (Finn Lead + Gabriela) - 3 SP - **[Finn Lead]**
- [ ] Prestige-Upgrade-UI umsetzen (Laura unter Finns Guidance) - 2 SP - **[Lightwork]**
- [ ] Tutorial-Flow & State Management (Fenia + Lea) - 3 SP - **[Minor]**
- [ ] Tutorial-Texte, Bilder & Content (Lea + Fenia) - 2 SP - **[Lightwork]**
- [ ] Button Styling & UI Polish (Laura) - 2 SP - **[Lightwork]**

### Backend Tasks (Supervisor: Marvin)

**Team:** Marvin

- [ ] Vollständiger Frontend-Backend-Integration-Smoke-Test (Marvin) - 2 SP - **[Lead-Aufgabe]**
- [ ] Balance-Parameter verfeinern & Daten-getriebene Anpassungen, jetzt wo das spielbare Frontend ein Gefühl für das Spiel gibt (Marvin) - 3 SP - **[Lead-Aufgabe]**

---

**Gesamte Story Points: 19**

---

## Definition of Done

- [ ] Code-Review durchgeführt
- [ ] Unit-Tests geschrieben & grün
- [ ] Integration-Tests bestanden
- [ ] Pull Request erstellt, Marvin oder Finn als Reviewer zugewiesen
- [ ] Gemergte Feature-Branches gelöscht, lokal und remote
- [ ] Keine kritischen Bugs
- [ ] Frontend und Backend sind vollständig verbunden und manuell getestet
- [ ] Das Spiel ist End-to-End spielbar (Currency produzieren -> Upgrades kaufen -> Prestige)

---

## Notizen aus dem Sprint-#3-Retrospective

- **Frontend Recovery (drastische Massnahme)** - das ganze Team ausser Marvin ist diesen Sprint im Frontend, um die sechs Carryover-Tasks abzuarbeiten und ein spielbares Spiel zu liefern.
- **Review-Turnaround** - Gabrielas PR lag letzten Sprint mehrere Tage ohne Review; Supervisors reviewen diesen Sprint innerhalb von 24-48h.
- **Git-Disziplin** - am Ende jedes Arbeitstags auf das Remote pushen; Feature-Branches mit `main` synchron halten und nach dem Merge löschen; Commit-Naming-Conventions befolgen.
- **Timing** - am ersten Tag mit der Arbeit beginnen, nicht spät im Sprint.

---
