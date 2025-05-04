# US236 - Edit a Show Request

## 1. Requirements Engineering

### 1.1. User Story Description

As a CRM Collaborator, I want to edit a show request of a client, so that I can update its details before a proposal is created. This ensures that the show request accurately reflects the client's needs while it is still in the initial stages of processing.

### 1.2. Customer Specifications and Clarifications

- **From Project Requirements (Sem4PI_Project_Requirements_v02a.pdf, Section 3.1.2)**:
    - "Show requests can be edited by CRM Collaborators as long as no show proposal has been created for them."
    - "The system must allow updates to the number of drones, show duration, show date, location, description, and selected figures."
- **Clarification from Customer Forum**:
    - *Q: Can a show request be edited after a proposal is created?*
        - *A: No, once a show proposal is created, the show request becomes locked to ensure consistency in the proposal process. (Customer, 2025-03-15)*
    - *Q: What fields can be edited in a show request?*
        - *A: All fields except the costumer and the request’s creation date can be edited, including the number of drones, duration, date, location, description, and figures. (Customer, 2025-03-16)*

### 1.3. Acceptance Criteria

- **AC1**: A CRM Collaborator must be authenticated to edit a show request.
- **AC2**: Only show requests without an associated show proposal can be edited.
- **AC3**: The system must allow updates to the following fields: number of drones, show duration, show date, location, description, and selected figures.
- **AC4**: The costumer associated with the show request cannot be changed.
- **AC5**: The creation date of the show request must remain unchanged.
- **AC6**: If the show request has a show proposal, the system must display an error message: "Cannot edit a show request with an associated proposal."
- **AC7**: The updated show request must be persisted in the system.
- **AC8**: After a successful edit, a confirmation message must be displayed with the updated details of the show request.

### 1.4. Found out Dependencies

- **US230 - Register Show Request**: This user story depends on US230 because a show request must first be created before it can be edited.
- **US237 - Create Show Proposal**: The constraint that a show request cannot be edited after a proposal is created introduces a dependency on the show proposal creation process.
- **US235 - List Show Requests of Client**: The user story relies on the ability to list and select a show request to edit, which is provided by US235.

### 1.5. Input and Output Data

**Input Data:**

- **Typed Data**:
    - Show request ID (to select the request to edit)
    - Updated number of drones (Integer)
    - Updated show duration (Integer, in minutes)
    - Updated show date (LocalDateTime, format: yyyy-MM-dd HH:mm)
    - Updated location (String for place, Double for latitude and longitude)
    - Updated description (String)
- **Selected Data**:
    - List of figures (List of Figure IDs)

**Output Data:**

- Updated show request details (displayed as a summary)
- Success message: "✅ Show request successfully updated!"
- Error messages (if applicable, e.g., "Cannot edit a show request with an associated proposal.")

### 1.6. System Sequence Diagram (SSD)

![System Sequence Diagram](./puml/img/us236-sequence-diagram.svg)

### 1.7. Other Relevant Remarks

- The system should validate the updated show date to ensure it is at least 72 hours from the current time, consistent with the original show request creation rules.
- The UI should provide a clear way to reselect figures, potentially reusing the `ListFiguresByCostumerUI` from US230.
- The implementation should ensure that the edit operation does not affect the show request's status history, except to log the edit action if required by the system.
