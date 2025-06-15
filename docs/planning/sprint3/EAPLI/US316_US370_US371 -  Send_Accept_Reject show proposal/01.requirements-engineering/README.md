# US316_US371 - Send_Accept_Reject Show Proposal

## 1. Requirements Engineering

### 1.1. User Story Description

**US316**  
As a CRM Collaborator, I want to send a show proposal to the customer so that they can evaluate and decide whether to proceed with the show.

**US371**  
As a Customer Representative, I want to be able to accept or reject a show proposal from the CRM Collaborator, so that I can communicate my decision directly to the system.

### 1.2. Customer Specifications and Clarifications

- Only authenticated users (with the CRM Collaborator role for sending proposals, and Customer Representative role for accepting/rejecting) can perform these actions.
- A proposal can only be sent **after** the show has been successfully tested (validated and simulated).
- When a proposal is sent, the system should attach a simulation video link (if available) and the generated document (PDF) with all show details.
- When rejecting a proposal, the Customer Representative can optionally provide feedback to help improve future proposals.
- Acceptance or rejection decisions are final and will be recorded as part of the proposal's status history.
- The system must ensure the integrity of the proposal's data when sending it to the Customer Representative.
- Proposals can be accessed by Customer Representatives through the Customer App, using secure login.
- All actions (send, accept, reject) should be fully auditable: who did it, when, and which proposal was affected.

### 1.3. Acceptance Criteria

- [x] Only CRM Collaborators can send proposals to customers.
- [x] A proposal can only be sent if it has passed the show test (successfully simulated).
- [x] The system generates a document for the proposal, applies the current template, and stores it securely.
- [x] The system sends the proposal to the corresponding Customer Representative, including a link to the video and document.
- [x] Only Customer Representatives can accept or reject proposals.
- [x] When rejecting, feedback can be provided optionally.
- [x] The system updates the proposal status to "CUSTOMER_ACCEPTED" or "REJECTED" accordingly.
- [x] A confirmation message is shown to the CRM Collaborator when sending the proposal and to the Customer Representative when making the decision.
- [x] All actions are logged for audit and accountability.

### 1.4. Found out Dependencies

- Relies on the authentication and authorization module to enforce roles (CRM Collaborator and Customer Representative).
- Requires the show simulation (test) module to ensure proposals can only be sent after successful tests.
- Depends on the proposal document generation module (templates and formatting).
- Integration with the Customer App for proposal delivery and decision submission.

### 1.5. Input and Output Data

**Input Data:**

* For sending:
    - The finalized proposal (including show details, drone models, figures, video simulation if available).
* For accepting/rejecting:
    - The decision (accept or reject).
    - Optional feedback from the Customer Representative.

**Output Data:**

* For sending:
    - Confirmation of successful sending (or error if preconditions are not met).
* For accepting/rejecting:
    - Confirmation that the decision was recorded successfully.


### 1.6. System Sequence Diagram (SSD)

![System Sequence Diagram](svg/us318_371-system-sequence-diagram.svg)

### 1.7. Other Relevant Remarks

- The proposal document must include all key data: show description, drone models, figures, date, and simulation video link.
- All data transferred between CRM and Customer App must be secure (e.g., over HTTPS or sockets).
- The system must handle multiple proposals per customer and ensure the correct one is updated upon decision.
- The proposal status lifecycle is managed internally by the system (draft → sent → accepted/rejected).
- The Customer App UI must clearly show proposals awaiting decisions, including status updates.
- Proposal rejection feedback should be stored for analysis and process improvement.