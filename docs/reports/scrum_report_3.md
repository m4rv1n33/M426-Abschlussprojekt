# Sprint #3 Report

**Sprint:** #3

**Status:** Completed

**Date:** 16.06.2026

---

## Contributions Overview

![Sprint #3 contributions by team member](assets/sprint_3_contributions.png)

The chart above shows the number of non-merge commits authored by each member during
the Sprint #3 week (10.06.2026 - 16.06.2026). Marvin leads with 10 commits, followed
by Gabriela (2), Finn (1), and Laura (1). Laura's commit is on her `feature/prestige_UI`
branch. Lea recorded no commits, and Fenia has none and therefore does not appear.

---

## Summary


| Area | Issues Closed | Issues Open | Completion Rate |
|------|:---:|:---:|:---:|
| Frontend | 1 / 7 | 6 | 14% |
| Backend | 4 / 5 | 1 | 80% |
| **Total** | **5 / 12** | **7** | **42%** |

---

## Frontend

**Status: Still Behind**

Frontend remains the critical area. Of the seven carryover tasks, only the prestige
UI and button shipped this sprint; the upgrades panel, tutorial flow and content,
styling, and the integration smoke test are all still open. As in Sprints #1 and #2,
work started late despite this being flagged repeatedly in past retrospectives.

### Individual Contributions

| Team Member | Experience Level | Contribution |
|---|---|---|
| Finn | Experienced | Supervisor; left Gabriela's PR unreviewed for 5 days |
| Gabriela | Junior | Delivered shape upgrade button work |
| Laura | Junior | Shipped the prestige UI & button |

### Notes
- **Laura:** shipped the prestige UI & button on her `feature/prestige_UI` branch. A few
  process points: she pushed late on a separate branch rather than at end of day, which made
  her progress harder to track; the branch is now 41 commits behind `main` and 3 ahead, so it
  needs to be brought up to date, merged, and then deleted once the feature is done (per the
  PR workflow in the Git doc); and her commit message did not follow the conventional-commits
  naming convention. Minor points, but worth aligning on.
- **Gabriela:** delivered the shape upgrade button and opened a PR, which then sat unreviewed
  for 5 days.
- **Finn:** the 5-day review delay on Gabriela's PR; should've been looked at earlier.

### Action Items
- [ ] Review open PRs within 24-48h
- [ ] All members to push work to the remote at the end of each working day, per the Git basics document
- [ ] Keep feature branches in sync with `main` and delete them after merge; follow the commit naming conventions
- [ ] Triage the remaining six carryover frontend issues into Sprint #4

---

## Backend

**Status: On Track via the Supervisor**

Backend delivery is healthy on paper (4 of 5 tasks closed), but the headline numbers
hide a serious problem: both assigned juniors produced nothing, and Marvin
absorbed their tasks to keep the area on track. The one remaining open item is the
balance tuning work, which unfortunately depends on the frontend to get a feel for the game.

### Individual Contributions

| Team Member | Experience Level | Contribution |
|---|---|---|
| Marvin | Experienced | Supervisor; drove backend delivery and completed both juniors' assigned test tasks |
| Fenia | Junior | No work done |
| Lea | Junior | No work done |

### Notes
- **Fenia:** no work, third consecutive sprint.
- **Lea:** no work, a regression from her minor Sprint #2 output.
- **Git workshop:** the optional follow-up was offered but drew zero requests from the juniors, so it was not held.

### Action Items
- [ ] Close out balance parameter tuning before next sprint end

---

## Retrospective Highlights

### What Went Well
- Laura shipped the prestige UI & button, the main frontend progress this sprint
- Backend test coverage was completed (prestige tree and UpgradeNode edge cases)

### What Didn't Go Well
- Fenia produced no output for the third consecutive sprint.
- Lea regressed to zero output after a step forward in Sprint #2
- Gabriela's pull request was left unreviewed for 5 days, blocking otherwise-completed work
- Work started late again, despite timing being a focus area in Sprints #1 and #2
- End-of-day push discipline was not followed, reducing progress visibility
- Frontend remains critically behind on its carryover backlog

### Focus Areas for Sprint #4
1. **Frontend recovery (drastic measure)** - all resources except Marvin move to frontend next sprint to clear the carryover backlog and ship a playable game
2. **Git discipline** - enforce end-of-day pushes so progress is trackable
3. **Timing** - begin work on day 1 rather than late in the sprint, as repeatedly agreed

---

*Prepared for Sprint Review | Sprint #3*