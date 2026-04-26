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

## Repository Structure

This repository serves as the complete project documentation repository for all assignments submitted throughout the semester. Each assignment builds upon the previous, maintaining full traceability from system specification through to domain modelling and implementation planning.

---

## Project Documentation

### Assignment 3 — System Specification and Architectural Modelling

> Defines the system scope, domain, problem statement, and complete C4 architectural diagrams.

| Document | Description |
|---|---|
| [SPECIFICATION.md](./SPECIFICATION.md) | System specification covering domain context, problem statement, individual scope justification, five core features, use cases, data model, and constraints |
| [ARCHITECTURE.md](./ARCHITECTURE.md) | C4 model diagrams at all four levels: System Context, Container, Component (backend and frontend), and Code (class relationships) |

---

### Assignment 4 — Stakeholder and System Requirements Documentation

> Identifies stakeholders and formally defines functional and non-functional requirements.

| Document | Description |
|---|---|
| [STAKEHOLDERS.md](./STAKEHOLDERS.md) | Analysis of seven stakeholders including roles, key concerns, pain points, success metrics, and identified trade-offs |
| [SRD.md](./SRD.md) | System Requirements Document containing twelve functional requirements with acceptance criteria, ten non-functional requirements across six quality categories, and a requirements traceability matrix |
| [REFLECTION.md](./REFLECTION.md) | Cumulative project reflection document — updated across all assignments |

---

### Assignment 5 — Use Case Diagrams, Specifications, and Test Cases

> Models system interactions through UML use case diagrams and detailed specifications.

| Document | Description |
|---|---|
| [USE_CASES.md](./USE_CASES.md) | UML use case diagram (SVG), eight detailed use case specifications with basic and alternative flows, fifteen functional test cases, and eight non-functional test cases |

---

### Assignment 6 — Agile User Stories, Backlog, and Sprint Planning

> Translates requirements into Agile user stories and defines the Sprint 1 development plan.

| Document | Description |
|---|---|
| [AGILE_PLANNING.md](./AGILE_PLANNING.md) | Twenty user stories traced to functional requirements and use cases, MoSCoW-prioritised product backlog with story point estimates, Sprint 1 plan with twenty-five tasks, and GitHub project setup guide |

---

### Assignment 7 — GitHub Project Templates and Kanban Board

> Evaluates GitHub project templates and implements a customised Kanban workflow.

| Document | Description |
|---|---|
| [TEMPLATE_ANALYSIS.md](./TEMPLATE_ANALYSIS.md) | Comparative analysis of four GitHub project templates with justification for the Automated Kanban selection and rationale for custom column additions |
| [KANBAN_EXPLANATION.md](./KANBAN_EXPLANATION.md) | Formal definition of Kanban methodology, explanation of the five-column board structure, WIP limit rationale, and alignment with Agile principles |

---

### Assignment 8 — Object State Modelling and Activity Workflow Modelling

> Models dynamic system behaviour through state transition and activity diagrams.

| Document | Description |
|---|---|
| [STATE_DIAGRAMS.md](./STATE_DIAGRAMS.md) | State transition diagrams for eight system objects: User Account, Academic Profile, Study Group, Membership, Join Request, Study Session, Admin Moderation Action, and Course Enrolment |
| [ACTIVITY_DIAGRAMS.md](./ACTIVITY_DIAGRAMS.md) | Activity workflow diagrams for eight system processes: User Registration, Login and Authentication, Create Study Group, Search and Join Group, Private Group Join Request, Schedule Study Session, Admin Group Moderation, and Edit Academic Profile |

---

### Assignment 9 — Domain Modelling and Class Diagram Development

> Defines the structural design of the system through domain entities and a UML class diagram.

| Document | Description |
|---|---|
| [DOMAIN_MODEL.md](./DOMAIN_MODEL.md) | Domain model documenting seven core entities with attributes, methods, inter-entity relationships, and ten formally stated business rules traced to functional requirements |
| [CLASS_DIAGRAM.md](./CLASS_DIAGRAM.md) | Full UML class diagram implemented in Mermaid.js, covering domain classes, service classes, enumerations, composition and association relationships, multiplicity constraints, and key design decision justifications |

---

## Project Management

### Kanban Board

The development workflow for this project is managed using a GitHub Projects Kanban board. The board is based on the Automated Kanban template and has been customised with two additional columns to enforce a quality gate before task completion.

**Live Board:** [StudySync — Sprint 1 Kanban Board](https://github.com/users/Keitudimps/projects/2)

![StudySync Sprint 1 Kanban Board](./Kanban_board.png)

#### Board Configuration

| Column | Classification | WIP Limit | Purpose |
|---|---|---|---|
| To Do | Default | Unlimited | Houses all tasks assigned to the current sprint that have not yet been initiated |
| In Progress | Default | 3 | Tracks tasks under active development; auto-populated when a linked issue is opened |
| Blocked | Custom | 2 | Isolates tasks that cannot progress due to unresolved dependencies or technical impediments, making blockers explicitly visible |
| Testing | Custom | 3 | Holds code-complete tasks pending manual verification; serves as the quality gate before a task is considered done |
| Done | Default | Unlimited | Contains fully verified tasks meeting the Definition of Done; auto-populated when a linked issue is closed |

#### Issue Labels

| Label | Scope |
|---|---|
| `sprint-1` | All issues assigned to Sprint 1 |
| `must-have` | MoSCoW Must-have user stories |
| `should-have` | MoSCoW Should-have user stories |
| `backend` | Tasks relating to Spring Boot API development |
| `frontend` | Tasks relating to React UI development |
| `security` | Tasks relating to authentication and authorisation |
| `database` | Tasks relating to JPA entities and database migrations |
| `testing` | Tasks relating to test case execution and verification |

---

## Technology Stack

| Layer | Technology |
|---|---|
| Frontend | React 18, Axios, React Router v6, TailwindCSS |
| Backend | Java 17, Spring Boot 3, Spring Security, Spring Data JPA |
| Database | PostgreSQL 15 |
| Authentication | JSON Web Tokens (JWT) — HMAC-SHA256 |
| Deployment | Vercel (Frontend), Railway (Backend and Database) |
| API Architecture | RESTful — JSON over HTTP/HTTPS |

---

## Project Reflection

A cumulative reflection document is maintained throughout the project, covering challenges and lessons learned at each stage of development. This document is updated with each assignment submission.

| Document | Coverage |
|---|---|
| [REFLECTION.md](./REFLECTION.md) | Stakeholder trade-offs (Assignment 4), use case modelling challenges (Assignment 5), Agile estimation and solo development (Assignment 6), Kanban template selection and tool comparison (Assignment 7), state and activity diagram granularity (Assignment 8), domain modelling and object-oriented design (Assignment 9) |

---

## Author

| Field | Detail |
|---|---|
| **Name(s)** | Fereshteh Keitumetse Gomolemo Dimpe |
| **Student Number** | 221806229 |
| **Course** | Software Engineering |
| **Institution** | CPUT |
| **Submission Period** | Assignments 3 through 9 |
| **Repository** | [github.com/Keitudimps/StudySync](https://github.com/Keitudimps/StudySync) |




