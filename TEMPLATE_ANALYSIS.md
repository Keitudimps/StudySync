# TEMPLATE_ANALYSIS.md — GitHub Project Template Analysis and Selection
## StudySync: Study Group Finder System

---

## 1. Overview

GitHub Projects offers several pre-built templates for managing software development workflows. This document evaluates four templates, compares them across key criteria relevant to Agile development, and justifies the selection of the most appropriate template for the StudySync project.

---

## 2. GitHub Project Templates — Comparison Table

| Criteria | Basic Kanban | Automated Kanban | Bug Triage | Team Planning |
|---|---|---|---|---|
| **Default Columns** | To Do, In Progress, Done | To Do, In Progress, Done | Needs Triage, High Priority, Low Priority, Closed | Backlog, Ready, In Progress, In Review, Done |
| **Number of Columns** | 3 | 3 | 4 | 5 |
| **Custom Columns** | Manual only | Manual only | Manual only | Manual only |
| **Automation** | None — all cards moved manually | Issues auto-move to "In Progress" when opened; auto-move to "Done" when closed | Issues auto-move to "Needs Triage" when opened | No built-in automation |
| **Linked Issues** | Yes — drag cards manually | Yes — auto-linked via triggers | Yes — auto-linked on open | Yes — manual linking |
| **Sprint / Milestone Support** | No built-in sprint concept | Partial — milestone filtering possible | No — focused on bugs | Yes — designed around sprint iterations |
| **WIP Limiting** | Not built-in | Not built-in | Not built-in | Not built-in (manual discipline required) |
| **Labels Support** | Yes | Yes | Yes — severity labels (high/low) | Yes |
| **Agile Suitability** | Basic — works for small simple projects | Good — automation reduces manual overhead during active development | Poor — designed for bug management, not feature delivery | Excellent — maps directly to sprint workflow |
| **Best For** | Solo or very small projects with minimal overhead | Teams doing continuous delivery where issues flow quickly | Projects with a high volume of bug reports to triage | Sprint-based Agile teams tracking features across review stages |
| **Weakness** | No automation means cards get forgotten | Only 3 columns — no room for Testing or Blocked states | Not suited for feature development | More columns means more discipline to maintain |

---

## 3. Template Selected: Automated Kanban (Customised)

### 3.1 Chosen Template
**Automated Kanban** — customised with additional columns to match StudySync's development workflow.

### 3.2 Justification

The **Automated Kanban** template was selected for the following reasons:

**1. Automation reduces solo developer overhead.**
StudySync is a solo project. The Automated Kanban template automatically moves issues to "In Progress" when they are opened and to "Done" when they are closed. This means the board stays accurate without requiring constant manual card movement — a significant advantage when one person is simultaneously developer, tester, and project manager.

**2. Issue-driven workflow matches the user story structure from Assignment 6.**
The 25 tasks (T-001 to T-025) defined in AGILE_PLANNING.md are all created as GitHub Issues. The Automated Kanban template is natively designed to link Issues to board cards, making traceability between the sprint plan and the board seamless.

**3. Customisation fills the gaps.**
The default 3 columns (To Do, In Progress, Done) are insufficient for a development workflow that includes testing and blocked states. By adding "Testing" and "Blocked" columns, the board becomes a complete picture of where every task stands — not just whether it is started or finished.

**4. Team Planning was considered but rejected.**
Team Planning has more columns out of the box (5 vs 3), but it has no automation. For a solo developer, manually moving cards through five stages is overhead that adds no value. The Automated Kanban's automation plus two custom columns achieves the same result with less friction.

**5. Bug Triage was eliminated immediately.**
Bug Triage is designed for reactive bug management, not proactive feature delivery. It does not map to user stories, sprints, or the Agile backlog defined in Assignment 6.

### 3.3 Customisation Plan

| New Column | Position | Purpose | WIP Limit |
|---|---|---|---|
| **Blocked** | Between In Progress and Testing | Holds tasks that cannot progress due to a dependency, unclear requirement, or technical blocker. Visibility of blockers prevents them from being invisible inside "In Progress". | 2 |
| **Testing** | Between Blocked and Done | Holds tasks that are code-complete but require manual testing, API verification, or UI review before being marked Done. Enforces a quality gate. | 3 |

**Why these two columns specifically:**
- "Blocked" makes invisible problems visible. Without it, a task stuck waiting on a dependency looks identical to a task actively being worked on.
- "Testing" enforces the Definition of Done from AGILE_PLANNING.md — tasks must be verified before moving to Done. Without this column, "In Progress" and "Done" become the only states, which encourages skipping verification.

---

## 4. Traceability to Prior Assignments

| Assignment | Connection |
|---|---|
| Assignment 4 (SRD.md) | Functional requirements FR-01 to FR-12 become GitHub Issues labelled `functional` |
| Assignment 5 (USE_CASES.md) | Use cases UC-01 to UC-08 inform acceptance criteria on each Issue |
| Assignment 6 (AGILE_PLANNING.md) | User stories US-001 to US-020 and tasks T-001 to T-025 populate the board |
| Assignment 7 (this document) | Automated Kanban template selected and customised with Blocked + Testing columns |
