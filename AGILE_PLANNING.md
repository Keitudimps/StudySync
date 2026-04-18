# AGILE_PLANNING.md — Agile User Stories, Backlog & Sprint Planning
## StudySync: Study Group Finder System

---

## 1. Introduction

This document applies Agile methodology to the StudySync project. It translates the functional requirements from Assignment 4 and the use cases from Assignment 5 into user stories, organises them into a prioritised product backlog using MoSCoW, and defines the first sprint plan toward a Minimum Viable Product (MVP).

### Traceability Map

| Source Document | Feeds Into |
|---|---|
| SRD.md (FR-01 to FR-12) | User Stories US-001 to US-012 |
| USE_CASES.md (UC-01 to UC-08) | User Stories US-013 to US-016 |
| STAKEHOLDERS.md | Story roles and acceptance criteria |
| ARCHITECTURE.md | Task breakdown (API endpoints, components) |

---

## 2. User Stories

> Format: *"As a [role], I want [action] so that [benefit]."*  
> All stories follow the **INVEST** criteria: Independent, Negotiable, Valuable, Estimable, Small, Testable.

### 2.1 Functional User Stories (from Assignment 4 FRs)

| Story ID | User Story | Linked FR | Priority | Acceptance Criteria |
|---|---|---|---|---|
| US-001 | As a **new student**, I want to register with my university email and password so that I can access the StudySync platform. | FR-01 | High | Account created within 1s. Duplicate email returns HTTP 409. Password stored as BCrypt hash. JWT token returned on success. |
| US-002 | As a **student**, I want to log in with my email and password so that I can access my groups and sessions securely. | FR-02 | High | Valid credentials return JWT within 500ms. Wrong credentials return HTTP 401. Deactivated accounts return HTTP 403. |
| US-003 | As a **new student**, I want to set up my academic profile with my year of study and enrolled courses so that my group recommendations are relevant to me. | FR-03 | High | Profile saved within 500ms. Enrolled courses visible on profile page. At least one course must be selected. |
| US-004 | As a **student**, I want to edit my academic profile at any time so that I can update my courses when a new semester starts. | FR-03 | Medium | Profile updates reflected immediately. Year of study constrained to 1–4. Changes persist after logout. |
| US-005 | As a **student**, I want to create a study group linked to a course so that I can organise peer study around my specific modules. | FR-04 | High | Group created with name, course, capacity, and privacy. Creator added as first ACTIVE member. Public group appears in search within 1s. |
| US-006 | As a **student**, I want to search for public study groups by course code or keyword so that I can quickly find groups relevant to my studies. | FR-05 | High | Search returns results in under 1s. Results show name, course, member count. Full groups display a "Full" badge. |
| US-007 | As a **student**, I want to instantly join a public study group so that I can start collaborating without waiting for approval. | FR-06 | High | Membership created with ACTIVE status within 500ms. Blocked if group is full (HTTP 400) or student already in 5 groups (HTTP 400). |
| US-008 | As a **student**, I want to send a join request to a private group so that I can request access to groups that require approval. | FR-07 | Medium | Membership record created with PENDING status. Request visible in creator's panel within 1s. |
| US-009 | As a **group creator**, I want to approve or reject join requests so that I can control who joins my private study group. | FR-07 | Medium | Approving sets status to ACTIVE within 500ms. Rejecting deletes the record. Only the creator can perform this action (HTTP 403 for others). |
| US-010 | As a **student**, I want to schedule a study session with a date, time, location, and optional notes so that all group members know when and where to meet. | FR-09 | High | Session created and visible to all members within 1s. Past-date sessions rejected with HTTP 400. Title field is required. |
| US-011 | As a **student**, I want to view all upcoming and past sessions for my groups so that I can plan my study schedule effectively. | FR-10 | High | Sessions load within 1s. Upcoming sorted ascending, past sorted descending. Each entry shows title, date, duration, location, creator. |
| US-012 | As a **platform administrator**, I want to deactivate or reactivate student accounts so that I can enforce platform policies and remove bad actors. | FR-11 | Medium | Deactivation takes effect immediately. Deactivated user receives HTTP 403 on login. Admin can search users by name or email. |
| US-013 | As a **platform administrator**, I want to view and permanently delete any study group so that I can moderate inappropriate or abandoned content. | FR-12 | Medium | Deletion completes within 1s. Cascade removes all memberships and sessions. Group no longer appears in search. |

