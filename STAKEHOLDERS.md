# STAKEHOLDERS.md — StudySync: Study Group Finder System

---

## 1. Overview

This document identifies and analyses all stakeholders for the StudySync platform. Each stakeholder's role, key concerns, pain points, and measurable success metrics are documented to ensure the system's requirements are grounded in real needs.

---

## 2. Stakeholder Analysis Table

| # | Stakeholder | Role | Key Concerns | Pain Points | Success Metrics |
|---|---|---|---|---|---|
| 1 | **Student (General)** | Primary end-user. Searches for and joins study groups, attends sessions. | Easily find relevant study groups by course. Have a reliable schedule of sessions. | Currently relies on WhatsApp groups or notice boards which are disorganised and hard to find. No way to know who else is studying the same course. | 80% of students can find a relevant group within 2 minutes of registering. Group search returns results in under 1 second. |
| 2 | **Group Creator (Student)** | A student who creates and manages a study group. Controls membership and schedules sessions. | Ability to control who joins their group. Easy session scheduling with clear location details. | No existing tool to manage a study group outside of chat apps where messages get lost. Difficult to track who confirmed attendance. | Group creator can schedule a session in under 3 clicks. Membership approval workflow completes in under 2 seconds. |
| 3 | **University Administrator** | Institutional stakeholder. Ensures the platform aligns with academic policies and does not facilitate misconduct. | Platform must not enable cheating or sharing of exam answers. User accounts must be tied to valid university emails. | No visibility into peer study platforms currently used by students. Risk of unofficial platforms creating data privacy issues. | 100% of registered accounts use a valid university email domain. Admin can deactivate any account within 30 seconds. |
| 4 | **Platform Administrator** | Internal admin user. Manages users, moderates groups, and monitors platform health. | Ability to quickly identify and remove inappropriate groups or inactive accounts. Access to platform usage statistics. | No centralised dashboard currently exists to oversee user activity or group quality. | Admin dashboard loads all users and groups in under 2 seconds. Inappropriate group can be deleted in under 5 clicks. |
| 5 | **IT / DevOps Staff** | Responsible for deploying, maintaining, and monitoring the system infrastructure. | System must be easy to deploy without complex infrastructure. Logs must be accessible for debugging. | Many university systems require complex on-premise setups. Lack of documentation leads to long onboarding times for new developers. | System deployable on Railway and Vercel in under 30 minutes using GitHub repo. API documentation available and up to date. |
| 6 | **Lecturer / Academic Staff** | Indirect stakeholder. Benefits from students collaborating effectively outside class. | Study groups should be organised around course codes that match actual taught courses. Platform should not distract from academic work. | No structured way for students to self-organise study sessions tied to specific courses. | At least 60% of active groups are tied to a valid course code. Platform usage peaks align with exam and assignment periods. |
| 7 | **New / First-Year Student** | A student unfamiliar with peers and campus. Needs help finding study partners quickly. | Simple onboarding. Ability to find groups without knowing anyone on the platform. | Social anxiety and not knowing who to approach for study. No centralised discovery tool. | New student can complete registration and join their first group within 5 minutes. At least one recommended group shown on first login. |

---

## 3. Stakeholder Concerns Summary

### Conflicts and Trade-offs

| Conflict | Stakeholder A | Stakeholder B | Resolution |
|---|---|---|---|
| Open vs Controlled Access | Student (General) — wants instant join | Group Creator — wants approval control | Support both PUBLIC (instant join) and PRIVATE (approval required) group types |
| Platform Openness vs Security | New Student — wants easy signup | University Administrator — wants verified emails only | Enforce university email format validation on registration |
| Feature Richness vs Simplicity | Group Creator — wants detailed session management | IT Staff — wants minimal complexity to maintain | Keep core features lean; avoid over-engineering in v1 |
| Data Visibility vs Privacy | Platform Admin — needs oversight | Student — expects private group privacy | Admin can see all groups but cannot read private chat; no chat feature in scope |
