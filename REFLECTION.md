# REFLECTION.md — Challenges in Balancing Stakeholder Needs
## StudySync: Study Group Finder System

---

## 1. Introduction

Requirements engineering is rarely straightforward. While it is easy to list what each stakeholder wants in isolation, the real challenge emerges when those needs conflict, contradict, or compete for the same limited development resources. This reflection documents the key tensions encountered while defining the requirements for StudySync and explains the trade-off decisions made.

---

## 2. Challenge 1: Openness vs. Access Control

**The conflict:** General students and especially first-year students want to join study groups instantly with as little friction as possible. The simpler the join process, the more likely they are to use the platform. However, group creators want control over who enters their group — particularly for groups preparing for specific assessments where strangers could disrupt the dynamic.

**How it was resolved:** The solution was to support two group types rather than forcing a single model on everyone. PUBLIC groups allow instant joining with no approval required, while PRIVATE groups route join requests through the creator for manual approval. This respects both the student's desire for ease of access and the creator's need for gatekeeping — the creator simply chooses which model fits their group when creating it.

**Lesson learned:** When two stakeholders want opposite things, the answer is often a configurable option rather than choosing one stakeholder over the other.

---

## 3. Challenge 2: Simplicity vs. Feature Depth

**The conflict:** IT/DevOps staff strongly favour a simple, lean system that is easy to deploy, monitor, and maintain. Every additional feature adds complexity, more database tables, more API endpoints, and more potential failure points. On the other hand, group creators and students wanted richer features such as in-app notifications, a chat system within groups, and file sharing for notes.

**How it was resolved:** Notifications and chat were deliberately removed from the v1 scope. The justification is rooted in the Agile principle of delivering a Minimum Viable Product (MVP) first and iterating. The core value of StudySync — connecting students to study groups and sessions — does not require real-time chat or notifications to function. These features are marked as future enhancements. This kept the system feasible for a single developer within a semester while satisfying IT staff's preference for a maintainable codebase.

**Lesson learned:** Every feature request must be weighed against the cost of maintaining it. Stakeholder needs should be prioritised by their impact on the system's core purpose.

---

## 4. Challenge 3: Privacy vs. Administrative Oversight

**The conflict:** Students reasonably expect that a group they mark as PRIVATE is not accessible to people outside it — including administrators. However, the university administrator and platform administrator need full visibility into all groups to enforce academic policies, moderate inappropriate content, and respond to complaints.

**How it was resolved:** The privacy setting controls what other students can see and join — not what administrators can see. Administrators can view all groups regardless of privacy setting via the admin dashboard, but they are restricted to seeing group metadata (name, course, members, sessions) rather than any private communications. This is also justified by the fact that StudySync does not have a chat feature, so there is no truly sensitive content — only group information that the institution has a legitimate interest in monitoring.

**Lesson learned:** "Private" in a university context means private from peers, not private from the institution. This distinction needed to be made explicit in both the requirements and the system design.

---

## 5. Challenge 4: University Email Enforcement vs. Accessibility

**The conflict:** The university administrator wants all accounts tied to verified university email addresses to prevent non-students from accessing the platform. However, this creates a barrier for exchange students, part-time students, or students whose institutional email has not yet been activated.

**How it was resolved:** For v1, the system validates that the email matches an expected format (e.g., ending in `@university.ac.za`) but does not perform live verification against an external student database or SSO system. This is a deliberate compromise — it prevents obviously invalid registrations while not blocking edge cases that a full SSO integration would handle. Integration with the university's Single Sign-On (SSO) system is noted as a future enhancement once the core system is proven.

**Lesson learned:** Perfect enforcement is sometimes the enemy of practical deployment. A good-enough solution that ships is more valuable than a perfect solution that never does.

---

## 6. Challenge 5: Performance Expectations vs. Free-Tier Infrastructure

