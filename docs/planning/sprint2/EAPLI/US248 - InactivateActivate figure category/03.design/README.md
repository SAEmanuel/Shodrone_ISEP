## 3. Design

### 3.1. Design Overview

The design for the **"Inactivate/Activate a Figure Category"** functionality follows the same layered and modular architecture as the rest of the system, ensuring clear separation between user interface, business logic, and data access layers. The process is initiated by the Show Designer through the UI, which delegates the request to a dedicated controller. The controller is responsible for:

- Validating input data (category existence, requested action, current status).
- Retrieving the target category aggregate from the repository.
- Checking if the status change is necessary (i.e., the category is not already in the requested state).
- Updating the status of the category (ACTIVE or INACTIVE) and recording audit information (who performed the change and when).
- Persisting the updated category back to the repository (whether in-memory or JPA-based).
- Returning clear feedback (success or error messages) to the UI, which then informs the user.

This approach ensures that all business rules, auditability, and consistency are enforced at the appropriate layer. The design is extensible, allowing for future enhancements such as requiring a justification for status changes or maintaining a history of changes.

### 3.2. Sequence Diagram(s)

![Sequence Diagram Full](svg/us248-sequence-diagram-full.svg)

The sequence diagram illustrates the complete flow for inactivating or activating a figure category:

- The Show Designer initiates the status change via the UI.
- The UI retrieves the list of categories that can have their status changed and allows the user to select one.
- The UI interacts with the controller to perform the action.
- The controller obtains the appropriate repository from the persistence context, using the `RepositoryProvider`.
- Depending on the implementation (in-memory or JPA), the corresponding method `changeStatus()` is invoked.
- The category’s status is toggled, and audit fields like `updatedBy` and `updatedOn` are set.
- The updated category is persisted, and a result is returned to the controller.
- The UI displays success or error messages depending on the outcome.

### 3.3. Design Patterns (if any)

- **Repository Pattern:** Abstracts data access and persistence, keeping the domain logic independent of the data source.
- **Factory Pattern:** Used for repository retrieval through `RepositoryProvider`, promoting decoupling and testability.
- **Aggregate Root (DDD):** `FigureCategory` is modeled as an aggregate root, ensuring all updates and business rules are encapsulated within the aggregate.
- **Controller Pattern:** The controller mediates between the UI and the domain/persistence layers, centralizing business logic and validation.
- **Separation of Concerns:** Clear separation between UI, controller, domain, and persistence layers.
- **SOLID and GoF Principles:** The solution applies object-oriented best practices and GoF design patterns to ensure scalability, flexibility, and clarity.

This design ensures robustness, clarity, and compliance with both functional and non-functional requirements, and is fully aligned with the project’s architectural standards.