# US315 - Add Video of Simulation to the Proposal

## 1. Requirements Engineering

### 1.1. User Story Description

As a CRM Collaborator, I want to add a video of the simulated show to the proposal so that the customer can have a preview of the show. This enhances customer engagement and provides a more immersive, realistic representation of the proposed experience.

### 1.2. Customer Specifications and Clarifications

The following specifications are derived from stakeholder interviews, the DDD model, and current workflow analysis:

- A simulation video file can be:
  - Uploaded during the proposal creation or update process.
  - Played or previewed directly from the proposal UI.
- Only one video per proposal is allowed.
- Accepted video formats: `.mp4`, `.mov`, `.webm`.
- Maximum file size: 200MB.
- Video should be stored securely and linked to the associated proposal.
- If a video is already uploaded, uploading a new one should **replace** the previous version.

**Clarifications**:
- **Q: Can videos be uploaded after proposal submission?**
  - A: Yes, but only if the proposal is in an editable state.
- **Q: Is streaming required or is download acceptable?**
  - A: Streaming (via embedded player) is preferred; download fallback is optional.
- **Q: Who can view the video?**
  - A: CRM Collaborators and the target customer (via shared proposal link or interface).

**Forum Questions**:
> **Question:** Pode-se substituir o vídeo simulado após o envio da proposta?  
> **Answer:** Sim, desde que a proposta ainda esteja em estado de edição.

### 1.3. Acceptance Criteria

- **AC1**: Only authenticated CRM Collaborators can upload simulation videos to proposals.
- **AC2**: The system allows uploading of one video file per proposal.
- **AC3**: The video must be in an accepted format (`.mp4`, `.mov`, `.webm`) and ≤ 200MB.
- **AC4**: If a video already exists, uploading a new one replaces the previous.
- **AC5**: The uploaded video is linked to the corresponding proposal and viewable within it.
- **AC6**: The video can be streamed within the proposal view (embedded player).
- **AC7**: Only users with appropriate access (e.g., CRM Collaborators and target customers) can view the video.

### 1.4. Found out Dependencies

- **US209**: Proposal creation and editing – Entry point for attaching simulation videos.
- **US312**: File upload service – Manages secure file storage and validation.
- **US250**: Proposal sharing and customer access – Enables viewing permissions for video.
- **US221**: User authentication – Validates CRM Collaborator roles for uploading.

### 1.5 Input and Output Data

**Input Data:**

- Video file (uploaded via form field)
  - Format: `.mp4`, `.mov`, `.webm`
  - Size: ≤ 200MB
- Proposal ID (integer)

**Output Data:**

- Updated proposal record with:
  - `proposal.id`
  - `proposal.videoUrl`
  - `proposal.videoUploadedBy`
  - `proposal.videoUploadDate`

### 1.6 System Sequence Diagram (SSD)

![System Sequence Diagram](svg/us315-sequence-diagram.svg)

The system interaction flow includes:
1. **CRM Collaborator** logs in.
2. Launches *Add Video* UI.
3. System requests the video file name from CRM.
4. CRM enters the video file name.
5. System launches the Drone3DSimulation with the provided name, waits for completion, and checks for the generated video file.
6. System reads the `.mov` video as a byte array, creates a `Video` object, and starts a local preview server.
7. System fetches proposals from the repository and requests proposal selection from CRM.
8. CRM selects a proposal.
9. System attaches the video to the selected proposal and saves the update in the repository.
10. System returns a success or failure message to CRM.

### 1.7 Other Relevant Remarks

- The video should be stored in a CDN or streaming-optimized location.
- Proposals with videos may be flagged or marked visually to indicate a preview is available.
- Proper error messages must be shown for upload issues (e.g., unsupported format, size too large).
