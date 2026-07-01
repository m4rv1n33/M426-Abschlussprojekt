# Release Plan

**Product:** Nusian - Incremental Shape Game
**Team:** Finn, Marvin, Lea, Gabriela, Fenia, Laura ([project_descr.md](project_descr.md))
**Release goal:** ship a complete, playable, end-to-end incremental game (produce currency -> upgrade shapes -> prestige) by the hard submission deadline.
**Hard deadline:** 30/06/2026, 21:00
**Cadence:** 5 sprints, 1 week each, 27/05/2026 - 30/06/2026

This document is the roadmap that ties the five individual sprint plans (`docs/sprints/sprint_1.md` - `sprint_5.md`) and the four sprint reports (`docs/reports/scrum_report_1.md` - `scrum_report_4.md`) together into one release-level view. It reflects both what was planned at the start of the project and the actual outcome of each sprint, since all five sprints have now run.

---

## Release scope (MVP)

Fixed at project kickoff, unchanged through the release (see `project_descr.md` for full acceptance criteria):

- **US1** Perform prestige (reset for a permanent bonus)
- **US2** Strategic decisions (multiple upgrade paths)
- **US3** Upgrade shapes (vertices increase, cost scales, production updates live)
- **US4** Tutorial (TEMP - explicitly marked lowest priority if scope needs to be cut)
- **US5** View currency (HUD, formatted, real-time)
- **US6** View upgrades (list, status, cost/benefit)

No scope was added after kickoff. Every sprint from #2 onward was a recovery/carryover sprint against this same backlog rather than new feature work - see "What changed" below.

## Release milestones

| Milestone | Target date | Actual outcome |
|---|---|---|
| Core backend systems playable via tests (production, upgrades, prestige, persistence) | End of Sprint #1 (03/06) | Met - backend 100% closed in Sprint #1 |
| Playable UI with tutorial and prestige flow | End of Sprint #2 (10/06) | Missed - frontend at 20%, all 6 UI tasks carried over |
| Fully playable end-to-end game | End of Sprint #3 (17/06) | Missed - frontend at 14%, same 6 tasks still open |
| Feature-complete, frontend/backend integrated | End of Sprint #4 (24/06) | Partially met - frontend at 44%, but key work (prestige UI, upgrade tree/tutorial state) stranded on unmerged branches |
| Merged, stabilised, submitted build | End of Sprint #5 / 30/06 21:00 | Met - outstanding branches merged day 1, integration smoke test run and fixed, submitted on time |

## Sprint-by-sprint summary

| Sprint | Dates | Goal | Planned SP | Frontend completion | Backend completion | Key result |
|---|---|---|---|---|---|---|
| 1 | 27/05 - 03/06 | Core gameplay mechanics (production, upgrades, prestige) | 39 | 14% (1/7) | 100% | Backend fully delivered; frontend severely underdelivered - triggered a junior swap for Sprint #2 |
| 2 | 03/06 - 10/06 | Catch up frontend, integrate full UI/tutorial/prestige flow | 39 | 20% (2/10) | ~progressing (1 issue open) | Frontend recovery attempt stalled again; Git workshop run for juniors |
| 3 | 11/06 - 17/06 | Ship a fully playable end-to-end game, close all carryover | 27 | 14% (1/7) | on track | Same 6 frontend tasks carried over a second time; balance tuning progressed on backend |
| 4 | 18/06 - 24/06 | All-hands frontend push + integration | 19 | 44% (4/9) | 100% | Real frontend progress, but 2 of 4 delivered items stuck on unmerged branches (`feature/prestige_UI`, `upgrade-tree`); backend fully closed (balance tuning, #31) |
| 5 (final) | 25/06 - 30/06 | Merge everything, integrate, stabilise, submit - no new scope | 13 (+3 stretch) | target: all carryover closed | target: integration smoke test green | Branches merged day 1; upgrade panel, prestige tree step 3, styling closed; integration smoke test (#54) run and defects fixed; submitted before deadline |

Story points planned per sprint fell steadily (39 -> 39 -> 27 -> 19 -> 13) as the plan shifted from "add scope" to "recover and finish the same backlog" - the total burned down against a fixed MVP rather than a growing one.

## What changed since the original plan

The release plan itself (the MVP and the six user stories) never changed. What changed sprint to sprint was **team allocation**, in direct response to frontend underdelivery flagged in each retrospective:

1. **Sprint #1 -> #2:** juniors swapped between supervisors (Gabriela/Laura to frontend, Fenia/Lea to backend) after Sprint #1's 14% frontend completion.
2. **Sprint #2 -> #3:** no team change; carryover backlog repeated for a second sprint, plus a Git basics workshop to unblock juniors.
3. **Sprint #3 -> #4:** drastic measure - the entire team except Marvin moved to frontend ("all-hands"), with Marvin staying on backend/integration/lead.
4. **Sprint #4 -> #5:** all-hands frontend setup continued; Sprint #5 was explicitly scoped as merge-and-stabilise only, with a hard rule of no new scope, because two sprints' worth of frontend work was sitting unmerged on `feature/prestige_UI` and `upgrade-tree`.

See `docs/reports/contribution-report.md` for the underlying individual-contribution data behind these allocation decisions.

## Velocity and risk pattern

- Frontend completion by sprint: 14% -> 20% -> 14% -> 44% -> (target 100% at close).
- Backend was at or near 100% every sprint from #1 onward and was never the bottleneck.
- The same risk (frontend velocity) was carried in the risk register of every sprint plan and every retrospective from Sprint #1 through Sprint #4, and is the reason Sprint #5 exists purely as an integration/stabilisation sprint rather than a feature sprint.
- The recurring secondary risk - completed work stranded on unmerged feature branches (Sprint #3 and #4) - was addressed structurally in Sprint #5 by making "merge everything" the explicit day-1 priority before any other task could start.

## Release Definition of Done

Carried from `docs/sprints/sprint_5.md`, this is the release-level (not sprint-level) exit criteria:

- [x] All feature branches merged into `main` and deleted (locally and remotely)
- [x] `main` builds cleanly
- [x] Unit and integration tests written and passing
- [x] Full frontend-backend integration smoke test passed
- [x] The game is playable end-to-end: produce currency -> buy upgrades -> prestige
- [x] No critical bugs open
- [x] Project submitted before 30/06/2026 21:00

## References

- Sprint plans: `docs/sprints/sprint_1.md` … `sprint_5.md` (German versions: `*_DE.md`)
- Sprint reports: `docs/reports/scrum_report_1.md` … `scrum_report_4.md`
- Project description and acceptance criteria: `docs/project_descr.md`
- Component documentation: `docs/internals/`
