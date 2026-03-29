# USE_CASES.md — Use Case Specifications
## StudySync: Study Group Finder System

---

## 1. Use Case Diagram

> UML Use Case Diagram (Mermaid – draw.io compatible)

usecaseDiagram

graph TD

%% ACTORS
NS[New Student]
S[Student]
GC[Group Creator]
L[Lecturer]
UA[University Admin]
PA[Platform Admin]

%% GENERALISATION
NS --|> S
GC --|> S

%% SYSTEM BOUNDARY
subgraph StudySync System

UC1(Register Account)
UC2(Login)
UC3(Setup Academic Profile)
UC4(Search and Discover Groups)
UC5(Join Public Group)
UC6(Request to Join Private Group)
UC7(Create Study Group)
UC8(Approve or Reject Join Requests)
UC9(Schedule Study Session)
UC10(View Study Sessions)
UC11(Manage Users)
UC12(Moderate Groups)

end

%% ACTOR RELATIONSHIPS
NS --> UC1

S --> UC2
S --> UC3
S --> UC4
S --> UC5
S --> UC6
S --> UC10

GC --> UC7
GC --> UC8
GC --> UC9

UA --> UC11
PA --> UC12

L --> UC4
L --> UC10

%% INCLUDE RELATIONSHIPS
UC5 ..> UC2:::include
UC7 ..> UC2:::include
UC9 ..> UC2:::include

%% EXTEND RELATIONSHIPS
UC6 ..> UC8:::extend

%% STYLE DEFINITIONS
classDef include stroke-dasharray: 5 5,fill:#fff,stroke:#000;
classDef extend stroke-dasharray: 5 5,fill:#fff,stroke:#000;


---

## 2. Actor and Relationship Explanation

### 2.1 Key Actors and Their Roles

| Actor | Role in the System |
|---|---|
| **Student** | Primary user. Can search, join groups, and participate in sessions. |
| **New Student** | A first-time user. Focused on registration and discovering groups. |
| **Group Creator** | A student who creates and manages study groups. |
| **Platform Administrator** |Full system control including moderation and platform integrity. |
| **University Administrator** | Manages users for institutional compliance. |
| **Lecturer** | Indirect user. Can browse public groups and sessions linked to their course codes to observe peer collaboration. |

### 2.2 Key Relationships

**Generalisation:**
- `New Student` → `Student`: A new student inherits all student capabilities.
- `Group Creator` → `Student`: A group creator is still a student with additional privileges.

**<<include>> relationships** (the included use case is always executed):
- `Join Public Group` includes `Login` — a user must be authenticated before joining.
- `Create Study Group` includes `Login` — authentication is mandatory.
- `Schedule Study Session` includes `Join Public Group` — a student must be a group member before scheduling.
- `Approve or Reject Join Request` includes `Create Study Group` — only the group creator can approve requests.
- `Setup Academic Profile` includes `Register Account` — profile setup follows immediately after registration.

**<<extend>> relationships** (the extending use case runs only under certain conditions):
- `Cancel or Edit Session` extends `Schedule Study Session` — only triggered if the creator chooses to modify an existing session.
- `Leave or Remove Member` extends `Join Public Group` — only relevant after membership exists.
- `View Platform Statistics` extends `Manage Users` — statistics are an optional additional view from the admin panel.

### 2.3 How the Diagram Addresses Assignment 4 Stakeholder Concerns

- The **Student** actor's access to `Search and Discover Groups` directly addresses the pain point that students currently rely on WhatsApp and notice boards.
- The **Group Creator**'s `Approve or Reject Join Request` use case addresses the concern of maintaining group quality for private groups.
- The **Platform Administrator**'s `Moderate Groups` and `Manage Users` use cases address the university administrator's concern about policy enforcement.
- The **New Student** generalisation ensures that first-year students are explicitly accounted for in the system's design — their primary use cases are registration and discovery, keeping the initial experience simple.
- The `<<include>>` of `Login` in all protected use cases directly implements the security NFR requiring JWT authentication on all protected endpoints.

---

## 3. Use Case Specifications

