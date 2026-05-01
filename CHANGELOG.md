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
