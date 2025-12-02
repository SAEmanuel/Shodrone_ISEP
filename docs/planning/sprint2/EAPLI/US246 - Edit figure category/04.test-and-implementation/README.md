# US246 - Edit figure category

## 4. Tests

The test suite for **US246** ensures robust validation of both **domain logic** (inside `FigureCategory`) and the **application controllers** (`EditFigureCategoryController`) and (`GetFigureCategoriesController`) . Additionally, tests cover persistence logic using the **in-memory repository** implementation.

---

### 4.1. Domain: `FigureCategory`

The `FigureCategoryTest` class validates:
- Correct instantiation with valid data.
- Handling of invalid constructor arguments.
- State transitions (`active` <-> `inactive`).
- Name and description update operations (including edge cases).
- Equality, identity, and audit methods.
- Proper formatted output via `toString()`.

```java
class FigureCategoryTest {

    private final Email creator = new Email("test@shodrone.app");

    // ---- Creation Tests ----

    @Test
    void testCreationWithValidValues() {
        FigureCategory category = new FigureCategory(new Name("Test Category"), new Description("Valid description"), creator);
        assertEquals("Test Category", category.identity());
        assertTrue(category.isActive());
        assertEquals("Valid description", category.description().toString());
    }

    @Test
    void testConstructorWithOnlyNameAndEmail() {
        FigureCategory category = new FigureCategory(new Name("Minimal Category"), creator);
        assertEquals("Minimal Category", category.identity());
        assertEquals("Description not provided!", category.description().toString());
        assertTrue(category.isActive());
    }

    @Test
    void testCreationWithNullEmailThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new FigureCategory(new Name("Invalid"), new Description("desc"), null);
        });
    }

    // ---- State Toggle Tests ----

    @Test
    void testToggleStateChangesActiveStatus() {
        FigureCategory category = new FigureCategory(new Name("Switchable"), new Description("Toggle state test"), creator);
        assertTrue(category.isActive());
        category.toggleState();
        assertFalse(category.isActive());
        category.toggleState();
        assertTrue(category.isActive());
    }

    // ---- Update Methods ----

    @Test
    void testUpdateName() {
        FigureCategory category = new FigureCategory(new Name("Old Name"), new Description("description"), creator);
        category.changeCategoryNameTo(new Name("New Name"));
        assertEquals("New Name", category.identity());
    }

    @Test
    void testUpdateDescription() {
        FigureCategory category = new FigureCategory(new Name("Name"), new Description("Old description"), creator);
        category.changeDescriptionTo(new Description("New description"));
        assertEquals("New description", category.description().toString());
    }

    @Test
    void testChangeNameToInvalidThrowsException() {
        FigureCategory category = new FigureCategory(new Name("Valid"), new Description("description"), creator);
        assertThrows(IllegalArgumentException.class, () -> {
            category.changeCategoryNameTo(new Name("  "));
        });
    }

    @Test
    void testChangeDescriptionToInvalidThrowsException() {
        FigureCategory category = new FigureCategory(new Name("Valid"), new Description("description"), creator);
        assertThrows(IllegalArgumentException.class, () -> {
            category.changeDescriptionTo(new Description("   "));
        });
    }

    // ---- Audit Field Tests ----

    @Test
    void testUpdateTimeChangesUpdatedOn() {
        FigureCategory category = new FigureCategory(new Name("Name"), new Description("description"), creator);
        assertNull(category.updatedOn());
        category.updateTime();
        assertNotNull(category.updatedOn());
        assertTrue(category.updatedOn().isBefore(LocalDateTime.now().plusSeconds(2)));
    }

    // ---- Identity and Equality ----

    @Test
    void testIdentityAndHasIdentity() {
        FigureCategory category = new FigureCategory(new Name("Unique"), new Description("description"), creator);
        assertTrue(category.hasIdentity("Unique"));
        assertFalse(category.hasIdentity("Another"));
    }

    @Test
    void testEquality() {
        FigureCategory category1 = new FigureCategory(new Name("Equal"), new Description("Same Description"), creator);
        FigureCategory category2 = new FigureCategory(new Name("Equal"), new Description("Same Description"), creator);
        assertEquals(category1, category2);
    }

    @Test
    void testEqualsWithItself() {
        FigureCategory category = new FigureCategory(new Name("Self"), new Description("Description"), creator);
        assertTrue(category.equals(category));
    }

    @Test
    void testEqualsWithNull() {
        FigureCategory category = new FigureCategory(new Name("NullCheck"), new Description("Description"), creator);
        assertFalse(category.equals(null));
    }

    @Test
    void testEqualsWithDifferentClass() {
        FigureCategory category = new FigureCategory(new Name("DiffClass"), new Description("Description"), creator);
        assertFalse(category.equals("not a category"));
    }

    @Test
    void testSameAs() {
        FigureCategory category1 = new FigureCategory(new Name("Cat"), new Description("Description"), creator);
        FigureCategory category2 = new FigureCategory(new Name("Cat"), new Description("Description"), creator);
        assertTrue(category1.sameAs(category2));
    }

    @Test
    void testSameAsDifferentCategory() {
        FigureCategory category1 = new FigureCategory(new Name("FirstCat"), new Description("Description1"), creator);
        FigureCategory category2 = new FigureCategory(new Name("SecondCat"), new Description("Description2"), creator);
        assertFalse(category1.sameAs(category2));
    }

    @Test
    void testHashCodeConsistency() {
        FigureCategory category = new FigureCategory(new Name("Hash"), new Description("Value"), creator);
        int hash1 = category.hashCode();
        int hash2 = category.hashCode();
        assertEquals(hash1, hash2);
    }

    // ---- toString Tests ----

    @Test
    void testToStringActive() {
        FigureCategory category = new FigureCategory(new Name("Display"), new Description("Active status"), creator);
        String output = category.toString();
        assertTrue(output.contains("ACTIVE"));
    }

    @Test
    void testToStringInactive() {
        FigureCategory category = new FigureCategory(new Name("Display"), new Description("Inactive status"), creator);
        category.toggleState();
        String output = category.toString();
        assertTrue(output.contains("INACTIVE"));
    }
}
```

