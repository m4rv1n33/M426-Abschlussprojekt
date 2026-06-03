# Git Basics & Workflow

## Branching Strategy

- **`main`** - Production-ready code. Only merged via Pull Request after review.
- **`feature/<short-description>`** - For new features (e.g. `feature/currency-display`, `feature/prestige-system`).
- **`fix/<short-description>`** - For bugfixes (e.g. `fix/upgrade-cost-calculation`).

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
5. **Add a reviewer**: **Marvin** or **Finn** (depending on who is responsible for the area).
6. **Do not merge yourself** - Pull Requests may **only be merged by the reviewer**.
7. After merging: delete the branch both locally and remotely.

## Common Git Commands

### Cloning the repository (once)
```bash
git clone <repository-url>
cd <repository-folder>
```

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

### Staging and committing changes
```bash
git add <file>
git commit -m "feat: add currency display to HUD"
```

Or stage all changed files at once:
```bash
git add .
git commit -m "feat: add currency display to HUD"
```

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

### Deleting a branch after merging
```bash
git checkout main
git pull
git branch -d feature/my-feature
```

> **Note:** All of the above can also be done using the **GitHub Desktop** app instead of the command line.

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

## Important Rules

- Never commit directly to `main`.
- Always have someone review your Pull Request.
- **Never merge your own Pull Request** - always let the reviewer do it.
- For merge conflicts: resolve them together with the reviewer.
- Tests must be green before merging.
