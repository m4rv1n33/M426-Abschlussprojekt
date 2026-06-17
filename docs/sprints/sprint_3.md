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

## Sprint Backlog as GitHub Issues

Each task above is broken down into a GitHub issue draft with acceptance criteria, ready to be created in the issue tracker.

### Frontend

#### Issue: Implement shape upgrade button UI
- **Labels:** `frontend`, `sprint-3`, `carryover`
- **Assignee:** Gabriela (guidance: Finn)
- **Story Points:** 3
- **Tag:** Lightwork

**Description**
Add the in-game button(s) that let the player purchase shape upgrades directly from the main view, wired to the existing `UpgradeNode` purchase logic.

**Acceptance Criteria**
- [ ] An upgrade button is rendered for each purchasable shape upgrade, showing its name and current cost
- [ ] Button is disabled/greyed out when `canPurchase()` returns `false` (insufficient currency, unmet prerequisites, or already purchased and non-repeatable)
- [ ] Clicking an enabled button calls the backend purchase logic and immediately updates the currency display
- [ ] Button state (enabled/disabled, cost text) refreshes automatically as currency changes
- [ ] Manually verified against the running backend (no mocked data)

---

#### Issue: Design and program upgrades panel
- **Labels:** `frontend`, `sprint-3`, `carryover`
- **Assignee:** Finn (assistance: Laura)
- **Story Points:** 3
- **Tag:** Finn Lead

**Description**
Build out the "Upgrades" tab (`upgradesCanvas` in `nusian-view.fxml`) to display the full upgrade tree, including names, descriptions, costs, and purchase state.

**Acceptance Criteria**
- [ ] All `UpgradeNode`s from the backend are rendered with name, description, and current cost
- [ ] Nodes with unmet prerequisites (`hasUnlockedPrerequisites() == false`) are visually marked as locked
- [ ] Purchased, non-repeatable nodes show a clear "purchased" indicator
- [ ] Infinitely purchaseable nodes display the current purchase count and updated cost after each purchase
- [ ] Panel content updates live without requiring an app restart
- [ ] Layout reviewed and approved by Finn

---

#### Issue: Create prestige UI & button
- **Labels:** `frontend`, `sprint-3`, `carryover`
- **Assignee:** Laura (guidance: Finn)
- **Story Points:** 2
- **Tag:** Lightwork

**Description**
Implement the "Prestige" tab and wire up the existing `prestigeButton` (in `nusian-view.fxml`) to the `PrestigeStateManager` reset flow.

**Acceptance Criteria**
- [ ] Prestige tab displays current prestige currency and the prestige tree's nodes
- [ ] `prestigeButton` shows the correct cost/reward and is disabled when prestige requirements aren't met
- [ ] Clicking `prestigeButton` triggers the backend prestige reset and the UI reflects the new game state
- [ ] A confirmation dialog is shown before the prestige reset is executed
- [ ] Prestige currency display (`prestigeCurrencyDisplay`) updates immediately after a successful prestige

---

#### Issue: Tutorial flow & state management
- **Labels:** `frontend`, `sprint-3`, `carryover`
- **Assignee:** Gabriela, Laura
- **Story Points:** 3
- **Tag:** Minor

**Description**
Implement the state machine that drives the tutorial: tracking the current step, allowing progression, and persisting completion so the tutorial isn't repeated.

**Acceptance Criteria**
- [ ] Tutorial state tracks the current step and advances on user action (next/skip)
- [ ] Players can skip the tutorial entirely or step through it sequentially
- [ ] Once completed or dismissed, the tutorial does not reappear on subsequent app launches
- [ ] Tutorial state does not interfere with or block normal gameplay state (currency, upgrades, prestige)

---

#### Issue: Tutorial texts, images & content
- **Labels:** `frontend`, `sprint-3`, `carryover`
- **Assignee:** Gabriela, Laura
- **Story Points:** 2
- **Tag:** Lightwork

**Description**
Write the copy and supply the images/icons for each tutorial step, covering the core game mechanics.

**Acceptance Criteria**
- [ ] Tutorial content covers: shape clicking/production, the upgrades panel, and the prestige mechanic
- [ ] All text is proofread, in correct German/English (per project language), and consistent in tone
- [ ] An image or icon is provided for each tutorial step and renders correctly at the target window size
- [ ] Content reviewed and approved by Finn before merge

---

#### Issue: Button styling & UI polish
- **Labels:** `frontend`, `sprint-3`, `carryover`
- **Assignee:** Laura
- **Story Points:** 2
- **Tag:** Lightwork

**Description**
Apply consistent visual styling across all buttons, tabs, and panels added during Sprint #2 and Sprint #3.

**Acceptance Criteria**
- [ ] Consistent color scheme and font are applied across all buttons and panels
- [ ] Hover, disabled, and active states are styled for every interactive button
- [ ] No layout overflow or clipping at the application's target window size
- [ ] Final styling reviewed against the existing UI for visual consistency

---

