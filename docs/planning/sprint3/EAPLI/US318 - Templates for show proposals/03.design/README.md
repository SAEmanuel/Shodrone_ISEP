## 3. Design

### 3.1. Design Overview

The design for the **"Edit Figure Category"** functionality follows the same modular, layered architecture as the rest of the system, ensuring maintainability and extensibility in line with project requirements. The process is initiated by the **Show Designer** through the **User Interface**, which delegates the request to a **dedicated controller**. The controller is responsible for:

- Validating input data (existence of the category, active status, uniqueness of the new name, and input constraints).
- Retrieving the target `FigureCategory` aggregate from the repository.
- Applying the requested updates (name and/or description) only if all business rules are satisfied.
- Updating audit fields (`updatedOn`, `updatedBy`) to maintain full traceability.
- Persisting the updated category in the repository (supporting both **in-memory** and **JPA-backed** persistence).
- Returning clear feedback (success or error messages) to the UI, which then informs the user.

This architecture enforces all **business rules**—including **auditability**, **uniqueness**, and **permission validation**—at the appropriate layer. The solution is **extensible**, making it easy to support future requirements such as versioning or additional audit data.

---

### 3.2. Sequence Diagram(s)

![Sequence Diagram Full](svg/us246-sequence-diagram-full.svg)

This sequence diagram illustrates the complete interaction flow for editing a figure category:

- The **Show Designer** initiates the process through the UI.
- The UI fetches all available categories from the controller and shows them to the user.
- The user selects a category and chooses the fields to update.
- The UI collects the new values and invokes the controller.
- The controller retrieves the repository instance from the persistence provider.
- Depending on the implementation used (**InMemory** or **JPA**), the corresponding repository method is called.
- Business rules (existence, uniqueness, active status) are validated internally.
- If validation fails, an appropriate error message is returned to the user.
- If validation succeeds:
    - The selected category is updated (fields and audit info).
    - Changes are persisted using the repository.
- A success or error message is sent to the user.

---

### 3.3. Design Patterns

- **Repository Pattern:** Abstracts access to storage, isolating the domain logic from data source logic.
- **Factory Pattern:** Used for repository creation via `RepositoryProvider`, allowing decoupled and flexible repository configuration.
- **Aggregate Root (DDD):** `FigureCategory` acts as the aggregate root, encapsulating consistency rules and update behavior.
- **Controller Pattern:** Centralizes business logic in the controller, separating concerns from UI and persistence layers.
- **Separation of Concerns:** Each layer (UI, Controller, Domain, Repository) has clearly defined responsibilities.
- **SOLID and GoF Principles:** The solution applies core object-oriented design principles and known design patterns for robustness and maintainability.

This design is aligned with both **functional** and **non-functional** requirements, ensuring the system remains **testable**, **scalable**, and **traceable** across future changes.