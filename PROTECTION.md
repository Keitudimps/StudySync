# Branch Protection Rules

## Overview

The `main` branch represents the stable version of the StudySync project. Branch protection rules are used to make sure that unstable, unreviewed, or untested code cannot be merged directly into `main`.

## Configured Rules for `main`

The following rules must be enabled in GitHub under **Settings → Branches → Branch protection rules**:

1. **Require a pull request before merging**
   - At least **1 approval** is required before a pull request can be merged.

2. **Require status checks to pass before merging**
   - The required GitHub Actions status check is: `Run Unit and Integration Tests`.
   - This check runs the Maven test command before code can be merged.

3. **Disable direct pushes to `main`**
   - Contributors must create a branch and open a pull request instead of pushing directly to `main`.

## Why These Rules Matter

### Pull Request Reviews

Pull request reviews help another developer check the code before it becomes part of the stable branch. This improves code quality, catches mistakes early, and supports teamwork.

### Required Status Checks

Required status checks make sure the CI pipeline passes before merging. If any unit or integration test fails, GitHub blocks the pull request from being merged into `main`.

### No Direct Pushes

Disabling direct pushes protects the stable branch. It ensures that every change follows the same professional workflow: branch → commit → pull request → review → automated tests → merge.

## Link to CI Workflow

The CI/CD workflow is stored in:

```text
.github/workflows/ci.yml
```

The workflow runs tests on every push and pull request, then builds and uploads a JAR artifact only after code is pushed or merged into `main`.

## Evidence Required for Submission

The submission should include screenshots showing:

- The branch protection rule for `main`
- The pull request review requirement
- The required status check
- The blocked direct-push or protected branch setting

## Conclusion

These rules support quality control, automation, and safe collaboration. They prevent broken code from reaching the stable branch and ensure that all project changes are reviewed and tested first.
