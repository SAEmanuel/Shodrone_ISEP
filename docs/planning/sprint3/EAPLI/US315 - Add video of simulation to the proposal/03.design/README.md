## 3. Design

### 3.1. Design Overview

The design for the *"Add Video of Simulation to the Proposal"* functionality adheres to a modular, layered architecture that balances simplicity with flexibility. It allows the **CRM Collaborator** to generate and attach a video to a proposal using a local simulation tool. Instead of uploading videos to cloud storage, the system renders and reads local `.mov` video files and links them to a proposal entity.

Key behaviors and responsibilities:

- **Simulation-Based Video Generation:** The video is generated through a local **JavaFX 3D drone simulation** based on input data, ensuring consistent format and structure.
- **Video Launch & Verification:** A subprocess executes the simulation. After completion, the UI verifies that the output `.mov` file exists in the local `videos/` directory.
- **File Reading & Embedding:** The video is read from the file system and converted into a byte array to embed into the proposal entity.
- **Proposal Selection & Binding:** A list of proposals is fetched. The user selects one, and the selected proposal is updated with the video.
- **HTTP Preview Server:** An embedded HTTP server serves the local video file for preview through a browser-based video player.
- **Auditability:** Upon video addition, a success or failure message is shown. If saved, the proposal entity includes the new video content.
- **Repository Interaction:** The controller ensures the updated proposal is saved through the `ShowProposalRepository`.

The design promotes separation of concerns between UI, control logic, and persistence, while embracing offline-first usability and direct system-level integration.

---

### 3.2. Sequence Diagram

![Sequence Diagram - Add Video to Proposal](svg/us315-sequence-diagram-full.svg)

The updated sequence of interactions is as follows:

1. **CRM Collaborator** starts the *Add Video* UI process.
2. The UI prompts the user to enter the video file name.
3. A **JavaFX drone simulation app** is launched using the file name and generates a video (`drone_<name>.mov`).
4. After the simulation ends, the UI checks if the `.mov` file was created.
5. If the file exists, it is read as a byte array and wrapped in a `Video` value object.
6. An **embedded HTTP server** starts to allow previewing the video in a web browser.
7. The system displays a list of available proposals.
8. The user selects one proposal from the list.
9. The selected proposal is updated with the video via the controller.
10. The controller saves the updated proposal using the repository.
11. The UI shows a success (`✔️`) or failure (`❌`) message based on the result.
---

### 3.3. Design Patterns Used

- **Controller Pattern:** Centralizes orchestration logic, delegating to the UI, domain entities, and persistence layer.
- **Repository Pattern:** Abstracts the access to proposal storage and ensures proper persistence.
- **Subprocess Handling (ProcessBuilder):** Launches an external JavaFX application to render the video simulation.
- **Embedded Server Pattern:** An internal HTTP server (via `HttpServer`) previews the video locally without external hosting.
- **Separation of Concerns:** Logic for file generation, video reading, proposal editing, and UI interaction is modularized.
- **Defensive Programming:** Graceful handling of file I/O failures, missing files, or failed simulations with user feedback.
- **SOLID Principles:** The implementation respects single responsibility and open/closed principles across controller, domain model, and UI components.

This approach provides a robust, offline-capable, and user-friendly workflow for embedding simulation videos into proposals, supporting visual validation and enhancing proposal quality with media content.
