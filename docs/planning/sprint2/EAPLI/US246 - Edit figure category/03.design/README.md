## 3. Design

### 3.1. Design Overview

The design for the "Edit Figure Category" functionality follows the same modular, layered architecture as the rest of the system, ensuring maintainability and extensibility in line with project requirements. The process is initiated by the Show Designer through the user interface, which delegates the request to a dedicated controller. The controller is responsible for:

- Validating input data (existence of the category, active status, uniqueness of the new name, and input constraints).
- Retrieving the target category aggregate from the repository.
- Applying the requested updates (name and/or description) only if all business rules are satisfied.
- Updating audit fields (such as updatedOn and updatedBy) to maintain traceability.
- Persisting the updated category in the repository (supporting both in-memory and database-backed persistence).
- Returning clear feedback (success or error messages) to the UI, which then informs the user.

This approach enforces all business rules-including auditability, uniqueness, and permissions-at the appropriate layer. The design is extensible, allowing for future enhancements such as additional validations, audit fields, or support for category versioning.

### 3.2. Sequence Diagram(s)

![Sequence Diagram Full](svg/us246-sequence-diagram-full.svg)

The sequence diagram illustrates the complete flow for editing a figure category:
- The Show Designer initiates the edit via the UI.
- The UI retrieves the editable categories and allows the user to select one.
- The UI requests the new data (name, description) from the user.
- The UI interacts with the controller to start the operation.
- The controller obtains the appropriate repository from the persistence context, using a factory for decoupling.
- The controller validates the input (category existence, active status, name uniqueness, input constraints).
- If validation fails, an error message is returned to the UI.
- If validation succeeds, the controller retrieves the category, applies the updates, updates audit fields, and persists the changes.
- The UI provides feedback to the user (success or error).

### 3.3. Design Patterns (if any)

- **Repository Pattern:** Abstracts data access and persistence, allowing the domain layer to remain independent of the data source.
- **Factory Pattern:** Used for repository creation, promoting decoupling and easier testing/configuration.
- **Aggregate Root (DDD):** `Category` is modeled as an aggregate root, ensuring all updates and business rules are encapsulated within the aggregate.
- **Controller Pattern:** The controller mediates between the UI and the domain/persistence layers, centralizing business logic and validation.
- **Separation of Concerns:** The system is organized into clear layers (UI, controller, domain, persistence), each with distinct responsibilities.
- **SOLID and GoF Principles:** The design adheres to SOLID object-oriented principles and applies classic GoF patterns to ensure maintainability and extensibility.

This design ensures robustness, clarity, and compliance with both functional and non-functional requirements, and is fully aligned with the project’s architectural guidelines.


