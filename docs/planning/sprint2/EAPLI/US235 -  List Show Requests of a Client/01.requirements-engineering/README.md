# US235 - List Show Requests of Client

## 1. Requirements Engineering

### 1.1. User Story Description

As a CRM Collaborator, I want to list all show requests of a specific client so that I can review and manage their requests efficiently. This functionality allows me to view details of each show request, such as the request ID, place, time, number of drones, duration, and status, ensuring I can track the progress of each request and take appropriate actions (e.g., editing or creating a proposal).

### 1.2. Customer Specifications and Clarifications

The following specifications are derived from the requirements document (Section 4.2, Page 14) and discussions with the LAPR4 PL teacher:

- The system must allow the CRM Collaborator to select a client and list all associated `ShowRequest` entities.
- Each `ShowRequest` in the list should display key details, including:
    - Request ID (e.g., "SR-001").
    - Place (textual description and coordinates, e.g., "Lisbon Central Park, (38.736946, -9.138611)").
    - Time (e.g., "2025-06-15 20:00").
    - Number of drones (e.g., 50).
    - Duration (e.g., 30 minutes).
    - Current status (e.g., "Created", "Proposed").
- The list should include all show requests for the selected client, regardless of their status.

**Clarifications**:
- **Q: Should the CRM Manager also be able to list show requests?**
    - A: Yes, both the CRM Collaborator and CRM Manager should have access to this functionality, as they both manage client interactions (Section 4.2, Page 14). Role-based access (NFR08) will ensure that only authorized users can perform this action.
- **Q: What if the client has no show requests?**
    - A: The system should display a message: "No show requests found for this client."
- **Q: Should the list include the sequence of figures in the `ShowDescription`?**
    - A: Not by default, as it may clutter the list. However, a future user story (e.g., US238 in Sprint 3) could add a "view details" option to display the full `ShowDescription`, including the sequence of figures.
- **Q: Should the list be sorted in any specific order?**
    - A: Yes, the list should be sorted by `creationDate` in descending order (newest first) to show the most recent requests at the top.

### 1.3. Acceptance Criteria

- **AC1**: The CRM Collaborator (or CRM Manager) must be authenticated and authorized to list show requests (role-based access, NFR08).
- **AC2**: The system must allow the user to select a client from a list of all clients (active, VIP, or inactive).
- **AC3**: The system must display all `ShowRequest` entities associated with the selected client, including the following details for each request:
    - Request ID (`ShowRequest.id`).
    - Place (`ShowDescription.place` and coordinates: `latitude`, `longitude`).
    - Time (`ShowDescription.time`).
    - Number of drones (`ShowRequest.numberOfDrones`).
    - Duration (`ShowRequest.duration`).
    - Current status (most recent `ShowRequestStatus.status`).
- **AC4**: If the client has no show requests, the system must display a message: "No show requests found for this client."
- **AC5**: The list must be sorted by `creationDate` in descending order (newest first).
- **AC6**: The list must be retrieved from both in-memory and RDBMS persistence modes (NFR07).
- **AC7**: The system must handle errors gracefully, displaying appropriate messages (e.g., "Error retrieving show requests" if the database fails).

### 1.4. Found out Dependencies

- **US210**: Authentication and user management – Required to authenticate the CRM Collaborator or CRM Manager and enforce role-based access (NFR08).
- **US220**: List all customers – Needed to display a list of clients for the user to select from, including their `status` (active, VIP, inactive).
- **US230**: Register show request – Required to have existing `ShowRequest` entities in the system to list. US235 depends on the `ShowRequest` entity and its associated entities (`ShowDescription`, `ShowRequestStatus`).
- **US110**: Iterative updates to the DDD model – The `ShowRequest`, `Customer`, `ShowDescription`, and `ShowRequestStatus` entities must be defined in the DDD model to support this functionality.

### 1.5 Input and Output Data

**Input Data:**

- Selected data:
    - Client (from a list of all clients, e.g., selected by `Customer.id`).

**Output Data:**

- List of show requests for the selected client, with each entry containing:
    - Request ID (e.g., "SR-001").
    - Place (e.g., "Lisbon Central Park, (38.736946, -9.138611)").
    - Time (e.g., "2025-06-15 20:00").
    - Number of drones (e.g., 50).
    - Duration (e.g., 30 minutes).
    - Current status (e.g., "Created").
- Message if no requests are found (e.g., "No show requests found for this client").

### 1.6. System Sequence Diagram (SSD)

Below is the PlantUML source code for the System Sequence Diagram (SSD) of US235, showing the interaction between the CRM Collaborator (or CRM Manager) and the system. The diagram is saved as `svg/us235-system-sequence-diagram.svg`.


@startuml
' System Sequence Diagram for US235 - List Show Requests of Client

actor "CRM Collaborator" as Collaborator
participant ":System" as System

Collaborator -> System: login()
System -> Collaborator: authenticated
Collaborator -> System: listShowRequestsOfClient()
System -> Collaborator: showClientList()
Collaborator -> System: selectClient(clientId)
System -> System: retrieveShowRequests(clientId)
alt show requests exist
System -> Collaborator: displayShowRequests(requests)
else no show requests
System -> Collaborator: "No show requests found for this client"
end

@enduml

![System Sequence Diagram](svg/us235-system-sequence-diagram.svg)

### 1.7 Other Relevant Remarks

- The system should provide a paginated list if the number of show requests for a client is large (e.g., more than 20 requests), to improve usability. This could be addressed in a future enhancement (e.g., US239 in Sprint 3).
- The displayed list should be formatted for readability, possibly as a table in the console UI (EAPLI framework), with columns for ID, place, time, number of drones, duration, and status.
- The system could benefit from a search or filter option (e.g., by status or date range), which could be added in a future user story.
- Performance considerations: Retrieving show requests from the RDBMS (NFR07) may involve joins with `ShowDescription` and `ShowRequestStatus`. The system should optimize queries to avoid performance bottlenecks, especially for clients with many requests.