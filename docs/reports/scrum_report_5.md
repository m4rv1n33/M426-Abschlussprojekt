# Sprint #5 Report (Final Sprint)

**Sprint:** #5

**Status:** Completed

**Date:** 01.07.2026

---

## Summary

| Area | Issues Closed | Issues Open | Completion Rate |
|------|:---:|:---:|:---:|
| Frontend | 4 / 4 (incl. 1 stretch) | 0 | 100% |
| Backend | 1 / 1 | 0 | 100% |
| **Total** | **5 / 5** | **0** | **100%** |

Verified against the git history for the sprint window (25.06 - 01.07): PR #53 (`feature/prestige_UI`), #59 (`upgrade-tree`), #60 (`rendering/refactor`), and #61 (`test/integration-smoke`) all merged into `main`, and both outstanding branches from Sprint #4 were deleted after merge.

---

## Frontend

**Status: Complete - Day-1 Merge Rule Held, Stretch Goal Delivered**

The Sprint #4 retrospective's single most important instruction - merge everything before doing anything else - was followed. `feature/prestige_UI` (Laura's prestige upgrade UI, PR #53) and `upgrade-tree` (PR #59) both landed on `main` early in the sprint, with `main` confirmed buildable before further work started. On top of the planned backlog, the rendering/layout refactor (#38, PR #60) - listed only as a stretch goal in the Sprint #5 plan - was also completed, along with a round of bug fixes and polish (tutorial no longer replaying on every launch, placeholder upgrades removed, mouse-event/GameState wiring fixes, button styling).

The standout development this sprint is Fenia: after four consecutive sprints of zero commits, she authored `ShapeRenderer` and `UpgradeRenderer` (with `FontHelper`) directly on the `upgrade-tree` branch, pairing with Finn who built on top of her work (`PaintHelper`, mouse-event fixes, final cleanup) before merging. This is the first individual code contribution from her across the whole project.

### Individual Contributions

| Team Member | Experience Level | Contribution |
|---|---|---|
| Finn | Experienced | Supervisor; by far the highest output this sprint - merged both outstanding branches, delivered the rendering refactor (#38, stretch), styling/polish (#15), tutorial-replay fix, and a post-freeze prestige-formula correction |
| Laura | Junior | Delivered the prestige upgrade buttons and dependency-based row layout (#55, prestige tree step 3) |
| Fenia | Junior | First individual code contribution of the project: authored `ShapeRenderer`, `UpgradeRenderer`, and `FontHelper`, paired with Finn on the rendering work |
| Gabriela | Junior | No additional commits recorded this sprint; her Sprint #4 tutorial-writing delivery (PR #57) already stood merged on `main` |
| Lea | Junior | No work done |

### Notes
- **Finn:** carried the bulk of the frontend this sprint (18 commits in the sprint window) - both merges, the stretch-goal rendering refactor, and the final polish pass. Also authored the last commit of the project (01.07, one day after the formal deadline): a small fix so the prestige payout uses the player's peak currency for the run instead of their spendable balance, so the auto-buyer no longer eats into prestige points.
- **Laura:** finished what she started in Sprint #4 - the prestige upgrade UI is fully merged and working, including the dependency-ordered row layout for prestige upgrades.
- **Fenia:** real, verifiable turnaround. Four sprints of zero commits (Sprints #1, #3, #4) and one sprint of no individual technical delivery even when co-assigned (Sprint #2) ended this sprint with two commits creating actual rendering classes that shipped to `main`. Credit due specifically for finally engaging - see `contribution-report.md` for the full historical record this breaks from.
- **Lea:** no commits in the sprint window. This is her third sprint at zero output out of five (Sprints #1, #3, #4), with only Sprint #2 showing minor contribution. Unlike Fenia, there was no late-sprint turnaround.

### Action Items
- [x] Merge `feature/prestige_UI` and `upgrade-tree` into `main`, delete both branches - done day 1
- [x] Close the upgrade panel (#13), prestige tree step 3 (#55), styling/polish (#15)
- [x] Rendering refactor (#38) - stretch goal, delivered anyway

---

## Backend / Integration

**Status: Complete**

Marvin ran the full frontend-backend integration smoke test (#54) against the merged build once both branches were in, and fixed every defect it surfaced: an FXML namespace mismatch against the JavaFX 21 runtime, a shape-focus upgrade gating bug, and stale balance/prestige tests that no longer matched the shipped formula. Documentation was synced to match the final code (`docs/internals/*`) before submission.

### Individual Contributions

| Team Member | Experience Level | Contribution |
|---|---|---|
| Marvin | Experienced | Supervisor; ran and fixed the integration smoke test (#54), fixed FXML/runtime and shape-focus gating bugs, added dev launch flags (`--reset`/`--max`) for demo purposes, synced internals docs with shipped code |

### Action Items
- [x] Run the full integration smoke test (#54) against the merged build
- [x] Fix defects surfaced by the smoke test

---

## Retrospective Highlights

### What Went Well
- The day-1 merge rule from the Sprint #4 retrospective was actually followed this time - both stranded branches landed on `main` before any other work started, closing the "progress hidden on unmerged branches" problem that repeated in Sprints #3 and #4.
- A stretch goal (#38, rendering refactor) was delivered on top of the required backlog, not instead of it.
- Fenia contributed real, merged code for the first time in the project, via direct pairing with Finn.
- The integration smoke test did its job: it surfaced concrete defects (FXML namespace, shape-focus gating) that were fixed before submission, not discovered by the player.
- The project was feature-complete, integrated, and submitted on schedule.

### What Didn't Go Well
- Lea produced no work in the final sprint, her third zero-output sprint of five. Combined with Sprints #1, #3, and #4, this is a consistent pattern across the project, not a one-off.
- One small fix (the prestige-formula correction) landed after the formal 30.06.2026 21:00 deadline. It's a minor, low-risk change, but it's worth flagging for process discipline in any future project: freeze the code, not just the scope, at the deadline.

### Final Notes
This was the last sprint of the project - there is no Sprint #6 to carry action items into. The recurring frontend-velocity and branch-visibility risks that were tracked from Sprint #1 through Sprint #4 were both resolved by the structural changes made in Sprints #4-5 (all-hands frontend, day-1 merge rule). The one unresolved thread across the whole release is Lea's contribution level, which stayed flat regardless of the same interventions (task reassignment, smaller scoped tasks, pairing) that worked for Fenia this sprint.

---

*Prepared for Sprint Review | Sprint #5 (Final)*
