# US248 - Inactivate/Activate a figure category

## 4. Tests

The test suite for **US248** ensures robust validation of both **domain logic** (inside `FigureCategory`) and the **application controller** (`GetFigureCategoriesController`) and (`ChangeFigureCategoryStatusController`). Additionally, tests cover persistence logic using the **in-memory repository** implementation.

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

### 4.2. Application Logic: `GetFigureCategoriesController` and `ChangeFigureCategoryStatusController`

These tests verify that the application layer behaves correctly when interacting with the repository:

- `GetFigureCategoriesControllerTest` ensures:
    - All categories are retrieved when available.
    - Only active categories are retrieved when requested.
    - Empty results are handled correctly and returned as `Optional.empty()`.
  
- `ChangeFigureCategoryStatusController` ensures:
  - The status of a selected category (active/inactive) can be toggled successfully.
  - The controller correctly delegates the status change operation to the repository.
  - If the repository returns Optional.empty(), the controller handles it gracefully, indicating failure.

This separation of test cases ensures the controller layer works as expected without relying on repository implementation.


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

```java
class ChangeFigureCategoryStatusControllerTest {

    private ChangeFigureCategoryStatusController controller;
    private FigureCategoryRepository mockRepository;

    private final FigureCategory mockCategory = new FigureCategory(new Name("Mock"), new Description("Mock Description"), new Email("test@shodrone.app"));

    @BeforeEach
    void setUp() {
        mockRepository = mock(FigureCategoryRepository.class);
        RepositoryProvider.injectFigureCategoryRepository(mockRepository);
        controller = new ChangeFigureCategoryStatusController();
    }

    @Test
    void testChangeStatusSuccess() {
        when(mockRepository.changeStatus(mockCategory)).thenReturn(Optional.of(mockCategory));
        Optional<FigureCategory> result = controller.changeStatus(mockCategory);
        assertTrue(result.isPresent());
    }

    @Test
    void testChangeStatusFails() {
        when(mockRepository.changeStatus(mockCategory)).thenReturn(Optional.empty());
        Optional<FigureCategory> result = controller.changeStatus(mockCategory);
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
    void testFindAllReturnsAllSaved() {
        repo.save(new FigureCategory(new Name("A name"), new Description("Description one"), creator));
        repo.save(new FigureCategory(new Name("B name"), new Description("Description two"), creator));
        List<FigureCategory> all = repo.findAll();
        assertEquals(2, all.size());
    }


    @Test
    void testChangeStatusTogglesActive() {
        FigureCategory category = new FigureCategory(new Name("Toggle"), new Description("Toggle status"), creator);
        repo.save(category);
        assertTrue(category.isActive());
        repo.changeStatus(category);
        assertFalse(category.isActive());
    }

}
```

## 5. Construction (Implementation)

The implementation of the *Change Figure Category Status* feature adheres to the established layered architecture, cleanly separating responsibilities across **UI**, **controller**, **domain**, and **persistence** layers. This ensures maintainability and testability, while leveraging shared components from previous features.

### 5.1. UI

The CLI-based UI is responsible for facilitating user interaction when changing the status (active/inactive) of a figure category. It retrieves all categories via the `GetFigureCategoriesController`, allows the user to select one, and delegates the toggle operation to the `ChangeFigureCategoryStatusController`.

* A status change message is printed for clarity.
* If no categories exist, a failure message is shown.
* Upon successful update, the user receives confirmation.

```java
public class ChangeFigureCategoryStatusUI implements Runnable {

    private final GetFigureCategoriesController getFigureCategoriesController = new GetFigureCategoriesController();
    private final ChangeFigureCategoryStatusController changeFigureCategoryStatusController = new ChangeFigureCategoryStatusController();


    @Override
    public void run() {
        Utils.printCenteredTitle("Inactivate/Activate a Figure Category");

        Optional<List<FigureCategory>> categoriesOptional = getFigureCategoriesController.getAllFigureCategories();

        if (categoriesOptional.isEmpty()) {
            Utils.printFailMessage("No categories in the system yet...");
        } else {
            Utils.printAlterMessage("The current status will change when selected");
            int index = Utils.showAndSelectIndexPartially(categoriesOptional.get(), "Select the desired category to enable/disable");

            FigureCategory selectedCategory = categoriesOptional.get().get(index);
            Optional<FigureCategory> optionalCategory = changeFigureCategoryStatusController.changeStatus(selectedCategory);

            if (optionalCategory.isEmpty()) {
                Utils.printFailMessage("Failed to change category status");
            } else {
                Utils.printSuccessMessage("Category status changed");
            }

        }
    }
}

```

### 5.2. Controller

This feature involves two controllers:

* **GetFigureCategoriesController**: retrieves all categories (active and inactive) for selection.

```java
public class GetFigureCategoriesController {

    private final FigureCategoryRepository repository;

    public GetFigureCategoriesController() {
        repository = RepositoryProvider.figureCategoryRepository();
    }

    public Optional<List<FigureCategory>> getAllFigureCategories() {
        List<FigureCategory> all = repository.findAll();
        return all.isEmpty() ? Optional.empty() : Optional.of(all);
    }
}
```

* **ChangeFigureCategoryStatusController**: responsible for executing the toggle operation. It delegates to the repository's `changeStatus(...)` method, returning an updated entity or `Optional.empty()` on failure.