---

### 4.2. Application Logic: `EditFigureCategoryController` and `GetFigureCategoriesController`

These tests verify that the application layer behaves correctly when interacting with the repository:

- `EditFigureCategoryControllerTest` checks:
    - That updates are successfully propagated to the repository when valid.
    - That the controller handles update failures gracefully (e.g., if the category does not exist).

- `GetFigureCategoriesControllerTest` ensures:
    - All categories are retrieved when available.
    - Only active categories are retrieved when requested.
    - Empty results are handled correctly and returned as `Optional.empty()`.

This separation of test cases ensures the controller layer works as expected without relying on repository implementation.

```java
public class EditFigureCategoryControllerTest {
    private EditFigureCategoryController controller;
    private FigureCategoryRepository mockRepository;
    private final FigureCategory category = new FigureCategory(new Name("Original"), new Description("Description"), new Email("editor@shodrone.app"));

    @BeforeEach
    void setUp() {
        mockRepository = mock(FigureCategoryRepository.class);
        RepositoryProvider.injectFigureCategoryRepository(mockRepository);
        controller = new EditFigureCategoryController();
    }

    @Test
    void testEditCategorySuccess() {
        when(mockRepository.editChosenCategory(any(), any(), any())).thenReturn(Optional.of(category));
        Optional<FigureCategory> result = controller.editChosenCategory(category, new Name("Updated"), new Description("New Desc"));
        assertTrue(result.isPresent());
    }

    @Test
    void testEditCategoryFails() {
        when(mockRepository.editChosenCategory(any(), any(), any())).thenReturn(Optional.empty());
        Optional<FigureCategory> result = controller.editChosenCategory(category, new Name("Updated"), new Description("New Desc"));
        assertTrue(result.isEmpty());
    }
}
```

