# M426-Abschluss Projekt

**Team:** Finn, Marvin, Lea, Gabriela, Fenia, Laura

**Titel:** Incremental Shape Game

---

## Team-Struktur

```
├── Frontend Supervisor: Finn
│   └── Fenia, Lea
└── Backend Supervisor: Marvin
    └── Gabriela, Laura
```

---

## Projektidee

- Incremental (Clicker-)Spiel
- Anfängerfreundlich
- Formen produzieren Währung
- Formen upgraden für mehr Vertices = mehr Währung
- Startet in 2 Dimensionen
- Prestige-System für weitere Dimensionen / Reset
- Java
- Automatisierte Backend-Tests

---

## Kernmechaniken

### Formen-System
- Man hat eine Form, die Währung produziert
- Die Form startet als einzelner Punkt
- Es gibt Upgrades, die die Produktion verbessern

### Prestige-Mechaniken (Vertices)
- Setze Upgrades und Währung zurück, um Prestige-Währung zu erhalten
- Gib Prestige-Währung für eine zweite Upgrade-Ebene aus, die zwischen Resets erhalten bleibt
- Es gibt ein unendlich kaufbares Upgrade, das die Vertex-Anzahl erhöht; Vertex-Anzahl wirkt als sofortiger Multiplikator auf die Produktion
- Prestige-Währung basiert auf der Währung zum Zeitpunkt des Resets

### Spiel-Loop
- Währung verdienen
- Form upgraden, sobald genug vorhanden
- Vertices steigen und Produktion schießt in die Höhe
- Irgendwann Prestige für einen Neustart mit Boost
- Endlos wiederholen mit steigenden Boni

### Persistenz
- Spiel speichert in lokaler JSON-Datei
- Automatische Speicherung bei jeder Zustandsänderung
- Laden beim Start - nahtloser Fortschritt

---

## User Stories

Format: Als [Rolle] möchte ich [Funktion], weil [Grund].

- Als Player möchte ich Prestiges durchführen können, weil ich meinen Score erhöhen möchte.
- Als Player möchte ich strategische Entscheidungen fällen müssen, weil es interessanter ist.
- Als Player möchte ich Formen upgraden können, weil sie dann mehr Vertices haben und mehr Currency produzieren.
- Als Neueinsteiger möchte ich ein Tutorial haben, weil ich die Spielmechaniken verstehen möchte. (TEMP)
- Als Player möchte ich meinen aktuellen Currency-Stand sehen, weil ich meinen Fortschritt verfolgen möchte.
- Als Player möchte ich meine Upgrades sehen, um meinen Stand zu sehen.

---

## Akzeptanzkriterien

### US1: Prestiges durchführen

- **Gegeben:** Spieler hat ein bestimmtes Level erreicht
- **Wenn:** Spieler klickt auf "Prestige"-Button
- **Dann:**
  - Score wird zur Persisting-Statistik hinzugefügt
  - Alle aktuellen Ressourcen/Formen werden zurückgesetzt
  - Spieler erhält Prestige-Bonus (z.B. +5% Basis-Currency-Produktion)
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
  - Interaktive Guides führen durch erste Schritte
  - Tutorial kann übersprungen werden
  - Nach Tutorial versteht Spieler: Klicks, Shapes, Currency, Upgrades

### US5: Currency-Stand sehen

- **Gegeben:** Spieler ist im Spiel
- **Wenn:** Spieler blickt auf das HUD
- **Dann:**
  - Aktueller Currency-Stand ist prominent angezeigt
  - Produktionsrate pro Sekunde ist sichtbar
  - Wert aktualisiert sich in Echtzeit
  - Format ist lesbar (z.B. 1.2M statt 1200000)

### US6: Upgrades einsehen

- **Gegeben:** Spieler ist im Spiel
- **Wenn:** Spieler öffnet "Upgrades"-Panel
- **Dann:**
  - Alle verfügbaren Upgrades werden gelistet
  - Status (gekauft/verfügbar/gesperrt) ist erkennbar
  - Kosten und Nutzen werden angezeigt
  - Aktuelle Level von Upgrades sind sichtbar
  - Beschreibungen sind verständlich
