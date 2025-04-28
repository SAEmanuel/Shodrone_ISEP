# US246 - Edit figure category

## 3. Design

### 3.1. Design Overview

The design for the "Edit Figure Category" functionality follows the same layered and modular architecture as the rest of the system. The process is initiated by the Show Designer through the user interface, which delegates the request to the controller. The controller is responsible for:

- Validating the user's permissions and input data (category existence, active status, name uniqueness, input constraints).
- Retrieving the category aggregate from the repository.
- Applying the requested updates (name and/or description) only if all business rules are satisfied.
- Persisting the updated category back to the repository.
- Returning clear feedback (success or error messages) to the UI, which then informs the user.

This approach ensures that all business rules, including auditability, uniqueness, and edit permissions, are enforced at the appropriate layer. The design is extensible, allowing for future enhancements such as additional validation, audit fields, or support for category versioning.

### 3.2. Sequence Diagrams

![Sequence Diagram Full](svg/us246-sequence-diagram-full.svg)

The sequence diagram illustrates the full flow for editing a figure category:
- The Show Designer initiates the edit via the UI.
- The UI interacts with the controller to start the operation.
- The controller requests the appropriate repository from the persistence context, using a factory for decoupling.
- The controller validates the input (category existence, active status, name uniqueness, input constraints).
- If validation fails, an error message is returned to the UI.
- If validation succeeds, the controller retrieves the category, applies the updates, and persists the changes.
- The UI provides feedback to the user (success or error).

### 3.3. Design Patterns (if any)

- **Repository Pattern:** Abstracts data access and persistence, allowing the domain layer to remain independent of the data source.
- **Factory Pattern:** Used for repository creation, promoting decoupling and easier testing/configuration.
- **Aggregate Root (DDD):** `Category` is modeled as an aggregate root, ensuring all updates and business rules are encapsulated within the aggregate.
- **Controller Pattern:** The controller mediates between the UI and the domain/persistence layers, centralizing business logic and validation.
- **Separation of Concerns:** The system is organized into clear layers (UI, controller, domain, persistence), each with distinct responsibilities.
- **SOLID and GoF Principles:** The design adheres to SOLID object-oriented principles and applies classic GoF patterns to ensure maintainability and extensibility.