```java
public class GetFigureCategoriesControllerTest {
    private GetFigureCategoriesController controller;
    private FigureCategoryRepository mockRepository;

    @BeforeEach
    void setUp() {
        mockRepository = mock(FigureCategoryRepository.class);
        RepositoryProvider.injectFigureCategoryRepository(mockRepository);
        controller = new GetFigureCategoriesController();
    }

    @Test
    void testGetAllFigureCategoriesReturnsList() {
        List<FigureCategory> list = new ArrayList<>();
        list.add(new FigureCategory(new Name("All"), new Description("All"), new Email("test@shodrone.app")));
        when(mockRepository.findAll()).thenReturn(list);
        Optional<List<FigureCategory>> result = controller.getAllFigureCategories();
        assertTrue(result.isPresent());
    }

    @Test
    void testGetAllFigureCategoriesReturnsEmpty() {
        when(mockRepository.findAll()).thenReturn(new ArrayList<>());
        Optional<List<FigureCategory>> result = controller.getAllFigureCategories();
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetActiveFigureCategoriesReturnsList() {
        List<FigureCategory> list = new ArrayList<>();
        list.add(new FigureCategory(new Name("Active"), new Description("Active Desc"), new Email("test@shodrone.app")));
        when(mockRepository.findActiveCategories()).thenReturn(list);
        Optional<List<FigureCategory>> result = controller.getActiveFigureCategories();
        assertTrue(result.isPresent());
    }

    @Test
    void testGetActiveFigureCategoriesReturnsEmpty() {
        when(mockRepository.findActiveCategories()).thenReturn(new ArrayList<>());
        Optional<List<FigureCategory>> result = controller.getActiveFigureCategories();
        assertTrue(result.isEmpty());
    }
}
```

---

### 4.3. Persistence: `InMemoryFigureCategoryRepository`

This test class validates the repository behavior for edit-related operations in-memory, including:

- Correct **update of name or description** independently or together.
- Ensuring **nonexistent updates return empty** (i.e., update on missing category).
- Validating that **active category filtering works**, especially after toggling the state using `changeStatus`.

These tests help verify that the in-memory backend used during development and testing behaves consistently with expectations and domain logic.

```java
class InMemoryFigureCategoryRepositoryTest {

    private InMemoryFigureCategoryRepository repo;
    private final Email creator = new Email("test@shodrone.app");

    @BeforeEach
    void setUp() {
        repo = new InMemoryFigureCategoryRepository();
    }
    

    @Test
    void testFindByNameSuccess() {
        FigureCategory category = new FigureCategory(new Name("Aviation"), new Description("Aircraft models"), creator);
        repo.save(category);
        Optional<FigureCategory> found = repo.findByName("aviation");
        assertTrue(found.isPresent());
        assertEquals("Aviation", found.get().identity());
    }
    

    @Test
    void testEditChosenCategoryNameOnly() {
        FigureCategory category = new FigureCategory(new Name("Old Name"), new Description("Some desc"), creator);
        repo.save(category);
        Optional<FigureCategory> updated = repo.editChosenCategory(category, new Name("New Name"), null);
        assertTrue(updated.isPresent());
        assertEquals("New Name", updated.get().identity());
    }

    @Test
    void testEditChosenCategoryDescriptionOnly() {
        FigureCategory category = new FigureCategory(new Name("Editable"), new Description("Old desc"), creator);
        repo.save(category);
        Optional<FigureCategory> updated = repo.editChosenCategory(category, null, new Description("Updated desc"));
        assertTrue(updated.isPresent());
        assertEquals("Updated desc", updated.get().description().toString());
    }

    @Test
    void testEditCategoryNotFoundReturnsEmpty() {
        FigureCategory category = new FigureCategory(new Name("Not Found"), new Description("Description "), creator);
        Optional<FigureCategory> result = repo.editChosenCategory(category, new Name("New Name"), new Description("Another"));
        assertTrue(result.isEmpty());
    }
    
    @Test
    void testFindActiveCategoriesReturnsOnlyActive() {
        FigureCategory active = new FigureCategory(new Name("Active"), new Description("Still active"), creator);
        FigureCategory inactive = new FigureCategory(new Name("Inactive"), new Description("To deactivate"), creator);
        repo.save(active);
        repo.save(inactive);
        repo.changeStatus(inactive); // makes it inactive
        List<FigureCategory> result = repo.findActiveCategories();
        assertEquals(1, result.size());
        assertEquals("Active", result.get(0).identity());
    }
}
```

