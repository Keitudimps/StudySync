# ACTIVITY_DIAGRAMS.md — Activity Workflow Modeling
## StudySync: Study Group Finder System

---

## 1. Introduction

This document models 8 complex workflows in the StudySync system using UML activity diagrams. Each diagram shows the start and end points, actions, decision nodes, parallel actions, and swimlanes identifying which actor or system component is responsible for each step.

### Traceability
| Workflow | Linked FR | Linked US | Linked UC |
|---|---|---|---|
| User Registration | FR-01, FR-03 | US-001, US-003 | UC-01, UC-03 |
| Login and Authentication | FR-02 | US-002 | UC-02 |
| Create Study Group | FR-04 | US-005 | UC-04 |
| Search and Join Public Group | FR-05, FR-06 | US-006, US-007 | UC-05, UC-06 |
| Private Group Join Request | FR-07 | US-008, US-009 | UC-07 |
| Schedule Study Session | FR-09 | US-010 | UC-08 |
| Admin Moderates a Group | FR-12 | US-013 | — |
| Edit Academic Profile | FR-03 | US-004 | UC-03 |

---

## 2. Workflow 1 — User Registration

```mermaid
flowchart TD
    Start([Start]) --> A

    subgraph Student["👤 Student"]
        A[Open Register page]
        B[Fill in name, email, password]
        C[Click Register button]
        H[Complete academic profile\nSelect year and courses]
        I[Click Save Profile]
    end

    subgraph System["⚙️ System"]
        D{Email already\nregistered?}
        E[Show error: Email already registered]
        F{Password\n≥ 8 characters?}
        G[Show error: Password too short]
        J[Hash password with BCrypt]
        K[Create User record in database]
        L[Generate JWT token]
        M{At least one\ncourse selected?}
        N[Show error: Select at least one course]
        O[Save UserCourse records]
        P[Redirect to Dashboard]
    end

    A --> B --> C --> D
    D -- Yes --> E --> B
    D -- No --> F
    F -- No --> G --> B
    F -- Yes --> J --> K --> L --> H
    H --> I --> M
    M -- No --> N --> H
    M -- Yes --> O --> P --> End([End])
```

### Explanation

This workflow covers the complete onboarding flow from first visiting the register page to landing on the dashboard. Two key decision nodes enforce data integrity: the duplicate email check prevents multiple accounts per address (FR-01 acceptance criteria), and the password length check enforces the minimum security requirement. The parallel concern of profile completion is handled sequentially — the student cannot reach the dashboard without completing their profile. This directly addresses the New Student stakeholder concern of a clear, guided onboarding experience.

**Stakeholder concern addressed:** New Student needs to find and join groups quickly — this workflow ensures their profile is complete before they can search.

---

## 3. Workflow 2 — Login and Authentication

```mermaid
flowchart TD
    Start([Start]) --> A

    subgraph Student["👤 Student"]
        A[Open Login page]
        B[Enter email and password]
        C[Click Login]
        I[Access protected feature]
    end

    subgraph System["⚙️ System"]
        D{User record\nexists for email?}
        E[Return HTTP 401:\nInvalid email or password]
        F{Password matches\nBCrypt hash?}
        G{Account\nactive?}
        H[Return HTTP 403:\nAccount deactivated]
        J[Generate JWT token — 24hr expiry]
        K[Return JWT to client]
        L[Store JWT in browser memory]
        M[Redirect to Dashboard]
        N{JWT valid and\nnot expired?}
        O[Return HTTP 401:\nToken expired]
        P[Attach JWT to request header]
        Q[Process request]
    end

    A --> B --> C --> D
    D -- No --> E --> B
    D -- Yes --> F
    F -- No --> E
    F -- Yes --> G
    G -- No --> H --> End1([End])
    G -- Yes --> J --> K --> L --> M
    M --> I --> N
    N -- No --> O --> A
    N -- Yes --> P --> Q --> End2([End])
```

### Explanation

This workflow has two phases — the initial login flow and the subsequent authenticated request flow. The three decision nodes guard against the three failure cases defined in FR-02: non-existent email, wrong password, and deactivated account. Importantly, the error message for both "user not found" and "wrong password" is identical ("Invalid email or password") — this is a deliberate security measure to prevent email enumeration attacks. The second phase shows how every subsequent request uses the stored JWT, addressing the stateless architecture requirement in NFR-SC2.

**Stakeholder concern addressed:** IT Staff concern about security — BCrypt validation and JWT expiry enforce the authentication NFRs.

---

## 4. Workflow 3 — Create Study Group