---

### UC-01: Register Account

| Field | Detail |
|---|---|
| **Use Case ID** | UC-01 |
| **Use Case Name** | Register Account |
| **Actor(s)** | Student, New Student |
| **Description** | A new user creates a StudySync account using their university email and a password. |
| **Preconditions** | The user does not have an existing account. The user has a valid university email address. |
| **Postconditions** | A new User record is created in the database. The user receives a JWT token and is redirected to the profile setup page. |
| **Linked FR** | FR-01 |

**Basic Flow:**
1. User navigates to the Register page.
2. User enters their full name, university email, and password.
3. User clicks the "Register" button.
4. System validates that the email format is correct and not already registered.
5. System hashes the password using BCrypt.
6. System creates the User record in the database.
7. System generates and returns a JWT token.
8. User is redirected to the Academic Profile Setup page (UC-03).

**Alternative Flows:**

| Step | Condition | System Response |
|---|---|---|
| 4 | Email already registered | Returns HTTP 409: "Email already registered." Form stays open. |
| 4 | Invalid email format | Returns HTTP 400: "Please enter a valid university email." |
| 2 | Password under 8 characters | Frontend validation error shown before submission. |

---

### UC-02: Login

| Field | Detail |
|---|---|
| **Use Case ID** | UC-02 |
| **Use Case Name** | Login |
| **Actor(s)** | Student, Group Creator, Platform Administrator |
| **Description** | An existing user authenticates using their email and password to receive a JWT token. |
| **Preconditions** | The user has a registered account. The account is not deactivated. |
| **Postconditions** | A valid JWT token is returned and stored client-side. The user is redirected to the Dashboard. |
| **Linked FR** | FR-02 |

**Basic Flow:**
1. User navigates to the Login page.
2. User enters their email and password.
3. User clicks "Login."
4. System validates the credentials against the database.
5. System checks that the account is active (not deactivated).
6. System generates a JWT token with a 24-hour expiry.
7. JWT is returned to the client and stored.
8. User is redirected to the Dashboard.

**Alternative Flows:**

| Step | Condition | System Response |
|---|---|---|
| 4 | Email not found | Returns HTTP 401: "Invalid email or password." |
| 4 | Password does not match | Returns HTTP 401: "Invalid email or password." |
| 5 | Account is deactivated | Returns HTTP 403: "Your account has been deactivated. Contact support." |

---

### UC-03: Setup Academic Profile

| Field | Detail |
|---|---|
| **Use Case ID** | UC-03 |
| **Use Case Name** | Setup Academic Profile |
| **Actor(s)** | Student, New Student |
| **Description** | After registering, the student selects their year of study and enrolled course codes to complete their academic profile. |
| **Preconditions** | UC-01 has been completed. The user is authenticated (valid JWT). |
| **Postconditions** | The user's year of study and enrolled courses are saved. The user is redirected to the Dashboard. |
| **Linked FR** | FR-03 |

**Basic Flow:**
1. System redirects the user to the Profile Setup page after registration.
2. User selects their year of study (1–4) from a dropdown.
3. User selects their enrolled courses from a searchable course list.
4. User clicks "Save Profile."
5. System saves the year of study and creates UserCourse records for each selected course.
6. User is redirected to the Dashboard.

**Alternative Flows:**

| Step | Condition | System Response |
|---|---|---|
| 3 | No courses selected | System shows a warning: "Please select at least one course." Submission is blocked. |
| 2 | Invalid year of study value | Frontend validation blocks the submission. |

---

### UC-04: Create Study Group

| Field | Detail |
|---|---|
| **Use Case ID** | UC-04 |
| **Use Case Name** | Create Study Group |
| **Actor(s)** | Group Creator (Student) |
| **Description** | An authenticated student creates a new study group linked to a course, with a chosen privacy setting and maximum capacity. |
| **Preconditions** | The user is authenticated. The user has completed their academic profile. |
| **Postconditions** | A new StudyGroup record is created. The creator is added as an ACTIVE member. The group is visible in search (if PUBLIC). |
| **Linked FR** | FR-04 |