```java
public class ChangeFigureCategoryStatusController {
    private final FigureCategoryRepository repository;

    public ChangeFigureCategoryStatusController() {
        repository = RepositoryProvider.figureCategoryRepository();
    }

    public Optional<FigureCategory> changeStatus(FigureCategory selectedCategory) {
        return repository.changeStatus(selectedCategory);
    }

}
```

These controllers abstract the business logic away from the UI and simplify interaction with the persistence layer.

### 5.3. Domain

The domain model `FigureCategory` encapsulates the logic for status toggling. It defines:

* `isActive()` to check current state
* `toggleState()` to flip between active/inactive
* `updateTime()` to timestamp changes
* `setUpdatedBy(...)` to track who made the change

The domain ensures state transitions are properly recorded and traceable.

### 5.4. Persistence

Both in-memory and JPA repository implementations support the status change operation by overriding `changeStatus(...)` in `FigureCategoryRepository`.

* In-memory implementation locates the category, modifies it in place, and returns the updated object.

```java
public class InMemoryFigureCategoryRepository implements FigureCategoryRepository {
    private final Map<String, FigureCategory> store = new HashMap<>();
    
    @Override
    public Optional<FigureCategory> findByName(String name) {
        return Optional.ofNullable(store.get(name.toLowerCase()));
    }

    @Override
    public List<FigureCategory> findAll() {
        return new ArrayList<>(store.values());
    }
    
    @Override
    public Optional<FigureCategory> changeStatus(FigureCategory category) {
        Optional<FigureCategory> categoryOptional = findByName(category.identity());

        if (categoryOptional.isEmpty()) {
            return Optional.empty();
        }

        categoryOptional.get().updateTime();
        categoryOptional.get().setUpdatedBy(new Email(AuthUtils.getCurrentUserEmail()));
        categoryOptional.get().toggleState();
        return categoryOptional;
    }
}
```

* JPA implementation performs an update within a transactional context and persists changes to the database.

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
    public Optional<FigureCategory> changeStatus(FigureCategory category) {
        try {
            FigureCategory managed = entityManager().find(FigureCategory.class, category.id());

            if (managed == null) {
                return Optional.empty();
            }

            entityManager().getTransaction().begin();

            managed.updateTime();
            managed.setUpdatedBy(new Email(AuthUtils.getCurrentUserEmail()));
            managed.toggleState();
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

Each approach maintains consistent logic and error handling.

### 5.5. Repository Selection

As with other features, the repository is chosen at runtime via the `RepositoryProvider`. This ensures the feature can operate in different environments (testing with in-memory or production with JPA) without changing controller or UI code.

```java
package persistence;


import lombok.Setter;
import persistence.inmemory.*;
import persistence.interfaces.*;

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

* **UI**: `ChangeFigureCategoryStatusUI`
* **Controllers**: `GetFigureCategoriesController`, `ChangeFigureCategoryStatusController`
* **Domain**: `FigureCategory`
* **Persistence Interface**: `FigureCategoryRepository`
* **In-Memory Implementation**: `InMemoryFigureCategoryRepository`
* **JPA Implementation**: `FigureCategoryJPAImpl`
* **Factory**: `RepositoryProvider`

This implementation enables users to maintain control over category availability while maintaining auditability and data integrity across all layers.


## 6. Integration and Demo

### 6.1 Integration Points

The *Change Figure Category Status* feature integrates smoothly with existing layers and components of the system:

- **Domain Layer**  
  The `FigureCategory` entity exposes the method `toggleState()` which switches a category between active and inactive states. It also supports auditing through `updateTime()` and `setUpdatedBy(...)`.

- **Controller Layer**  
  The feature utilizes:
    - `GetFigureCategoriesController` to fetch all categories (both active and inactive).
    - `ChangeFigureCategoryStatusController` to delegate the status change logic to the repository.

- **Infrastructure**  
  The `AuthUtils.getCurrentUserEmail()` is used to log the user responsible for changing the status by setting the `updatedBy` field.

- **Persistence Layer**  
  The repository (`FigureCategoryRepository`) provides the method `changeStatus(...)`, implemented in both the in-memory and JPA backends. This method:
    - Fetches the correct category by identity.
    - Toggles its status.
    - Updates its metadata (timestamp and updater).
    - Returns the updated object or `Optional.empty()` on failure.

- **UI Layer**  
  The CLI-based UI (`ChangeFigureCategoryStatusUI`) orchestrates the entire flow and handles user interaction.

---

### 6.2 Demo Walkthrough (CLI)

This feature is demonstrated through a user-friendly CLI interface:

1. **Access the UI**  
   The user selects the “Activate/Inactivate a Figure Category” option from the main menu.

2. **List All Categories**  
   The system retrieves and displays all categories using `GetFigureCategoriesController.getAllFigureCategories()` regardless of their current status.

3. **Inform Current Behavior**  
   The UI notifies the user that selecting a category will toggle its status (e.g., active → inactive, or vice versa).

4. **Category Selection**  
   The user selects a category from the list. The `Utils.showAndSelectIndexPartially(...)` method allows an intuitive and indexed selection process.

5. **Trigger Toggle Operation**  
   The selected category is passed to `ChangeFigureCategoryStatusController.changeStatus(...)`, which performs the operation and handles audit tracking.

6. **Feedback to User**
    - If the operation succeeds, a success message is printed.
    - If the operation fails (e.g., invalid object, missing from repo), an error message is displayed.

7. **Edge Case Handling**
    - If no categories are found in the system, the UI prints a failure message and terminates the flow gracefully.

## 7. Observations

There are no additional observations, limitations, or open questions for this User Story at this time.

