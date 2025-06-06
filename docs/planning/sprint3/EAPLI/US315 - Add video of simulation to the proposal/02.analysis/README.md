# US315 - Add Video of Simulation to the Proposal

## 2. Analysis

### 2.1. Relevant Domain Model Excerpt

![Domain Model](svg/us315-domain-model.svg)

This diagram presents the updated domain model for proposal management, extended to support the attachment of a **simulation video** to a **Proposal**. It introduces a new association between proposals and video metadata, ensuring all relevant information required to manage and render simulation previews is properly represented.

While the base domain model for proposals covers customers, shows, and pricing, this excerpt focuses specifically on enriching the **Proposal** entity with video-related attributes and lifecycle awareness.

This level of detail is important because:

- It supports the functional requirement of adding and updating simulation videos linked to proposals.
- It enables proper validation (e.g., format, size) and lifecycle controls (e.g., allowing replacements while editable).
- It lays the foundation for UI integration, such as embedded video previews for customers.

#### **Explanation of the model elements**

- **Proposal** (`<<AggregateRoot>>`): Central entity representing a proposal sent to a customer. Now extended with:
  - `id`: Unique identifier for the proposal.
  - `title`: Optional title used internally.
  - `videoUrl`: Direct link or path to the stored simulation video.
  - `videoUploadedBy`: Reference to the user who uploaded the video (e.g., CRM Collaborator).
  - `videoUploadDate`: Timestamp marking the last upload time.
  - `status`: Workflow status (e.g., *Draft*, *Submitted*). Determines whether a video can be replaced.
- **User**: References the authenticated CRM Collaborator who uploads the video.
- **Video Storage**: Logical representation of the external service or infrastructure handling secure video storage and streaming. Not a persisted domain entity, but relevant to system design and interfaces.

This domain view captures the minimal yet complete structure needed to support video functionality in proposals, while ensuring tight integration with authentication and editing workflows.

### 2.2. Other Remarks

This enriched model is optimized for secure handling and controlled presentation of simulation videos. The design ensures:
- **Traceability and ownership** of uploaded content (who uploaded, when).
- **Secure and accessible storage** integration, abstracted from the core domain.
- **Enforced validation and replacement rules** based on proposal status.

The model is structured for future enhancements, such as:
- Supporting video previews in mobile or shared views.
- Logging download/play events for auditing.
- Adding support for multiple video formats or transcodings.
