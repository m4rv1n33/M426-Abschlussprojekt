# Sprint 1

## Sprint Goal

"Implementiert die Core-Gameplay-Mechaniken eines funktionierenden Incremental Games mit Shape-Upgrades, Currency-System und Prestige-Funktion"

## Sprint Duration

**1 Woche (5 Arbeitstage)**
Start: 27/05/2026
End: 03/06/2026

## User Stories & Story Points

| # | User Story                  | Story Points | Assignee           | Status |
|---|-----------------------------|--------------|--------------------|--------|
| 1 | Currency-Stand sehen        | 5            | Frontend           | TODO   |
| 2 | Formen upgraden             | 8            | Backend + Frontend | TODO   |
| 3 | Upgrades einsehen           | 5            | Frontend           | TODO   |
| 4 | Strategische Entscheidungen | 8            | Backend (Balance)  | TODO   |
| 5 | Tutorial (TEMP)             | 5            | Frontend           | TODO   |
| 6 | Prestiges durchführen       | 8            | Backend + Frontend | TODO   |

**Total Story Points: 39**

## Sprint Backlog - Technische Tasks

### Frontend Tasks (Supervisor: Finn)

- [x] HUD-Layout mit Currency-Anzeige erstellen (Finn) - 3 SP - **[Lead-Aufgabe]**
- [ ] Shape-Upgrade Button UI implementieren (Lea, Fenia unter Finns Anleitung) - 3 SP - **[Lightwork]**
- [ ] Upgrades-Panel designen und programmieren (Finn + Fenia Assistance) - 3 SP - **[Finn Lead]**
- [ ] Tutorial-Flow & State Management (Finn) - 3 SP - **[Lead-Aufgabe]**
- [ ] Real-time Currency-Update - Backend-Integration (Finn + Lea Assistance) - 3 SP - **[Finn Lead]**
- [ ] Tutorial-Texte, Bilder & Content (Lea, Fenia) - 2 SP - **[Lightwork]**
- [ ] Button Styling & UI Polish (Lea) - 2 SP - **[Lightwork]**

### Backend Tasks (Supervisor: Marvin)

- [x] Currency-Production-System implementieren (Marvin) - 5 SP - **[Lead-Aufgabe]**
- [x] Upgrade-Kosten-Berechnung mit Tests (Marvin + Gabriela Codereview) - 3 SP - **[Marvin Lead]**
- [x] Shape-Datenmodell & Entity erstellen (Gabriela unter Marvins Guidance) - 2 SP - **[Lightwork]**
- [x] Shape-Vertices-Logik (Laura unter Marvins Guidance) - 2 SP - **[Lightwork]**
- [x] Prestige-System & Score-Speicherung (Marvin) - 5 SP - **[Lead-Aufgabe]**
- [x] Balance-Parameter & Tweaking (Marvin) - 3 SP - **[Lead-Aufgabe]**
- [x] Unit-Tests Template & Struktur (Marvin + Laura) - 2 SP - **[Laura: einfache Tests]**
- [x] Upgrade-State Manager (Gabriela unter Marvins Guidance) - 2 SP - **[Lightwork]**

## Definition of Done

- [ ] Code-Review durchgeführt
- [ ] Unit-Tests geschrieben & grün
- [ ] Integration-Tests bestanden
- [ ] UI/UX auf Responsive-Design getestet
- [ ] Code in `main`-Branch gemergt
- [ ] Keine kritischen Bugs

## Risiken & Mitigationen

| Risiko                                 | Wahrscheinlichkeit | Impact | Mitigation                                          |
|----------------------------------------|--------------------|--------|-----------------------------------------------------|
| Backend-Performance bei vielen Shapes  | Mittel             | Hoch   | Early Load-Testing, Caching                         |
| UI-Verzögerungen bei Real-time Updates | Niedrig            | Mittel | Optimization Sprint geplant                         |
| Balance-Issues                         | Hoch               | Mittel | Weekly Balance-Review, Daten-getriebene Anpassungen |

