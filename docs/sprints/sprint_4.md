# Sprint 4

## Sprint Goal

"Ship a complete, playable end-to-end game by concentrating the entire team on the frontend carryover backlog and integrating it with the backend"

## Sprint Duration

**1 week (5 working days)**
Start: 18/06/2026
End: 24/06/2026

## Team Setup (All-Hands Frontend Push)

Following the Sprint #3 retrospective, and as a drastic measure to clear the frontend backlog, all team members except Marvin move to the frontend. Marvin stays on backend, handles integration, and acts as overall lead:

```
├── Frontend (all hands, Supervisor: Finn)
│   └── Gabriela, Laura, Fenia, Lea
└── Backend / Integration / Lead: Marvin
```

---

## Sprint Backlog - Technical Tasks

### Frontend Tasks (Supervisor: Finn)

**Team:** Finn, Gabriela, Laura, Fenia, Lea

**All feature tasks below are carryover from Sprint #3.**

- [ ] Merge & clean up outstanding work: Review & Merge Laura's `feature/prestige_UI` branch, then delete the merged branches - first day priority - **[Lead task]**
- [ ] Design and program upgrades panel (Finn lead + Gabriela) - 3 SP - **[Finn Lead]**
- [ ] Implement prestige upgrade UI (Laura under Finn's guidance) - 2 SP - **[Lightwork]**
- [ ] Tutorial flow & state management (Fenia + Lea) - 3 SP - **[Minor]**
- [ ] Tutorial texts, images & content (Lea + Fenia) - 2 SP - **[Lightwork]**
- [ ] Button styling & UI polish (Laura) - 2 SP - **[Lightwork]**

### Backend Tasks (Supervisor: Marvin)

**Team:** Marvin

- [ ] Full frontend-backend integration smoke test (Marvin) - 2 SP - **[Lead task]**
- [ ] Refine balance parameters & data-driven adjustments, now that the playable frontend gives a feel for the game (Marvin) - 3 SP - **[Lead task]**

---

**Total Story Points: 19**

---

## Definition of Done

- [ ] Code review conducted
- [ ] Unit tests written & passing
- [ ] Integration tests passed
- [ ] Pull request created, Marvin or Finn assigned as reviewer
- [ ] Merged feature branches deleted, locally and remotely
- [ ] No critical bugs
- [ ] Frontend and backend are fully connected and manually tested
- [ ] The game is playable end-to-end (produce currency -> buy upgrades -> prestige)

---

## Notes from Sprint #3 Retrospective

- **Frontend recovery (drastic measure)** - the whole team except Marvin is on frontend this sprint to clear the six carryover tasks and ship a playable game.
- **Review turnaround** - Gabriela's PR sat unreviewed for several days last sprint; supervisors review within 24-48h this sprint.
- **Git discipline** - push to the remote at end of each working day; keep feature branches in sync with `main` and delete them after merge; follow the commit naming conventions.
- **Timing** - begin work on day 1, not late in the sprint.

---
