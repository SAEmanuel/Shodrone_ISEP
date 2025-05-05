# US236 - Edit a Show Request

## 3. Design

### 3.1. Design Overview

The design for US236 focuses on implementing the functionality to edit an existing show request associated with a specific client within the Shodrone back-office application, adhering to the EAPLI framework (NFR07). The process involves the following high-level steps:

1. **Authentication and Authorization**: The CRM Collaborator logs into the system, and their role is verified (via US210, NFR08: role-based access).
2. **User Interaction**: The user interacts with a console-based UI (provided by EAPLI) to select a costumer, view their associated show requests, and choose one to edit.
3. **Validation**: The system ensures the selected show request has no associated show proposal (a key constraint of US236) and validates the updated data (e.g., future date, valid number of drones).
4. **Business Logic Execution**: An application service (`EditShowRequestService`) orchestrates the editing process, retrieving the show request, applying updates to fields like `numberOfDrones`, `duration`, `ShowDescription`, and `figures`, and logging the edit action with a new `ShowRequestStatus` entry ("Edited").
5. **Persistence**: The `ShowRequestRepository` persists the updated show request in both in-memory and RDBMS modes (NFR07).
6. **Feedback**: The system displays a confirmation message with the updated show request details, or an error message if the request cannot be edited (e.g., "Cannot edit a show request with an associated proposal").

The design follows a layered architecture:
- **UI Layer**: Handles user interaction via EAPLI’s console UI (`EditShowRequestUI`).
- **Application Layer**: Contains the `EditShowRequestService`, which coordinates the use case logic.
- **Domain Layer**: Includes entities (`Costumer`, `ShowRequest`, `ShowDescription`, `ShowRequestStatus`, `ShowProposal`) and enforces business rules (e.g., no editing if a proposal exists).
- **Persistence Layer**: Uses EAPLI’s repository pattern (`ShowRequestRepository`, `CostumerRepository`) for data access.
- **Infrastructure Layer**: Leverages EAPLI’s authentication (`AuthFacade`) and persistence mechanisms.

### 3.2. Sequence Diagrams

#### 3.2.1. Class Diagram
![Class Diagram](img/class_diagram.svg)

#### 3.2.2. Sequence Diagram (SD)
The Sequence Diagram (SD) below provides a detailed view of the internal interactions within the system to edit a show request. It includes the UI, application service, domain entities, repositories, and authentication components.

![Sequence Diagram](img/us236-domain-model-UI_Sequence_Diagram__SSD____Edit_Show_Request_of_Client__with_History_.svg)

### 3.3. Design Patterns (if any)

The design for US236 leverages several design patterns, primarily those provided by the EAPLI framework and common in domain-driven design (DDD):

- **Application Service Pattern**:
    - The `EditShowRequestService` acts as an application service, orchestrating the use case logic. It coordinates interactions between the UI, domain entities, and repositories, handling tasks like retrieving the show request, validating editability, applying updates, and persisting changes.

- **Repository Pattern**:
    - Repositories (`ShowRequestRepository`, `CostumerRepository`) are used to abstract persistence logic, supporting both in-memory and RDBMS modes (NFR07). This pattern decouples the domain layer from the persistence layer, allowing for flexible data access (e.g., `findByCostumer` and `saveInStore` methods in `ShowRequestRepository`).

- **Factory Pattern**:
    - The domain layer uses a factory (e.g., `ShowRequestStatusFactory`) to create new `ShowRequestStatus` entries (e.g., with status "Edited") when the show request is updated. This ensures that status updates are consistently formatted and include required metadata (e.g., timestamp, changedBy).

- **Decorator Pattern (for Formatting)**:
    - The `EditShowRequestService` applies a lightweight Decorator-like approach to format the updated show request for display. It "decorates" the raw `ShowRequest` data by adding formatted strings (e.g., combining `place` and coordinates from `ShowDescription`, extracting the most recent status) before passing the result to the UI for confirmation.

- **Strategy Pattern (Potential Future Use)**:
    - While not implemented in US236, the system could use the Strategy pattern for future enhancements, such as supporting different validation strategies for edited fields (e.g., different rules for date validation based on costumer type). This would involve defining a `ValidationStrategy` interface with implementations like `FutureDateValidation`.

### Explanation of the Design Section

#### 3.1. Design Overview
- Provides a high-level view of the design, outlining the steps involved in editing a show request:
    - Authentication ensures role-based access (NFR08).
    - The UI allows the user to select a costumer and a show request to edit.
    - The `EditShowRequestService` validates the request’s editability, applies updates, and logs the edit action.
    - Repositories handle data persistence.
    - The UI displays a confirmation or error message.
- Describes the layered architecture (UI, Application, Domain, Persistence, Infrastructure), aligning with EAPLI’s structure and ensuring separation of concerns.

#### 3.2. Sequence Diagrams
- **SSD**: Reuses the SSD from the Requirements Engineering phase (Section 1.6), providing a high-level view of the interaction between the user and the system.
- **SD**: The detailed Sequence Diagram (`us236-sequence-diagram.svg`) shows internal interactions:
    - The `EditShowRequestUI` handles user interaction, using EAPLI’s console UI.
    - The `EditShowRequestService` orchestrates the use case, retrieving the show request, validating constraints (e.g., no associated proposal), applying updates, and persisting changes.
    - Repositories (`CostumerRepository`, `ShowRequestRepository`) handle data access.
    - Entities (`Costumer`, `ShowRequest`, `ShowDescription`, `ShowRequestStatus`, `ShowProposal`) are queried and updated.
    - The `AuthFacade` (from US210) ensures role-based access.
    - The diagram includes alternative flows for edge cases (e.g., invalid role, request not found, proposal exists).

#### 3.3. Design Patterns
- Identifies patterns used in the design:
    - **Application Service**: `EditShowRequestService` coordinates the use case, a standard pattern in DDD and EAPLI.
    - **Repository**: Used for persistence, aligning with EAPLI’s approach (NFR07).
    - **Factory**: Used to create new `ShowRequestStatus` entries for tracking edits.
    - **Decorator**: Used for formatting the output in the application service.
    - **Strategy**: Suggested for future validation enhancements, but not implemented in US236.

### Additional Information
- **Validation Logic**: The design ensures that validations (e.g., no associated `ShowProposal`, future date for `ShowDescription.time`) are handled in the domain layer to maintain business rules consistency.
- **Status Tracking**: Each edit action logs a new `ShowRequestStatus` entry ("Edited") with a timestamp and the collaborator’s ID, ensuring an audit trail.
- **Reusability**: The design reuses components like `FoundCostumerUI` (from US230/US235) for costumer selection and `ListShowRequestByCostumerController` (from US235) for listing editable requests.
