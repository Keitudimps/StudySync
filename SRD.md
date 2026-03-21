# SRD.md — System Requirements Document
## StudySync: Study Group Finder System

---

## 1. Introduction

### 1.1 Purpose
This System Requirements Document (SRD) defines the complete functional and non-functional requirements for the StudySync platform. It is intended to guide the development of the system across the remaining semester and serves as the reference point for all implementation decisions.

### 1.2 Scope
StudySync is a web-based academic collaboration platform built with React (frontend) and Java Spring Boot (backend), backed by PostgreSQL. The system allows university students to create, discover, and join study groups; schedule study sessions; and be managed by a platform administrator.

### 1.3 Definitions

| Term | Definition |
|---|---|
| SRD | System Requirements Document |
| FR | Functional Requirement |
| NFR | Non-Functional Requirement |
| JWT | JSON Web Token — used for stateless authentication |
| Group Creator | A student who creates and owns a study group |
| Session | A scheduled study meeting within a study group |
| PENDING | Membership status for an unreviewed join request |
| ACTIVE | Membership status for an approved or auto-approved member |

---

## 2. Functional Requirements

> Each requirement follows the format: **The system shall…**
> Acceptance criteria are provided for critical requirements.

---

### FR-01: User Registration
**The system shall allow a student to register using a university email address and password.**

- Email must match a valid format (e.g., `name@university.ac.za`)
- Password must be at least 8 characters
- Duplicate email addresses must be rejected with a clear error message
- Password must be stored as a bcrypt hash — never in plain text

**Acceptance Criteria:**
- Given a valid email and password, the system creates the account and returns a JWT token within 1 second.
- Given a duplicate email, the system returns HTTP 409 Conflict with the message "Email already registered."

**Traced to:** Student (General), New Student, University Administrator

---

### FR-02: User Login and Authentication
**The system shall authenticate registered users using email and password, returning a JWT token on success.**

- JWT token must expire after 24 hours
- Invalid credentials must return HTTP 401 Unauthorised
- All protected endpoints must reject requests without a valid JWT

**Acceptance Criteria:**
- Given correct credentials, a JWT token is returned within 500ms.
- Given an expired token, the system returns HTTP 401 and the frontend redirects to the login page.

**Traced to:** All stakeholders

---

### FR-03: Academic Profile Setup
**The system shall allow a student to complete an academic profile after registration, specifying their year of study and enrolled courses.**

- Courses are selected from a pre-seeded list of course codes
- Year of study must be a value between 1 and 4
- Profile can be updated at any time after registration

**Acceptance Criteria:**
- Profile is saved and retrievable within 500ms of submission.
- Enrolled courses are visible on the student's profile page.

**Traced to:** Student (General), Lecturer/Academic Staff

---

### FR-04: Study Group Creation
**The system shall allow an authenticated student to create a study group by providing a name, course, description, maximum capacity, and privacy setting.**

- Privacy options: PUBLIC or PRIVATE
- Maximum capacity must be between 2 and 50
- Course must be selected from the valid course list
- The creator is automatically added as the first ACTIVE member

**Acceptance Criteria:**
- A new group appears in search results (if PUBLIC) within 1 second of creation.
- The creator sees themselves listed as a member immediately after creation.

**Traced to:** Group Creator, Student (General), Lecturer/Academic Staff

---

### FR-05: Study Group Search and Discovery
**The system shall allow any authenticated student to search for public study groups by course code or keyword.**

- Search must be case-insensitive
- Results must display: group name, course code, member count, and capacity
- Groups that are full (member count = max capacity) must be clearly marked

**Acceptance Criteria:**
- Search returns results in under 1 second for queries against up to 10,000 groups.
- Full groups are displayed with a "Full" badge and the join button is disabled.

**Traced to:** Student (General), New Student

---

### FR-06: Join a Public Group
**The system shall allow an authenticated student to instantly join a public study group, provided the group is not full and the student has fewer than 5 active memberships.**

- Membership is created with status ACTIVE immediately
- A student cannot join a group they are already a member of
- Joining a full group must return HTTP 400 with message "Group is at full capacity."

**Acceptance Criteria:**
- Membership is created and confirmed within 500ms.
- The student's group count increments and is reflected on the dashboard immediately.

**Traced to:** Student (General), New Student

---

### FR-07: Join Request for Private Groups
**The system shall allow a student to submit a join request to a private group, which the group creator must approve or reject.**

- Join request creates a Membership record with status PENDING
- Group creator can view all pending requests on the group management page
- Creator can approve (status → ACTIVE) or reject (record deleted) each request

**Acceptance Criteria:**
- Pending request appears in the creator's management panel within 1 second of submission.
- Approving a request updates the membership status to ACTIVE within 500ms.

**Traced to:** Group Creator, Student (General)

---

### FR-08: Leave or Remove from Group
**The system shall allow a student to leave a group at any time, and allow the group creator to remove any member.**

- When a student leaves, their Membership record is deleted
- When a creator removes a member, the Membership record is deleted
- The creator cannot leave their own group without transferring ownership or deleting the group
- Removing a member must not delete the group or any sessions

**Acceptance Criteria:**
- Membership is removed and the member count decrements within 500ms.
- The removed student no longer sees the group in their "My Groups" list.

**Traced to:** Group Creator, Student (General)

---

### FR-09: Study Session Scheduling
**The system shall allow any active group member to schedule a study session by providing a title, date and time, duration, and location.**

- Location can be a physical address or a virtual meeting link (URL)
- Optional session notes or agenda can be included
- Sessions must be scheduled at least 30 minutes in the future
- The session creator can edit or cancel their session