**Basic Flow:**
1. User navigates to "Create Group."
2. User enters group name, description, and selects a course code.
3. User sets maximum capacity (2–50) and privacy (PUBLIC or PRIVATE).
4. User clicks "Create Group."
5. System validates all fields.
6. System creates the StudyGroup record.
7. System creates a Membership record for the creator with status ACTIVE.
8. User is redirected to the new group's detail page.

**Alternative Flows:**

| Step | Condition | System Response |
|---|---|---|
| 5 | Group name already used by same creator | Returns HTTP 409: "You already have a group with this name." |
| 5 | Capacity outside 2–50 range | Returns HTTP 400: "Capacity must be between 2 and 50." |
| 5 | No course selected | Returns HTTP 400: "A course must be selected." |

---

### UC-05: Search and Discover Groups

| Field | Detail |
|---|---|
| **Use Case ID** | UC-05 |
| **Use Case Name** | Search and Discover Groups |
| **Actor(s)** | Student, New Student, Lecturer |
| **Description** | An authenticated user searches for public study groups using a course code or keyword. Results show group name, course, member count, and availability. |
| **Preconditions** | The user is authenticated. |
| **Postconditions** | A list of matching public groups is displayed. No data is modified. |
| **Linked FR** | FR-05 |

**Basic Flow:**
1. User navigates to the "Find Groups" page.
2. User enters a course code or keyword in the search field.
3. User clicks "Search" or presses Enter.
4. System queries the database for PUBLIC groups matching the input (case-insensitive).
5. System returns results showing group name, course, member count, and a "Full" badge where applicable.
6. User browses results and clicks a group to view details.

**Alternative Flows:**

| Step | Condition | System Response |
|---|---|---|
| 4 | No matching groups found | Displays message: "No groups found for your search. Try a different keyword." |
| 4 | Search field is empty | Returns all public groups (browse mode). |

---

### UC-06: Join Public Group

| Field | Detail |
|---|---|
| **Use Case ID** | UC-06 |
| **Use Case Name** | Join Public Group |
| **Actor(s)** | Student |
| **Description** | An authenticated student joins a public study group instantly, provided it is not full and they have fewer than 5 active memberships. |
| **Preconditions** | The user is authenticated. The target group is PUBLIC. The group is not full. The student has fewer than 5 active memberships. |
| **Postconditions** | A Membership record is created with status ACTIVE. The group's member count increments by 1. |
| **Linked FR** | FR-06 |

**Basic Flow:**
1. User finds a group via Search (UC-05) and views the group detail page.
2. User clicks "Join Group."
3. System checks: group is PUBLIC, not full, user not already a member, user under 5-group limit.
4. System creates a Membership record with status ACTIVE.
5. The "Join Group" button changes to "Leave Group."
6. Member count on the group detail page increments immediately.

**Alternative Flows:**

| Step | Condition | System Response |
|---|---|---|
| 3 | Group is full | Returns HTTP 400: "This group is at full capacity." Join button is disabled. |
| 3 | Already a member | Returns HTTP 409: "You are already a member of this group." |
| 3 | Student has 5 active memberships | Returns HTTP 400: "You cannot join more than 5 groups at a time." |

---

### UC-07: Approve or Reject Join Request

| Field | Detail |
|---|---|
| **Use Case ID** | UC-07 |
| **Use Case Name** | Approve or Reject Join Request |
| **Actor(s)** | Group Creator |
| **Description** | The group creator reviews pending membership requests for their private group and approves or rejects each one. |
| **Preconditions** | The user is authenticated and is the creator of a PRIVATE group. At least one Membership record with status PENDING exists for the group. |
| **Postconditions** | Approved: Membership status changes to ACTIVE. Rejected: Membership record is deleted. |
| **Linked FR** | FR-07 |

**Basic Flow:**
1. Group creator navigates to their group's management page.
2. Creator views the list of pending join requests.
3. Creator clicks "Approve" or "Reject" on a request.
4. If Approve: System updates Membership status to ACTIVE.
5. If Reject: System deletes the Membership record.
6. The request disappears from the pending list.

