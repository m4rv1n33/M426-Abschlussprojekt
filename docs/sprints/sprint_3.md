# Sprint 3

## Sprint Goal

"Deliver a fully playable end-to-end game: complete all pending frontend UI, connect it to the backend, and close out all remaining open issues"

## Sprint Duration

**1 week (5 working days)**
Start: 11/06/2026
End: 17/06/2026

## Team Setup

No changes from Sprint #2:

```
├── Frontend Supervisor: Finn
│   └── Gabriela, Laura
└── Backend Supervisor: Marvin
    └── Fenia, Lea
```

---

## Sprint Backlog - Technical Tasks

### Frontend Tasks (Supervisor: Finn)

**Team:** Finn, Gabriela, Laura

**All tasks below are carryover from Sprint #2.**

- [ ] Implement shape upgrade button UI (Gabriela under Finn's guidance) - 3 SP - **[Lightwork]**
- [ ] Design and program upgrades panel (Finn + Laura assistance) - 3 SP - **[Finn Lead]**
- [ ] Create prestige UI & button (Laura under Finn's guidance) - 2 SP - **[Lightwork]**
- [ ] Tutorial flow & state management (Gabriela, Laura) - 3 SP - **[Minor]**
- [ ] Tutorial texts, images & content (Gabriela, Laura) - 2 SP - **[Lightwork]**
- [ ] Button styling & UI polish (Laura) - 2 SP - **[Lightwork]**
- [ ] Full frontend-backend integration smoke test (Finn + Marvin) - 2 SP - **[Lead task]**

### Backend Tasks (Supervisor: Marvin)

**Team:** Marvin, Fenia, Lea

- [ ] Close 1 remaining open issue from Sprint #2 (Marvin) - first day priority - **[Lead task]**
- [ ] Refine balance parameters & data-driven adjustments (Marvin(yes, we're gonna do ts again)) - 3 SP - **[Lead task]**
- [ ] Git basics workshop (Marvin leads, all juniors participate) - 2 SP - **[Team goal]**
- [ ] Write prestige tree unit tests (Fenia) - 2 SP - **[Lightwork]**
- [ ] Write UpgradeNode edge-case tests (Lea) - 2 SP - **[Lightwork]**

---

### Team-wide Tasks

- [ ] Git basics workshop improvements or hands-on session if needed(Marvin leads, all juniors participate) - **2 SP**

---

**Total Story Points: 27**

---

## Definition of Done

- [ ] Code review conducted
- [ ] Unit tests written & passing
- [ ] Integration tests passed
- [ ] Pull request created, Marvin or Finn assigned as reviewer
- [ ] No critical bugs
- [ ] Frontend and backend are fully connected and manually tested

---

## Risks & Mitigations

| Risk | Probability | Impact | Mitigation |
|------|-------------|--------|------------|
| Frontend carryover scope too large for one sprint | High | High | Finn to triage and cut tutorial scope to MVP if needed |
| Fenia produces no output again | High | Medium | Assign smallest possible concrete task; daily check-in with Marvin |
| Frontend-backend integration reveals bugs | Medium | High | Schedule integration smoke test mid-sprint, not end |
| Git workshop deprioritized again | Medium | Low | Block first 2 hours of sprint day 1 for the session |

---

## Notes from Sprint #2 Retrospective

- **Fenia** - two consecutive sprints of zero output; assigned the smallest meaningful task (prestige tree unit tests, 2 SP) with no ambiguity in scope. 
- **Lea** - minor output in Sprint #2; given sole ownership of UpgradeNode edge-case tests to build accountability.
- **Frontend recovery** - all 6 carryover tasks must be completed this sprint; the game cannot ship without them.
- **Git workshop** - may need a hadns-on lesson if the juniors wish one. 
- **Balance tuning** - now that the full test suite is in place, balance parameters can be adjusted with confidence.

---
