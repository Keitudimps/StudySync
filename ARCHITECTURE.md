# ARCHITECTURE.md — StudySync: Study Group Finder System

---

## 1. Project Title
**StudySync — Study Group Finder System**

## 2. Domain
**Education / Academic Collaboration**

University-level peer study coordination. The platform sits at the intersection of student social networking and academic scheduling, enabling self-organized study groups within an institutional course context.

## 3. Problem Statement
Students lack a centralized platform to discover, create, and coordinate study groups by course. StudySync provides a structured web application that handles group formation, membership management, session scheduling, and admin oversight.

## 4. Individual Scope
The system is scoped to a single developer over one semester. It includes 5 features: authentication, group creation/discovery, membership management, session scheduling, and an admin dashboard — all implementable with React and Spring Boot.

---

## 5. C4 Architectural Diagrams

The C4 model describes a software system at four levels of abstraction:
- **Level 1 — System Context**: The system and its external users/dependencies
- **Level 2 — Container**: The deployable units (apps, databases, etc.)
- **Level 3 — Component**: The internal building blocks within each container
- **Level 4 — Code**: Key class/entity relationships (data model)

---

### Level 1 — System Context Diagram

> Shows StudySync and how it relates to its users and any external systems.

```mermaid
C4Context
    title System Context — StudySync Study Group Finder

    Person(student, "Student", "A university student who wants to find or create study groups and schedule sessions.")
    Person(admin, "Administrator", "A platform admin who manages users, groups, and platform content.")

    System(studysync, "StudySync", "A web-based platform that allows students to create, discover, and join study groups and schedule study sessions.")

    System_Ext(videoService, "Video Conferencing (External)", "Third-party platforms like Zoom or Google Meet. Links are manually entered by users.")

    Rel(student, studysync, "Registers, creates/joins groups, schedules sessions", "HTTPS / Browser")
    Rel(admin, studysync, "Manages users and groups via admin dashboard", "HTTPS / Browser")
    Rel(studysync, videoService, "Students paste meeting links manually into session forms", "Manual URL input")

    UpdateRelStyle(student, studysync, $textColor="black", $lineColor="#1168BD")
    UpdateRelStyle(admin, studysync, $textColor="black", $lineColor="#1168BD")
```

---

### Level 2 — Container Diagram

> Shows the deployable containers that make up StudySync and how they communicate.

```mermaid
C4Container
    title Container Diagram — StudySync Study Group Finder

    Person(student, "Student", "Uses the web app via browser")
    Person(admin, "Administrator", "Uses the web app via browser")

    System_Boundary(studysync, "StudySync System") {

        Container(webapp, "React Web Application", "React, TailwindCSS, Axios, React Router", "Single-Page Application served to the browser. Handles all UI rendering, routing, and REST API calls.")

        Container(api, "Spring Boot REST API", "Java 17, Spring Boot 3, Spring Security, Spring Data JPA", "Provides all backend business logic via RESTful endpoints. Handles authentication, group management, session scheduling, and admin operations.")

        Container(db, "PostgreSQL Database", "PostgreSQL 15", "Stores all persistent data: users, groups, memberships, sessions, and course data.")
    }

    Rel(student, webapp, "Accesses via browser", "HTTPS")
    Rel(admin, webapp, "Accesses via browser", "HTTPS")
    Rel(webapp, api, "Makes REST API calls (JSON over HTTP)", "HTTP/HTTPS, JWT in Authorization header")
    Rel(api, db, "Reads and writes data", "JDBC / JPA / HikariCP connection pool")

    UpdateRelStyle(webapp, api, $textColor="black", $lineColor="#1168BD")
    UpdateRelStyle(api, db, $textColor="black", $lineColor="#1168BD")
```

---

### Level 3 — Component Diagram (Backend: Spring Boot API)

> Shows the internal components of the Spring Boot REST API container.

**Part A — Security, Controllers & Services**

