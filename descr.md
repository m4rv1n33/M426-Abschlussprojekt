# M426-Abschluss Projekt

Team: Finn, Marvin, Lea, Gabriela, Fenia, Laura

Titel: Game (whatever that means)

---

Struktur / Arbeitsteilung:

```
├── Frontend Supervisor: Finn
│   └── Fenia, Lea
└── Backend Supervisor: Marvin
    └── Gabriela, Laura
```

Projektidee / Grundstruktur:

- Incremental game
- Beginner friendly
- Use shapes to produce currency
- Upgrade shapes to add more vertices = more currency
- Starts in 2 dimensions
- Prestiges for more dimensions / reset
- Java
- Automated backend tests

## Core Mechanics:

**Shape System:**
- You have a shape which produces currency
- The shape starts off as a single point
- There are upgrades you can purchase to improve your production

**Prestige Mechanics (Vertices):**
- Reset your upgrades and currency etc. to gain prestige currency
- Spend prestige currency on a second set of upgrades which stay between resets
- There is an infinitely purchaseable upgrade that increases your vertex count,
vertex count acts as an immediate multiplier to your production
- Prestige currency gain is based on currency when you reset

**Game Loop:**
- Earn currency
- Upgrade shape when you have enough
- Watch vertices increase and production skyrocket
- Eventually prestige to start fresh with a boost
- Repeat endlessly with increasing bonuses

**Persistence:**
- Game saves to local JSON file
- Automatic saves on every state change
- Load on startup - seamless progression

---

User stories:
Als [Rolle] möchte ich [Funktion] weil [Grund].

- Als Player möchte ich Prestiges durchführen können, weil ich meinen Score erhöhen möchte.
- Als Player möchte ich strategische Entscheidungen fällen müssen, weil es interessanter ist.
- Als Player möchte ich Formen upgraden können, weil sie dann mehr Vertices haben und mehr Currency produzieren.
- Als Neueinsteiger möchte ich ein Tutorial haben, weil ich die Spielmechaniken verstehen möchte. (TEMP)
- Als Player möchte ich meinen aktuellen Currency-Stand sehen, weil ich meinen Fortschritt verfolgen möchte.
- Als Player möchte ich meinen Upgrades sehen, um meinen Stand zu sehen.

## Akzeptanzkriterien pro User Story

### US1: Prestiges durchführen

- **Gegeben:** Spieler hat ein Preset-Level erreicht
- **Wenn:** Spieler klickt auf "Prestige"-Button
- **Dann:**
    - Score wird zur Persisting-Statistik hinzugefügt
    - Alle aktuellen Ressourcen/Formen werden zurückgesetzt
    - Spieler erhält Prestige-Bonuse (z.B. +5% Basis-Currency-Produktion)
    - Fortschritt wird korrekt gespeichert

### US2: Strategische Entscheidungen

- **Gegeben:** Spieler ist im aktiven Spiel
- **Wenn:** Spieler muss zwischen mehreren Upgrade-Optionen wählen
- **Dann:**
    - Mind. 2 verschiedene Upgrade-Pfade stehen zur Verfügung
    - Kosten und Nutzen sind klar erkennbar
    - Entscheidungen wirken sich langfristig aus

### US3: Formen upgraden

- **Gegeben:** Spieler hat genug Currency
- **Wenn:** Spieler klickt "Upgrade" für eine Form
- **Dann:**
    - Vertices erhöhen sich um 1
    - Upgrade-Kosten steigen progressiv
    - Currency wird abgezogen
    - Neue Produktionsrate wird sofort angewendet
    - UI zeigt neue Stats an

### US4: Tutorial (TEMP)

- **Gegeben:** Neuer Spieler startet das Spiel
- **Wenn:** Tutorial wird aktiviert
- **Dann:**
    - Alle Basis-Mechaniken werden erklärt
    - Interactive Guides führen durch erste Schritte
    - Tutorial kann übersprungen werden
    - Nach Tutorial versteht Spieler: Klicks, Shapes, Currency, Upgrades

### US5: Currency-Stand sehen

- **Gegeben:** Spieler ist im Spiel
- **Wenn:** Spieler blickt auf das HUD
- **Dann:**
    - Aktueller Currency-Stand ist prominent angezeigt
    - Produktionsrate pro Sekunde ist sichtbar
    - Wert aktualisiert sich Echtzeit
    - Format ist lesbar (z.B. 1.2M statt 1200000)

### US6: Upgrades einsehen

