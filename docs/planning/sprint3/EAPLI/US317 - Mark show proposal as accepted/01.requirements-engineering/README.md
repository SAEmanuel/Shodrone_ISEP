# US317 - Mark Show Proposal as Accepted

## 1. Requirements Engineering

### 1.1. User Story Description

As a CRM Collaborator, I want to mark a show proposal as accepted after it has been accepted by the Customer Representative in the Customer App.

### 1.2. Customer Specifications and Clarifications

- The proposal must have the status CUSTOMER_APPROVED before it can be accepted internally.
- The CRM Collaborator accesses this functionality via the CRMCollaboratorUI, using the "Accept show proposal" menu option, which opens the AcceptShowProposalUI.
- Only proposals in CUSTOMER_APPROVED status are shown to the user. This is enforced through ListShowProposalController#getAllSentAcceptedProposals().
- When accepted:
* The proposal status is updated to COLLABORATOR_APPROVED.
* A new Show is created through CreateShowController#createShowFromProposal().
* The proposal is persisted using ShowProposalRepository#saveInStore().
- The creation of the show is only allowed if:
* A valid associated ShowRequest exists.
* No other show already exists for the same customer, location, and date (ShowRepository#findDuplicateShow()).
- Only authenticated users with the CRM Collaborator role can execute this operation (NFR08 – enforced by restricted menu access).
- If any validation fails, the operation is blocked, and the user receives a clear error message via the UI.

**Clarifications**

Q: What happens if someone tries to accept an already accepted/rejected proposal?
A: The system must block the operation and return an appropriate error message.

### 1.3. Acceptance Criteria

* AC1: Only authenticated CRM Collaborators can access this operation.
* AC2: The proposal must be in status CUSTOMER_APPROVED.
* AC3: On success, the proposal status is updated to COLLABORATOR_APPROVED and persisted.
* AC4: The user receives a success or failure message accordingly.
* AC5: The list shown to the user must only include proposals eligible for acceptance.

### 1.4. Found out Dependencies

* US316 – Sending the proposal to the customer must precede this operation.
* US371 – The proposal must have been accepted through the Customer App.
* US372 – The created show will be scheduled; the proposal must be valid and free of conflicts.
* NFR07 – Persistence of proposal and show entities must be ensured.
* NFR08 – Authentication and role-based access are required.
* NFR03 – Exception handling and unit testing should be considered for controller logic.


* 
### 1.5 Input and Output Data

**Input Data:**

* Selection of a show proposal from a list filtered by status (CUSTOMER_APPROVED).

**Output Data:**

* Updated proposal state (COLLABORATOR_APPROVED)
* Success or failure confirmation message

### 1.6. System Sequence Diagram (SSD)

![System Sequence Diagram](svg/us317-system-sequence-diagram.svg)

### 1.7 Other Relevant Remarks

None