```mermaid
C4Component
    title Backend Component Diagram (Part A) — Controllers and Services

    Container_Ext(webapp, "React Web App", "React SPA", "Sends HTTP requests with JWT tokens")

    Container_Boundary(api, "Spring Boot REST API") {

        Component(securityFilter, "JWT Security Filter", "Spring Security", "Validates JWT on every request. Sets SecurityContext. Rejects invalid tokens with 401.")

        Component(authController, "Auth Controller", "@RestController", "POST /api/auth/register and /api/auth/login")
        Component(groupController, "Group Controller", "@RestController", "CRUD for /api/groups and /api/groups/{id}")
        Component(membershipController, "Membership Controller", "@RestController", "POST /join, PUT /approve, DELETE membership")
        Component(sessionController, "Session Controller", "@RestController", "CRUD for /api/groups/{id}/sessions")
        Component(adminController, "Admin Controller", "@RestController", "Admin endpoints under /api/admin/")

        Component(authService, "Auth Service", "@Service", "JWT generation and BCrypt password hashing")
        Component(groupService, "Group Service", "@Service", "Group creation, search, capacity and privacy checks")
        Component(membershipService, "Membership Service", "@Service", "Join requests, approvals, 5-group limit enforcement")
        Component(sessionService, "Session Service", "@Service", "Create, update and cancel study sessions")
        Component(adminService, "Admin Service", "@Service", "User and group moderation, platform statistics")
    }

    Rel(webapp, securityFilter, "All requests", "HTTPS + JWT")
    Rel(securityFilter, authController, "Public routes", "")
    Rel(securityFilter, groupController, "Authenticated", "")
    Rel(securityFilter, membershipController, "Authenticated", "")
    Rel(securityFilter, sessionController, "Authenticated", "")
    Rel(securityFilter, adminController, "ADMIN role only", "")

    Rel(authController, authService, "Delegates to", "")
    Rel(groupController, groupService, "Delegates to", "")
    Rel(membershipController, membershipService, "Delegates to", "")
    Rel(sessionController, sessionService, "Delegates to", "")
    Rel(adminController, adminService, "Delegates to", "")
```

**Part B — Services & Repositories**

```mermaid
C4Component
    title Backend Component Diagram (Part B) — Services and Repositories

    Container_Ext(db, "PostgreSQL Database", "PostgreSQL 15", "Persistent data store")

    Container_Boundary(api, "Spring Boot REST API") {

        Component(authService, "Auth Service", "@Service", "JWT generation and BCrypt password hashing")
        Component(groupService, "Group Service", "@Service", "Group creation, search, capacity and privacy checks")
        Component(membershipService, "Membership Service", "@Service", "Join requests, approvals, 5-group limit enforcement")
        Component(sessionService, "Session Service", "@Service", "Create, update and cancel study sessions")
        Component(adminService, "Admin Service", "@Service", "User and group moderation, platform statistics")

        Component(userRepo, "User Repository", "@Repository", "findByEmail(), existsByEmail(), standard CRUD")
        Component(groupRepo, "Group Repository", "@Repository", "findByCourseId(), keyword search queries")
        Component(membershipRepo, "Membership Repository", "@Repository", "findByUserId(), findByGroupId()")
        Component(sessionRepo, "Session Repository", "@Repository", "findByGroupId(), upcoming session queries")
    }

    Rel(authService, userRepo, "Reads/writes users", "JPA")
    Rel(groupService, groupRepo, "Reads/writes groups", "JPA")
    Rel(membershipService, membershipRepo, "Reads/writes memberships", "JPA")
    Rel(membershipService, groupRepo, "Reads group capacity", "JPA")
    Rel(sessionService, sessionRepo, "Reads/writes sessions", "JPA")
    Rel(adminService, userRepo, "Reads/updates users", "JPA")
    Rel(adminService, groupRepo, "Reads/deletes groups", "JPA")

    Rel(userRepo, db, "SQL queries", "JDBC")
    Rel(groupRepo, db, "SQL queries", "JDBC")
    Rel(membershipRepo, db, "SQL queries", "JDBC")
    Rel(sessionRepo, db, "SQL queries", "JDBC")
```