- **Gegeben:** Spieler ist im Spiel
- **Wenn:** Spieler öffnet "Upgrades"-Panel
- **Dann:**
    - Alle verfügbaren Upgrades werden listet
    - Status (gekauft/verfügbar/gesperrt) ist erkennbar
    - Kosten und Nutzen werden angezeigt
    - Aktuelle Level von Upgrades sind sichtbar
    - Beschreibungen sind verständlich

---

## SCRUM Sprint Plan

### Sprint Goal

"Implementiert die Core-Gameplay-Mechaniken eines funktionierenden Incremental Games mit Shape-Upgrades, Currency-System
und Prestige-Funktion"

### Sprint Duration

**1 Woche (5 Arbeitstage)**  
Start: 27/05/2026
End: 03/06/2026

### User Stories & Story Points

| # | User Story                  | Story Points | Assignee           | Status |
|---|-----------------------------|--------------|--------------------|--------|
| 1 | Currency-Stand sehen        | 5            | Frontend           | TODO   |
| 2 | Formen upgraden             | 8            | Backend + Frontend | TODO   |
| 3 | Upgrades einsehen           | 5            | Frontend           | TODO   |
| 4 | Strategische Entscheidungen | 8            | Backend (Balance)  | TODO   |
| 5 | Tutorial (TEMP)             | 5            | Frontend           | TODO   |
| 6 | Prestiges durchführen       | 8            | Backend + Frontend | TODO   |

**Total Story Points: 39**

### Sprint Backlog - Technische Tasks

#### Frontend Tasks (Supervisor: Finn)

- [x] HUD-Layout mit Currency-Anzeige erstellen (Finn) - 3 SP - **[Lead-Aufgabe]**
- [ ] Shape-Upgrade Button UI implementieren (Lea, Fenia unter Finns Anleitung) - 3 SP - **[Lightwork]**
- [ ] Upgrades-Panel designen und programmieren (Finn + Fenia Assistance) - 3 SP - **[Finn Lead]**
- [ ] Tutorial-Flow & State Management (Finn) - 3 SP - **[Lead-Aufgabe]**
- [ ] Real-time Currency-Update - Backend-Integration (Finn + Lea Assistance) - 3 SP - **[Finn Lead]**
- [ ] Tutorial-Texte, Bilder & Content (Lea, Fenia) - 2 SP - **[Lightwork]**
- [ ] Button Styling & UI Polish (Lea) - 2 SP - **[Lightwork]**

#### Backend Tasks (Supervisor: Marvin)

- [x] Currency-Production-System implementieren (Marvin) - 5 SP - **[Lead-Aufgabe]**
- [x] Upgrade-Kosten-Berechnung mit Tests (Marvin + Gabriela Codereview) - 3 SP - **[Marvin Lead]**
- [x] Shape-Datenmodell & Entity erstellen (Gabriela unter Marvins Guidance) - 2 SP - **[Lightwork]**
- [x] Shape-Vertices-Logik (Laura unter Marvins Guidance) - 2 SP - **[Lightwork]**
- [x] Prestige-System & Score-Speicherung (Marvin) - 5 SP - **[Lead-Aufgabe]**
- [x] Balance-Parameter & Tweaking (Marvin) - 3 SP - **[Lead-Aufgabe]**
- [x] Unit-Tests Template & Struktur (Marvin + Laura) - 2 SP - **[Laura: einfache Tests]**
- [x] Upgrade-State Manager (Gabriela unter Marvins Guidance) - 2 SP - **[Lightwork]**

### Definition of Done

- [ ] Code-Review durchgeführt
- [ ] Unit-Tests geschrieben & grün
- [ ] Integration-Tests bestanden
- [ ] UI/UX auf Responsive-Design getestet
- [ ] Code in `main`-Branch gemergt
- [ ] Keine kritischen Bugs

### Risiken & Mitigationen

| Risiko                                 | Wahrscheinlichkeit | Impact | Mitigation                                          |
|----------------------------------------|--------------------|--------|-----------------------------------------------------|
| Backend-Performance bei vielen Shapes  | Mittel             | Hoch   | Early Load-Testing, Caching                         |
| UI-Verzögerungen bei Real-time Updates | Niedrig            | Mittel | Optimization Sprint geplant                         |
| Balance-Issues                         | Hoch               | Mittel | Weekly Balance-Review, Daten-getriebene Anpassungen |

### Daily Standup Format (15 min)

- Was habe ich gestern getan?
- Was werde ich heute tun?
- Welche Hindernisse gibt es?
- Aktualisierung des Burndown-Charts