### 2.2 User Stories from Use Cases (Assignment 5)

| Story ID | User Story | Linked UC | Priority | Acceptance Criteria |
|---|---|---|---|---|
| US-014 | As a **student**, I want to leave a group I no longer need so that my group slot is freed up for another group. | UC-06 (extend) | Medium | Membership record deleted within 500ms. Member count decrements immediately. Student no longer sees group in "My Groups". |
| US-015 | As a **group creator**, I want to remove a member from my group so that I can manage group quality and remove inactive participants. | UC-06 (extend) | Medium | Membership record deleted within 500ms. Removed member loses access to group sessions. Only creator can remove members (HTTP 403 for others). |
| US-016 | As a **group creator**, I want to cancel or edit a scheduled session so that the group is informed when plans change. | UC-08 (extend) | Medium | Session updated or deleted within 500ms. Changes visible to all members immediately. Only the session creator can edit or cancel. |
| US-017 | As a **lecturer**, I want to browse public study groups linked to my course codes so that I can observe how students are self-organising around my module. | UC-05 | Low | Lecturer can search groups without joining. No create, join, or manage options shown. Read-only access confirmed. |

### 2.3 Non-Functional User Stories

| Story ID | User Story | Linked NFR | Priority | Acceptance Criteria |
|---|---|---|---|---|
| US-018 | As a **system**, I want all passwords to be stored as BCrypt hashes with cost factor 10 so that user credentials are protected in the event of a data breach. | NFR-SE1 | High | All `password_hash` values in DB begin with `$2a$10$`. No plain text password appears in any log file. |
| US-019 | As a **student**, I want all API calls to respond within 500ms under normal load so that the platform feels fast and responsive. | NFR-P1 | High | JMeter load test with 100 concurrent users shows p95 ≤ 500ms on GET /api/groups. |
| US-020 | As an **IT staff member**, I want the system to be deployable on Vercel and Railway using only a GitHub repo so that setup takes under 30 minutes without manual server configuration. | NFR-D1 | Medium | New developer deploys full system from scratch following README in under 30 minutes. No secrets hardcoded in source. |

---

## 3. Product Backlog

> Prioritised using **MoSCoW**: Must-have, Should-have, Could-have, Won't-have.
> Effort estimated using **Fibonacci story points**: 1, 2, 3, 5, 8, 13.

### 3.1 Backlog Table

| Story ID | User Story (Summary) | MoSCoW | Story Points | Dependencies | Justification |
|---|---|---|---|---|---|
| US-001 | Register with university email | Must-have | 3 | None | Entry point to the entire system. No other story is possible without this. |
| US-002 | Login and receive JWT | Must-have | 2 | US-001 | All protected features require authentication. Core security requirement. |
| US-003 | Setup academic profile | Must-have | 3 | US-001, US-002 | Profile data (courses) drives group relevance. Required for meaningful group creation and search. |
| US-005 | Create a study group | Must-have | 5 | US-002, US-003 | Core value proposition of the system. Without groups, nothing else functions. |
| US-006 | Search public groups | Must-have | 3 | US-002 | Primary discovery mechanism. Directly addresses the student pain point from stakeholder analysis. |
| US-007 | Join a public group | Must-have | 3 | US-002, US-006 | Completing the group joining flow delivers the MVP's core loop: find → join → collaborate. |
| US-010 | Schedule a study session | Must-have | 5 | US-007 | Sessions are what make groups actionable. Without sessions, groups have no output. |
| US-011 | View upcoming and past sessions | Must-have | 2 | US-010 | Students must be able to see what sessions exist. Tightly coupled to scheduling. |
| US-018 | BCrypt password hashing | Must-have | 1 | US-001 | Security baseline. Cannot ship registration without this. Zero additional effort given Spring Security defaults. |
| US-019 | API response under 500ms | Must-have | 2 | All backend stories | Performance NFR affects all endpoints. Must be validated before any release. |
| US-004 | Edit academic profile | Should-have | 2 | US-003 | Valuable for returning students each semester but not required for the initial MVP experience. |
| US-008 | Send join request to private group | Should-have | 3 | US-002, US-005 | Adds group privacy control, important for group quality but not blocking the core open-group flow. |
| US-009 | Approve or reject join requests | Should-have | 3 | US-008 | Companion to US-008. Together they complete the private group workflow. |
| US-014 | Leave a group | Should-have | 2 | US-007 | Important for managing the 5-group limit but the system is functional without it initially. |
| US-015 | Creator removes a member | Should-have | 2 | US-007, US-009 | Group quality control for creators. Valuable but not MVP-blocking. |
| US-016 | Cancel or edit a session | Should-have | 3 | US-010 | Practical necessity once sessions exist, but not required before sessions are created. |
| US-012 | Admin deactivates user accounts | Should-have | 3 | US-002 | Needed for platform governance, but admin features are not MVP for the student-facing experience. |
| US-013 | Admin deletes groups | Should-have | 3 | US-005 | Important for moderation but not blocking early user testing. |
| US-020 | Deploy on Vercel and Railway | Should-have | 3 | All stories | Required for public access but local development works for the sprint period. |
| US-017 | Lecturer browses public groups | Could-have | 1 | US-006 | Lecturer is an indirect, read-only actor. Minimal extra effort but low urgency. |
| — | In-app notification system | Won't-have | — | — | Explicitly removed from scope. Adds significant complexity (polling/WebSockets) with limited MVP value. |
| — | Group chat / messaging | Won't-have | — | — | Out of scope for v1. Would require a separate real-time infrastructure layer. |
| — | SSO / university login integration | Won't-have | — | — | Deferred to v2. Requires coordination with university IT systems. |
| — | File sharing within groups | Won't-have | — | — | Requires storage infrastructure. Not part of the core collaboration value. |