**Alternative Flows:**

| Step | Condition | System Response |
|---|---|---|
| 3 | Approving would exceed max capacity | Returns HTTP 400: "Cannot approve — group is at full capacity." |
| 1 | No pending requests | Pending requests list shows: "No pending requests." |
| 3 | Non-creator tries to approve | Returns HTTP 403: "Only the group creator can manage membership requests." |

---

### UC-08: Schedule Study Session

| Field | Detail |
|---|---|
| **Use Case ID** | UC-08 |
| **Use Case Name** | Schedule Study Session |
| **Actor(s)** | Group Creator, Student (active group member) |
| **Description** | An active group member schedules a study session within a group by specifying title, date/time, duration, location, and optional notes. |
| **Preconditions** | The user is authenticated and has an ACTIVE membership in the target group. |
| **Postconditions** | A StudySession record is created and linked to the group. The session is visible to all group members. |
| **Linked FR** | FR-09 |

**Basic Flow:**
1. User navigates to their group's detail page.
2. User clicks "Schedule Session."
3. User fills in: session title, date and time, duration (hours), location (address or URL), and optional notes.
4. User clicks "Create Session."
5. System validates that the scheduled time is at least 30 minutes in the future.
6. System creates the StudySession record linked to the group.
7. The session appears in the group's upcoming sessions list for all members.

**Alternative Flows:**

| Step | Condition | System Response |
|---|---|---|
| 5 | Session time is in the past | Returns HTTP 400: "Session must be scheduled at least 30 minutes in the future." |
| 5 | Title field is empty | Frontend validation blocks submission: "Session title is required." |
| 3 | Non-member attempts to access schedule form | Returns HTTP 403: "You must be a group member to schedule sessions." |

---

## 4. Test Cases

### 4.1 Functional Test Cases

| Test ID | Requirement ID | Description | Steps | Expected Result | Actual Result | Status |
|---|---|---|---|---|---|---|
| TC-001 | FR-01 | Register with valid university email | 1. Navigate to /register. 2. Enter valid name, email, password (8+ chars). 3. Click Register. | HTTP 201 returned. JWT token issued. User redirected to profile setup. | — | — |
| TC-002 | FR-01 | Register with duplicate email | 1. Register with an email that already exists. | HTTP 409 returned. Message: "Email already registered." No duplicate record created. | — | — |
| TC-003 | FR-02 | Login with valid credentials | 1. Navigate to /login. 2. Enter correct email and password. 3. Click Login. | HTTP 200 returned. JWT token returned. User redirected to dashboard. | — | — |
| TC-004 | FR-02 | Login with wrong password | 1. Navigate to /login. 2. Enter correct email, wrong password. 3. Click Login. | HTTP 401 returned. Message: "Invalid email or password." | — | — |
| TC-005 | FR-04 | Create a public study group | 1. Login. 2. Navigate to Create Group. 3. Fill all fields. Set privacy to PUBLIC. 4. Click Create Group. | HTTP 201 returned. Group appears in search results. Creator listed as member. | — | — |
| TC-006 | FR-05 | Search groups by course code | 1. Login. 2. Navigate to Find Groups. 3. Enter a valid course code. 4. Click Search. | HTTP 200 returned. Results show only groups matching the course code. Response time under 1 second. | — | — |
| TC-007 | FR-06 | Join a public group successfully | 1. Login as a student with fewer than 5 groups. 2. Find a public group that is not full. 3. Click Join Group. | HTTP 201 returned. Membership created with status ACTIVE. Member count increments. | — | — |
| TC-008 | FR-06 | Attempt to join a full group | 1. Login. 2. Find a group where member count equals max capacity. 3. Click Join Group. | HTTP 400 returned. Message: "This group is at full capacity." Membership not created. | — | — |
| TC-009 | FR-06 | Attempt to join more than 5 groups | 1. Login as a student already in 5 groups. 2. Attempt to join a 6th group. | HTTP 400 returned. Message: "You cannot join more than 5 groups at a time." | — | — |
| TC-010 | FR-07 | Submit join request to private group | 1. Login. 2. Find a PRIVATE group. 3. Click Request to Join. | HTTP 201 returned. Membership record created with status PENDING. Request visible in creator's management panel. | — | — |
| TC-011 | FR-07 | Group creator approves a join request | 1. Login as group creator. 2. Navigate to group management page. 3. Click Approve on a pending request. | HTTP 200 returned. Membership status updated to ACTIVE. Request removed from pending list. | — | — |
| TC-012 | FR-09 | Schedule a valid future session | 1. Login as active group member. 2. Navigate to group detail. 3. Fill session form with future date/time. 4. Click Create Session. | HTTP 201 returned. Session appears in upcoming sessions list for all members. | — | — |
| TC-013 | FR-09 | Schedule a session in the past | 1. Login as group member. 2. Fill session form with a past date/time. 3. Click Create Session. | HTTP 400 returned. Message: "Session must be scheduled at least 30 minutes in the future." No session created. | — | — |
| TC-014 | FR-11 | Admin deactivates a user account | 1. Login as Platform Admin. 2. Navigate to /admin/users. 3. Search for target user. 4. Click Deactivate. | HTTP 200 returned. User's isActive set to false. User cannot login — HTTP 403 returned on login attempt. | — | — |
| TC-015 | FR-12 | Admin deletes a group | 1. Login as Platform Admin. 2. Navigate to /admin/groups. 3. Find target group. 4. Click Delete. | HTTP 200 returned. Group deleted. All memberships and sessions for that group also deleted (cascade). Group no longer appears in search. | — | — |

