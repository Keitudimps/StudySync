# CLASS_DIAGRAM.md — Class Diagram in Mermaid.js
## StudySync: Study Group Finder System

---

## 1. Class Diagram

```mermaid
classDiagram
    direction TB

    class User {
        -Long userId
        -String name
        -String email
        -String passwordHash
        -Role role
        -Integer yearOfStudy
        -Boolean isActive
        -LocalDateTime createdAt
        +register(name, email, password) User
        +login(email, password) String
        +logout() void
        +updateProfile(year, courses) void
        +deactivate() void
        +reactivate() void
    }

    class StudyGroup {
        -Long groupId
        -String name
        -String description
        -Privacy privacy
        -Integer maxCapacity
        -LocalDateTime createdAt
        +create(name, course, capacity, privacy) StudyGroup
        +delete() void
        +isFull() Boolean
        +getMembers() List~Membership~
        +getSessions() List~StudySession~
        +updateDetails(name, description) void
    }

    class Membership {
        -Long membershipId
        -MembershipStatus status
        -LocalDateTime joinedAt
        +approve() void
        +reject() void
        +leave() void
        +remove() void
    }

    class StudySession {
        -Long sessionId
        -String title
        -LocalDateTime scheduledAt
        -Integer durationHours
        -String location
        -String notes
        +schedule(title, time, duration, location) StudySession
        +cancel() void
        +reschedule(newTime) void
        +isUpcoming() Boolean
        +isPast() Boolean
    }

    class Course {
        -Long courseId
        -String courseCode
        -String courseName
        -Boolean isActive
        +activate() void
        +deactivate() void
        +getGroups() List~StudyGroup~
        +getEnrolledStudents() List~User~
    }

    class UserCourse {
        -Long userCourseId
        -LocalDateTime enrolledAt
        +enrol() void
        +drop() void
    }

    class Role {
        <<enumeration>>
        STUDENT
        ADMIN
    }

    class Privacy {
        <<enumeration>>
        PUBLIC
        PRIVATE
    }

    class MembershipStatus {
        <<enumeration>>
        PENDING
        ACTIVE
    }

    class AuthService {
        +register(name, email, password) User
        +login(email, password) String
        +generateToken(user) String
        +validateToken(token) Boolean
        +hashPassword(raw) String
    }

    class StudyGroupService {
        +createGroup(userId, dto) StudyGroup
        +searchGroups(query) List~StudyGroup~
        +deleteGroup(groupId, requesterId) void
        +getGroupById(groupId) StudyGroup
    }

    class MembershipService {
        +joinGroup(userId, groupId) Membership
        +approveRequest(membershipId, creatorId) Membership
        +rejectRequest(membershipId, creatorId) void
        +leaveGroup(userId, groupId) void
        +removeMember(targetId, groupId, creatorId) void
    }

    class StudySessionService {
        +scheduleSession(userId, groupId, dto) StudySession
        +cancelSession(sessionId, userId) void
        +rescheduleSession(sessionId, userId, newTime) StudySession
        +getUpcomingSessions(groupId) List~StudySession~
        +getPastSessions(groupId) List~StudySession~
    }

    class AdminService {
        +getAllUsers() List~User~
        +deactivateUser(userId) void
        +reactivateUser(userId) void
        +getAllGroups() List~StudyGroup~
        +deleteGroup(groupId) void
        +getPlatformStats() Map
    }

    %% ── Enumerations used by classes ──
    User --> Role : has
    StudyGroup --> Privacy : has
    Membership --> MembershipStatus : has

    %% ── User creates and owns ──
    User "1" --> "0..*" StudyGroup : creates
    User "1" *-- "0..5" Membership : has
    User "1" --> "0..*" StudySession : creates
    User "1" *-- "1..*" UserCourse : enrolled in

    %% ── StudyGroup compositions ──
    StudyGroup "1" *-- "0..*" Membership : contains
    StudyGroup "1" *-- "0..*" StudySession : contains
    StudyGroup "0..*" --> "1" Course : linked to

    %% ── Course and enrolment ──
    Course "1" --> "0..*" UserCourse : referenced by
    UserCourse "0..*" --> "1" User : for
    UserCourse "0..*" --> "1" Course : for

    %% ── Service dependencies ──
    AuthService ..> User : manages
    StudyGroupService ..> StudyGroup : manages
    StudyGroupService ..> Course : reads
    MembershipService ..> Membership : manages
    MembershipService ..> StudyGroup : reads
    StudySessionService ..> StudySession : manages
    StudySessionService ..> Membership : validates
    AdminService ..> User : moderates
    AdminService ..> StudyGroup : moderates
```

---

## 2. Key Design Decisions

### 2.1 Composition vs Association

**Membership is a composition of both User and StudyGroup.** When a StudyGroup is deleted, all its Membership records are deleted with it (cascade). Similarly, when a User is deleted, their Membership records are removed. This is modelled as composition (`*--`) rather than a simple association because Membership has no meaningful existence outside of the User-Group relationship.

**StudySession is a composition of StudyGroup.** A session cannot exist without a group. If the group is deleted, all its sessions are cascade-deleted. However, the relationship to the creating User is an association — the session remains valid even if the creator later leaves the group.

**UserCourse is a composition of User.** Enrolment records are tied to the user's lifecycle. Deleting a user removes all their enrolments.

### 2.2 Services as Separate Classes

The service classes (AuthService, StudyGroupService, MembershipService, StudySessionService, AdminService) are included in the diagram as separate classes rather than embedding business logic inside the domain entities. This reflects the **Spring Boot layered architecture** defined in ARCHITECTURE.md — domain entities are pure data carriers (JPA entities) and services hold the business logic. This separation is shown using dependency arrows (`..>`) rather than associations.

### 2.3 Enumerations

Three enumerations are defined as separate classes with the `<<enumeration>>` stereotype:
- **Role** — controls endpoint access (STUDENT vs ADMIN)
- **Privacy** — controls group join behaviour (PUBLIC vs PRIVATE)
- **MembershipStatus** — tracks the approval state of a membership (PENDING vs ACTIVE)

Using enumerations rather than String fields enforces type safety at the database level (PostgreSQL ENUM types) and prevents invalid state values.

### 2.4 Multiplicity Choices

| Relationship | Multiplicity | Reasoning |
|---|---|---|
| User → Membership | 0..5 | Business rule BR-01 — max 5 active groups |
| User → UserCourse | 1..* | Business rule BR-05 — at least one course required |
| StudyGroup → Membership | 0..* | A group can exist with just the creator (1 member) |
| StudyGroup → Course | many to 1 | Every group must have exactly one course |
| StudyGroup → StudySession | 0..* | A group may have no sessions scheduled yet |

### 2.5 Traceability to Prior Assignments

| Design Decision | Traced To |
|---|---|
| User.role drives access control | NFR-SE3 (authorisation), FR-11, FR-12 |
| Membership.status = PENDING for private groups | FR-07 (join request flow), UC-07 |
| StudySession.scheduledAt guard (30 mins) | FR-09 acceptance criteria, US-010 |
| Cascade delete on group | FR-12 acceptance criteria (no orphan records) |
| Max 5 memberships per user | FR-06 acceptance criteria, US-007 |
| BCrypt in AuthService.hashPassword() | NFR-SE1, US-018 |
