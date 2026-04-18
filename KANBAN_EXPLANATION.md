# KANBAN_EXPLANATION.md — Kanban Board Definition and Purpose
## StudySync: Study Group Finder System

---

## 1. What is a Kanban Board?

A Kanban board is a visual project management tool that represents the current state of all work in a system at a single glance. The word "Kanban" comes from Japanese and means "visual signal" or "card" — the original Kanban system was used in Toyota's manufacturing plants in the 1940s to signal when parts needed to be replenished on an assembly line. In software development, the same principle applies: work items move through defined stages, and the board makes the state of every item visible to everyone involved.

At its core, a Kanban board consists of:
- **Columns** — each representing a stage in the workflow (e.g., To Do, In Progress, Testing, Done)
- **Cards** — each representing a single unit of work (a task, user story, or bug)
- **WIP Limits** — rules that cap how many cards can be in any given column at one time

---

## 2. StudySync's Kanban Board

### 2.1 Board Columns and Their Purpose

The StudySync Kanban board uses five columns, derived from the Automated Kanban template with two custom additions:

| Column | Purpose | WIP Limit |
|---|---|---|
| **To Do** | All tasks from the current sprint that have not yet been started. Cards enter here from the backlog at the start of each sprint. | No limit |
| **In Progress** | Tasks currently being actively worked on. A task moves here when development begins. | 3 tasks maximum |
| **Blocked** | *(Custom)* Tasks that cannot proceed due to a dependency, unresolved question, or technical blocker. Moving a task here makes the problem visible and prevents it from hiding inside "In Progress". | 2 tasks maximum |
| **Testing** | *(Custom)* Tasks where coding is complete but verification is still needed — API testing, UI walkthrough, or unit test execution. This column enforces the Definition of Done. | 3 tasks maximum |
| **Done** | Tasks that have been fully verified and meet the Definition of Done. No further action required. | No limit |

### 2.2 How the Board Visualises Workflow

Each card on the board represents one task from AGILE_PLANNING.md (T-001 to T-025). The column a card sits in tells you — at a glance — exactly where that piece of work stands in the development lifecycle. There is no need to ask "is this done?" or "who is working on what?" — the board answers those questions visually.

For example, during Sprint 1:
- T-001 (Create User JPA entity) starts in **To Do**
- When development begins, it moves to **In Progress**
- If a database migration error blocks progress, it moves to **Blocked** with a comment explaining the blocker
- Once the entity is coded and the migration runs cleanly, it moves to **Testing**
- After a manual database check confirms the table is correctly created with constraints, it moves to **Done**

This flow is visible to anyone viewing the board at any moment. Nothing is hidden inside a developer's head or a private chat.

### 2.3 How WIP Limits Prevent Bottlenecks

Work-in-progress (WIP) limits are the most important discipline mechanism in Kanban. The rule is simple: a column cannot hold more cards than its WIP limit allows. If the "In Progress" column has a WIP limit of 3 and already contains 3 cards, no new card can enter that column until one moves forward.

**Why this matters for StudySync:**
As a solo developer, the temptation is to start multiple tasks simultaneously — begin the backend endpoint, then switch to the React component, then start the database migration, all at once. Without WIP limits, all three end up "In Progress" indefinitely, creating the illusion of productivity while nothing actually reaches Done.

The WIP limit of 3 for "In Progress" enforces a discipline: finish what you started before picking up something new. This principle — "stop starting, start finishing" — is the core value of WIP limits in Kanban.

The "Blocked" column has the tightest limit (2) because having more than 2 blocked tasks simultaneously is a signal that something is systemically wrong — either the requirements are unclear, or there is a dependency that needs to be resolved before development can continue. A hard limit of 2 forces those issues to be addressed rather than accumulated.

### 2.4 How the Board Supports Agile Principles

| Agile Principle | How the StudySync Board Supports It |
|---|---|
| **Continuous delivery of working software** | The Testing column ensures every task is verified before Done. Done truly means working and verified. |
| **Welcome changing requirements** | Cards in To Do can be re-ordered or replaced at the start of any sprint without disrupting In Progress work. |
| **Simplicity — maximising work not done** | WIP limits force focus. Cards that would have been started and abandoned stay in To Do until there is capacity. |
| **Regular reflection and adaptation** | The Blocked column surfaces impediments visually. At any sprint review, the number of Blocked cards is a direct measure of process health. |
| **Sustainable development pace** | WIP limits prevent overloading. If In Progress is full, the signal is clear: slow down and complete what is started. |

---

## 3. GitHub-Specific Features Used

| Feature | How It Is Used in StudySync |
|---|---|
| **Issues as cards** | Each task (T-001 to T-025) is a GitHub Issue. Linked to its parent user story via `Closes #issue_number`. |
| **Labels** | Issues labelled: `sprint-1`, `must-have`, `should-have`, `backend`, `frontend`, `testing`, `blocked` |
| **Milestones** | Sprint 1 milestone groups all Sprint 1 issues with a 2-week due date |
| **Assignees** | All tasks assigned to the solo developer using @mention |
| **Automation** | Issues auto-move to In Progress when opened; auto-move to Done when closed — reducing manual overhead |
| **Linked PRs** | Pull requests reference their issue (`Fixes #T-001`) so merging a PR automatically closes the issue and moves the card to Done |

---

## 4. Board at a Glance — Sprint 1 Initial State

At the start of Sprint 1, the board looks like this:

| To Do | In Progress | Blocked | Testing | Done |
|---|---|---|---|---|
| T-001 Create User entity | — | — | — | — |
| T-002 Register endpoint | — | — | — | — |
| T-003 Email validation | — | — | — | — |
| T-004 Register UI | — | — | — | — |
| T-005 AuthService tests | — | — | — | — |
| T-006 Login endpoint | — | — | — | — |
| T-007 JWT Security Filter | — | — | — | — |
| T-008 Login UI | — | — | — | — |
| T-009 Axios interceptor | — | — | — | — |
| T-010 Auth Context | — | — | — | — |
| T-011 Course entities | — | — | — | — |
| T-012 Profile endpoint | — | — | — | — |
| T-013 Profile Setup UI | — | — | — | — |
| T-014 Register redirect | — | — | — | — |
| T-015 StudyGroup entity | — | — | — | — |
| T-016 Search endpoint | — | — | — | — |
| T-017 DB index on groups | — | — | — | — |
| T-018 Group Search UI | — | — | — | — |
| T-019 Full badge display | — | — | — | — |
| T-020 Membership entity | — | — | — | — |
| T-021 Join endpoint | — | — | — | — |
| T-022 Group Detail UI | — | — | — | — |
| T-023 Error states UI | — | — | — | — |
| T-024 BCrypt config | — | — | — | — |
| T-025 BCrypt verification | — | — | — | — |

All 25 tasks start in **To Do**. As Sprint 1 progresses, cards flow rightward through the columns toward Done.