---

### 4.2 Non-Functional Test Cases

| Test ID | NFR ID | Category | Description | Steps | Expected Result | Actual Result | Status |
|---|---|---|---|---|---|---|---|
| TC-NF-001 | NFR-P1 | Performance | API response time under normal load | 1. Use Apache JMeter. 2. Simulate 100 concurrent users. 3. Each user calls GET /api/groups. 4. Run for 60 seconds. | p95 response time ≤ 500ms. No HTTP 500 errors. | — | — |
| TC-NF-002 | NFR-SC1 | Scalability | API handles 200 concurrent users | 1. Use Apache JMeter. 2. Ramp up to 200 concurrent users over 30 seconds. 3. Each calls GET /api/groups with a search query. | p95 response time ≤ 1 second. Zero failed requests. | — | — |
| TC-NF-003 | NFR-SE1 | Security | Passwords stored as BCrypt hash | 1. Register a new user. 2. Query the database directly: SELECT password_hash FROM users WHERE email = 'test@uni.ac.za'. | Value begins with `$2a$10$`. No plain-text password stored. | — | — |
| TC-NF-004 | NFR-SE2 | Security | Expired JWT token rejected | 1. Obtain a valid JWT token. 2. Manually set the expiry to a past timestamp. 3. Send GET /api/groups with the modified token. | HTTP 401 returned. Message: "Token expired or invalid." | — | — |
| TC-NF-005 | NFR-SE3 | Security | Student cannot access another student's data | 1. Login as Student A. 2. Attempt GET /api/users/{studentB_id} using Student A's JWT. | HTTP 403 returned. Student A's token cannot access Student B's private data. | — | — |
| TC-NF-006 | NFR-U1 | Usability | New user completes onboarding in under 5 minutes | 1. Recruit a first-year student with no prior knowledge of the app. 2. Ask them to register, set up profile, and join a group unassisted. 3. Record time taken. | Task completed in under 5 minutes in 4 out of 5 test participants. | — | — |
| TC-NF-007 | NFR-U2 | Usability | UI is usable on mobile screen (375px wide) | 1. Open the app in Chrome DevTools. 2. Set viewport to 375px width (iPhone SE). 3. Navigate through all main pages. | No horizontal scrolling. All buttons and text visible. No overlapping elements. Google Lighthouse mobile score ≥ 90. | — | — |
| TC-NF-008 | NFR-D2 | Deployability | No secrets hardcoded in source code | 1. Search the entire GitHub repository for known secret patterns: JWT secret, database URL, passwords. 2. Use GitHub's secret scanning feature. | Zero secrets found in source code. All environment values loaded from `.env` or Railway environment variables. | — | — |