### 3.2 Prioritisation Justification

**Must-have stories** (US-001 to US-011, US-018, US-019) form the absolute MVP — the minimum set of functionality that delivers the core value: a student can register, find a group, join it, and see when sessions are happening. Without any one of these stories, the system does not function as intended. These map directly to the top-priority stakeholder success metrics defined in STAKEHOLDERS.md.

**Should-have stories** add the depth and governance needed for real-world use. Private groups, session management, admin tools, and deployment are all necessary for a production-grade release but do not block initial user testing of the core flow.

**Could-have stories** (US-017) require almost no extra development effort since the search endpoint already exists — the lecturer just needs read-only access without join buttons. It is deferred only because it has no user-facing urgency in the MVP.

**Won't-have stories** were deliberately scoped out. Notifications and chat were removed following the stakeholder trade-off analysis in REFLECTION.md — they would roughly double the system complexity for features that are not part of the core study group coordination value proposition.

---

## 4. Sprint Planning

### 4.1 Sprint 1 Goal

> **"Deliver a working authentication and group discovery flow — a student can register, log in, set up their profile, search for public groups, and join one."**

This sprint delivers the complete MVP user journey from zero to being inside a group. It is the foundation that every subsequent sprint builds on. By the end of Sprint 1, the system will be demonstrable end-to-end to a real user.

**Sprint Duration:** 2 weeks
**Sprint Number:** 1
**Team:** Solo developer (full-stack)

---

### 4.2 Selected Sprint 1 Stories

| Story ID | Summary | Story Points | Reason for inclusion |
|---|---|---|---|
| US-001 | Register with university email | 3 | Entry point — nothing works without accounts |
| US-002 | Login and receive JWT | 2 | Required by all protected endpoints |
| US-003 | Setup academic profile | 3 | Needed for course-based group relevance |
| US-006 | Search public groups by course / keyword | 3 | Core discovery — the main student pain point |
| US-007 | Join a public group | 3 | Completes the core loop: find → join |
| US-018 | BCrypt password hashing | 1 | Security baseline, zero extra effort |
| **Total** | | **15 points** | Within solo developer 2-week capacity |

---

### 4.3 Sprint 1 Backlog — Task Breakdown

#### US-001: Register with university email

| Task ID | Task Description | Assigned To | Est. Hours | Status |
|---|---|---|---|---|
| T-001 | Create `User` JPA entity and PostgreSQL migration | Developer | 2 | To Do |
| T-002 | Implement `POST /api/auth/register` endpoint with BCrypt hashing | Developer | 3 | To Do |
| T-003 | Add email format and duplicate validation with error responses | Developer | 2 | To Do |
| T-004 | Build Register page UI in React with form validation | Developer | 3 | To Do |
| T-005 | Write unit tests for AuthService.register() | Developer | 2 | To Do |

#### US-002: Login and receive JWT

