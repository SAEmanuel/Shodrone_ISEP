# US317 - Mark Show Proposal as Accepted

## 1. Requirements Engineering

### 1.1. User Story Description

As a CRM Collaborator, I want to mark a show proposal as accepted after it has been accepted by the Customer Representative in the Customer App.

### 1.2. Customer Specifications and Clarifications

- This user story represents the final step in the proposal acceptance workflow, confirming internal acknowledgment of the customer's decision.
- The proposal must be in state CUSTOMER_APPROVED.
- Upon success, the system must update the status to COLLABORATOR_APPROVED and persist the change.
- Only authenticated users with the CRM Collaborator role can perform this operation.
- It is not required to attach any email in this step, as confirmation by the customer was already done through the Customer App.
- No other changes to the proposal should be made during this operation.

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

* US316 – Sending the proposal to the customer must occur before US317.
* NFR07 – Persistence of proposal state and email attachment must be ensured.
* US372 – Accepted proposals will later be scheduled (Check show dates).
* NFR08 – Authentication/authorization must be enforced.
* 
### 1.5 Input and Output Data

**Input Data:**

* Selection of a proposal from a filtered list (those with status CUSTOMER_APPROVED)

**Output Data:**

* Updated proposal state (COLLABORATOR_APPROVED)
* Success or failure confirmation message

### 1.6. System Sequence Diagram (SSD)

![System Sequence Diagram](svg/us317-system-sequence-diagram.svg)

### 1.7 Other Relevant Remarks

None