# SPECIFICATION.md — StudySync: Study Group Finder System

---

## 1. Introduction

### 1.1 Project Title
**StudySync — Study Group Finder System**

### 1.2 Domain
**Education / Academic Collaboration**

The domain is university-level education, specifically the space of peer-to-peer academic collaboration. Universities and higher education institutions host thousands of students enrolled in overlapping courses who frequently need to study together, review material, prepare for exams, and work on group assignments. This domain includes actors such as students, academic administrators, and the institution's course registry. The platform operates within the academic calendar cycle, where demand spikes around assignment deadlines and examination periods.

### 1.3 Problem Statement
University students often struggle to find peers studying the same subject at the same time and place. Study groups form informally through social networks, notice boards, or word-of-mouth — all of which are unreliable, exclusionary, and unscalable. There is no centralized system that allows students to:

- Discover study groups by course or subject
- Schedule and coordinate study sessions efficiently
- Manage group membership in a structured way

**StudySync** addresses this gap by providing a dedicated, web-based platform where students can create, discover, and join study groups, and schedule study sessions — all in one place.

### 1.4 Individual Scope — Feasibility Justification
This project is feasible for a single developer over one semester for the following reasons:

1. **Bounded domain**: The system is focused solely on study group coordination. It does not attempt to replicate a full Learning Management System (LMS) like  Blackboard.
2. **Familiar tech stack**: The developer is proficient in React (frontend) and Java Spring Boot (backend), which maps directly to the planned architecture.
3. **5 clearly scoped features**: The system targets exactly 5 core features (see Section 3), each independently implementable and testable.
4. **Standard data model**: Entities (User, Group, Session, Membership) are well-understood relational constructs with no exotic data requirements.
5. **No hardware dependencies**: The system is entirely software-based and deployable on free cloud platforms (Vercel for frontend, Railway for backend and database).

---

## 2. Stakeholders

| Stakeholder | Role | Interest |
|---|---|---|
| Student | Primary user | Find and join study groups, schedule sessions |
| Group Creator | Student who creates a group | Manage group membership and sessions |
| Administrator | Platform admin | Manage users, moderate groups, view platform stats |
| University (Institution) | Indirect stakeholder | Improved student academic performance |

---

## 3. Functional Requirements

The system shall support the following **5 core features**:

### Feature 1: User Registration and Authentication
- Students can register using their email address and a password.
- Upon registration, students complete an academic profile specifying their enrolled courses and year of study.
- The system authenticates users login.
- Passwords are stored.
- Users can log out.

### Feature 2: Study Group Creation and Discovery
- Authenticated students can create a study group by providing:
  - Group name
  - Associated course code (e.g., CS301)
  - Description
  - Maximum member capacity
  - Privacy setting: Public or Private
- Any student can search and browse public study groups by course code or keyword.
- Students can view group details including member count, upcoming sessions, and the creator.

### Feature 3: Group Membership and Join Requests
- Students can send a join request to any public group.
- For private groups, the group creator must approve or reject incoming join requests.
- The group creator can remove members from a group.
- Students can leave a group at any time.
- A student may belong to a maximum of 5 active groups simultaneously (to prevent overcommitting).

### Feature 4: Study Session Scheduling
- Group members (or the creator) can schedule a study session within a group by providing:
  - Session title
  - Date and time
  - Duration (in hours)
  - Location: physical address or virtual link (e.g., Zoom/Google Meet URL)
  - Optional session notes/agenda
- All group members can view upcoming and past sessions for their groups.
- The creator can cancel or reschedule sessions.

### Feature 5: Admin Dashboard
- A designated administrator account can:
  - View all registered users and their profiles
  - View all study groups (including private ones)
  - Delete inappropriate or inactive groups
  - Deactivate or ban user accounts
  - View aggregate platform statistics (total users, groups, sessions)

---

## 4. Non-Functional Requirements

| Category | Requirement |
|---|---|
| **Performance** | API endpoints must respond within 500ms under normal load (up to 100 concurrent users) |
| **Security** | All endpoints except registration and login must require a valid token | 
| **Usability** | The UI must be responsive and usable. |
| **Scalability** | The backend must be stateless to allow horizontal scaling; sessions are managed client-side |
| **Maintainability** | Code must follow RESTful API conventions; frontend components must be modular and reusable |
| **Availability** | The system should target 99% uptime during the academic term when deployed |
| **Data Integrity** | Foreign key constraints must be enforced in the database. |

---

## 5. Use Cases

### UC-01: Register Account
- **Actor**: Student
- **Precondition**: User does not have an existing account
- **Flow**: User provides name, email, password, year of study, and enrolled courses → System validates input → System creates account → User is logged in
- **Postcondition**: User account exists in the database

### UC-02: Create Study Group
- **Actor**: Authenticated Student
- **Precondition**: User is logged in
- **Flow**: User fills in group details → System validates (e.g., course code format) → System creates group with user as creator and first member
- **Postcondition**: Group is visible in search results (if public)

### UC-03: Join a Public Group
- **Actor**: Authenticated Student
- **Precondition**: Group exists, is public, and user is not already a member
- **Flow**: User searches for group → Views details → Clicks "Join" → System adds user as member
- **Postcondition**: User is a member of the group

### UC-04: Request to Join a Private Group
- **Actor**: Authenticated Student / Group Creator
- **Precondition**: Group is private; user is not a member
- **Flow**: Student sends join request → Group creator reviews pending requests → Creator approves or rejects → Membership status is updated accordingly
- **Postcondition**: Student is added or request is declined

### UC-05: Schedule a Session
- **Actor**: Group Member (or Creator)
- **Precondition**: User is a member of the group
- **Flow**: Member fills in session details → System saves session → Session is visible to all group members
- **Postcondition**: Session is visible to all group members

### UC-06: Admin Deletes a Group
- **Actor**: Administrator
- **Precondition**: Admin is logged in; target group exists
- **Flow**: Admin searches for group in dashboard → Views group details → Clicks Delete → System removes group and all associated sessions/memberships
- **Postcondition**: Group and all its related data are permanently removed

---

## 6. Data Model Overview

| Entity | Key Attributes |
|---|---|
| **User** | userId, name, email, password, role (STUDENT/ADMIN), yearOfStudy, createdAt |
| **Course** | courseId, courseCode, courseName |
| **UserCourse** | userId, courseId (join table — enrolled courses) |
| **StudyGroup** | groupId, name, description, courseId, creatorId, maxCapacity, privacy (PUBLIC/PRIVATE), createdAt |
| **Membership** | membershipId, groupId, userId, status (PENDING/ACTIVE), joinedAt |
| **StudySession** | sessionId, groupId, title, scheduledAt, durationHours, location, notes, createdBy |

---

## 7. Constraints and Assumptions

- The system assumes students have access to the internet and a modern web browser.
- Course codes are pre-seeded in the database by an administrator; students select from a list.
- The system does not integrate with any external university authentication system .
- Virtual meeting links (e.g., Zoom) are entered manually by session creators; the system does not generate them.
