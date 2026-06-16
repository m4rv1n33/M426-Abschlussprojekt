"""
Generates the Sprint #3 contributions chart from git history data.

Regenerate with:
    python docs/reports/assets/generate_sprint3_chart.py

Data covers the Sprint #3 week (10.06.2026 - 16.06.2026) and is sourced from git
(see command below); update the dict if regenerating for a different period.
Note: --all is required so commits on un-merged feature branches (e.g. Laura's
feature/prestige_UI) are included.

    Commits: git shortlog -sn --all --no-merges --since="2026-06-10 00:00"

Git author -> team member name mapping:
    m4rv1n33 -> Marvin, Elysiummmm -> Finn, Gabriela13102008 -> Gabriela,
    laurab05 -> Laura, leaamaa -> Lea
"""

import matplotlib

matplotlib.use("Agg")
import matplotlib.pyplot as plt

# Sprint #3 week commits, 10.06.2026 - 16.06.2026 (excludes merge commits)
members = ["Marvin", "Gabriela", "Finn", "Laura", "Lea"]
commits = [10, 2, 1, 1, 0]

fig, ax = plt.subplots(figsize=(7, 4.5))
fig.suptitle(
    "Sprint #3 - Commits by Team Member (10.06.2026 - 16.06.2026)",
    fontsize=13,
    fontweight="bold",
)

bars = ax.bar(members, commits, color="#4C72B0")
ax.set_title("Commits (excl. merges)")
ax.set_ylabel("Commits")
ax.bar_label(bars, padding=3)
ax.spines["top"].set_visible(False)
ax.spines["right"].set_visible(False)
ax.set_ylim(0, max(commits) * 1.15)

fig.tight_layout(rect=(0, 0, 1, 0.95))
out = "docs/reports/assets/sprint_3_contributions.png"
fig.savefig(out, dpi=150)
print("wrote", out)