**Acceptance Criteria:**
- Scheduled session appears in the group's session list within 1 second of creation.
- Sessions scheduled in the past are rejected with HTTP 400 and message "Session must be in the future."

**Traced to:** Group Creator, Student (General), Lecturer/Academic Staff

---

### FR-10: View Upcoming and Past Sessions
**The system shall display all upcoming and past study sessions for each group a student belongs to.**

- Upcoming sessions are sorted by date ascending
- Past sessions are sorted by date descending
- Each session displays: title, date/time, duration, location, and creator name

**Acceptance Criteria:**
- Session list loads within 1 second.
- Sessions are correctly sorted with no manual refresh required.

**Traced to:** Student (General), Group Creator

---

### FR-11: Admin User Management
**The system shall provide a platform administrator with the ability to view, deactivate, and reactivate any student account.**

- Admin can search users by name or email
- Deactivated users cannot log in (HTTP 403 returned on login attempt)
- Admin can reactivate deactivated accounts

**Acceptance Criteria:**
- User list loads within 2 seconds for up to 10,000 users.
- Deactivation takes effect immediately — active JWT tokens for that user are rejected.

**Traced to:** Platform Administrator, University Administrator

---

### FR-12: Admin Group Moderation
**The system shall allow the platform administrator to view all groups (including private ones) and permanently delete any group.**

- Deleting a group removes all associated memberships and sessions (cascade delete)
- Admin sees group name, course, member count, creator, and creation date
- Admin can filter groups by course or creation date

**Acceptance Criteria:**
- Group deletion completes within 1 second and the group no longer appears in any search.
- Cascade deletion leaves no orphan membership or session records in the database.

**Traced to:** Platform Administrator, University Administrator

---

## 3. Non-Functional Requirements

---

### 3.1 Usability

**NFR-U1: Intuitive Navigation**
The user interface shall allow a first-time user to register, find a group, and join it within 5 minutes without any external guidance.

*Measurable criterion:* Validated through usability testing with 5 first-year students — 80% must complete the flow within 5 minutes unassisted.

**NFR-U2: Responsive Design**
The interface shall be fully usable on screen widths from 375px (mobile) to 1920px (desktop) without horizontal scrolling or broken layouts.

*Measurable criterion:* All pages pass Google Lighthouse mobile usability audit with a score of 90 or above.

---

### 3.2 Deployability

**NFR-D1: Cloud Deployment**
The system shall be deployable on Vercel (frontend) and Railway (backend and database) using only a GitHub repository connection — no manual server configuration required.

*Measurable criterion:* A new developer can deploy the full system from a fresh GitHub clone in under 30 minutes by following the README instructions.

**NFR-D2: Environment Configuration**
All environment-specific values (database URL, JWT secret, allowed origins) shall be configurable via environment variables and must not be hardcoded in source code.

*Measurable criterion:* No secrets or environment-specific values appear in the GitHub repository. Verified by automated secret scanning.

---

### 3.3 Maintainability

**NFR-M1: API Documentation**
All REST API endpoints shall be documented using Swagger/OpenAPI 3.0, accessible at `/api/docs` when the backend is running.

*Measurable criterion:* Every endpoint has a documented request body, response schema, and at least one example. New developer can call any endpoint correctly using only the Swagger UI.

**NFR-M2: Code Structure**
The backend shall follow a strict layered architecture: Controller → Service → Repository. No business logic shall exist in Controller classes or Repository interfaces.

*Measurable criterion:* Code review confirms zero business logic in any `@RestController` or `@Repository` class.

---

### 3.4 Scalability

**NFR-SC1: Concurrent Users**
The backend API shall support at least 200 concurrent users without response time degrading beyond 1 second for standard read operations (group search, session list).

*Measurable criterion:* Load test using Apache JMeter with 200 virtual users shows p95 response time ≤ 1 second for GET /api/groups.

**NFR-SC2: Stateless Backend**
The Spring Boot API shall be stateless — no user session data stored on the server. All state shall be managed client-side via JWT tokens.

*Measurable criterion:* Horizontal scaling is achievable by adding a second API instance with zero code changes. Verified by running two parallel instances and confirming both serve requests correctly.

---

### 3.5 Security

**NFR-SE1: Password Storage**
All user passwords shall be hashed using BCrypt with a minimum cost factor of 10 before being stored in the database. Plain text passwords must never be stored or logged.

*Measurable criterion:* Database audit confirms all `password_hash` values begin with `$2a$10$` (BCrypt format). No password appears in any application log file.

**NFR-SE2: JWT Security**
JWT tokens shall be signed using HMAC-SHA256 with a secret key of at least 256 bits. Tokens shall expire after 24 hours.

*Measurable criterion:* Attempting to use an expired or tampered token returns HTTP 401 in 100% of test cases.

**NFR-SE3: Authorisation Enforcement**
No student shall be able to access, modify, or delete data belonging to another student or a group they are not a member of.

*Measurable criterion:* Penetration test confirms that authenticated requests targeting another user's data return HTTP 403 Forbidden in 100% of cases.

---

### 3.6 Performance

**NFR-P1: API Response Time**
All API endpoints shall respond within 500ms under normal load (up to 100 concurrent users) for read operations and within 1 second for write operations.

*Measurable criterion:* Monitored via Railway deployment metrics. p95 response time stays below 500ms during standard operation.

**NFR-P2: Search Performance**
The group search endpoint shall return results within 1 second for a dataset of up to 10,000 groups.

*Measurable criterion:* Database query for group search uses an index on `course_id` and `name`. Verified with EXPLAIN ANALYZE in PostgreSQL showing index scan used.

---

