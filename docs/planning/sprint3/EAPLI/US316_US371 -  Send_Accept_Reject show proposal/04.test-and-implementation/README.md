# US316_US371 - Send_Accept_Reject show proposal

## 4. Tests



---



---

### 4.2. Application Logic: `AddFigureCategoryController`



---

### 4.3. Persistence: `InMemoryFigureCategoryRepository`



Each test above ensures that the repository behaves correctly in both typical and edge cases.


## 5. Construction (Implementation)




## 6. Integration and Demo

### 6.1 Integration Points

The implementation of **Add Figure Category** integrates with several components of the system:

- **Domain Layer**  
  The `FigureCategory` entity is defined as an aggregate root. It encapsulates validation logic and lifecycle methods (e.g., status toggle, auditing fields).

- **Persistence Layer**  
  The feature uses the `FigureCategoryRepository` interface, which supports both in-memory (`InMemoryFigureCategoryRepository`) and JPA-based (`FigureCategoryJPAImpl`) persistence. This duality allows the system to remain flexible and adaptable to different storage backends.

- **Infrastructure**  
  The repository instance is obtained through `RepositoryProvider`, which determines whether to use the in-memory or JPA implementation based on configuration flags.

- **Authentication Module**  
  The `createdBy` audit field in `FigureCategory` is tied to the currently authenticated user. It leverages the `AuthenticationRepository` and `AuthUtils.getCurrentUserEmail()` to fetch user identity.

### 6.2 Demo Walkthrough (UI)

The demo is performed through a CLI-based interface, using a menu-driven interaction pattern. The main steps are:

1. **Access the UI**  
   The user selects “Add Figure Category” from the main CLI menu. Only users with the role `Show Designer` are allowed to proceed.

2. **Input Phase**  
   The system prompts the user to input:
    - A category name (mandatory, validated on input).
    - An optional category description.
      Validation feedback is provided interactively.

3. **Business Logic Execution**  
   The UI invokes the `AddFigureCategoryController`, which creates a new `FigureCategory` object and attempts to persist it.

4. **Persistence and Feedback**
    - If the category is successfully saved (i.e., name is unique), a success message is shown.
    - If validation fails or the name already exists, an appropriate error message is displayed.

5. **Verification**  
   The new category becomes immediately available for listing and association with new figures, confirming its correct integration in the system.


## 7. Observations

There are no additional observations, limitations, or open questions for this User Story at this time.
