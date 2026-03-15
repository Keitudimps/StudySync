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

```mermaid
flowchart TB
    WebApp["🌐 React Web App\n(React SPA)"]
    DB[("🗄️ PostgreSQL Database")]

    subgraph API["Spring Boot REST API"]
        direction TB

        SEC["🔒 JWT Security Filter\nSpring Security / OncePerRequestFilter"]

        subgraph CONTROLLERS["Controllers Layer"]
            direction LR
            AC["Auth Controller\nPOST /api/auth/register\nPOST /api/auth/login"]
            GC["Group Controller\nGET POST /api/groups\nGET DELETE /api/groups/{id}"]
            MC["Membership Controller\nPOST /api/groups/{id}/join\nPUT /api/memberships/{id}/approve"]
            SC["Session Controller\nPOST GET /api/groups/{id}/sessions\nPUT DELETE /api/sessions/{id}"]
            ADC["Admin Controller\nGET DELETE /api/admin/users\nGET DELETE /api/admin/groups"]
        end

        subgraph SERVICES["Services Layer"]
            direction LR
            AS["Auth Service\nJWT generation\nBCrypt password hashing"]
            GS["Group Service\nGroup CRUD\nSearch & privacy logic"]
            MS["Membership Service\nJoin requests & approvals\n5-group limit enforcement"]
            SS["Session Service\nCreate, update\ncancel sessions"]
            ADS["Admin Service\nUser & group moderation\nPlatform statistics"]
        end

        subgraph REPOS["Repository Layer  —  Spring Data JPA"]
            direction LR
            UR["User Repository\nfindByEmail()"]
            GR["Group Repository\nfindByCourseId()"]
            MR["Membership Repository\nfindByUserId()"]
            SR["Session Repository\nfindByGroupId()"]
        end
    end

    WebApp -->|"HTTPS + JWT Bearer Token"| SEC

    SEC -->|"Public routes"| AC
    SEC -->|"Authenticated"| GC
    SEC -->|"Authenticated"| MC
    SEC -->|"Authenticated"| SC
    SEC -->|"ADMIN role only"| ADC

    AC --> AS
    GC --> GS
    MC --> MS
    SC --> SS
    ADC --> ADS

    AS --> UR
    GS --> GR
    MS --> MR
    MS --> GR
    SS --> SR
    ADS --> UR
    ADS --> GR

    UR -->|"JDBC / JPA"| DB
    GR -->|"JDBC / JPA"| DB
    MR -->|"JDBC / JPA"| DB
    SR -->|"JDBC / JPA"| DB
```

---

### Level 3 — Component Diagram (Frontend: React Web Application)

> Shows the internal structure of the React SPA.

```mermaid
C4Component
    title Component Diagram — React Web Application (Frontend)

    Container_Ext(api, "Spring Boot REST API", "Backend", "Provides JSON REST endpoints")

    Container_Boundary(webapp, "React Web Application") {

        Component(router, "React Router", "react-router-dom", "Manages client-side routing. Defines protected routes (require auth) and public routes (login/register).")

        Component(authPages, "Auth Pages", "React Components", "Login and Register pages. On success, stores JWT token in localStorage and redirects to dashboard.")

        Component(axiosClient, "Axios HTTP Client", "Axios instance with interceptors", "Centralized HTTP client. Automatically attaches JWT Bearer token to all outgoing requests. Handles 401 responses by redirecting to login.")

        Component(authContext, "Auth Context", "React Context API", "Global authentication state: current user object, login(), logout() functions. Consumed by all protected components.")

        Component(dashboard, "Dashboard Page", "React Component", "Main landing page after login. Shows the student's groups, upcoming sessions, and unread notification count.")

        Component(groupPages, "Group Pages", "React Components", "Group list/search page, group detail page, and create group form. Allows browsing public groups and managing owned groups.")

        Component(sessionPages, "Session Pages", "React Components", "Session scheduling form and session list view within a group page.")

        Component(membershipUI, "Membership UI Components", "React Components", "Join/Leave buttons, join request list for group creators, member list view.")

        Component(adminPages, "Admin Dashboard Pages", "React Components", "User management table, group management table, and platform statistics. Only rendered for users with ADMIN role.")

        Component(apiServices, "API Service Modules", "JavaScript modules (authService, groupService, etc.)", "Thin wrappers around Axios calls. One module per domain entity. Used by all page components.")
    }

    Rel(router, authPages, "Renders on /login and /register", "")
    Rel(router, dashboard, "Renders on /dashboard (protected)", "")
    Rel(router, groupPages, "Renders on /groups/* (protected)", "")
    Rel(router, sessionPages, "Renders inside group detail (protected)", "")
    Rel(router, adminPages, "Renders on /admin/* (ADMIN only)", "")

    Rel(authPages, authContext, "Calls login() / stores token", "")
    Rel(dashboard, authContext, "Reads current user", "")
    Rel(groupPages, authContext, "Reads current user for ownership checks", "")

    Rel(dashboard, apiServices, "Calls groupService.getMyGroups(), sessionService.getUpcoming()", "")
    Rel(groupPages, apiServices, "Calls groupService.search(), groupService.create()", "")
    Rel(sessionPages, apiServices, "Calls sessionService.create(), sessionService.getByGroup()", "")
    Rel(membershipUI, apiServices, "Calls membershipService.join(), approve(), leave()", "")
    Rel(adminPages, apiServices, "Calls adminService.getUsers(), deleteGroup()", "")

    Rel(apiServices, axiosClient, "Uses for all HTTP calls", "")
    Rel(axiosClient, api, "Sends HTTP requests with JWT", "HTTPS / JSON")
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