```mermaid
flowchart TD
    Start([Start]) --> A

    subgraph Student["👤 Group Creator"]
        A[Navigate to Create Group page]
        B[Enter group name and description]
        C[Select course from dropdown]
        D[Set max capacity between 2 and 50]
        E[Choose privacy: PUBLIC or PRIVATE]
        F[Click Create Group]
    end

    subgraph System["⚙️ System"]
        G{All required\nfields present?}
        H[Show validation errors]
        I{Capacity\nbetween 2–50?}
        J[Show error: Capacity must be 2–50]
        K[Create StudyGroup record in database]
        L[Create Membership record:\ncreator with ACTIVE status]
        M{Group is\nPUBLIC?}
        N[Index group in public search results]
        O[Redirect to Group Detail page]
    end

    A --> B --> C --> D --> E --> F --> G
    G -- No --> H --> B
    G -- Yes --> I
    I -- No --> J --> D
    I -- Yes --> K --> L --> M
    M -- Yes --> N --> O --> End([End])
    M -- No --> O
```

### Explanation

Group creation is a straightforward linear workflow with two validation gates. The capacity validation is a separate decision node from the general field validation because it has a specific numeric constraint (2–50) that warrants its own error message. The split after group creation based on privacy setting reflects a real functional difference — only public groups are indexed for search, which is what drives the FR-05 search feature. The creator's automatic membership creation is shown as a parallel system action, not a user action, making it clear this happens without any additional user input.

**Stakeholder concern addressed:** Group Creator needs to quickly set up a group with the right privacy settings — the workflow makes both paths (public/private) explicit.

---

## 5. Workflow 4 — Search and Join Public Group

```mermaid
flowchart TD
    Start([Start]) --> A

    subgraph Student["👤 Student"]
        A[Navigate to Find Groups page]
        B[Enter course code or keyword]
        C[Click Search]
        H[Browse results]
        I[Click on a group to view details]
        J[Click Join Group button]
    end

    subgraph System["⚙️ System"]
        D[Query database for PUBLIC groups\nmatching course or keyword]
        E{Results\nfound?}
        F[Display No groups found message]
        G[Display results with name,\ncourse, member count, Full badge]
        K{Student already\na member?}
        L[Show error: Already a member]
        M{Group\nfull?}
        N[Show disabled Join button:\nGroup is full]
        O{Student in\n5 groups already?}
        P[Show error: 5-group limit reached]
        Q[Create Membership record\nstatus: ACTIVE]
        R[Update member count display]
        S[Show Leave Group button]
    end

    A --> B --> C --> D --> E
    E -- No --> F --> B
    E -- Yes --> G --> H --> I --> J --> K
    K -- Yes --> L --> H
    K -- No --> M
    M -- Yes --> N --> H
    M -- No --> O
    O -- Yes --> P --> H
    O -- No --> Q --> R --> S --> End([End])
```

### Explanation

This workflow is the core value loop of StudySync — the primary way a student finds and joins a study group. Four sequential guard checks ensure data integrity before the membership is created: existing membership, group capacity, and the 5-group limit. Each failure path loops back to browsing results, not back to the search input, because the student has already found groups they are interested in. The parallel actions of updating member count and showing the Leave button reflect immediate UI feedback without requiring a page refresh.

**Stakeholder concern addressed:** Student and New Student pain point — finding relevant groups quickly. The search returns results in under 1 second (NFR-P2) and shows capacity status upfront.

---

## 6. Workflow 5 — Private Group Join Request

```mermaid
flowchart TD
    Start([Start]) --> A

    subgraph Student["👤 Student"]
        A[Find PRIVATE group in search]
        B[View group detail page]
        C[Click Request to Join]
        K[Wait for creator decision]
    end

    subgraph System["⚙️ System"]
        D{Student already\npending or active\nmember?}
        E[Show error: Request already submitted]
        F{Group\nfull?}
        G[Show disabled button: Group is full]
        H[Create Membership record\nstatus: PENDING]
        I[Show: Request sent — awaiting approval]
    end

    subgraph GroupCreator["👤 Group Creator"]
        J[Open group management panel]
        L[View pending requests list]
        M{Approve or\nReject?}
        N[Click Approve]
        O[Click Reject]
    end

    subgraph System2["⚙️ System — Response"]
        P{Group still\nhas capacity?}
        Q[Show error: Group now full]
        R[Update Membership status\nto ACTIVE]
        S[Delete Membership record]
        T[Requester sees: Request approved]
        U[Requester sees: Request declined]
    end

    A --> B --> C --> D
    D -- Yes --> E --> B
    D -- No --> F
    F -- Yes --> G --> B
    F -- No --> H --> I --> K
    K --> J --> L --> M
    M -- Approve --> N --> P
    P -- No --> Q --> L
    P -- Yes --> R --> T --> End1([End])
    M -- Reject --> O --> S --> U --> End2([End])
```

### Explanation

This is the most actor-rich workflow in the system, involving both the requesting student and the group creator as active participants. The workflow is split across two sessions — the student submits the request and then waits, while the creator reviews asynchronously. The guard condition on approval (`group still has capacity`) handles the race condition where a group fills up while a request is pending — a scenario that could cause data inconsistency without this check. The two terminal paths (approved and rejected) both clean up correctly.

**Stakeholder concern addressed:** Group Creator's need to control group membership quality. Student's need for clear feedback on their request status.

---

## 7. Workflow 6 — Schedule Study Session

