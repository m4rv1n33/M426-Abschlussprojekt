# Git Basics & Workflow

This guide assumes you have never used Git before. If a term feels unfamiliar, check the **Key Terms** section first, then come back here.

## What is Git and why do we use it?

Git is a **version control system**: a tool that tracks every change made to the project's files over time. Instead of emailing zip files back and forth or working in a single shared folder (where someone's changes can overwrite someone else's), Git lets several people work on the same project at the same time, on their own copy, and then combine ("merge") their work safely.

Why this matters for our project:

- **History**: Every change is saved with a message explaining what and why. You can always go back to an earlier version.
- **Parallel work**: Everyone can work on their own feature without blocking or breaking what others are doing.
- **Safety net**: Mistakes can almost always be undone (see "Undoing Mistakes" below).
- **Review**: Changes go through a Pull Request, so a second person checks the code before it becomes part of the main project. This catches bugs early and spreads knowledge across the team.

**GitHub** is the website/service that hosts our repository online (the "remote"). Git is the tool you run on your computer; GitHub is where everyone's work comes together.

## Key Terms

| Term | Meaning |
|------|---------|
| **Repository ("repo")** | The project folder, including its entire history of changes. Ours lives on GitHub and as a copy on each person's computer. |
| **Clone** | Downloading a copy of the repository (with its full history) to your computer. You only do this once. |
| **Commit** | A saved snapshot of changes, with a message describing what changed and why. Think of it as a checkpoint you can return to. |
| **Branch** | An independent line of work, branched off from `main`. Lets you work on a feature without affecting the main codebase until it's ready. |
| **`main`** | The branch that always contains the stable, working version of the project. |
| **Staging area** | A "waiting room" for changes you want to include in your next commit. `git add` moves changes here; `git commit` saves them permanently. |
| **Working directory** | The actual files on your computer, as you see and edit them in your editor/IDE. |
| **Remote** | A version of the repository hosted elsewhere (for us: on GitHub). `origin` is the default name for our main remote. |
| **Push** | Uploading your local commits to the remote (GitHub), so others can see them. |
| **Pull** | Downloading the latest commits from the remote into your local branch. |
| **Merge** | Combining the changes from one branch into another. |
| **Conflict** | Happens when Git can't automatically combine changes (e.g. two people edited the same line). You resolve it by choosing or combining the correct version. |
| **Pull Request (PR)** | A request on GitHub to merge your branch into `main`, so someone else can review your changes first. |

## Branching Strategy

- **`main`** - Production-ready code. Only merged via Pull Request after review.
- **`feature/<short-description>`** - New features (e.g. `feature/currency-display`, `feature/prestige-system`).
- **`fix/<short-description>`** - Bugfixes (e.g. `fix/upgrade-cost-calculation`).
- **`refactor/<short-description>`** - Code cleanup or restructuring (e.g. `refactor/prestige-system`).
- **`docs/<short-description>`** - Documentation changes (e.g. `docs/api-documentation`).
- **`test/<short-description>`** - Adding or updating tests.
- **`chore/<short-description>`** - Tooling, dependencies, config changes.
- **`balancing/<short-description>`** - Game balance parameter tuning (e.g. `balancing/refine-balance-params`).

Branches are created from `main` and merged back into `main` via Pull Request.

## Small Commits

- Commit often, commit small. Each commit should contain a single logical, complete change.
- Avoid huge commits with many unrelated changes.
- Work-in-progress commits must not end up on `main`.

## Commit Naming Conventions

Use conventional commits:

```
<type>: <short description>
```

**Types:**

| Type       | Usage                                   |
|------------|-----------------------------------------|
| `feat`     | New feature                             |
| `fix`      | Bugfix                                  |
| `refactor` | Code cleanup / restructuring            |
| `test`     | Adding or updating tests                |
| `docs`     | Documentation changes                   |
| `chore`    | Tooling, dependencies, config changes   |

**Examples:**

```
feat: add currency display to HUD
fix: correct prestige multiplier calculation
refactor: extract upgrade cost logic
test: add unit tests for shape entity
docs: update README with setup instructions
```

Write in English, keep it short but descriptive.

## Pull Request Workflow

1. **Create a branch** from `main`.
2. **Work and commit** regularly with small commits.
3. **Push** the branch.
4. **Open a Pull Request** on GitHub once the task is complete.
5. **Add a reviewer**: **Marvin** or **Finn** (depending on who is responsible for the area, they will be notified automatically).
6. **Do not merge yourself** - Pull Requests may **only be merged by the reviewer**.
7. After merging: delete the branch both locally and remotely (see commands below).