---

### Level 3 — Component Diagram (Frontend: React Web Application)

> Shows the internal structure of the React SPA.

**Part A — Routing, Auth Context & Pages**

```mermaid
C4Component
    title Frontend Component Diagram (Part A) — Router and Pages

    Container_Boundary(webapp, "React Web Application") {

        Component(router, "React Router", "react-router-dom", "Manages all client-side routes. Enforces protected routes and public routes.")
        Component(authContext, "Auth Context", "React Context API", "Global state: current user, login(), logout(), JWT token.")

        Component(authPages, "Auth Pages", "React Component", "Login and Register pages at /login and /register")
        Component(dashboard, "Dashboard Page", "React Component", "Main page at /dashboard showing groups and sessions")
        Component(groupPages, "Group Pages", "React Component", "Group search, detail and create at /groups/*")
        Component(sessionPages, "Session Pages", "React Component", "Session schedule and list inside group detail")
        Component(membershipUI, "Membership UI", "React Component", "Join and Leave buttons and pending requests list")
        Component(adminPages, "Admin Pages", "React Component", "User and group management at /admin/*")
    }

    Rel(router, authPages, "Renders /login and /register", "")
    Rel(router, dashboard, "Renders /dashboard", "")
    Rel(router, groupPages, "Renders /groups/*", "")
    Rel(router, sessionPages, "Renders inside group detail", "")
    Rel(router, adminPages, "Renders /admin/*", "")

    Rel(authPages, authContext, "Calls login()", "")
    Rel(dashboard, authContext, "Reads current user", "")
    Rel(groupPages, authContext, "Reads current user", "")
```

**Part B — Pages, API Services & Axios**

