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
2. [Source Code Navigation](#source-code-navigation)
3. [Quick Links](#quick-links)
4. [Technology Stack](#technology-stack)
5. [Running the Project](#running-the-project)
6. [Project Documentation](#project-documentation)
7. [Design Patterns Implementation](#design-patterns-implementation)
8. [Repository Layer](#repository-layer)
9. [Service Layer and REST API](#service-layer-and-rest-api)
10. [CI/CD Pipeline](#cicd-pipeline)
11. [Project Management](#project-management)
12. [Current Project Status](#current-project-status)
13. [Project Reflection](#project-reflection)
14. [Author](#author)

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

# Source Code Navigation

## Main Application

| Component | Link |
|---|---|
| Main Application | [StudySyncApplication.java](./backend/src/main/java/com/studysync/StudySyncApplication.java) |

---

## Backend Packages

| Package | Link |
|---|---|
| Domain Package | [domain/](./backend/src/main/java/com/studysync/domain/) |
| Repository Package | [repository/](./backend/src/main/java/com/studysync/repository/) |
| Service Package | [service/](./backend/src/main/java/com/studysync/service/) |
| Controller Package | [controller/](./backend/src/main/java/com/studysync/controller/) |
| DTO Package | [dto/](./backend/src/main/java/com/studysync/dto/) |
| Creational Package | [creational/](./backend/src/main/java/com/studysync/creational/) |

---

## Domain Layer

| Class | Link |
|---|---|
| User | [User.java](./backend/src/main/java/com/studysync/domain/User.java) |
| StudyGroup | [StudyGroup.java](./backend/src/main/java/com/studysync/domain/StudyGroup.java) |
| Membership | [Membership.java](./backend/src/main/java/com/studysync/domain/Membership.java) |
| StudySession | [StudySession.java](./backend/src/main/java/com/studysync/domain/StudySession.java) |
| Course | [Course.java](./backend/src/main/java/com/studysync/domain/Course.java) |

---

## Repository Layer

| Repository | Link |
|---|---|
| UserRepository | [UserRepository.java](./backend/src/main/java/com/studysync/repository/UserRepository.java) |
| StudyGroupRepository | [StudyGroupRepository.java](./backend/src/main/java/com/studysync/repository/StudyGroupRepository.java) |
| MembershipRepository | [MembershipRepository.java](./backend/src/main/java/com/studysync/repository/MembershipRepository.java) |
| StudySessionRepository | [StudySessionRepository.java](./backend/src/main/java/com/studysync/repository/StudySessionRepository.java) |
| RepositoryFactory | [RepositoryFactory.java](./backend/src/main/java/com/studysync/factory/RepositoryFactory.java) |

---

## Service Layer

| Service | Link |
|---|---|
| UserService | [UserService.java](./backend/src/main/java/com/studysync/service/UserService.java) |
| StudyGroupService | [StudyGroupService.java](./backend/src/main/java/com/studysync/service/StudyGroupService.java) |
| StudySessionService | [StudySessionService.java](./backend/src/main/java/com/studysync/service/StudySessionService.java) |

---

## REST Controllers

| Controller | Link |
|---|---|
| UserController | [UserController.java](./backend/src/main/java/com/studysync/controller/UserController.java) |
| StudyGroupController | [StudyGroupController.java](./backend/src/main/java/com/studysync/controller/StudyGroupController.java) |
| StudySessionController | [StudySessionController.java](./backend/src/main/java/com/studysync/controller/StudySessionController.java) |

---

## DTO Layer

| DTO | Link |
|---|---|
| UserDTO | [UserDTO.java](./backend/src/main/java/com/studysync/dto/UserDTO.java) |
| StudyGroupDTO | [StudyGroupDTO.java](./backend/src/main/java/com/studysync/dto/StudyGroupDTO.java) |
| StudySessionDTO | [StudySessionDTO.java](./backend/src/main/java/com/studysync/dto/StudySessionDTO.java) |

---

## CI/CD and Configuration

| File | Link |
|---|---|
| GitHub Actions Workflow | [.github/workflows/ci.yml](./.github/workflows/ci.yml) |
| Branch Protection Documentation | [PROTECTION.md](./PROTECTION.md) |
| Maven Build File | [pom.xml](./backend/pom.xml) |

---

## Test Suites

| Test Class | Link |
|---|---|
| UserServiceTest | [UserServiceTest.java](./backend/src/test/java/com/studysync/service/UserServiceTest.java) |
| StudyGroupServiceTest | [StudyGroupServiceTest.java](./backend/src/test/java/com/studysync/service/StudyGroupServiceTest.java) |
| StudySessionServiceTest | [StudySessionServiceTest.java](./backend/src/test/java/com/studysync/service/StudySessionServiceTest.java) |
| UserControllerTest | [UserControllerTest.java](./backend/src/test/java/com/studysync/controller/UserControllerTest.java) |
| RepositoryFactoryTest | [RepositoryFactoryTest.java](./backend/src/test/java/com/studysync/repository/RepositoryFactoryTest.java) |
| GroupPrototypeTest | [GroupPrototypeTest.java](./backend/src/test/java/com/studysync/creational/GroupPrototypeTest.java) |
| StudyGroupBuilderTest | [StudyGroupBuilderTest.java](./backend/src/test/java/com/studysync/creational/StudyGroupBuilderTest.java) |

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

Source Directory:  
[backend/src/main/java/com/studysync/repository/](./backend/src/main/java/com/studysync/repository/)

The repository layer provides a persistence abstraction using in-memory implementations and future-proofing stubs.

## Features

- Generic repository interface
- CRUD operations
- In-memory implementations
- Repository factory
- Database and filesystem stubs

---

# Service Layer and REST API

Source Directory:  
[backend/src/main/java/com/studysync/service/](./backend/src/main/java/com/studysync/service/)

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