## Common Git Commands

### First-time setup (once per computer)

Git needs to know who you are, so your commits are correctly attributed:
```bash
git config --global user.name "Your Name"
git config --global user.email "your.email@example.com"
```

### Cloning the repository (once)
```bash
git clone <repository-url>
cd <repository-folder>
```
This downloads the project (with its full history) into a new folder and switches you into it. You only need to do this once per computer.

### Starting a new feature / fix
```bash
git checkout main
git pull
git checkout -b feature/my-feature
```

### Checking the current status
```bash
git status
```
This is the command you will run the most. It shows you:
- which branch you are on,
- which files you have changed,
- which changes are staged (ready to be committed) and which are not.

When in doubt about what Git will do next, run `git status` first.

### Staging and committing changes

Committing is a two-step process: first you choose which changes to include (**staging**), then you save them as a commit.

```bash
git add <file>
git commit -m "feat: add currency display to HUD"
```
`git add <file>` moves the file's changes into the staging area. `git commit` then takes everything in the staging area and saves it as a new commit with the given message.

Or stage all changed files at once:
```bash
git add .
git commit -m "feat: add currency display to HUD"
```

> **Note:** `git add .` stages everything in the working directory. Double-check with `git status` beforehand to avoid accidentally committing config files, credentials, or generated files.

### Pushing your branch
```bash
git push -u origin feature/my-feature
```

After the first push, subsequent pushes only need:
```bash
git push
```

### Keeping your branch up to date with main
```bash
git checkout main
git pull
git checkout feature/my-feature
git merge main
```

### Resolving a merge conflict

Sometimes Git cannot automatically combine two sets of changes (e.g. you and a teammate both edited the same line of the same file). This is called a **conflict**, and it's completely normal, not a sign that something went wrong.

When it happens, Git marks the conflicting spot directly in the file like this:

```
<<<<<<< HEAD
your version of the line
=======
the other version of the line
>>>>>>> main
```

To resolve it:

1. Open the file and find the `<<<<<<<` / `=======` / `>>>>>>>` markers.
2. Decide which version to keep (or combine both), then delete the markers and the version(s) you don't want.
3. Save the file, then stage and commit it:
   ```bash
   git add <file>
   git commit
   ```
4. Continue your work as normal.

If you're unsure which version is correct, talk to the other person whose code is involved before deciding (see "Important Rules" below). Your editor or IDE (e.g. IntelliJ, VS Code) usually offers a visual merge tool that makes this easier than editing the markers by hand.

### Deleting a branch after merging
```bash
# Delete locally
git checkout main
git pull
git branch -d feature/my-feature

# Delete remotely
git push origin --delete feature/my-feature
```

### Link your branch to an issue

It is recommended to link your branch to the issue you are working on. This makes tracking progress easier.

**Via CLI:** Include the issue number in your branch name:
```bash
git checkout -b feature/42-currency-display
```

**Via GitHub Desktop:** You can create a branch from an issue directly in the app.

When you open a Pull Request, reference the issue in the description (e.g. `Closes #42`) to automatically close it when merged.

### Typical daily workflow
```bash
# 1. Start your day: get the latest main
git checkout main
git pull

# 2. Create your feature branch (only once per task)
git checkout -b feature/my-task

# 3. Do work and commit often
git add .
git commit -m "feat: implement shape upgrade logic"

# 4. Push when ready or to save progress
git push -u origin feature/my-task

# 5. When the task is done, open a Pull Request on GitHub
#    and add Marvin or Finn as reviewer
```

## Undoing Mistakes

### Fix the last commit message
```bash
git commit --amend -m "feat: correct commit message"
```

> **Warning:** Only amend commits that have **not been pushed** yet. Amending a pushed commit rewrites history and causes problems for anyone else who has pulled the branch.

### Unstage a file (keep changes)
```bash
git reset <file>
```

### Discard unstaged changes in a file
```bash
git restore <file>
```

### Undo the last commit (keep changes staged)
```bash
git reset --soft HEAD~1
```

### Undo the last commit (discard changes)
```bash
git reset --hard HEAD~1
```

> **Warning:** `--hard` permanently deletes changes.

> **Note:** All of the above can also be done using the **GitHub Desktop** app instead of the command line.

## Important Rules

- Never commit directly to `main`.
- **Never commit `.env` files, passwords, API keys, or any credentials.** Add `.env` to `.gitignore` immediately. If secrets are committed, rotate them right away.
- Always have someone review your Pull Request.
- **Never merge your own Pull Request** - always let the reviewer do it.
- For merge conflicts: resolve them together with the reviewer.
- Tests must be green before merging.
