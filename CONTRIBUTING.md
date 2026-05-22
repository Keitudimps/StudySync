# Contributing to StudySync

Thank you for your interest in contributing to StudySync.

StudySync is a web-based academic collaboration platform designed to help students create, discover, and participate in study groups. Contributions are welcome from classmates, peers, and future contributors who want to improve the project.

---

# Prerequisites

Before contributing, ensure the following tools are installed:

- Java 17 or later
- Maven
- Git
- Node.js and npm
- IntelliJ IDEA, Visual Studio Code, or another suitable IDE

---

# Project Setup

## Fork the Repository

Fork the repository to your own GitHub account.

## Clone Your Fork

```bash
git clone https://github.com/your-username/StudySync.git
```

## Navigate to the Project

```bash
cd StudySync
```

## Navigate to the Backend

```bash
cd backend
```

## Install Dependencies

```bash
mvn clean install
```

## Run the Application

```bash
mvn spring-boot:run
```

---

# Running Tests

Before submitting a pull request, run all tests:

```bash
mvn clean test
```

All tests must pass before code can be merged into the main branch.

---

# Coding Standards

Contributors should follow these standards:

- Use meaningful class, method, and variable names.
- Follow Java naming conventions.
- Keep methods focused and readable.
- Do not commit unnecessary generated files.
- Add or update tests when changing functionality.
- Keep documentation clear and professional.
- Follow the existing project package structure.

---

# How to Pick an Issue

Contributors should start by choosing an issue with one of the following labels:

| Label | Meaning |
|---|---|
| `good-first-issue` | Suitable for new contributors |
| `feature-request` | Suggested future enhancement |
| `backend` | Backend-related task |
| `frontend` | Frontend-related task |
| `testing` | Testing-related task |
| `documentation` | Documentation-related task |

---

# Contribution Workflow

## 1. Create a New Branch

```bash
git checkout -b feature/your-feature-name
```

Examples:

```bash
git checkout -b feature/add-validation-utils
```

```bash
git checkout -b docs/add-deployment-notes
```

---

## 2. Make Your Changes

Make the required code or documentation changes.

---

## 3. Run Tests

```bash
cd backend
mvn clean test
```

---

## 4. Commit Your Changes

Use a clear commit message:

```bash
git add .
git commit -m "Add validation utility helper methods"
```

---

## 5. Push Your Branch

```bash
git push origin feature/your-feature-name
```

---

## 6. Open a Pull Request

When opening a pull request, include:

- A clear title
- A short explanation of the change
- The issue being addressed, if applicable
- Test results or screenshots where relevant

---

# Pull Request Checklist

Before submitting a pull request, confirm that:

- The project builds successfully.
- All tests pass.
- The change is focused and not too large.
- No unrelated files are included.
- Documentation is updated if needed.
- The pull request has a clear description.

---

# Example Pull Request Description

```text
This pull request adds reusable validation helper methods for checking blank values, email format, and group capacity. The change supports cleaner validation logic across the backend service layer.
```

---

# Code Review

Pull requests may be reviewed before merging. Reviewers may suggest improvements related to:

- Code readability
- Test coverage
- Documentation clarity
- Project structure
- Maintainability

---

# Thank You

Thank you for helping improve StudySync and supporting collaborative software development.
