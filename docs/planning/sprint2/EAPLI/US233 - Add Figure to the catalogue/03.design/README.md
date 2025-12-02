## 3. Design

### 3.1. Design Overview

The design for the *"Add Figure to the Catalogue"* functionality follows a modular architecture, emphasizing the separation of concerns and flexibility. The process is initiated by the **Show Designer** through the UI, which facilitates the entry of figure details such as `name`, `description`, `version`, `category`, `availability`, `status`, and `customer`. The UI then interacts with the relevant controllers and repositories to store the new figure.

Key behaviors and responsibilities:
- **Authenticated Access:** Only authenticated users with the **Show Designer** role can initiate the figure addition process.
- **Input Validation:** The UI ensures that all fields, including `name`, `description`, and `version`, are validated before being processed.
- **Category and Customer Selection:** The user is prompted to select an appropriate category and customer for the figure.
- **Repository Interaction:** The controller retrieves the appropriate repository (either in-memory or persistent) and stores the figure.
- **Success/Failure Feedback:** The UI provides feedback to the user, either confirming a successful figure addition or notifying them of an error.
- **Repository Pattern:** The design uses the repository pattern to abstract data storage operations, allowing flexibility in storage implementations.

The architecture ensures the system is clear, modular, and easily extensible while meeting both functional and non-functional requirements.

---

### 3.2. Sequence Diagram

![Sequence Diagram - Add Figure](svg/us233-sequence-diagram-full.svg)

The sequence diagram illustrates the flow for adding a figure to the catalogue:
1. The **Show Designer** initiates the process via the UI to add a new figure.
2. The UI prompts the user to enter details such as `name`, `description`, and `version`.
3. The user provides the required details.
4. The UI retrieves the active figure categories from the `FigureCategoryController`.
5. The **Show Designer** selects a figure category from the provided options.
6. The UI prompts the user to select the `availability` and `status` of the figure.
7. The **Show Designer** selects the customer from the provided options.
8. The UI sends the gathered information to the `AddFigureController`.
9. The `AddFigureController` creates a new figure and selects the appropriate repository (either in-memory or JPA).
10. The controller saves the figure in the selected repository.
11. The figure is stored or not, and the UI provides feedback to the **Show Designer** with either a success or error message.

---

### 3.3. Design Patterns Used

- **Controller Pattern:** Acts as the intermediary between the UI and the core business logic. The controller handles user requests, applies business rules, and coordinates data flow between the presentation and data layers.
- **Factory Pattern:** Used to instantiate the appropriate repository for storing the figure, promoting decoupling and enhancing maintainability.
- **Repository Pattern:** Encapsulates data access logic, allowing different storage mechanisms (e.g., in-memory or JPA) to be used interchangeably without affecting the business logic.
- **Separation of Concerns:** Each layer—UI, controller, and repository—has distinct responsibilities, making the system easier to maintain and scale.
- **SOLID Principles:** The architecture adheres to SOLID design principles to ensure the system is modular, flexible, and easy to maintain.

This design ensures clarity, scalability, and maintainability, allowing for future enhancements while adhering to both functional and non-functional requirements.