## 5. Construction (Implementation)

The implementation of the *Edit Figure Category* feature adheres to the system’s layered architecture and clean separation of responsibilities. This allows for extensibility, testability, and consistent behavior regardless of the chosen persistence backend (in-memory or JPA).

### 5.1. UI

The user starts the editing process from the CLI. The UI lists available categories (retrieved via `GetFigureCategoriesController`) and prompts the user to select one. After selection, the user is asked to enter a new name and/or description.
The UI validates the input, then delegates the update operation to the `EditFigureCategoryController`. It handles both successful and failed edits (e.g., duplicate names, no changes made).

```java
public class EditFigureCategoryUI implements Runnable {

    private final EditFigureCategoryController editFigureCategoryController = new EditFigureCategoryController();
    private final GetFigureCategoriesController getFigureCategoriesController = new GetFigureCategoriesController();

    @Override
    public void run() {
        Utils.printCenteredTitle("Edit Figure Category");

        Optional<List<FigureCategory>> activeCategoriesOptional = getFigureCategoriesController.getActiveFigureCategories();
        if (activeCategoriesOptional.isPresent()) {
            int index = Utils.showAndSelectIndexPartially(activeCategoriesOptional.get(), "Select the desired category to edit");

            if (index < 0) {
                Utils.printFailMessage("No category selected.");
                return;
            }

            FigureCategory chosenCategory = activeCategoriesOptional.get().get(index);

            Utils.printAlterMessage("Current name: " + chosenCategory.identity());
            boolean editName = Utils.confirm("Do you want to edit the category's name? (y/n)");

            Utils.showNameRules();
            Name newName = editName ? Utils.rePromptWhileInvalid("Enter the Category name: ", Name::new) : null;

            Utils.printAlterMessage("Current description: " + chosenCategory.description());
            boolean editDescription = Utils.confirm("Do you want to edit the category's description? (y/n)");

            Utils.showDescriptionRules();
            Description newDescription = editDescription ? Utils.rePromptWhileInvalid("Enter the Category description: ", Description::new) : null;

            if (!editName && !editDescription) {
                Utils.printFailMessage("Nothing has changed!");
                return;
            }

            Optional<FigureCategory> editedCategory = editFigureCategoryController.editChosenCategory(chosenCategory, newName, newDescription);
            editedCategory.ifPresentOrElse(
                    figureCategory -> Utils.printSuccessMessage("Category updated successfully!"),
                    () -> Utils.printFailMessage("Failed to update category.")
            );
        } else {
            Utils.printFailMessage("No categories in the system yet...");
        }
    }
}
```

### 5.2. Controller

There are two key controllers involved:

- `EditFigureCategoryController`: receives the user’s selected category and new data, and delegates the update to the repository. It returns the updated category or an empty `Optional` on failure.
```java
public class EditFigureCategoryController {

    private final FigureCategoryRepository repository;

    public EditFigureCategoryController() {
        repository = RepositoryProvider.figureCategoryRepository();
    }

    public Optional<FigureCategory> editChosenCategory(FigureCategory category, Name newName, Description newDescription) {
        return repository.editChosenCategory(category, newName, newDescription);
    }

}
```
- `GetFigureCategoriesController`: retrieves all or only active categories for UI selection.
```java
public class GetFigureCategoriesController {

    private final FigureCategoryRepository repository;

    public GetFigureCategoriesController() {
        repository = RepositoryProvider.figureCategoryRepository();
    }
    
    public Optional<List<FigureCategory>> getActiveFigureCategories() {
        List<FigureCategory> all = repository.findActiveCategories();
        return all.isEmpty() ? Optional.empty() : Optional.of(all);
    }
}
```

