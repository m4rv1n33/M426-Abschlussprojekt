# Sprint 1

## Sprint Goal

"Implement the core gameplay mechanics of a functional incremental game with shape upgrades, currency system, and prestige feature"

## Sprint Duration

**1 week (5 working days)**
Start: 27/05/2026
End: 03/06/2026

## User Stories & Story Points

| # | User Story                  | Story Points | Assignee           | Status |
|---|-----------------------------|--------------|--------------------|--------|
| 1 | View currency               | 5            | Frontend           | TODO   |
| 2 | Upgrade shapes              | 8            | Backend + Frontend | TODO   |
| 3 | View upgrades               | 5            | Frontend           | TODO   |
| 4 | Strategic decisions         | 8            | Backend (Balance)  | TODO   |
| 5 | Tutorial (TEMP)             | 5            | Frontend           | TODO   |
| 6 | Perform prestige            | 8            | Backend + Frontend | TODO   |

**Total Story Points: 39**

## Sprint Backlog - Technical Tasks

### Frontend Tasks (Supervisor: Finn)

- [x] Create HUD layout with currency display (Finn) - 3 SP - **[Lead task]**
- [ ] Implement shape upgrade button UI (Lea, Fenia under Finn's guidance) - 3 SP - **[Lightwork]**
- [ ] Design and program upgrades panel (Finn + Fenia Assistance) - 3 SP - **[Finn Lead]**
- [ ] Tutorial flow & state management (Finn) - 3 SP - **[Lead task]**
- [ ] Real-time currency update - backend integration (Finn + Lea Assistance) - 3 SP - **[Finn Lead]**
- [ ] Tutorial texts, images & content (Lea, Fenia) - 2 SP - **[Lightwork]**
- [ ] Button styling & UI polish (Lea) - 2 SP - **[Lightwork]**

### Backend Tasks (Supervisor: Marvin)

- [x] Implement currency production system (Marvin) - 5 SP - **[Lead task]**
- [x] Upgrade cost calculation with tests (Marvin + Gabriela code review) - 3 SP - **[Marvin Lead]**
- [x] Create shape data model & entity (Gabriela under Marvin's guidance) - 2 SP - **[Lightwork]**
- [x] Shape vertices logic (Laura under Marvin's guidance) - 2 SP - **[Lightwork]**
- [x] Prestige system & score persistence (Marvin) - 5 SP - **[Lead task]**
- [x] Balance parameters & tweaking (Marvin) - 3 SP - **[Lead task]**
- [x] Unit test template & structure (Marvin + Laura) - 2 SP - **[Laura: simple tests]**
- [x] Upgrade state manager (Gabriela under Marvin's guidance) - 2 SP - **[Lightwork]**

## Definition of Done

- [ ] Code review conducted
- [ ] Unit tests written & passing
- [ ] Integration tests passed
- [ ] UI/UX tested for responsive design
- [ ] Code merged into `main` branch
- [ ] No critical bugs

## Risks & Mitigations

| Risk                                     | Probability | Impact | Mitigation                                          |
|------------------------------------------|-------------|--------|-----------------------------------------------------|
| Backend performance with many shapes     | Medium      | High   | Early load testing, caching                         |
| UI lag with real-time updates            | Low         | Medium | Optimization sprint planned                         |
| Balance issues                           | High        | Medium | Weekly balance review, data-driven adjustments      |

