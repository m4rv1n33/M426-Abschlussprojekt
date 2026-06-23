# Sprint #4 Report

**Sprint:** #4

**Status:** Completed

**Date:** 23.06.2026

---

## Summary

| Area | Issues Closed | Issues Open | Completion Rate |
|------|:---:|:---:|:---:|
| Frontend | 4 / 9 | 5 | 44% |
| Backend | 1 / 1 | 0 | 100% |
| **Total** | **5 / 10** | **5** | **50%** |

---

## Frontend

**Status: Real Progress, Still Behind for the Deadline**

This was the first sprint where frontend genuinely moved. Following the Sprint #3
decision to shift all resources except Marvin onto frontend, the team closed four
issues: prestige tree step 2 (#45), the prestige upgrade UI (#14), UI state management
for the tutorial (#5), and the tutorial writing (#10). That lifts frontend completion
from 14% to 44% in a single sprint.

The concern is that the gains are not yet visible where they need to be. Of the four
contributors, only Gabriela's tutorial work (PR #57) actually landed on `main`. Finn's
tutorial state code sits on the `upgrade-tree` branch and Laura's prestige work sits on
`feature/prestige_UI` (10 ahead, 4 behind `main`), both still un-merged. With styling
and UI polish (#15), the upgrade panel (#13), the rendering/layout refactor (#38), and
the full frontend-backend integration smoke test (#54) all still open, and a hard
deadline of 30.06.2026 21:00, the area remains at risk despite the progress.

### Individual Contributions

| Team Member | Experience Level | Contribution |
|---|---|---|
| Finn | Experienced | Supervisor; delivered tutorial UI state management (#5) |
| Laura | Junior | Closed prestige tree step 2 (#45) and prestige upgrade UI (#14) |
| Gabriela | Junior | Delivered the tutorial writing (#10), merged to `main` |
| Fenia | Junior | No work done |
| Lea | Junior | No work done |

### Notes
- **Laura:** strongest individual sprint. She made the prestige button clickable, added
  the `PrestigeUpgradeRenderer`, and co-delivered the tutorial content. The work is solid
  but still lives on `feature/prestige_UI`, which is now 10 ahead and 4 behind `main`. As
  flagged in Sprint #3, this branch needs to be synced with `main`, merged, and deleted
  before the deadline so the work is actually in the game. Prestige tree step 3 (#55)
  remains open.
- **Finn:** delivered real tutorial-flow code in `NusianController` (issue #5), but lost a
  noticeable amount of time fighting git rebases (commit messages "i hope i can rebase now"
  and "im never rebasing anything again" tell the story). His output is on `upgrade-tree`
  and not yet merged. He still owns three open items: the upgrade panel (#13), the rendering
  manager refactor (#38), and overall styling (#15).
- **Gabriela:** delivered the tutorial writing via PR #57, the only frontend work that
  reached `main` this sprint. Clean end-to-end (commit, PR, merge).

### Action Items
- [ ] Merge `feature/prestige_UI` and `upgrade-tree` into `main` immediately, then delete
      the branches; nothing un-merged should remain heading into the final sprint
- [ ] Close the upgrade panel (#13), styling/polish (#15), and rendering refactor (#38)
- [ ] Run the full frontend-backend integration smoke test (#54) once branches are merged

### Notes
- **Fenia:** no work, fourth consecutive sprint with zero output.
- **Lea:** no work, second sprint at zero.

---

## Backend

**Status: Complete**

Backend is effectively done. Marvin closed the last carryover item, balance parameter
tuning, with data-driven adjustments and a progression playtest (#31, PR #56 merged). The
only remaining backend-adjacent task is the integration smoke test (#54), which is
deliberately blocked until the frontend branches are merged and the game is playable
end to end.

### Individual Contributions

| Team Member | Experience Level | Contribution |
|---|---|---|
| Marvin | Experienced | Supervisor; closed balance tuning + data-driven playtest (#31) |

### Action Items
- [ ] Run #54 (integration smoke test) as soon as the frontend branches land

---

## Retrospective Highlights

### What Went Well
- Frontend completion jumped from 14% to 44%; the "all hands to frontend" decision worked
- Laura, Finn, and Gabriela all delivered real frontend code this sprint
- Backend reached 100%; the last balance item shipped and merged
- Gabriela's tutorial work went cleanly through PR and merge to `main`

### What Didn't Go Well
- Most frontend work is stranded on un-merged branches, so the progress is not yet in the
  game; this repeats the Sprint #3 visibility problem
- Fenia produced no output for the fourth consecutive sprint
- Lea again produced nothing
- Styling, the upgrade panel, the rendering refactor, and integration are all still open
  with one week to the final deadline

### Focus Areas for Sprint #5 (Final Sprint)
The project must be finished and submitted by **30.06.2026 21:00**. Sprint #5 is the last
sprint, so it is a stabilisation and integration push, not new scope.

1. **Merge everything** - get `feature/prestige_UI` and `upgrade-tree` onto `main` on day 1
2. **Integrate and smoke test** - run #54 against a fully merged build and fix what breaks
3. **Finish remaining frontend** - upgrade panel (#13), styling/polish (#15), prestige tree
   step 3 (#55)
4. **Freeze early** - aim to be feature-complete by 29.06 to leave a full day for the final
   playtest and submission prep

---

*Prepared for Sprint Review | Sprint #4*
