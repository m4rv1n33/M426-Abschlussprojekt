# Sprint 5 (Final Sprint)

## Sprint Goal

"Ein vollständiges, integriertes, spielbares Spiel bis zur harten Deadline liefern und einreichen. Alle ausstehenden Branches mergen, das verbleibende Frontend fertigstellen, stabilisieren und früh einfrieren. Kein neuer Scope."

## Sprint Duration

**1 Woche (finaler Sprint)**
Start: 25/06/2026
End: 30/06/2026
**Harte Einreichungs-Deadline: 29/06/2026, 21:00**

## Team Setup (Finaler Integration Push)

Das All-Hands-Frontend-Setup aus Sprint #4 wird fortgesetzt. Dies ist der letzte Sprint, es geht also um Stabilisierung und Integration, nicht um neue Features. Die Arbeit auf dem kritischen Pfad wird den Mitgliedern zugewiesen, die konstant geliefert haben; Marvin verantwortet die Integration und bleibt Gesamt-Lead:

```
├── Frontend (all hands, Supervisor: Finn)
│   └── Gabriela, Laura, Fenia, Lea
└── Backend / Integration / Lead: Marvin
```

Das Backend ist Feature-complete, daher liegt Marvins Fokus diesen Sprint auf der Integration, dem Smoke-Test sowie der Begleitung von Merge und Einreichung.

---

## Sprint Backlog - Technische Tasks

### Priorität Tag 1 - Alles mergen

Nichts kann fertiggestellt oder getestet werden, bevor die ausstehenden Branches auf `main` sind. Das muss an Tag 1 geschehen.

- [ ] Lauras Branch `feature/prestige_UI` mit `main` synchronisieren und mergen, dann löschen (Stand Sprint-#4-Abschluss: 10 voraus, 4 hinter) - **[Lead-Aufgabe]**
- [ ] Finns Branch `upgrade-tree` (Tutorial-UI-State, #5) mit `main` synchronisieren und mergen, dann löschen - **[Lead-Aufgabe]**
- [ ] Ein sauberes, baubares `main` nach beiden Merges bestätigen, bevor weitere Arbeit beginnt - **[Lead-Aufgabe]**

### Frontend Tasks (Supervisor: Finn)

**Team:** Finn, Gabriela, Laura, Fenia, Lea

- [ ] Upgrades-Panel designen und umsetzen - #13 - 3 SP - **[Finn Lead]**
- [ ] Prestige-Tree, Schritt 3 - #55 - 2 SP - **[Lightwork]**
- [ ] Gesamtes Styling & UI Polish - #15 - 2 SP - **[Lightwork]**
- [ ] Manuelles Playtesting und Bug-Reporting gegen den gemergten Build - 2 SP - **[Minor]**

### Backend / Integration Tasks (Supervisor: Marvin)

**Team:** Marvin

- [ ] Vollständiger Frontend-Backend-Integration-Smoke-Test gegen den vollständig gemergten Build - #54 - 2 SP - **[Lead-Aufgabe]**
- [ ] Integrationsdefekte beheben, die der Smoke-Test aufdeckt - #58 - 2 SP - **[Lead-Aufgabe]**

---

**Gesamte Story Points (ohne Stretch): 13**
**Stretch: +3 (#38)**

---

## Zeitplan finale Woche

- **25/06 (Tag 1):** beide Branches mergen, `main` aufräumen, baubares Spiel bestätigen.
- **26/06 - 28/06:** Upgrades-Panel (#13), Prestige Schritt 3 (#55), Styling (#15) fertigstellen; Integration-Smoke-Test (#54) ausführen und Defekte beheben.
- **29/06 (Freeze):** Feature-Freeze, vollständiger End-to-End-Playtest, nur kritische Bugs beheben.
- **30/06:** finaler Playtest, Einreichungs-Vorbereitung, **vor 21:00 einreichen.**

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
- [ ] Das Projekt ist vor dem 30/06/2026 21:00 eingereicht

---

## Notizen aus dem Sprint-#4-Retrospective

- **Zuerst mergen** - der grösste Teil des Sprint-#4-Fortschritts steckt auf un-gemergten Branches fest (`feature/prestige_UI`, `upgrade-tree`). Das an Tag 1 auf `main` zu bringen ist die wichtigste Aktion des Sprints.
- **Früh einfrieren** - bis 29/06 Feature-complete sein, damit ein voller Tag für den finalen Playtest und die Einreichung bleibt und kein Last-Minute-Stress entsteht.
- **Kein neuer Scope** - dies ist der finale Sprint; nur fertigstellen, integrieren und stabilisieren, was bereits existiert.
- **Git-Disziplin** - am Ende jedes Arbeitstags auf das Remote pushen; Branches mit `main` synchron halten und nach dem Merge löschen.
- **Rebase-Unterstützung** - Finn hat letzten Sprint Zeit mit Rebase-Problemen verloren; bei den Merges zusammenarbeiten statt allein zu rebasen.

---