```mermaid
C4Component
    title Frontend Component Diagram (Part B) — Services and HTTP Client

    Container_Ext(api, "Spring Boot REST API", "Backend", "Provides JSON REST endpoints")

    Container_Boundary(webapp, "React Web Application") {

        Component(authPages, "Auth Pages", "React Component", "Login and Register pages")
        Component(dashboard, "Dashboard Page", "React Component", "Shows groups and upcoming sessions")
        Component(groupPages, "Group Pages", "React Component", "Group search, detail and create")
        Component(sessionPages, "Session Pages", "React Component", "Session schedule and list")
        Component(membershipUI, "Membership UI", "React Component", "Join, Leave and approval actions")
        Component(adminPages, "Admin Pages", "React Component", "User and group management")

        Component(apiServices, "API Service Modules", "JS Modules", "One service per domain: authService, groupService, membershipService, sessionService, adminService")
        Component(axiosClient, "Axios HTTP Client", "Axios + interceptors", "Attaches JWT to all requests. Redirects to login on 401.")
    }

    Rel(authPages, apiServices, "Calls authService", "")
    Rel(dashboard, apiServices, "Calls groupService, sessionService", "")
    Rel(groupPages, apiServices, "Calls groupService", "")
    Rel(sessionPages, apiServices, "Calls sessionService", "")
    Rel(membershipUI, apiServices, "Calls membershipService", "")
    Rel(adminPages, apiServices, "Calls adminService", "")

    Rel(apiServices, axiosClient, "All HTTP calls through", "")
    Rel(axiosClient, api, "REST calls", "HTTPS + JWT")
```
```

---

### Level 4 — Code Diagram (Core Data Model / Entity Relationships)

> Shows the key JPA entities and their relationships in the backend. This maps directly to the PostgreSQL schema.

```mermaid
classDiagram
    class User {
        +Long userId
        +String name
        +String email
        +String passwordHash
        +Role role
        +Integer yearOfStudy
        +Boolean isActive
        +LocalDateTime createdAt
    }

    class Course {
        +Long courseId
        +String courseCode
        +String courseName
    }

    class UserCourse {
        +Long userId
        +Long courseId
    }

    class StudyGroup {
        +Long groupId
        +String name
        +String description
        +Integer maxCapacity
        +Privacy privacy
        +LocalDateTime createdAt
        +Long creatorId
        +Long courseId
    }

    class Membership {
        +Long membershipId
        +MembershipStatus status
        +LocalDateTime joinedAt
        +Long userId
        +Long groupId
    }

    class StudySession {
        +Long sessionId
        +String title
        +LocalDateTime scheduledAt
        +Integer durationHours
        +String location
        +String notes
        +Long groupId
        +Long createdBy
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

    User "1" --> "0..*" UserCourse : enrolled in
    Course "1" --> "0..*" UserCourse : referenced by
    User "1" --> "0..*" StudyGroup : creates
    Course "1" --> "0..*" StudyGroup : categorizes
    User "1" --> "0..*" Membership : has
    StudyGroup "1" --> "0..*" Membership : has members
    StudyGroup "1" --> "0..*" StudySession : contains
    User "1" --> "0..*" StudySession : creates
    User --> Role : has
    StudyGroup --> Privacy : has
    Membership --> MembershipStatus : has
```

---

## 6. End-to-End Request Flow Examples

### Flow 1: Student Joins a Public Group

```
Browser (React)
  → clicks "Join Group"
  → GroupPages component calls membershipService.join(groupId)
  → axiosClient sends POST /api/groups/{id}/join  [JWT attached]
  → JWT Security Filter validates token → sets SecurityContext
  → MembershipController.joinGroup() called
  → MembershipService checks: group exists? user not already member? under 5-group limit?
  → Creates Membership record (status=ACTIVE for public groups)
  → Returns 201 Created with membership DTO
  → React UI updates member count and shows "Leave Group" button
```

### Flow 2: Group Creator Approves a Join Request (Private Group)

```
Browser (React)
  → Group creator opens join requests panel
  → membershipService.approve(membershipId) called
  → axiosClient sends PUT /api/memberships/{id}/approve  [JWT attached]
  → MembershipController.approveMembership() called
  → MembershipService verifies caller is the group creator
  → Updates Membership status: PENDING → ACTIVE
  → Returns 200 OK
  → Creator UI removes request from pending list
```

### Flow 3: Session Scheduled

```
Browser (React)
  → Group member fills in session form, submits
  → sessionService.create(groupId, sessionData) called
  → POST /api/groups/{id}/sessions  [JWT attached]
  → SessionController.createSession() called
  → SessionService creates StudySession record
  → Returns 201 Created
  → Session appears in the group's session list for all members on next page load
```

---

## 7. Deployment Architecture (Vercel + Railway)

```
┌─────────────────────────────────────────────────────┐
│                  Internet (HTTPS)                   │
│                                                     │
│  ┌──────────────────┐      ┌─────────────────────┐  │
│  │   Vercel         │      │   Railway           │  │
│  │   React App      │────▶│   Spring Boot API    │  │
│  │   (Free Tier)    │      │   Port: 8080        │  │
│  └──────────────────┘      └──────────┬──────────┘  │
│                                        │            │
│                             ┌──────────▼──────────┐ │
│                             │   Railway           │ │
│                             │   PostgreSQL DB     │ │
│                             │   (Plugin)          │ │
│                             └─────────────────────┘ │
└─────────────────────────────────────────────────────┘
```

- **Vercel**: Hosts the React frontend. Connect your GitHub repo and it auto-deploys on every `git push`.
- **Railway (API)**: Hosts the Spring Boot JAR. Detects Java automatically from your GitHub repo.
- **Railway (Database)**: Add a PostgreSQL plugin to your Railway project. Provides a `DATABASE_URL` connection string that Spring Boot reads automatically via `application.properties`.
