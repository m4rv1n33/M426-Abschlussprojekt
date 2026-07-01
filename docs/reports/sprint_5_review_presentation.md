# Sprint #5 Review - Presentation Outline

**Sprint:** #5 (final)
**Meeting type:** Sprint Review (increment demo + retrospective highlights), teacher present
**Prepared by:** Marvin
**Duration target:** ~20-25 minutes (leave time for questions)

This is a run-of-show, not slides to read from verbatim - use it to structure the meeting and know what to click through live. Pair with `docs/release-plan.md` (overall roadmap) and `docs/reports/scrum_report_5.md` (the written report for this sprint - use it as the source of truth for the accomplishments and retrospective sections below).

## Presenter assignments

Lea is not attending this review, so no section is assigned to her; her sprint had no shipped work to demo (see `scrum_report_5.md`). Everyone else presents the part they actually built, so questions land on the person who can answer them.

| # | Section | Presenter | Why them |
|---|---|---|---|
| 1 | Opening | Marvin | Frames the meeting; backend/integration lead |
| 2 | Release recap | Finn | Frontend supervisor across all 5 sprints - lived the whole arc |
| 3a | Demo: fresh start & tutorial | Gabriela | Wrote the tutorial content (Sprint #4, PR #57) |
| 3b | Demo: core loop & upgrade tree | Fenia | Co-authored `ShapeRenderer`/`UpgradeRenderer` this sprint |
| 3c | Demo: prestige | Laura | Built the prestige upgrade UI and row layout (#55) |
| 3d | Demo: persistence | Marvin | Owns the save system and dev launch flags |
| 4 | Sprint #5 accomplishments | Finn | Highest individual output this sprint; can speak to all of it firsthand |
| 5 | Retrospective highlights | Marvin | Facilitates - contribution is a sensitive topic, kept with the lead rather than put on a junior |
| 6 | Metrics | Gabriela | Straightforward walk-through of the velocity numbers |
| 7 | Q&A / close | Marvin | Facilitates; defers to whoever built the thing being asked about |

Each section below is tagged with its presenter - swap freely if someone can't make it, but keep retrospective/contribution talk with Marvin.

---

## 1. Opening (1 min) - *Marvin*

- State the goal in one sentence: *"Ship a complete, playable, end-to-end incremental game by 30/06/2026 21:00 - this review covers the final sprint and the finished product."*
- One-line team reminder: Frontend Supervisor Finn (Gabriela, Laura, Fenia, Lea), Backend/Integration Lead Marvin.

## 2. Release recap - where we started, where we ended (3 min) - *Finn*

Talk through the milestone table from `docs/release-plan.md`, not the whole doc - just the shape of it:

- Backend was essentially done after Sprint #1 and stayed at/near 100% every sprint.
- Frontend was the whole story: 14% -> 20% -> 14% -> 44% -> (this sprint's close).
- Name the two structural fixes that got us here: the all-hands frontend move in Sprint #4, and the "merge everything on day 1" rule in Sprint #5.
- Keep this brief - the point is to set up the demo, not re-litigate old sprints (that's the retrospective section).

## 3. Live demo (8-10 min) - *handoff between Gabriela, Fenia, Laura, Marvin*

Run this against a real build, not screenshots. Use the dev flags documented in `docs/internals/persistence.md` to control what the audience sees:

1. **Fresh start** *(Gabriela)*: `mvn javafx:run@reset` to wipe the save, then launch normally. Show the tutorial firing on first run (`TutorialManager`, five steps) and confirm it doesn't reappear after restart.
2. **Core loop & upgrade tree UI** *(Fenia)*: click through production ticking up in real time (`GameEngine`/`ShapeRenderer` - the spinning polygon), buy a shape upgrade from the upgrade tree canvas, and point out the cost scaling and the vertex count/production rate increasing immediately. Hover a node to show the tooltip (name/cost/description), pan the tree by dragging, and show a locked node vs. a purchased one (this is the #13 upgrade panel work from Sprint #5).
3. **Prestige** *(Laura)*: either play up to the threshold live or switch to a boosted save (`mvn javafx:run@max`) to show prestige immediately - click Prestige, show the reset (currency/shape/upgrades cleared) and the prestige tree persisting, then buy a prestige upgrade and show the vertex multiplier effect.
4. **Persistence** *(Marvin)*: close and reopen the app to prove the save survives a restart (autosave every 5s + save-on-close).

Have a fallback: if live play risks running long, use `--max` up front and narrate faster, since the boosted save exists specifically for demos.

## 4. Sprint #5 accomplishments (4 min) - *Finn*

Go issue-by-issue against the Sprint #5 backlog (`docs/sprints/sprint_5.md`):

- Day-1 merges: `feature/prestige_UI` and `upgrade-tree` merged into `main` and deleted, confirmed clean/buildable build before any further work.
- Frontend: upgrade panel (#13), prestige tree step 3 (#55), overall styling/polish (#15) - closed, plus the rendering refactor (#38) delivered as a bonus even though it was only a stretch goal.
- Fenia's turnaround: call this out by name - first individual code contribution of the whole project (`ShapeRenderer`/`UpgradeRenderer`), paired with Finn. It's a good, concrete "what went well" moment.
- Integration: full frontend-backend smoke test (#54) run against the merged build, defects found and fixed (FXML namespace, shape-focus gating).
- Result: game submitted before the 30/06/2026 21:00 deadline, per the release Definition of Done in `docs/release-plan.md`.

Full detail (who did what) is in `docs/reports/scrum_report_5.md` - use this bullet list as the talking points, not a script to read verbatim.

## 5. Retrospective highlights across the whole release (5 min) - *Marvin*

Pull from `docs/reports/scrum_report_5.md`'s retrospective (Sprint #5) and `scrum_report_4.md`'s (prior sprint) - Suggested structure - What went well / What didn't / What we'd do differently:

**What went well**
- Backend delivery was consistently strong and complete every sprint.
- The all-hands pivot in Sprint #4-5 measurably lifted frontend output (14% -> 44% -> complete).
- Structural fixes (junior swap, Git workshop, day-1 merge rule) were each a direct response to a named retrospective finding, not a repeated mistake.

**What didn't go well**
- Frontend work sat unmerged on feature branches for two sprints running (#3 and #4), which hid real progress and repeatedly caused the "is this actually done" visibility problem.
- Contribution was uneven - be ready to discuss this factually and briefly if the teacher raises it; see `docs/reports/contribution-report.md` for the prepared talking points (private doc, not for screen-share, but you can speak from it).

**What we'd do differently**
- Set a "merge by end of day" rule from Sprint #1, not Sprint #5.
- Surface branch-sync status in every standup, not just at sprint boundaries.

## 6. Metrics (2 min, optional if time is short) - *Gabriela*

- Story points planned per sprint: 39 / 39 / 27 / 19 / 13 - point out this is a shrinking backlog against a fixed MVP, not scope creep.
- Frontend completion by sprint (from `docs/release-plan.md`): 14% -> 20% -> 14% -> 44% -> 100%. This is the team-level chart to show, if one is wanted - it tells the "struggled, then fixed it structurally" story without naming individuals.

## 7. Q&A / close (remaining time) - *Marvin*

- Invite questions on the demo first, architecture/process second.
- Have `docs/internals/architecture.md` open in case a technical question needs a package-layout answer.
- Close by restating: MVP scope was fixed at kickoff (`docs/project_descr.md`), all six user stories are shown working in the demo above, and the release Definition of Done is met.

---

## Pre-meeting checklist

- [ ] Build `main` fresh right before the meeting (`mvn clean javafx:run`) to catch any last-minute breakage
- [ ] Have both a fresh save (`--reset`) and a boosted save (`--max`) ready to switch between
- [ ] Confirm no unmerged branches remain (`git branch -a`)
- [ ] `docs/release-plan.md`, `docs/reports/scrum_report_4.md`, and this file open in tabs
- [ ] Contribution report reviewed privately beforehand if contribution is likely to come up (do not screen-share it)
- [ ] Everyone knows their assigned section (see "Presenter assignments" above) and has rehearsed their demo handoff
- [ ] Since Lea isn't attending: confirm with the group who's covering the retrospective mention of her contribution (assigned to Marvin above) - no one should be caught off guard by the question if the teacher asks