| Task ID | Task Description | Assigned To | Est. Hours | Status |
|---|---|---|---|---|
| T-006 | Implement `POST /api/auth/login` with JWT generation | Developer | 3 | To Do |
| T-007 | Configure Spring Security JWT filter (OncePerRequestFilter) | Developer | 3 | To Do |
| T-008 | Build Login page UI with error handling | Developer | 2 | To Do |
| T-009 | Implement Axios interceptor to attach JWT to all requests | Developer | 2 | To Do |
| T-010 | Implement Auth Context in React (login/logout/currentUser) | Developer | 2 | To Do |

#### US-003: Setup academic profile

| Task ID | Task Description | Assigned To | Est. Hours | Status |
|---|---|---|---|---|
| T-011 | Create `Course` and `UserCourse` entities and seed course data | Developer | 2 | To Do |
| T-012 | Implement `PUT /api/users/profile` endpoint | Developer | 2 | To Do |
| T-013 | Build Profile Setup page UI with course multi-select | Developer | 3 | To Do |
| T-014 | Add redirect from Register to Profile Setup on first login | Developer | 1 | To Do |

#### US-006: Search public groups

| Task ID | Task Description | Assigned To | Est. Hours | Status |
|---|---|---|---|---|
| T-015 | Create `StudyGroup` JPA entity and PostgreSQL migration | Developer | 2 | To Do |
| T-016 | Implement `GET /api/groups?query=` search endpoint with course/keyword filter | Developer | 3 | To Do |
| T-017 | Add database index on `course_id` and `name` columns for search performance | Developer | 1 | To Do |
| T-018 | Build Group Search page UI with search bar and results list | Developer | 3 | To Do |
| T-019 | Display member count, capacity, and "Full" badge on results | Developer | 2 | To Do |

#### US-007: Join a public group

| Task ID | Task Description | Assigned To | Est. Hours | Status |
|---|---|---|---|---|
| T-020 | Create `Membership` JPA entity and PostgreSQL migration | Developer | 2 | To Do |
| T-021 | Implement `POST /api/groups/{id}/join` with capacity and limit checks | Developer | 3 | To Do |
| T-022 | Build Group Detail page with "Join Group" / "Leave Group" button | Developer | 3 | To Do |
| T-023 | Handle error states: full group, already member, 5-group limit | Developer | 2 | To Do |

#### US-018: BCrypt password hashing (security baseline)

| Task ID | Task Description | Assigned To | Est. Hours | Status |
|---|---|---|---|---|
| T-024 | Configure BCryptPasswordEncoder bean in Spring Security config | Developer | 1 | To Do |
| T-025 | Verify no plain text passwords appear in logs or DB (manual check) | Developer | 1 | To Do |

---

### 4.4 Sprint 1 Summary

| Metric | Value |
|---|---|
| Total story points | 15 |
| Total estimated hours | ~56 hours |
| Sprint duration | 2 weeks (10 working days) |
| Average hours per day | ~5.6 hours |
| Stories included | 6 |
| Stories deferred | US-004, US-008–US-017, US-019, US-020 |

**Definition of Done for Sprint 1:**
- All tasks marked Done in the GitHub Project board
- Register, Login, and Profile Setup work end-to-end in the browser
- A student can search for groups and see results
- A student can join a public group and see their membership reflected
- All BCrypt hashing confirmed via DB inspection
- No critical bugs open in GitHub Issues

---

## 5. GitHub Project Setup Instructions

> This section explains how to implement this Agile plan using free GitHub tools.

### Step 1 — Create GitHub Issues (User Stories)
1. Go to your `StudySync` repo → **Issues** → **New Issue**
2. Create one issue per user story (US-001 to US-020)
3. Title format: `[US-001] Register with university email`
4. In the body: paste the full user story, acceptance criteria, and story points
5. Add labels: `must-have`, `should-have`, `could-have`, `sprint-1`, `frontend`, `backend`

### Step 2 — Create a GitHub Project Board
1. Go to repo → **Projects** → **New Project** → choose **Board** template
2. Name it: `StudySync Sprint Board`
3. Create columns: `Backlog`, `To Do`, `In Progress`, `Done`
4. Add all issues to the board
5. Drag Sprint 1 stories (US-001, 002, 003, 006, 007, 018) into **To Do**

### Step 3 — Create a Milestone for Sprint 1
1. Go to **Issues** → **Milestones** → **New Milestone**
2. Name: `Sprint 1 — Auth and Group Discovery MVP`
3. Due date: 2 weeks from start
4. Assign all Sprint 1 issues to this milestone