---

## 5. Reflection — Challenges in Translating Requirements to Use Cases and Tests

### 5.1 Introduction

Translating functional requirements into use cases and test cases sounds straightforward in theory — a requirement becomes a use case becomes a test. In practice however, the process exposed several gaps, ambiguities, and design decisions that were not visible when writing requirements in plain language. This reflection documents the most significant challenges encountered while completing this assignment.

### 5.2 Challenge 1: Determining the Right Level of Granularity for Use Cases

The first challenge was deciding how detailed each use case should be. Requirements like FR-06 (Join Public Group) seemed like a single interaction, but when writing the specification it became clear that it contained multiple embedded decisions: is the group full? is the user already a member? have they hit the 5-group limit? Each of these is an alternative flow, not a separate use case.

Getting this balance wrong in either direction causes problems. Too coarse and the use case hides important behaviour — testers miss scenarios. Too fine and every validation rule becomes its own use case, making the diagram unreadably complex. The resolution was to keep use cases at the level of a complete user goal (e.g., "join a group") and capture all branching within the alternative flows table of that specification.

### 5.3 Challenge 2: Identifying `<<include>>` vs `<<extend>>` Correctly

The UML distinction between `<<include>>` and `<<extend>>` proved more subtle than expected. Initially, "Cancel or Edit Session" was modelled as `<<include>>` of "Schedule Session" — meaning it would always run alongside scheduling. This was incorrect. Cancellation is optional and only triggered under a specific condition (the user decides to edit). It is therefore `<<extend>>`.

Similarly, the login check was correctly identified as `<<include>>` in all protected use cases because it is mandatory — not optional. Taking time to apply these relationships correctly made the diagram significantly more meaningful and accurate to how the system actually behaves.

### 5.4 Challenge 3: Writing Acceptance Criteria That Are Actually Testable

Several requirements from Assignment 4 used language that felt specific but was still too vague to test directly. For example, "the system shall respond within 500ms" — 500ms measured where? At the server? At the browser? Under what load?

Writing the test cases forced every NFR to be made concrete: the tool used (Apache JMeter), the number of virtual users (100 or 200), the metric measured (p95 response time), and the specific endpoint tested (GET /api/groups). This level of specificity was not present in the original SRD and had to be added during this assignment. This is a natural part of the requirements refinement process — test case development is itself a form of requirements validation.

### 5.5 Challenge 4: Mapping Tests Back to Requirements Without Redundancy

With 12 functional requirements and 10 non-functional requirements, deciding which 15 functional test cases and 8 non-functional test cases to write required prioritisation. Not every requirement could receive equal coverage within the scope of this document.

The approach taken was to prioritise test cases for requirements that were either high-risk (security requirements like password hashing and JWT validation), high-frequency (login, group search, join group), or had previously been identified as potential conflict points in the stakeholder analysis (the 5-group limit, full group rejection, private group approval).

### 5.6 Challenge 5: Consistency Across Three Assignments

Because this is the third document in a chain — building on Assignments 3 and 4 — every use case and test case had to remain consistent with both the architecture diagrams and the stakeholder analysis. For instance, the Lecturer actor in the use case diagram had to match the Lecturer stakeholder defined in STAKEHOLDERS.md, and the FR references in test cases had to match exactly the FR-IDs used in SRD.md.

Maintaining this consistency required revisiting earlier documents multiple times. This was time-consuming but necessary — inconsistency between requirement IDs and test case references is one of the most common causes of failed software audits in real projects.

### 5.7 Conclusion

This assignment reinforced that use cases and test cases are not just documentation artifacts — they are a mechanism for stress-testing the requirements themselves. Every alternative flow written in a use case specification represents a gap that the original requirement did not explicitly address. Every test case written forces a decision about what "correct behaviour" actually means in precise, measurable terms. Requirements that cannot be tested are not requirements — they are wishes.
