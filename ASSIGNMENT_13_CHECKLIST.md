# Assignment 13 Checklist — CI/CD with GitHub Actions

## 1. Branch Protection Setup

Completed in documentation:

- `PROTECTION.md` explains why branch protection matters.
- `Screenshots/Branch protection screenshot.png` is included for evidence.

Required GitHub settings for `main`:

- Require pull request reviews before merging.
- Require at least 1 approval.
- Require status checks to pass before merging.
- Select the status check named `Run Unit and Integration Tests`.
- Do not allow direct pushes to `main`.

## 2. CI Pipeline: Test Automation

Completed file:

```text
.github/workflows/ci.yml
```

The workflow:

- Runs on push to any branch.
- Runs on pull requests to `main`.
- Sets up Java 21 using Temurin.
- Runs all Maven tests with `mvn --batch-mode clean test`.
- Blocks pull request merging when branch protection requires the test status check.

Included evidence:

```text
Screenshots/Actions Workflow.png
```

Existing local Surefire reports show:

```text
116 tests, 0 failures, 0 errors, 0 skipped
```

## 3. CD Pipeline: Release Artifact

Completed in:

```text
.github/workflows/ci.yml
```

The release artifact job:

- Runs only on a push or merge to `main`.
- Builds the backend JAR using Maven.
- Uploads the generated JAR as a GitHub Actions artifact named `StudySync-JAR`.
- Fails if no JAR file is found.

Included evidence:

```text
Screenshots/Artifact generation screenshot.png
```

## 4. Documentation and PR Workflow

Completed in:

```text
README.md
```

The README explains:

- How to run tests locally.
- How the CI pipeline works.
- How the CD artifact job works.
- How the pull request workflow protects `main`.

Included evidence:

```text
Screenshots/PR blocked screenshot.png
```

## Final Submission Reminder

Before submitting to Blackboard:

1. Push the updated project to GitHub.
2. Confirm the repository is accessible.
3. Open the **Actions** tab and confirm the workflow passes.
4. Confirm the artifact appears after merging to `main`.
5. Include all required screenshots in the repository or submission document.
