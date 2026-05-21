# Changelog - Assignment 10

## [1.0.0] - 2024-12-XX

### Added - Class Implementation (30 marks)
- Domain classes: User, StudyGroup, Membership, StudySession, Course, UserCourse
- Enums: Role, Privacy, MembershipStatus
- Business methods matching CLASS_DIAGRAM.md

### Added - Creational Patterns (40 marks)
- Simple Factory: NotificationFactory with Email/SMS/Push
- Factory Method: PaymentProcessor with CreditCard/PayPal/Crypto
- Abstract Factory: GUIFactory with Windows/Mac implementations
- Builder: StudyGroupBuilder for complex object construction
- Prototype: GroupTemplateRegistry with cloning support
- Singleton: DatabaseConnection (Bill Pugh thread-safe)

### Added - Unit Tests (20 marks)
- 5 tests for Simple Factory
- 4 tests for Factory Method
- 4 tests for Abstract Factory
- 4 tests for Builder
- 4 tests for Prototype
- 4 tests for Singleton

### Changed
- Updated README with pattern justifications

### Fixed
- N/A (initial implementation)

---

## [Assignment 11] — Repository Layer Implementation

### Added

**Repository Interfaces** (`backend/src/main/java/com/studysync/repository/`)
- `Repository.java` — Generic interface with `save`, `findById`, `findAll`, `deleteById`, `existsById`, `count`
- `UserRepository.java` — Adds `findByEmail`, `existsByEmail`, `findByRole`, `findAllActive`
- `StudyGroupRepository.java` — Adds `findByCourseId`, `findPublicGroups`, `findByCreatorId`, `searchByName`, `findGroupsWithAvailableSpace`
- `MembershipRepository.java` — Adds `findByUserId`, `findByGroupId`, `findByStatus`, `findByUserIdAndGroupId`, `countActiveByUserId`
- `StudySessionRepository.java` — Adds `findByGroupId`, `findUpcomingSessions`, `findPastSessions`, `findByCreatedBy`
- `CourseRepository.java` — Adds `findByCourseCode`, `existsByCourseCode`, `searchByKeyword`

**In-Memory Implementations** (`backend/src/main/java/com/studysync/repository/inmemory/`)
- `InMemoryUserRepository.java` — HashMap + AtomicLong ID counter
- `InMemoryStudyGroupRepository.java`
- `InMemoryMembershipRepository.java`
- `InMemoryStudySessionRepository.java`
- `InMemoryCourseRepository.java`

**Storage Factory** (`backend/src/main/java/com/studysync/factory/`)
- `RepositoryFactory.java` — Returns MEMORY, DATABASE, or FILESYSTEM implementation per entity

**Future-Proofing Stubs** (`backend/src/main/java/com/studysync/repository/stubs/`)
- `DatabaseUserRepository.java`, `DatabaseStudyGroupRepository.java`, `DatabaseMembershipRepository.java`, `DatabaseStudySessionRepository.java`, `DatabaseCourseRepository.java`
- `FileSystemUserRepository.java`, `FileSystemStudyGroupRepository.java`

**Tests** (`backend/src/test/java/com/studysync/repository/`)
- `InMemoryUserRepositoryTest.java` — 8 tests
- `InMemoryStudyGroupRepositoryTest.java` — 6 tests
- `InMemoryMembershipRepositoryTest.java` — 5 tests
- `InMemoryStudySessionRepositoryTest.java` — 4 tests
- `RepositoryFactoryTest.java` — 8 tests

### Modified
- `domain/User.java` — Added `setUserId(Long)`
- `domain/StudyGroup.java` — Added `setGroupId(Long)`
- `domain/Membership.java` — Added `setMembershipId(Long)`
- `domain/StudySession.java` — Added `setSessionId(Long)`
- `domain/Course.java` — Added `setCourseId(Long)`

### Test Results
```
Tests run: 60 total (29 creational + 31 repository), Failures: 0, Errors: 0
BUILD SUCCESS
```

## Assignment 12 — Service Layer and REST API Implementation

### Added
- Added Spring Boot REST API endpoints for Users, Study Groups, and Study Sessions.
- Added service-layer business logic for registration, group creation, group availability, session scheduling, rescheduling, cancellation, and deletion rules.
- Added `RepositoryConfig` to wire Assignment 11 in-memory repositories into Spring Boot services.
- Added OpenAPI/Swagger documentation in `docs/openapi.yaml`.
- Added Swagger screenshot instruction note in `docs/SWAGGER_SCREENSHOT_NOTE.md`.

### Fixed
- Fixed user update logic so existing users are updated instead of being replaced incorrectly.
- Fixed user deactivation logic so DELETE `/api/users/{userId}` now marks the user inactive and saves the change.
- Fixed group update logic so group name/description changes are applied.
- Fixed session update logic so title, location, and notes changes are applied.
- Changed Maven compiler target to Java 21 for better Spring Boot compatibility.
