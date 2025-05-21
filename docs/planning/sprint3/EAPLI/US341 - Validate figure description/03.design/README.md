## 3. Design

### 3.1. Design Overview

The design for the "List Figure Categories" functionality follows a layered and modular architecture, ensuring a clear separation of concerns between the user interface, business logic, and data access layers. The process begins with the Show Designer requesting to view all available figure categories via the user interface. This request is delegated to a controller, which communicates with the appropriate repository to fetch the data from the persistence layer.

Key design aspects:
- Only authenticated users with the Show Designer role can access the category listing.
- The controller ensures that all categories are retrieved and presented as they exist, allowing future extensibility for filtering by active status or other criteria.
- The repository abstracts the data access logic, supporting both in-memory and JPA-backed persistence through polymorphic handling.
- The user interface receives the list (if available) and displays each category’s name and description.
- In the case of an empty list, an appropriate message is shown to the user.
- The solution allows easy extensibility (e.g., search, filter, pagination) while maintaining traceability and maintainability.

This design enforces consistency across the system, providing robustness and flexibility aligned with the architecture's quality standards.

### 3.2. Sequence Diagram(s)

![Sequence Diagram Full](svg/us247-sequence-diagram-full.svg)

The sequence diagram illustrates the complete flow for listing figure categories:

- The Show Designer initiates the request through the UI.
- The UI delegates the request to a controller.
- The controller acquires the appropriate repository using a factory-based provider.
- The repository (in-memory or JPA) retrieves all categories.
- The controller receives the list and returns it to the UI.
- The UI either displays the list of categories (if available) or shows a message indicating the absence of categories.

The sequence diagram includes an `alt` fragment to illustrate both the scenario where categories are available and the scenario where the list is empty.

### 3.3. Design Patterns (if any)

- **Repository Pattern:** Provides an abstraction over data access, allowing persistence logic to be decoupled from business logic.
- **Factory Pattern:** Used by the repository provider to instantiate the correct repository implementation (JPA or in-memory).
- **Controller Pattern:** Coordinates the request, encapsulating logic between the UI and data layer.
- **Separation of Concerns:** Ensures each layer (UI, controller, domain, persistence) has a well-defined responsibility.
- **SOLID and GoF Principles:** The implementation respects object-oriented design principles, promoting flexibility and maintainability.

This architecture supports both immediate business needs and future requirements, ensuring a scalable and testable solution.