#### Issue: Full frontend-backend integration smoke test
- **Labels:** `frontend`, `backend`, `sprint-3`, `integration`
- **Assignee:** Finn, Marvin
- **Story Points:** 2
- **Tag:** Lead task

**Description**
Run an end-to-end manual playthrough covering production, currency updates, upgrade purchases, and prestige to confirm the frontend and backend are fully connected.

**Acceptance Criteria**
- [ ] A full game loop (produce currency -> buy upgrades -> prestige) can be played without crashes
- [ ] Currency and upgrade state remain in sync between UI and backend throughout
- [ ] Edge cases are tested: zero currency, all upgrades purchased, repeated prestige
- [ ] Any issues found are filed as new GitHub issues before sprint close

---

### Backend

#### Issue: Close remaining open issue from Sprint #2
- **Labels:** `backend`, `sprint-3`, `carryover`, `priority:high`
- **Assignee:** Marvin
- **Story Points:** -
- **Tag:** Lead task (Day 1 priority)

**Description**
Resolve the single open issue carried over from Sprint #2 before any new backend work begins.

**Acceptance Criteria**
- [ ] The carried-over Sprint #2 issue is identified and linked to this issue
- [ ] Root cause is fixed with accompanying unit/integration test coverage
- [ ] PR is merged and the original issue is closed on day 1 of the sprint

---

#### Issue: Refine balance parameters & data-driven adjustments
- **Labels:** `backend`, `sprint-3`, `balance`
- **Assignee:** Marvin
- **Story Points:** 3
- **Tag:** Lead task

**Description**
Use the now-complete test suite to tune game balance (costs, growth rates, multipliers) for a smoother progression curve, moving hardcoded values to a data-driven format where reasonable.

**Acceptance Criteria**
- [ ] Balance-relevant parameters (e.g. `UpgradeCost` values, growth rates) are reviewed and adjusted
- [ ] Existing unit and integration tests pass with the updated values; new tests added where coverage is missing
- [ ] Changed values and the rationale behind them are documented
- [ ] Manual playtest confirms an improved progression curve (no excessively long flat spots or runaway scaling)

---

#### Issue: Git basics workshop
- **Labels:** `backend`, `sprint-3`, `team-goal`, `onboarding`
- **Assignee:** Marvin (leads), Fenia, Lea (participants)
- **Story Points:** 2
- **Tag:** Team goal

**Description**
Run a hands-on Git fundamentals workshop for the junior team members, covering branching, committing, pushing, and resolving merge conflicts.

**Acceptance Criteria**
- [ ] Workshop session is held with both juniors attending
- [ ] Each junior creates a branch, commits a change, and opens a PR during the session
- [ ] At least one merge conflict is resolved hands-on as part of the exercise
- [ ] Workshop notes/cheat sheet are added under `docs/` for future reference

---

#### Issue: Write prestige tree unit tests
- **Labels:** `backend`, `sprint-3`, `testing`
- **Assignee:** Fenia
- **Story Points:** 2
- **Tag:** Lightwork

**Description**
Extend `PrestigeTreeTest` with coverage for node lookup, purchased-node tracking, and reference resolution in `PrestigeTree`.

**Acceptance Criteria**
- [ ] `getNode(name)` is tested for both existing and non-existent node names
- [ ] `getPurchasedNodes()` is tested with zero, one, and multiple purchased nodes
- [ ] `resolveReferences()` correctly links `previousNodes` based on `previousNodeNames`
- [ ] All new tests pass locally and in CI

---

#### Issue: Write UpgradeNode edge-case tests
- **Labels:** `backend`, `sprint-3`, `testing`
- **Assignee:** Lea
- **Story Points:** 2
- **Tag:** Lightwork

**Description**
Extend `UpgradeNodeTest` with edge cases for purchase eligibility, repeat-purchase rules, and progress reset on `UpgradeNode`.

**Acceptance Criteria**
- [ ] `canPurchase()` is tested for: wrong required shape type, unmet prerequisites, insufficient currency, and an already-purchased non-repeatable node
- [ ] `recordPurchase()` throws `IllegalStateException` when called a second time on a non-repeatable node
- [ ] `resetProgress()` resets `purchaseCount` to 0 and re-enables purchase where applicable
- [ ] `getCurrentCost()` is verified across multiple purchase counts for an infinitely purchaseable node
- [ ] All new tests pass locally and in CI

---

### Team-wide

#### Issue: Git basics workshop follow-up / hands-on session
- **Labels:** `team-goal`, `sprint-3`, `onboarding`
- **Assignee:** Marvin (leads), Fenia, Lea (participants)
- **Story Points:** 2

**Description**
Optional follow-up hands-on Git session for juniors, scheduled if the initial workshop reveals gaps.

**Acceptance Criteria**
- [ ] Feedback on the initial Git workshop is collected from both juniors
- [ ] A follow-up session is scheduled only if the feedback indicates it's needed
- [ ] If held, juniors can independently create a branch, commit, push, and open a PR without supervisor assistance afterward

---
