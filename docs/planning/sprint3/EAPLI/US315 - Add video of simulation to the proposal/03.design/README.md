## 3. Design

### 3.1. Design Overview

The design for the *"Add Video of Simulation to the Proposal"* functionality follows a modular and layered architecture, ensuring maintainability and secure handling of media assets. The process is initiated by the **CRM Collaborator** via the UI during the editing phase of a proposal. The user selects a proposal and uploads a simulation video, which is then validated, stored, and linked to the corresponding proposal record.

Key behaviors and responsibilities:
- **Authenticated Access:** Only users with the **CRM Collaborator** role can upload simulation videos.
- **Proposal Edit Context:** Videos can only be uploaded or replaced if the proposal is in an editable state (e.g., *Draft*).
- **Video Validation:** The system validates video attributes, including format, size, and length, before storing the file.
- **Storage and Linking:** The video is uploaded to a secure video storage service (e.g., cloud storage), and a reference (`videoUrl`) is saved in the proposal entity.
- **Auditability:** Metadata such as `videoUploadedBy` and `videoUploadDate` are recorded for traceability.
- **Repository Interaction:** A dedicated controller delegates operations to the Proposal Repository, which abstracts access to the proposal's persistence layer and enforces domain constraints.

The architecture enforces separation of concerns between the UI, controller, domain logic, and persistence layers. It supports security, scalability, and easy future enhancements (e.g., video previews or version history).

---

### 3.2. Sequence Diagram

![Sequence Diagram - Add Video to Proposal](svg/us315-sequence-diagram.svg)

The sequence diagram illustrates the flow for uploading a simulation video to a proposal:
1. The **CRM Collaborator** opens a proposal in editable state via the UI.
2. The UI prompts the user to upload a video file.
3. The video file and proposal ID are sent to the `UploadProposalVideoController`.
4. The controller validates the user role and the status of the proposal.
5. The controller calls the **ProposalRepository** to retrieve and verify the proposal.
6. The video is passed to a **VideoStorageService**, which handles storage and returns a secure video URL.
7. The controller updates the proposal entity with the `videoUrl`, `videoUploadedBy`, and `videoUploadDate`.
8. The updated proposal is saved through the repository.
9. The UI displays a confirmation message and renders the video preview if needed.

---

### 3.3. Design Patterns Used

- **Controller Pattern:** Serves as the entry point for the upload request. It manages user validation, orchestrates dependencies, and handles responses.
- **Repository Pattern:** Encapsulates data access logic and ensures that domain rules around proposal status and video uploads are respected.
- **Service Layer Pattern:** Introduces a dedicated service for managing video storage (e.g., `VideoStorageService`), abstracting external integration details.
- **Factory Pattern:** (Optional) Can be used to instantiate repository or storage services, improving testability and flexibility.
- **Separation of Concerns:** Keeps business rules, presentation logic, and storage concerns cleanly separated for maintainability.
- **SOLID and GoF Principles:** The solution adheres to solid design fundamentals—single responsibility, open/closed, and interface segregation—alongside classic patterns for scalable architecture.

This design ensures modularity, secure video handling, and auditability, while staying aligned with functional and non-functional requirements. It supports extensibility for additional media types, preview generation, or integration with external review portals.
