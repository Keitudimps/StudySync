# DOMAIN_MODEL.md — Domain Model Documentation
## StudySync: Study Group Finder System

---

## 1. Introduction

The domain model defines the core entities of the StudySync system, their attributes, responsibilities, and the relationships between them. This model is the conceptual foundation for the class diagram and directly reflects the functional requirements defined in Assignment 4, the use cases in Assignment 5, and the state diagrams in Assignment 8.

---

## 2. Domain Entities

### Entity 1 — User

| Property | Detail |
|---|---|
| **Description** | Represents any registered person on the platform. Can be a student, group creator, or administrator. |
| **Attributes** | userId, name, email, passwordHash, role (STUDENT / ADMIN), yearOfStudy, isActive, createdAt |
| **Methods** | register(), login(), logout(), updateProfile(), deactivate(), reactivate() |
| **Relationships** | Creates StudyGroups · Has Memberships · Creates StudySessions · Receives Notifications (future) |

**Business Rules:**
- Email must be unique across all users
- Password must be stored as BCrypt hash — never plain text
- A student may belong to a maximum of 5 active groups simultaneously
- Only users with role ADMIN can access admin endpoints
- A deactivated user cannot log in

---

### Entity 2 — StudyGroup

| Property | Detail |
|---|---|
| **Description** | A named study group linked to a course. Can be public (open join) or private (approval required). |
| **Attributes** | groupId, name, description, privacy (PUBLIC / PRIVATE), maxCapacity, createdAt, creatorId, courseId |
| **Methods** | create(), delete(), isFull(), getMembers(), getSessions(), updateDetails() |
| **Relationships** | Belongs to one Course · Has many Memberships · Has many StudySessions · Created by one User |

**Business Rules:**
- Maximum capacity must be between 2 and 50
- A group must be linked to exactly one course
- A PUBLIC group allows instant join; a PRIVATE group requires creator approval
- Deleting a group cascades to delete all its Memberships and StudySessions
- Only the creator or an admin can delete a group

---

### Entity 3 — Membership

| Property | Detail |
|---|---|
| **Description** | Represents the relationship between a User and a StudyGroup. Tracks join status. |
| **Attributes** | membershipId, status (PENDING / ACTIVE), joinedAt, userId, groupId |
| **Methods** | approve(), reject(), leave(), remove() |
| **Relationships** | Belongs to one User · Belongs to one StudyGroup |

**Business Rules:**
- A user cannot have more than one membership record per group
- PUBLIC group join creates membership with ACTIVE status immediately
- PRIVATE group join creates membership with PENDING status — requires creator approval
- Approving a PENDING membership requires the group to still have capacity
- Deleting a membership decrements the group's effective member count

---

### Entity 4 — StudySession

| Property | Detail |
|---|---|
| **Description** | A scheduled study meeting within a group, created by any active group member. |
| **Attributes** | sessionId, title, scheduledAt, durationHours, location, notes, createdBy, groupId |
| **Methods** | schedule(), cancel(), reschedule(), isUpcoming(), isPast() |
| **Relationships** | Belongs to one StudyGroup · Created by one User (active member) |

**Business Rules:**
- A session must be scheduled at least 30 minutes in the future
- Only an active group member can create a session
- Only the session creator can cancel or reschedule it
- Cancelled sessions are deleted; completed sessions are retained for history
- Location can be a physical address or a virtual meeting URL

---

### Entity 5 — Course

| Property | Detail |
|---|---|
| **Description** | Represents a university course. Pre-seeded by administrators. Students select from this list during profile setup. |
| **Attributes** | courseId, courseCode, courseName, isActive |
| **Methods** | activate(), deactivate(), getGroups(), getEnrolledStudents() |
| **Relationships** | Has many StudyGroups · Has many UserCourse enrolments |

**Business Rules:**
- Course codes are unique
- Students cannot create a group for an inactive course
- Deactivating a course does not delete existing groups linked to it
- Courses are managed exclusively by administrators — students cannot create them

---

### Entity 6 — UserCourse (Enrolment)

| Property | Detail |
|---|---|
| **Description** | Junction entity representing a student's enrolment in a course. Created during profile setup or edit. |
| **Attributes** | userCourseId, userId, courseId, enrolledAt |
| **Methods** | enrol(), drop() |
| **Relationships** | Belongs to one User · Belongs to one Course |

**Business Rules:**
- A student must be enrolled in at least one course at all times
- A student cannot enrol in the same course twice
- Dropping a course is only allowed if at least one other enrolment remains
- All enrolment records are deleted when the parent user account is deleted

---

### Entity 7 — Role (Enumeration)

| Property | Detail |
|---|---|
| **Description** | Defines the access level of a User. Determines which endpoints and actions are available. |
| **Values** | STUDENT, ADMIN |
| **Relationships** | Used by User entity |

**Business Rules:**
- Every user must have exactly one role
- ADMIN role grants access to /api/admin/* endpoints
- Role is assigned at registration and can only be changed by an existing ADMIN

---

## 3. Entity Relationship Summary

| Entity A | Relationship | Entity B | Multiplicity | Type |
|---|---|---|---|---|
| User | creates | StudyGroup | 1 to 0..* | Association |
| User | has | Membership | 1 to 0..5 | Composition |
| User | creates | StudySession | 1 to 0..* | Association |
| User | enrolls via | UserCourse | 1 to 1..* | Composition |
| StudyGroup | contains | Membership | 1 to 0..* | Composition |
| StudyGroup | contains | StudySession | 1 to 0..* | Composition |
| StudyGroup | linked to | Course | many to 1 | Association |
| Course | has | UserCourse | 1 to 0..* | Association |
| UserCourse | links | User + Course | 1 to 1 each | Association |

---

## 4. Business Rules Summary

| Rule ID | Rule | Enforced In |
|---|---|---|
| BR-01 | A student may belong to a maximum of 5 active groups | MembershipService.join() |
| BR-02 | Passwords must be stored as BCrypt hash — never plain text | AuthService.register() |
| BR-03 | A group capacity must be between 2 and 50 | StudyGroupService.create() |
| BR-04 | A session must be at least 30 minutes in the future | StudySessionService.schedule() |
| BR-05 | A student must be enrolled in at least one course | UserCourseService.drop() |
| BR-06 | Only active group members can schedule sessions | StudySessionService — membership check |
| BR-07 | Deleting a group cascades to memberships and sessions | Database foreign key constraints |
| BR-08 | PRIVATE groups require creator approval for membership | MembershipService.join() — privacy check |
| BR-09 | Email must be unique across all users | AuthService.register() — DB constraint |
| BR-10 | Only ADMIN role can access /api/admin/* endpoints | JwtSecurityFilter — role check |
