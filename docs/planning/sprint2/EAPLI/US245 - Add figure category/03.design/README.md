## 3. Design

### 3.1. Design Overview

The design for the **"Add Figure Category"** functionality adopts a layered, modular, and domain-driven approach. It aligns with the system's architectural style and promotes separation of concerns between UI, controller logic, domain model, and persistence.

The process is initiated by the **Show Designer** via the UI, which:
- Collects and validates user input (category name and optional description).
- Delegates the creation of the figure category to a dedicated controller.

The controller:
- Validates business constraints (e.g., name non-null, length limits, optional description).
- Instantiates the domain object (`FigureCategory`) with all necessary attributes (name, description, createdBy, etc.).
- Requests the repository from a centralized `RepositoryProvider` and delegates persistence.

The repository used can be either:
- **InMemoryFigureCategoryRepository**, where it checks for duplicate keys and stores the object in a HashMap.
- **FigureCategoryJPAImpl**, which queries the database to verify uniqueness and persists the object using JPA.

The system ensures that:
- The category is treated as an **aggregate root**, encapsulating its full lifecycle and business logic.
- All audit data (`createdOn`, `createdBy`) is set during instantiation.
- The repository pattern abstracts data access for easy switching between persistence backends.
- Clear feedback is sent back to the UI, based on success or validation failure.

This modular design supports extensibility (e.g., edit, inactivation), maintainability, and robustness.

### 3.2. Sequence Diagram

![Sequence Diagram Full](svg/us245-sequence-diagram-full.svg)

The diagram illustrates:
- Show Designer starts the process via UI.
- The UI prompts for and validates input (Name and Description).
- The controller is invoked with valid inputs and creates a `FigureCategory` instance.
- It uses `RepositoryProvider` to get the current persistence implementation.
- The controller checks for duplicates before saving.
- Based on the persistence implementation, the object is saved in-memory or persisted via JPA.
- The result (success or failure) is returned to the UI.

### 3.3. Design Patterns

- **Repository Pattern**: Abstracts persistence access, decoupling domain logic from data storage.
- **Factory Pattern**: `RepositoryProvider` dynamically provides the correct implementation.
- **Aggregate Root (DDD)**: `FigureCategory` is an aggregate root enforcing domain consistency.
- **Controller Pattern**: Business logic is centralized in the controller, improving testability.
- **Separation of Concerns**: Responsibilities are clearly divided among layers.
- **SOLID Principles**: Follows best practices for extensible, maintainable object-oriented design.
- **GoF Patterns**: Classic design patterns like Repository and Factory ensure flexibility.

This design provides a robust, extensible solution fully aligned with business and technical requirements.