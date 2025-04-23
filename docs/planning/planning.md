# Planning

## Overview

The Shodrone project is developed over the 4th semester (2nd year, 2nd semester) of the Degree in Informatics Engineering (LEI) at ISEP during the 2024-2025 academic year. It follows a Project-Based Learning (PBL) approach using Scrum methodology (NFR01), integrating five course units (UCs): Applications Engineering (EAPLI), Laboratory and Project IV (LAPR4), Languages and Programming (LPROG), Computer Networks (RCOMP), and Computer Systems (SCOMP). The project is divided into three sprints, each with specific deadlines and UC participation, culminating in a fully functional system by June 15, 2025.

## Team Composition

- **Team Size**: 5 students
  The team consists of students identified in the following table.

| Student Number | Name                 |
|----------------|----------------------|
| **1230564**    | Francisco dos Santos |
| **1230839**    | Emanuel Almeida      |
| **1231274**    | Jorge Ubaldo         |
| **1230444**    | Romeu Xu             |
| **1231498**    | Paulo Mendes         |

- **Roles**:
    - **Scrum Master**: LAPR4 PL teacher (weekly meetings during LAPR4 PL+OT class).
    - **Development Team**: 5 students responsible for analysis, design, implementation, testing, and documentation.

- **Formation**: Teams are finalized within the first two weeks of the semester (by mid-March 2025) and validated by the LAPR4 PL teacher via Moodle submission.

## Task Distribution

Throughout the project development period, the distribution of _tasks / requirements / features_ by the team members was carried out as described in the following table.


| Task                        | [Sprint A]()                                                                                    | [Sprint B]() | [Sprint C]() |
|-----------------------------|-------------------------------------------------------------------------------------------------|--------------|--------------|
| Glossary                    | [1230444](global-artifacts/01.requirements-engineering/glossary.md)                             |              |              |
| Use Case Diagram (UCD)      | [1231274](global-artifacts/01.requirements-engineering/use-case-diagram.md)                     |              |              |
| Supplementary Specification | [1231274](global-artifacts/01.requirements-engineering/supplementary-specification.md)          |              |              |
| DDD                         | [All team members](sprint1/us110/Readme.md)                                                     |              |              |
| Readme.md                   | [1231274](../../readme.md)                                                                      |              |              |
| Planning.md                 | [1231274](planning.md)                                                                          |              |              |
| Sprint1 US's documentation  | [1231274](sprint1)                                                                              |              |              |
| Scripts                     | [1231274](../../build-all.sh), [1230444](../../build-all.sh) and [1231498](../../build-all.sh)  |              |              |

## Sprint Schedule

The project is structured into three sprints with the following deadlines (commits on GitHub before 20:00 as per 2 Rules):

| Sprint | Duration                | Deadline           | Participating UCs                 |
|--------|-------------------------|--------------------|-----------------------------------|
| Sprint 1 | March 17 - April 6, 2025 | April 6, 2025     | EAPLI, LAPR4                      |
| Sprint 2 | April 7 - May 18, 2025  | May 18, 2025      | EAPLI, RCOMP, SCOMP, LAPR4        |
| Sprint 3 | May 19 - June 15, 2025  | June 15, 2025     | EAPLI, LPROG, RCOMP, SCOMP, LAPR4 |


### **Sprint 1: Foundation and DSL Setup**
- **Duration**: March 17 - April 6, 2025
- **Focus**: Initial setup, technical constraints, and foundational project structure.

### **UC Contributions**
- **EAPLI**:
  - Define the domain model using Domain-Driven Design (US110).

- **LAPR4**:
  - Establish the Scrum process and ensure adherence to project constraints (US101).
  - Configure the GitHub repository and integrate project management tools (US102).
  - Define the project structure to support development and envisioned architecture (US103).
  - Set up a Continuous Integration server using GitHub Actions/Workflows (US104).
  - Implement automated deployment scripts for seamless execution of builds and deployments (US105).

### **Deliverables**
- **Project Repository**:
  - GitHub repository initialized with `main` branch and project management tools configured (NFR04, US102).
  - Defined project structure aligned with architecture and supported technologies (US103).