These controllers shield the UI from persistence logic.


### 5.3. Domain

The `FigureCategory` aggregate root handles:

- Validation of name and description.
- Audit tracking via `updatedOn` and `updatedBy` fields.
- Methods to update name, description, and audit timestamps.

It ensures all updates follow business rules and maintain internal consistency.

```java
@XmlRootElement
@Entity
public class FigureCategory implements AggregateRoot<String>, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pk;

    @Version
    private Long version;

    @XmlElement
    @JsonProperty
    @Column(nullable = false, unique = true)
    @Size(min = 3, max = 80)
    private Name name;

    @XmlElement
    @JsonProperty
    @Column(nullable = false)
    @Size(max = 300)
    private Description description;

    @XmlElement
    @JsonProperty
    @Column(nullable = false)
    private boolean active;

    @XmlElement
    @JsonProperty
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdOn;

    @XmlElement
    @JsonProperty
    @Embedded
    @AttributeOverride(name = "email", column = @Column(name = "created_by", nullable = false, updatable = false))
    private Email createdBy;

    @XmlElement
    @JsonProperty
    private LocalDateTime updatedOn;

    @Setter
    @XmlElement
    @JsonProperty
    @Embedded
    @AttributeOverride(name = "email", column = @Column(name = "updated_by"))
    private Email updatedBy;


    protected FigureCategory() {
        // For ORM
    }

    public FigureCategory(Name name, Description description, Email createdBy) {
        if (!nameMeetsMinimumRequirements(name)) {
            throw new IllegalArgumentException("Invalid Category name.");
        }
        if (!descriptionMeetsMinimumRequirements(description)) {
            throw new IllegalArgumentException("Invalid Category Description.");
        }
        if (createdBy == null) {
            throw new IllegalArgumentException("CreatedBy cannot be null.");
        }

        this.name = name;
        this.description = description;
        this.active = true;
        this.createdOn = LocalDateTime.now();
        this.createdBy = createdBy;
        this.updatedOn = null;
        this.updatedBy = null;
    }

    public FigureCategory(Name name, Email createdBy) {
        this(name, new Description("Description not provided!"), createdBy);
    }


    private static boolean nameMeetsMinimumRequirements(final Name name) {
        return !StringPredicates.isNullOrEmpty(name.toString()) && !StringPredicates.isNullOrWhiteSpace(name.toString());
    }

    private static boolean descriptionMeetsMinimumRequirements(final Description description) {
        return description.toString() != null;
    }


    public void changeDescriptionTo(final Description newDescription) {
        if (!descriptionMeetsMinimumRequirements(newDescription)) {
            throw new IllegalArgumentException();
        }
        this.description = newDescription;
    }

    public void changeCategoryNameTo(final Name newCategoryName) {
        if (!nameMeetsMinimumRequirements(newCategoryName)) {
            throw new IllegalArgumentException();
        }
        this.name = newCategoryName;
    }

    public boolean isActive() {
        return this.active;
    }

    public void toggleState() {
        this.active = !this.active;
    }

    public Description description() {
        return this.description;
    }

    public void updateTime() {
        this.updatedOn = LocalDateTime.now();
    }

    public Long id() {
        return this.pk;
    }

    public LocalDateTime updatedOn() {
        return this.updatedOn;
    }

    @Override
    public String identity() {
        return this.name.toString();
    }

    @Override
    public boolean hasIdentity(final String id) {
        return id.equalsIgnoreCase(this.name.toString());
    }

    @Override
    public boolean sameAs(Object other) {
        final FigureCategory figureCategory = (FigureCategory) other;
        return this.equals(figureCategory) && description().equals(figureCategory.description()) && isActive() == figureCategory.isActive();
    }

    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }

    @Override
    public boolean equals(final Object o) {
        return DomainEntities.areEqual(this, o);
    }
    
    @Override
    public String toString() {
        if (isActive()) {
            return String.format("[%s -> %s] %s(%s)%s", name, description, ANSI_FOREST_GREEN, "ACTIVE", ANSI_RESET);
        }
        return String.format("[%s -> %s] %s(%s)%s" , name, description, ANSI_BRIGHT_BLACK, "INACTIVE", ANSI_RESET);
    }

}
```