### Step 4 — Create Issues for Tasks
1. For each task (T-001 to T-025), create a GitHub Issue
2. Link each task issue to its parent user story using `Closes #issue_number` in the body
3. Assign tasks to yourself
4. Move tasks to **In Progress** as you work, **Done** when complete

---

## 6. Reflection — Challenges in Agile Planning as a Solo Developer

### 6.1 Introduction

Agile methodology is designed for teams — a product owner sets priorities, a scrum master facilitates, developers estimate, and testers validate. Applying this framework as a single person means playing every role simultaneously, which creates a specific and unusual set of tensions that this reflection explores honestly.

### 6.2 Challenge 1: Being Both Product Owner and Developer

The hardest part of solo Agile is that the product owner and the developer are the same person. A product owner's job is to ruthlessly prioritise — to say "this feature doesn't make the sprint" and hold that line. A developer's instinct is often the opposite: to build everything, to not leave things undone, to add "just one more feature."

Writing the MoSCoW prioritisation for this backlog exposed this conflict directly. Notifications felt important to build — they make the system feel alive. But as a product owner, the honest answer is that notifications are not what delivers core value in the MVP. A student can find and join a group without any notifications. Sitting with that discomfort and keeping notifications in the "Won't-have" column despite the developer urge to include them was the most important discipline this assignment required.

### 6.3 Challenge 2: Story Point Estimation Without Historical Data

Fibonacci story points are meant to be relative estimates calibrated against a team's past velocity. A team that has delivered sprints before knows what a "3-point story" feels like in practice. As a solo developer on a brand new project, there is no velocity data to anchor estimates against.

The estimates in this plan are therefore educated guesses based on technical knowledge of the stack — knowing roughly how long a Spring Boot endpoint takes to build versus a React page with form validation. US-001 (Register) is estimated at 3 points because it involves an entity, an endpoint, validation logic, password hashing, and a UI form — that is more work than US-002 (Login, 2 points) which reuses the same entity and just needs credential checking and JWT generation. This relative reasoning is the best available substitute for historical velocity.

### 6.4 Challenge 3: Defining "Done" Without a QA Team

In a real Scrum team, a Definition of Done includes code review by peers, QA sign-off, and sometimes a product owner demo. Solo, all of those gates collapse into one person. The risk is that "done" becomes "I think it works" rather than "it has been independently verified."

To compensate, the Definition of Done for Sprint 1 was made deliberately concrete and checkable: specific endpoints must return specific HTTP codes, BCrypt hashing must be confirmed via direct database inspection, and the full user flow must be walkable in a browser. Making the criteria observable rather than subjective is the solo equivalent of peer review.

### 6.5 Challenge 4: Scope Creep Resistance

Working alone without a scrum master means there is no one to enforce the sprint scope. Every time a new idea emerged during planning — "should I add the session scheduling to Sprint 1?" or "the profile edit is small, I could squeeze it in" — there was no external check on that impulse.

The discipline applied here was to ask one question: does this story contribute to the Sprint Goal? The Sprint 1 goal is specifically about authentication and group discovery. Session scheduling (US-010) is genuinely important but it does not contribute to a student being able to find and join a group. It belongs in Sprint 2. Keeping the sprint goal statement sharp and referring back to it every time scope creep tempted was the most useful practical tool.

### 6.6 Challenge 5: Traceability Across Five Assignments

Each assignment in this project builds on the last, and by Assignment 6, the requirement is that every user story traces back to a functional requirement (Assignment 4) and a use case (Assignment 5). Maintaining this traceability while also doing the Agile planning work — writing stories, estimating, building the sprint — required constant cross-referencing between documents.

The most common mistake discovered during this process was writing a user story that did not map cleanly to any existing requirement. For example, the first draft of US-016 (Cancel or edit session) was written before checking whether FR-09 explicitly covered cancellation — it did, but only as a single line. That mismatch required going back and verifying the SRD. This kind of back-and-forth is exactly what Agile's iterative nature is meant to support: requirements are living documents, and user stories are how those requirements get refined into actionable work.

### 6.7 Conclusion

Solo Agile is not really Agile in its intended form — it is a discipline framework applied by one person to avoid the chaos of unstructured solo development. The value is not in the ceremonies (there is no standup when you are alone) but in the artifacts: a prioritised backlog forces hard decisions, story points create a shared language between "what I want" and "what is realistic," and a sprint goal keeps the work focused. Done honestly, these tools make solo development significantly more intentional.
