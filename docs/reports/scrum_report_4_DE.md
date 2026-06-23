# Sprint #4 Report

**Sprint:** #4

**Status:** Abgeschlossen

**Datum:** 23.06.2026

---

## Zusammenfassung

| Bereich | Issues geschlossen | Issues offen | Completion Rate |
|---------|:---:|:---:|:---:|
| Frontend | 4 / 9 | 5 | 44% |
| Backend | 1 / 1 | 0 | 100% |
| **Total** | **5 / 10** | **5** | **50%** |

---

## Frontend

**Status: Echter Fortschritt, weiterhin hinter der Deadline**

Dies war der erste Sprint, in dem sich das Frontend wirklich bewegt hat. Nach der
Sprint-#3-Entscheidung, alle Ressourcen ausser Marvin ins Frontend zu verlagern, hat das
Team vier Issues geschlossen: Prestige-Tree Schritt 2 (#45), die Prestige-Upgrade-UI (#14),
UI State Management für das Tutorial (#5) und das Tutorial-Writing (#10). Das hebt die
Frontend-Completion in einem einzigen Sprint von 14% auf 44%.

Das Problem ist, dass die Fortschritte noch nicht dort sichtbar sind, wo sie sein müssten.
Von den vier Beitragenden hat nur Gabrielas Tutorial-Arbeit (PR #57) tatsächlich `main`
erreicht. Finns Tutorial-State-Code liegt auf dem Branch `upgrade-tree` und Lauras
Prestige-Arbeit auf `feature/prestige_UI` (10 voraus, 4 hinter `main`), beide noch
un-gemergt. Mit Styling und UI Polish (#15), dem Upgrades-Panel (#13), dem
Rendering/Layout-Refactor (#38) und dem vollständigen Frontend-Backend-Integration-Smoke-Test
(#54) allesamt noch offen, und einer harten Deadline am 30.06.2026 21:00, bleibt der
Bereich trotz des Fortschritts gefährdet.

### Individuelle Beiträge

| Teammitglied | Erfahrungslevel | Beitrag |
|---|---|---|
| Finn | Erfahren | Supervisor; lieferte das Tutorial-UI-State-Management (#5) |
| Laura | Junior | Schloss Prestige-Tree Schritt 2 (#45) und die Prestige-Upgrade-UI (#14) |
| Gabriela | Junior | Lieferte das Tutorial-Writing (#10), nach `main` gemergt |
| Fenia | Junior | Keine Arbeit geleistet |
| Lea | Junior | Keine Arbeit geleistet |

### Notizen
- **Laura:** stärkster individueller Sprint. Sie hat den Prestige-Button klickbar gemacht, den
  `PrestigeUpgradeRenderer` hinzugefügt und den Tutorial-Content mitgeliefert. Die Arbeit ist
  solide, liegt aber noch auf `feature/prestige_UI`, der jetzt 10 voraus und 4 hinter `main`
  ist. Wie in Sprint #3 angesprochen, muss dieser Branch mit `main` synchronisiert, gemergt und
  gelöscht werden, bevor die Deadline kommt, damit die Arbeit auch wirklich im Spiel ist.
  Prestige-Tree Schritt 3 (#55) bleibt offen.
- **Finn:** lieferte echten Tutorial-Flow-Code im `NusianController` (Issue #5), verlor aber
  spürbar Zeit im Kampf mit Git-Rebases (die Commit-Messages "i hope i can rebase now" und
  "im never rebasing anything again" sprechen für sich). Sein Output liegt auf `upgrade-tree`
  und ist noch nicht gemergt. Er verantwortet weiterhin drei offene Punkte: das Upgrades-Panel
  (#13), den Rendering-Manager-Refactor (#38) und das gesamte Styling (#15).
- **Gabriela:** lieferte das Tutorial-Writing via PR #57, die einzige Frontend-Arbeit, die
  diesen Sprint `main` erreicht hat. Sauber von Anfang bis Ende (Commit, PR, Merge).

### Action Items
- [ ] `feature/prestige_UI` und `upgrade-tree` sofort nach `main` mergen, dann die Branches
      löschen; nichts Un-gemergtes sollte in den finalen Sprint übergehen
- [ ] Upgrades-Panel (#13), Styling/Polish (#15) und Rendering-Refactor (#38) abschliessen
- [ ] Den vollständigen Frontend-Backend-Integration-Smoke-Test (#54) ausführen, sobald die Branches gemergt sind

### Notizen
- **Fenia:** keine Arbeit, vierter Sprint in Folge mit null Output.
- **Lea:** keine Arbeit, zweiter Sprint bei null.

---

## Backend

**Status: Abgeschlossen**

Das Backend ist faktisch fertig. Marvin hat den letzten Carryover-Punkt geschlossen, das
Balance-Parameter-Tuning, mit Daten-getriebenen Anpassungen und einem Progression-Playtest
(#31, PR #56 gemergt). Der einzige verbleibende backend-nahe Task ist der
Integration-Smoke-Test (#54), der bewusst blockiert ist, bis die Frontend-Branches gemergt
sind und das Spiel End-to-End spielbar ist.

### Individuelle Beiträge

| Teammitglied | Erfahrungslevel | Beitrag |
|---|---|---|
| Marvin | Erfahren | Supervisor; schloss Balance-Tuning + Daten-getriebenen Playtest (#31) |

### Action Items
- [ ] #54 (Integration-Smoke-Test) ausführen, sobald die Frontend-Branches gelandet sind

---

## Retrospective Highlights

### Was gut lief
- Frontend-Completion sprang von 14% auf 44%; die "All-Hands-ins-Frontend"-Entscheidung hat funktioniert
- Laura, Finn und Gabriela haben diesen Sprint alle echten Frontend-Code geliefert
- Backend erreichte 100%; der letzte Balance-Punkt wurde geliefert und gemergt
- Gabrielas Tutorial-Arbeit lief sauber durch PR und Merge nach `main`

### Was nicht gut lief
- Der grösste Teil der Frontend-Arbeit steckt auf un-gemergten Branches fest, der Fortschritt
  ist also noch nicht im Spiel; das wiederholt das Sichtbarkeitsproblem aus Sprint #3
- Fenia hat den vierten Sprint in Folge keinen Output geliefert
- Lea hat erneut nichts geliefert
- Styling, das Upgrades-Panel, der Rendering-Refactor und die Integration sind alle noch
  offen, mit einer Woche bis zur finalen Deadline

### Fokusbereiche für Sprint #5 (Finaler Sprint)
Das Projekt muss bis zum **30.06.2026 21:00** fertiggestellt und eingereicht sein. Sprint #5
ist der letzte Sprint, es ist also ein Stabilisierungs- und Integrations-Push, kein neuer Scope.

1. **Alles mergen** - `feature/prestige_UI` und `upgrade-tree` an Tag 1 auf `main` bringen
2. **Integrieren und Smoke-Test** - #54 gegen einen vollständig gemergten Build ausführen und beheben, was bricht
3. **Verbleibendes Frontend fertigstellen** - Upgrades-Panel (#13), Styling/Polish (#15), Prestige-Tree
   Schritt 3 (#55)
4. **Früh einfrieren** - bis 29.06 Feature-complete sein, um einen vollen Tag für den finalen
   Playtest und die Einreichungs-Vorbereitung zu lassen

---

*Erstellt für Sprint Review | Sprint #4*