```mermaid
flowchart TD
    Start([Start]) --> A

    subgraph Member["👤 Group Member"]
        A[Navigate to group detail page]
        B[Click Schedule Session]
        C[Enter title, date, time, duration]
        D[Enter location: address or URL]
        E[Enter optional notes or agenda]
        F[Click Create Session]
    end

    subgraph System["⚙️ System"]
        G{Is user an\nACTIVE member\nof this group?}
        H[Return HTTP 403: Access denied]
        I{Title\nprovided?}
        J[Show error: Title is required]
        K{Scheduled time\nat least 30 mins\nin future?}
        L[Show error: Must be at least 30 mins in future]
        M[Create StudySession record]
        N[Display session in upcoming list\nfor all group members]
        O[Show confirmation to creator]
    end

    A --> B --> G
    G -- No --> H --> End1([End])
    G -- Yes --> C --> D --> E --> F --> I
    I -- No --> J --> C
    I -- Yes --> K
    K -- No --> L --> C
    K -- Yes --> M --> N & O
    N --> End2([End])
    O --> End2
```

### Explanation

Session scheduling has two access control checks before any input validation — the system first verifies the requester is an active member before showing them the form (preventing unauthorised access) and then validates the future-time constraint. The parallel fork at the end — updating the sessions list for all members AND showing a confirmation to the creator simultaneously — reflects a real concurrent system action where both happen in the same API response. This addresses the scalability concern from the IT Staff stakeholder: all members see the session without requiring individual requests.

**Stakeholder concern addressed:** Lecturer and Group Creator stakeholders want sessions tied to specific courses and viewable by all members immediately.

---

## 8. Workflow 7 — Admin Moderates a Group

```mermaid
flowchart TD
    Start([Start]) --> A

    subgraph Admin["👤 Platform Administrator"]
        A[Navigate to /admin/groups]
        B[Search or filter groups]
        C[Click on target group]
        D[Review group details:\nname, course, members, sessions]
        E{Decision?}
        F[Click Delete Group]
        G[Confirm deletion in dialog]
    end

    subgraph System["⚙️ System"]
        H[Load all groups including private ones]
        I[Display group list with\nname, course, member count, creator]
        J{Admin\nconfirms?}
        K[Cancel — no action]
        L[Delete StudyGroup record]
        M[Cascade delete all\nMembership records for group]
        N[Cascade delete all\nStudySession records for group]
        O[Remove group from\npublic search index]
        P[Show success: Group deleted]
    end

    A --> H --> I --> B --> C --> D --> E
    E -- Keep group --> End1([End — no change])
    E -- Delete --> F --> G --> J
    J -- No --> K --> End2([End — cancelled])
    J -- Yes --> L
    L --> M & N
    M --> O
    N --> O
    O --> P --> End3([End])
```

### Explanation

The admin moderation workflow includes a confirmation dialog step before the irreversible delete action — this is a safeguard against accidental deletion. The cascade delete is shown as parallel actions (memberships and sessions deleted simultaneously) because in the database implementation these are handled by foreign key cascade constraints rather than sequential queries. The parallel merge before showing success ensures the admin only sees the confirmation once both cascades are complete. This workflow directly addresses the University Administrator's concern about being able to remove inappropriate groups quickly.

**Stakeholder concern addressed:** University Administrator and Platform Administrator need to enforce academic policy by removing inappropriate or inactive groups. FR-12 acceptance criteria requires cascade deletion to leave no orphan records.

---

## 9. Workflow 8 — Edit Academic Profile

```mermaid
flowchart TD
    Start([Start]) --> A

    subgraph Student["👤 Student"]
        A[Navigate to Profile page]
        B[Click Edit Profile]
        C[Change year of study\nor modify course selection]
        D[Click Save Changes]
        H[Review updated profile]
    end

    subgraph System["⚙️ System"]
        E{Year of study\nbetween 1–4?}
        F[Show error: Year must be 1–4]
        G{At least one\ncourse selected?}
        I[Show error: At least one course required]
        J[Delete existing UserCourse records\nfor this student]
        K[Create new UserCourse records\nfor selected courses]
        L[Update yearOfStudy on User record]
        M[Show success: Profile updated]
    end

    A --> B --> C --> D --> E
    E -- No --> F --> C
    E -- Yes --> G
    G -- No --> I --> C
    G -- Yes --> J & L
    J --> K
    K --> M
    L --> M
    M --> H --> End([End])
```

### Explanation

Profile editing uses a replace-all strategy for course enrolments — rather than calculating a diff between old and new courses, the system deletes all existing UserCourse records and recreates them from the new selection. This is shown as a parallel action alongside updating the year of study. This approach is simpler to implement and equally correct since the user always sees the full list of their enrolled courses. The two guard conditions (year range and minimum one course) match exactly the constraints defined in UC-03 and US-004.

**Stakeholder concern addressed:** Student needs to update their courses at the start of each new semester without losing their account or group memberships. Lecturer stakeholder benefits from accurate course data ensuring groups remain linked to active modules.
