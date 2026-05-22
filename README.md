# StudySync — Study Group Finder System

## Project Overview

StudySync is a web-based academic collaboration platform developed as part of a Software Engineering course project. The system is designed to address a common challenge faced by university students: the lack of a structured, centralised tool for forming and coordinating peer study groups.

The platform enables students to discover, create, and participate in study groups organised by course code or subject area. It provides structured workflows for group membership, session scheduling, and administrative oversight — replacing the informal and unreliable methods students currently rely on, such as social media groups and physical notice boards.

Upon completion, the system will provide the following core capabilities:

- Student registration and academic profile management linked to enrolled courses
- Creation and discovery of study groups with configurable privacy settings
- Membership management including join requests and creator approval workflows
- Study session scheduling with location and agenda support
- Administrative tools for user account management and group moderation

The system is being developed using **React** for the frontend, **Java Spring Boot** for the backend REST API, and **PostgreSQL** as the relational database.

---

# Table of Contents

1. [Repository Structure](#repository-structure)
2. [Quick Links](#quick-links)
3. [Technology Stack](#technology-stack)
4. [Running the Project](#running-the-project)
5. [Project Documentation](#project-documentation)
6. [Design Patterns Implementation](#design-patterns-implementation)
7. [Repository Layer](#repository-layer)
8. [Service Layer and REST API](#service-layer-and-rest-api)
9. [CI/CD Pipeline](#cicd-pipeline)
10. [Project Management](#project-management)
11. [Current Project Status](#current-project-status)
12. [Project Reflection](#project-reflection)
13. [Author](#author)

---

# Repository Structure

This repository serves as the complete project documentation and source code repository for all assignments submitted throughout the semester. Each implementation builds upon the previous phase, maintaining full traceability from requirements engineering through to system implementation and CI/CD automation.

```text
StudySync/
│
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── controller/
│   │   │   ├── creational/
│   │   │   ├── domain/
│   │   │   ├── dto/
│   │   │   ├── factory/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   └── StudySyncApplication.java
│   │   │
│   │   └── test/
│   │       ├── controller/
│   │       ├── creational/
│   │       ├── repository/
│   │       └── service/
│   │
│   ├── pom.xml
│   └── run-tests.sh
│
├── .github/
│   └── workflows/
│       └── ci.yml
│
├── README.md
├── PROTECTION.md
├── CHANGELOG.md
└── .gitignore
```

---

# Quick Links

| Resource | Link |
|---|---|
| System Specification | [SPECIFICATION.md](./SPECIFICATION.md) |
| Architecture Documentation | [ARCHITECTURE.md](./ARCHITECTURE.md) |
| Stakeholders | [STAKEHOLDERS.md](./STAKEHOLDERS.md) |
| System Requirements | [SRD.md](./SRD.md) |
| Use Cases | [USE_CASES.md](./USE_CASES.md) |
| Agile Planning | [AGILE_PLANNING.md](./AGILE_PLANNING.md) |
| Kanban Documentation | [KANBAN_EXPLANATION.md](./KANBAN_EXPLANATION.md) |
| State Diagrams | [STATE_DIAGRAMS.md](./STATE_DIAGRAMS.md) |
| Activity Diagrams | [ACTIVITY_DIAGRAMS.md](./ACTIVITY_DIAGRAMS.md) |
| Domain Model | [DOMAIN_MODEL.md](./DOMAIN_MODEL.md) |
| Class Diagram | [CLASS_DIAGRAM.md](./CLASS_DIAGRAM.md) |
| Reflection Document | [REFLECTION.md](./REFLECTION.md) |
| Branch Protection Rules | [PROTECTION.md](./PROTECTION.md) |
| Changelog | [CHANGELOG.md](./CHANGELOG.md) |

---

# Technology Stack

| Layer | Technology |
|---|---|
| Frontend | React 18, Axios, React Router v6, TailwindCSS |
| Backend | Java 17, Spring Boot 3 |
| Database | PostgreSQL 15 |
| Authentication | JSON Web Tokens (JWT) |
| Build Tool | Apache Maven 3.9.15 |
| Testing | JUnit 5, Mockito |
| CI/CD | GitHub Actions |
| Deployment | Vercel, Railway |
| Version Control | Git and GitHub |

---

# Running the Project

## Clone the Repository

```bash
git clone https://github.com/Keitudimps/StudySync.git
```

---

## Navigate to Backend

```bash
cd backend
```

---

## Run the Application

```bash
mvn spring-boot:run
```

---

## Run All Tests

```bash
mvn clean test
```

---

## Build the Application

```bash
mvn clean package
```

Generated JAR artifacts will be located in:

```text
backend/target/
```

---

# Project Documentation

The repository contains all documentation, diagrams, specifications, architectural models, implementation artefacts, and project management documentation developed throughout the project lifecycle.

---

## System Specification and Architecture

| Document | Description |
|---|---|
| [SPECIFICATION.md](./SPECIFICATION.md) | Defines system scope, features, constraints, and problem domain |
| [ARCHITECTURE.md](./ARCHITECTURE.md) | Full C4 architectural diagrams covering context, containers, components, and code relationships |

---

## Requirements Engineering

| Document | Description |
|---|---|
| [STAKEHOLDERS.md](./STAKEHOLDERS.md) | Stakeholder analysis including concerns, trade-offs, and success metrics |
| [SRD.md](./SRD.md) | Functional and non-functional requirements with traceability matrix |

---

## Analysis and Modelling

| Document | Description |
|---|---|
| [USE_CASES.md](./USE_CASES.md) | Use case diagrams, specifications, and functional test cases |
| [STATE_DIAGRAMS.md](./STATE_DIAGRAMS.md) | State transition diagrams |
| [ACTIVITY_DIAGRAMS.md](./ACTIVITY_DIAGRAMS.md) | Workflow activity diagrams |
| [DOMAIN_MODEL.md](./DOMAIN_MODEL.md) | Domain entities, relationships, and business rules |
| [CLASS_DIAGRAM.md](./CLASS_DIAGRAM.md) | UML class diagram implementation |

---

## Agile Planning and Project Management

| Document | Description |
|---|---|
| [AGILE_PLANNING.md](./AGILE_PLANNING.md) | User stories, product backlog, sprint planning |
| [KANBAN_EXPLANATION.md](./KANBAN_EXPLANATION.md) | Kanban board workflow and structure |
| [TEMPLATE_ANALYSIS.md](./TEMPLATE_ANALYSIS.md) | GitHub project template analysis |

---

## Supporting Documentation

| Document | Description |
|---|---|
| [REFLECTION.md](./REFLECTION.md) | Project reflections and lessons learned |
| [CHANGELOG.md](./CHANGELOG.md) | Project updates and cumulative changes |
| [PROTECTION.md](./PROTECTION.md) | Branch protection configuration explanation |

---

# Design Patterns Implementation

The project implements multiple creational design patterns integrated into the system architecture.

## Patterns Implemented

| Pattern | Purpose |
|---|---|
| Simple Factory | Notification object creation |
| Factory Method | Payment processor creation |
| Abstract Factory | Cross-platform GUI creation |
| Builder | Complex StudyGroup object construction |
| Prototype | Cloning reusable group templates |
| Singleton | Single database connection instance |

---

## Quality Metrics

| Metric | Value |
|---|---|
| Java Source Files | 67 |
| Pattern Classes | 31 |
| Test Suites | 7 |
| Unit Tests | 35 |
| Test Pass Rate | 100% |

---

# Repository Layer

The repository layer provides a persistence abstraction using in-memory implementations and future-proofing stubs.

## Features

- Generic repository interface
- CRUD operations
- In-memory implementations
- Repository factory
- Database and filesystem stubs

---

## Repository Tests

| Test Class | Tests |
|---|---|
| InMemoryUserRepositoryTest | 9 |
| InMemoryStudyGroupRepositoryTest | 6 |
| InMemoryMembershipRepositoryTest | 5 |
| InMemoryStudySessionRepositoryTest | 4 |
| RepositoryFactoryTest | 8 |

---

# Service Layer and REST API

The project includes a complete service layer and REST API implementation.

---

## Service Layer

| Service | Responsibility |
|---|---|
| UserService | User management |
| StudyGroupService | Group management |
| StudySessionService | Session scheduling |

---

## REST API Endpoints

### Users

```text
/api/users
```

### Study Groups

```text
/api/groups
```

### Study Sessions

```text
/api/sessions
```

---

## Business Rules Enforced

- Unique email validation
- Unique group name validation
- Creator-only deletion
- Future session validation
- Session ownership checks

---

## Test Coverage

| Layer | Tests |
|---|---|
| Creational Patterns | 25 |
| Repository Layer | 41 |
| Service Layer | 29 |
| Controller Layer | 9 |
| Total Tests | 104 |

All tests pass successfully with zero failures.

---

# CI/CD Pipeline

The project uses GitHub Actions to automate testing and deployment preparation.

---

## Continuous Integration (CI)

The CI workflow automatically:
- Runs on pushes to all branches
- Runs on pull requests to `main`
- Executes all unit and integration tests
- Prevents merges when tests fail

Workflow file:

```text
.github/workflows/ci.yml
```

---

## Continuous Deployment (CD)

When code is merged into the `main` branch:
- The backend application is built
- A JAR artifact is generated
- The artifact is uploaded automatically through GitHub Actions

---

# Branch Protection Rules

The repository uses GitHub branch protection rules on the `main` branch.

Configured rules:
- Require pull request reviews
- Require status checks to pass
- Prevent direct pushes to `main`

Additional details:
- [PROTECTION.md](./PROTECTION.md)

---

# Project Management

## GitHub Projects Kanban Board

### Live Board

[StudySync — Sprint 1 Kanban Board](https://github.com/users/Keitudimps/projects/2)

---

## Board Preview

![StudySync Sprint 1 Kanban Board](./Kanban_board.png)

---

## Board Structure

| Column | Purpose |
|---|---|
| To Do | Planned sprint tasks |
| In Progress | Tasks currently under development |
| Blocked | Tasks waiting for dependencies or fixes |
| Testing | Tasks pending verification |
| Done | Completed and verified tasks |

---

## Issue Labels

| Label | Purpose |
|---|---|
| sprint-1 | Sprint 1 tasks |
| backend | Backend development |
| frontend | Frontend development |
| security | Security implementation |
| database | Database tasks |
| testing | Testing tasks |

---

# Current Project Status

| Area | Status |
|---|---|
| Domain Model | Completed |
| Design Patterns | Completed |
| Repository Layer | Completed |
| Service Layer | Completed |
| REST API | Completed |
| Unit Testing | Completed |
| CI/CD Pipeline | Completed |
| Branch Protection | Completed |

Build Status: Passing

---

# Project Reflection

A cumulative reflection document is maintained throughout the project lifecycle.

| Document | Coverage |
|---|---|
| [REFLECTION.md](./REFLECTION.md) | Reflections, challenges, and lessons learned throughout development |

---

# Author

| Field | Detail |
|---|---|
| Name | Fereshteh Keitumetse Gomolemo Dimpe |
| Student Number | 221806229 |
| Course | Software Engineering |
| Institution | Cape Peninsula University of Technology |
| Repository | [github.com/Keitudimps/StudySync](https://github.com/Keitudimps/StudySync) |

---

# License

This project was developed for academic and educational purposes.

---

# Conclusion

StudySync demonstrates modern software engineering practices including layered architecture, design patterns, repository abstraction, REST API development, automated testing, Agile project management, and CI/CD workflow automation using GitHub Actions.