### 5.4. Persistence

This feature supports both **in-memory** and **JPA** implementations of `FigureCategoryRepository`:

- In-memory is used primarily for testing.
```java
public class InMemoryFigureCategoryRepository implements FigureCategoryRepository {
    private final Map<String, FigureCategory> store = new HashMap<>();
    

    @Override
    public Optional<FigureCategory> findByName(String name) {
        return Optional.ofNullable(store.get(name.toLowerCase()));
    }
    

    @Override
    public List<FigureCategory> findActiveCategories() {
       List<FigureCategory> activeCategories = new ArrayList<>();
       for(FigureCategory category : store.values()) {
           if (category.isActive())
               activeCategories.add(category);
       }
       return activeCategories;
    }

    @Override
    public Optional<FigureCategory> editChosenCategory(FigureCategory category, Name newName, Description newDescription) {
        Optional<FigureCategory> categoryOptional = findByName(category.identity());

        if (categoryOptional.isEmpty()) {
            return Optional.empty();
        }

        FigureCategory existing = categoryOptional.get();
        existing.updateTime();
        existing.setUpdatedBy(new Email(AuthUtils.getCurrentUserEmail()));

        if (newName != null) {
            existing.changeCategoryNameTo(newName);
        }
        if (newDescription != null) {
            existing.changeDescriptionTo(newDescription);
        }

        return Optional.of(existing);
    }
    
}
```
- JPA ensures durable storage.
```java
public class FigureCategoryJPAImpl extends JpaBaseRepository<FigureCategory, Long>
        implements FigureCategoryRepository {
    
    @Override
    public Optional<FigureCategory> findByName(String name) {
        List<FigureCategory> results = entityManager()
                .createQuery("SELECT f FROM FigureCategory f WHERE LOWER(f.name.name) = :name", FigureCategory.class)
                .setParameter("name", name.toLowerCase())
                .getResultList();

        if (results.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(results.get(0));
        }
    }

    @Override
    public List<FigureCategory> findActiveCategories() {
        return entityManager()
                .createQuery("SELECT f FROM FigureCategory f WHERE f.active = true", FigureCategory.class)
                .getResultList();
    }

    @Override
    public Optional<FigureCategory> editChosenCategory(FigureCategory category, Name newName, Description newDescription) {
        try {
            FigureCategory managed = entityManager().find(FigureCategory.class, category.id());

            if (managed == null) {
                return Optional.empty();
            }

            entityManager().getTransaction().begin();
            if(newName != null) {
                managed.changeCategoryNameTo(newName);
            }
            if(newDescription != null) {
                managed.changeDescriptionTo(newDescription);
            }
            managed.updateTime();
            managed.setUpdatedBy(new Email(AuthUtils.getCurrentUserEmail()));
            entityManager().getTransaction().commit();

            return Optional.of(managed);
        } catch (Exception e) {
            if (entityManager().getTransaction().isActive()) {
                entityManager().getTransaction().rollback();
            }
            e.printStackTrace();
            return Optional.empty();
        }
    }
    
}
```

Each implementation overrides `editChosenCategory(...)` to locate, update, and persist the edited category.


### 5.5. Repository Selection

The repository instance is obtained dynamically via `RepositoryProvider`, which selects the backend depending on whether in-memory mode is enabled.

