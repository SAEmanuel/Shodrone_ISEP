## US322 - List Maintenance Types

## 1. Requirements Engineering

### 1.1. User Story Description

As a Drone Tech, I want to list all maintenance types in the system. The system must present all maintenance types with their relevant details, such as name or identifier, in a clear and accessible format.

### 1.2. Customer Specifications and Clarifications

- **From Project Document (Sem4PI_Project_Requirements_v03a.pdf, Page 18)**:
  - "As a Drone Tech, I want to list all maintenance types in the system."
  - Maintenance types are used to categorize maintenance records and apply to all drone models (related to US321 and US326).
  - Implied requirement: The list should be retrieved from persistent storage to ensure consistency with maintenance type creation (US321) and usage (US326).

### 1.3. Acceptance Criteria

- [ ] The system allows a Drone Tech to retrieve a list of all maintenance types stored in the system.
- [ ] The list includes at least the name or identifier of each maintenance type.
- [ ] Only users with the Drone Tech role can access the list, as per authentication and authorization requirements (US210).
- [ ] The list is retrieved from persistent storage (relational database, as per NFR07) and displayed efficiently.
- [ ] If no maintenance types exist, the system displays an appropriate message (e.g., "No maintenance types found in the system").
- [ ] The list is presented in a sorted order (e.g., alphabetically by name) for user convenience (pending clarification with LAPR4 RUC).

> **Note:** These acceptance criteria will be checked off as they are addressed and implemented during the development process.

### 1.4. Found out Dependencies

- **US210 (Authentication and Authorization)**: Listing maintenance types requires authentication and role-based authorization for Drone Tech users.
- **US321 (Add Maintenance Type)**: Maintenance types must be created and stored in the system before they can be listed.
- **US110 (Domain Model)**: The domain model must define the `MaintenanceType` entity to support this functionality.

### 1.5 Input and Output Data

**Input Data:**
- None (the Drone Tech simply requests the list of maintenance types).

**Output Data:**
- List of maintenance types, including at least the name or identifier for each (e.g., ["Battery Replacement", "Motor Calibration", "MT-003"]).
- Message indicating no maintenance types exist (e.g., "No maintenance types found in the system") if the list is empty.

### 1.6. System Sequence Diagram (SSD)

![System Sequence Diagram](svg/us322-system-sequence-diagram-System_Sequence_Diagram.svg)

### 1.7 Other Relevant Remarks

- None