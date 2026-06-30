# Sprint 5 (Final Sprint)

## Sprint Goal

"Deliver and submit a complete, integrated, playable game by the hard deadline. Merge all outstanding branches, finish the remaining frontend, stabilise, and freeze early. No new scope."

## Sprint Duration

**1 week (final sprint)**
Start: 25/06/2026
End: 30/06/2026
**Hard submission deadline: 29/06/2026, 21:00**

## Team Setup (Final Integration Push)

The all-hands frontend setup from Sprint #4 continues. This is the last sprint, so it is stabilisation and integration, not new features. Critical-path work is assigned to the members who have delivered consistently; Marvin owns integration and remains overall lead:

```
├── Frontend (all hands, Supervisor: Finn)
│   └── Gabriela, Laura, Fenia, Lea
└── Backend / Integration / Lead: Marvin
```

Backend is feature-complete, so Marvin's focus this sprint is integration, the smoke test, and shepherding the merge and submission.

---

## Sprint Backlog - Technical Tasks

### Day 1 Priority - Merge Everything

Nothing can be finished or tested until the outstanding branches are on `main`. This must happen on day 1.

- [ ] Sync and merge Laura's `feature/prestige_UI` branch into `main`, then delete it (10 ahead, 4 behind as of Sprint #4 close) - **[Lead task]**
- [ ] Sync and merge Finn's `upgrade-tree` branch (tutorial UI state, #5) into `main`, then delete it - **[Lead task]**
- [ ] Confirm a clean, buildable `main` after both merges before any further work starts - **[Lead task]**

### Frontend Tasks (Supervisor: Finn)

**Team:** Finn, Gabriela, Laura, Fenia, Lea

- [ ] Design and implement the upgrades panel - #13 - 3 SP - **[Finn Lead]**
- [ ] Prestige tree, step 3 - #55 - 2 SP - **[Lightwork]**
- [ ] Overall styling & UI polish - #15 - 2 SP - **[Lightwork]**
- [ ] Manual playtesting and bug reporting against the merged build - 2 SP - **[Minor]**

### Backend / Integration Tasks (Supervisor: Marvin)

**Team:** Marvin

- [ ] Full frontend-backend integration smoke test against the fully merged build - #54 - 2 SP - **[Lead task]**
- [ ] Fix integration defects surfaced by the smoke test - 2 SP - **[Lead task]**

---

**Total Story Points (excl. stretch): 13**
**Stretch: +3 (#38)**

---

## Final-Week Timeline

- **25/06 (Day 1):** merge both branches, clean `main`, confirm buildable game.
- **26/06 - 28/06:** finish upgrade panel (#13), prestige step 3 (#55), styling (#15); run integration smoke test (#54) and fix defects.
- **29/06 (Freeze):** feature freeze, full end-to-end playtest, fix only critical bugs.
- **30/06:** final playtest, submission prep, **submit before 21:00.**

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
- [ ] Project is submitted before 30/06/2026 21:00

---

## Notes from Sprint #4 Retrospective

- **Merge first** - most of Sprint #4's progress is stranded on un-merged branches (`feature/prestige_UI`, `upgrade-tree`). Getting it onto `main` on day 1 is the single most important action of the sprint.
- **Freeze early** - aim to be feature-complete by 29/06 so there is a full day for the final playtest and submission, not a last-minute scramble.
- **No new scope** - this is the final sprint; only finish, integrate, and stabilise what already exists.
- **Git discipline** - push to the remote at end of each working day; keep branches in sync with `main` and delete them after merge.
- **Rebase support** - Finn lost time to rebase struggles last sprint; pair on the merges rather than rebasing solo.

---
