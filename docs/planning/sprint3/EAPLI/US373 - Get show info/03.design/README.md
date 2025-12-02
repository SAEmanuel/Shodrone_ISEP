## 3. Design

### 3.1. Design Overview

The design for the **"Get Show Info"** functionality adheres to the system’s layered architecture, promoting separation of concerns and scalability. This feature allows a **Show Manager** (or Customer Representative) to retrieve detailed information about the shows associated with their corresponding customer entity.

The flow is as follows:
- The **Show Manager** initiates the process via the UI.
- The UI requests the email of the currently logged-in user.
- This email is used by the `GetShowInfoController` to identify the customer (via their NIF) associated with the representative.
- Once the customer is identified, the controller requests all shows linked to that customer.
- The shows are fetched from the data layer via `RepositoryProvider`, using the appropriate repository implementation.
- The list of shows is then presented to the user in a paginated and navigable UI.
- The user selects a show from the list to view detailed show information such as ID, date, duration, location, number of drones, status, and associated figures.

This modular architecture ensures that the business rules and domain constraints are enforced at the correct layer, while keeping the UI clean, responsive, and driven by controllers.

---

### 3.2. Sequence Diagram(s)

![Sequence Diagram - Get Show Info](svg/us373-sequence-diagram-full.svg)

This sequence diagram outlines the process for retrieving show information:

1. The **Show Manager** initiates a request to view shows.
2. The UI delegates to `GetShowInfoController` to determine the `NIF` of the associated customer.
3. The controller uses `FindCustomerOfRepresentativeController` to retrieve the customer based on the logged-in email.
4. A `CostumerRepository` instance is provided via `RepositoryProvider` and queried with the representative’s email/NIF.
5. If a customer is found, the controller uses `FindShows4CustomerController` to fetch all shows for that customer.
6. The `ShowRepository`, provided via `RepositoryProvider`, is queried to return the relevant shows.
7. If any shows exist, the list is returned to the UI for display.
8. The UI supports partial/paginated viewing and lets the user select a show.
9. Once a show is selected, full details are displayed in a formatted and styled manner.
10. If no customer or shows are found, appropriate error messages are presented to the user.

---

### 3.3. Design Patterns

- **Repository Pattern:** Abstracts access to persistent data (e.g., customers and shows), allowing for backend flexibility (e.g., switching between memory or JPA).
- **Controller Pattern:** Business and application logic for finding customers and their shows is encapsulated in controller classes (`GetShowInfoController`, `FindShows4CustomerController`, etc.).
- **Factory Pattern:** `RepositoryProvider` acts as a factory for repositories, enabling swappable persistence strategies.
- **DTO Pattern:** Domain entities are converted into `ShowDTO` objects for safe and structured transfer across layers and network boundaries.
- **Separation of Concerns:** Responsibilities are cleanly divided between UI (presentation), controllers (application logic), entities (domain), and repositories (persistence).
- **Exception Handling:** Error states such as missing customers or shows are managed through exceptions and communicated via user-friendly messages.

This design ensures a robust, testable, and user-friendly mechanism for viewing customer-specific show information, aligned with the project’s architecture and quality standards.