- **Development Foundations**:
  - Continuous Integration (CI) setup using GitHub Actions (US104).
  - Automated scripts for build, execution, and deployment (US105).

- **Domain and DSL Implementation**:
  - Domain model designed following Domain-Driven Design principles (US110).
  - Initial documentation available in `docs/` (NFR02).

### **Tasks**
- Define the domain model and technical constraints.
- Establish repository structure, CI/CD pipeline, and automation scripts.
- Plan Sprint 2 tasks and dependencies.

## **Scrum Process**

### **1. Weekly Meetings**
Weekly meetings are held during the LAPR4 PL+OT classes, led by the Scrum Master. These sessions serve to:
- Review task progress and compare it with sprint goals.
- Plan upcoming tasks based on project needs and identified challenges.
- Discuss and resolve any blockers preventing development progress.
- Adjust the sprint backlog as needed to optimize workflow.

These meetings follow an **adapted Daily Scrum format**, ensuring efficient communication among team members and promoting development transparency.

### **2. User Stories**
At the beginning of each sprint, **User Stories** are provided to guide feature development. The workflow involving user stories follows these steps:
1. **Reception and analysis:** The team evaluates each story, identifying requirements, dependencies, and acceptance criteria.
2. **Clarification of doubts:** If uncertainties arise or further details are needed, the team consults the responsible parties via **LAPR4 RUC**.
3. **Planning and task assignment:** Each story is broken down into smaller tasks, which are then distributed among team members to ensure balanced and collaborative development.
4. **Implementation and validation:** Throughout the sprint, tasks are implemented, reviewed, and validated based on defined criteria, ensuring quality and alignment with project objectives.

### **3. Task Division and Responsibilities**
Task distribution follows a balanced collaboration model, ensuring that all team members actively contribute to the User Stories they are involved in. This process includes:
- **Workload balance:** No member is overloaded, and tasks are assigned based on technical expertise and availability.
- **Rotation and learning:** Whenever possible, team members are encouraged to work on different project areas, fostering continuous learning and preventing over-reliance on a single developer for specific functionalities.
- **Integration across different UCs:** Students participate in multiple Curricular Units (UCs), and task allocation is structured to ensure significant contributions across all enrolled courses.

### **4. Evidence and Progress Tracking**
To ensure effective progress tracking, the team follows a continuous documentation and evidence flow on GitHub, including:
- **Frequent commits:** Each developer makes multiple commits per week, maintaining a detailed history of code modifications.
- **Issue management:** Tasks are tracked through issues, facilitating organization and progress monitoring.
- **Pull requests with review:** Before any code is merged into the main branch, it undergoes a pull request review process to ensure software quality and prevent errors.
- **Documentation and Wiki:** In addition to code, the team maintains updated documentation in the repository, outlining technical decisions, architectures, and relevant project details.

### **Conclusion**
The Scrum process applied in the LAPR4 course enables iterative and collaborative development, ensuring the delivery of high-quality software. The structured organization of meetings, effective planning of user stories, balanced task division, and progress tracking on GitHub are essential aspects contributing to the project’s success.

## Technical Documentation

- **Location**: Stored in the `docs/` folder of the GitHub repository in Markdown format (NFR02).
- **Content**:
    - Sprint plans and retrospectives.
    - UML diagrams (e.g., use case, class, sequence) generated with PlantUML in SVG and PNG formats.
    - Development process for each user story (analysis, design, testing).
- **Tools**: PlantUML for diagrams, updated via `./generate-plantuml-diagrams.sh` script (see root README).

## Milestones and Deadlines

- **Team Formation**: By March 31, 2025 (end of week 2).
- **Sprint 1 Deadline**: April 6, 2025, 20:00.
- **Sprint 2 Deadline**: May 18, 2025, 20:00.
- **Sprint 3 Deadline**: June 15, 2025, 20:00.
- **Final Submission**: June 15, 2025, assessed across all UCs (2.4).

## Risk Management

- **Conflicts**: Resolved internally first; escalate to LAPR4 PL teacher if needed (2.2).
- **Technical Issues**: Mitigated by test-driven development (NFR03) and CI nightly builds (NFR05).
- **Scalability**: Addressed by parallel simulation design and server subarea division (3 System Specifications).