**The conflict:** IT staff and the performance non-functional requirements call for sub-500ms API responses and support for 200 concurrent users. However, the system is deployed on Railway's free tier, which has limited compute resources and may experience cold starts (where the server takes several seconds to wake up after a period of inactivity).

**How it was resolved:** The NFRs are written as targets for a production-grade deployment, not constraints of the free tier. For the purposes of this assignment and development phase, the free tier is acceptable. The requirements document makes clear that the system is architecturally stateless (enabling horizontal scaling) and that moving to a paid tier or a VPS would immediately satisfy the performance NFRs without code changes. The architecture does not create any technical debt that would prevent scaling.

**Lesson learned:** Requirements should describe what the system needs to achieve at scale, even if the current deployment does not yet reach that scale. Architecture decisions made now should not block future performance improvements.

---

## 7. Summary of Key Trade-offs

| Trade-off | Decision Made | Rationale |
|---|---|---|
| Instant join vs. approval control | Both supported via PUBLIC/PRIVATE setting | Respects both stakeholder needs without compromise |
| Rich features vs. lean system | Notifications and chat deferred to v2 | MVP focus; feasible for one developer |
| Student privacy vs. admin oversight | Admin sees all groups; privacy applies to peers only | Institution has legitimate oversight rights |
| Strict email enforcement vs. accessibility | Format validation only; SSO deferred | Pragmatic v1 solution; SSO planned for future |
| High performance vs. free-tier hosting | NFRs target production scale; free tier used for development | Architecture supports scaling without code changes |

---

## Assignment 7 Reflection — Challenges in Selecting and Customising a GitHub Project Template

### Challenge 1: Choosing Between Simplicity and Completeness

The first difficulty was deciding between the Basic Kanban and the Automated Kanban templates. Basic Kanban is simpler — three columns, no automation, nothing to configure. For a first-time GitHub Projects user, it is the path of least resistance. However, for a project with 25 tasks spread across a 2-week sprint, manually moving every card through every stage would create enough friction that the board would quickly fall out of sync with reality.

The Automated Kanban template solves this by auto-moving issues when they are opened and closed. The trade-off is that automation can create a false sense of progress — a card moving to "Done" because an issue was closed does not mean the feature actually works. The resolution was to add a "Testing" column as a mandatory gate before "Done", so automation gets the card to Testing but a human decision is still required to move it to Done.

### Challenge 2: GitHub Templates vs. Trello and Jira

GitHub Projects is tightly integrated with the codebase — issues, pull requests, and milestones all live in the same place, making traceability effortless. Closing a PR automatically closes an issue, which moves a card. This end-to-end link from code to board is GitHub's strongest advantage.

Trello is more flexible and visually polished, with drag-and-drop that feels more natural than GitHub's interface. However, Trello has no native code integration — linking a Trello card to a GitHub commit requires a third-party power-up. For a solo developer where the board and the code repository are the same product, that disconnection is a real cost.

Jira is the industry standard for large teams — it has sophisticated sprint planning, velocity tracking, burndown charts, and deep customisation. But Jira's complexity is genuinely overkill for a solo academic project. The overhead of configuring Jira boards, epics, and sprint reports would consume time better spent building the system. GitHub Projects offers approximately 70% of Jira's value at 10% of the setup cost, which is the right trade-off for this context.

### Challenge 3: WIP Limits Without Enforcement

GitHub Projects does not enforce WIP limits natively — there is no built-in mechanism that prevents a developer from dragging a fourth card into a column with a WIP limit of 3. The limits exist only as column header annotations and as personal discipline.

This is a genuine weakness compared to tools like Kanbanize or LeanKit, which can hard-block card movement when a column is full. The practical mitigation is documentation: by writing the WIP limits clearly in KANBAN_EXPLANATION.md and in the column headers, the constraint becomes a visible commitment rather than an invisible rule. Whether that commitment is honoured depends entirely on the developer's self-discipline — which is, ultimately, the real challenge of solo Agile.