```java
public class RepositoryProvider {
    private static FigureCategoryRepository figureCategoryRepository;
    private static FigureRepository figureRepository;
    private static CostumerRepository costumerRepository;
    private static ShowRequestRepository showRequestRepository;
    private static AuthenticationRepository authenticationRepository;

    @Setter
    private static boolean useInMemory = true;

    private static boolean isInMemory() {
        return useInMemory;
    }

    public static FigureCategoryRepository figureCategoryRepository() {
        if (figureCategoryRepository == null) {
            if (isInMemory()) {
                figureCategoryRepository = new InMemoryFigureCategoryRepository();
            } else {
                 figureCategoryRepository = new FigureCategoryJPAImpl();
            }
        }
        return figureCategoryRepository;
    }
    
    // Only for testing purposes
    public static void injectFigureCategoryRepository(FigureCategoryRepository mockRepo) {
        figureCategoryRepository = mockRepo;
    }

    public static void injectAuthenticationRepository(AuthenticationRepository mockRepo) {
        authenticationRepository = mockRepo;
    }
}
```

### 5.6. Summary of Classes Involved

- **UI**: `EditFigureCategoryUI`
- **Controllers**: `EditFigureCategoryController`, `GetFigureCategoriesController`
- **Domain**: `FigureCategory`, `Name`, `Description`
- **Persistence Interface**: `FigureCategoryRepository`
- **In-Memory Implementation**: `InMemoryFigureCategoryRepository`
- **JPA Implementation**: `FigureCategoryJPAImpl`
- **Factory**: `RepositoryProvider`

The design follows solid architecture principles, isolating concerns and enabling consistent behavior across storage backends.

## 6. Integration and Demo

### 6.1 Integration Points

The *Edit Figure Category* feature is integrated across several layers of the application architecture, ensuring clean separation of concerns and reuse of shared services:

- **Domain Layer**  
  The domain model `FigureCategory` provides encapsulated business logic for name and description updates, validation rules, and audit tracking.

- **Persistence Layer**  
  The editing operation is performed through the `FigureCategoryRepository`, which is abstracted to support both in-memory and JPA implementations via the `RepositoryProvider`.

- **Infrastructure**  
  The `AuthUtils` utility retrieves the current authenticated user to properly set the `updatedBy` field during the update operation.

- **Controller Layer**  
  The `EditFigureCategoryController` and `GetFigureCategoriesController` mediate between the UI and the persistence layer, handling update logic and retrieval of categories respectively.

- **UI Layer**  
  The CLI-based `EditFigureCategoryUI` orchestrates the entire flow of user interaction, input validation, and feedback display.

---

### 6.2 Demo Walkthrough (CLI)

The feature is demonstrated via a CLI (Command-Line Interface) with an interactive, user-friendly flow:

1. **Access the UI**  
   The user selects the option “Edit Figure Category” from the main application menu. Only users with the appropriate role (e.g., `Show Designer`) have access.

2. **List Active Categories**  
   The system fetches all *active* figure categories using `GetFigureCategoriesController` and displays them in a numbered list.

3. **User Selection**  
   The user selects a category to edit from the presented list. If the selection is invalid (e.g., user cancels), the process is aborted with a warning message.

4. **Prompt for Updates**
  - The current name and description are shown.
  - The user is prompted to choose whether they wish to edit the name, the description, or both.
  - If editing is chosen, the system validates the new inputs using the same validation rules defined in the domain (e.g., name cannot be empty, description must meet minimum length).

5. **Submission**  
   The system calls `EditFigureCategoryController.editChosenCategory(...)` to attempt the update using the provided values. Null values are passed for fields that were not chosen to be edited.

6. **Result Feedback**
  - If the update is successful, a success message is shown.
  - If the operation fails (e.g., repository error or unchanged category), the system notifies the user with an appropriate failure message.

7. **No Categories Found (Edge Case)**  
   If no active categories exist in the system, the UI immediately informs the user and exits the flow gracefully.


## 7. Observations

There are no additional observations, limitations, or open questions for this User Story at this time.
