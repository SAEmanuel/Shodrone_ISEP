# US247 - List figure categories

## 4. Tests

The test suite for **US247** ensures robust validation of both **domain logic** (inside `FigureCategory`) and the **application controller** (`GetFigureCategoriesController`) . Additionally, tests cover persistence logic using the **in-memory repository** implementation.

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

### 4.2. Application Logic: `GetFigureCategoriesController`

These tests verify that the application layer behaves correctly when interacting with the repository:

- `GetFigureCategoriesControllerTest` ensures:
    - All categories are retrieved when available.
    - Only active categories are retrieved when requested.
    - Empty results are handled correctly and returned as `Optional.empty()`.

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
    void testFindAllReturnsAllSaved() {
        repo.save(new FigureCategory(new Name("A name"), new Description("Description one"), creator));
        repo.save(new FigureCategory(new Name("B name"), new Description("Description two"), creator));
        List<FigureCategory> all = repo.findAll();
        assertEquals(2, all.size());
    }
    
}
```

## 5. Construction (Implementation)

The implementation of the *List Figure Categories* feature follows the same layered architectural principles as previous features. It cleanly separates responsibilities across the **UI**, **controller**, **domain**, and **persistence** layers to promote maintainability and reuse.

### 5.1. UI

The UI interaction is implemented in a class responsible for displaying all existing figure categories to the user. It leverages the `GetFigureCategoriesController` to retrieve the full list of categories and uses a utility function to render them in a clean, readable format.

* If no categories exist, a failure message is displayed to inform the user.
* If categories are available, they are printed in a formatted list using `Utils.showListElements(...)`.

```java
public class ListAllFigureCategoriesUI implements Runnable {
    private final GetFigureCategoriesController controller = new GetFigureCategoriesController();

    @Override
    public void run() {
        Utils.printCenteredTitle("All Figure Categories");

        Optional<List<FigureCategory>> allFigureCategoriesOptional = controller.getAllFigureCategories();

        if (allFigureCategoriesOptional.isEmpty()) {
            Utils.printFailMessage("No categories in the system yet...");
        } else {
            List<FigureCategory> allCategories = allFigureCategoriesOptional.get();
            Utils.showListElements(allCategories, "");
        }


    }
}
```

### 5.2. Controller

The controller used for this feature is `GetFigureCategoriesController`. It provides methods to retrieve:

* All figure categories (`getAllFigureCategories()`)

In this use case, only the method for fetching all categories is used.

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

This controller interacts with the repository and wraps the result in an `Optional`, returning `Optional.empty()` when no categories exist.

### 5.3. Domain

The domain model `FigureCategory` remains unchanged from previous features. It encapsulates all business rules for category identity, status, and descriptive metadata. This feature does not mutate domain objects, so only the read-related behaviors are relevant:

* `identity()` to get the name
* `description()` for display
* `isActive()` to determine status

This allows the UI layer to present meaningful, validated data while remaining agnostic of implementation details.

### 5.4. Persistence

Both the **in-memory** and **JPA-based** repositories fully support the listing operation through the `findAll()` method defined in `FigureCategoryRepository`.

* In-memory variant reads from a `Map` store.

```java
public class InMemoryFigureCategoryRepository implements FigureCategoryRepository {
    private final Map<String, FigureCategory> store = new HashMap<>();
    
    @Override
    public List<FigureCategory> findAll() {
        return new ArrayList<>(store.values());
    }


}
```

* JPA variant uses a JPQL query to fetch all persisted entities.

```java
    public List<T> findAll() {
        Query query = entityManager().createQuery(
                "SELECT e FROM " + entityClass.getSimpleName() + " e");
        List<T> list = query.getResultList();
        return list;
    }
```
Both implementations provide consistent results to the controller.

### 5.5. Repository Selection

As with previous features, the repository implementation is selected dynamically using the `RepositoryProvider`. This provider ensures that the same interface is used regardless of the backend (in-memory or JPA):

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

This allows the `GetFigureCategoriesController` and UI to remain decoupled from the persistence mechanism.

### 5.6. Summary of Classes Involved

* **UI**: `ListAllFigureCategoriesUI`
* **Controller**: `GetFigureCategoriesController`
* **Domain**: `FigureCategory`, `Name`, `Description`
* **Persistence Interface**: `FigureCategoryRepository`
* **In-Memory Implementation**: `InMemoryFigureCategoryRepository`
* **JPA Implementation**: `FigureCategoryJPAImpl`
* **Factory**: `RepositoryProvider`

This feature, while read-only, adheres to the same architectural rigor as the rest of the application and provides a clean and extensible solution for listing figure categories.


## 6. Integration and Demo

### 6.1 Integration Points

The *List Figure Categories* functionality integrates seamlessly across several architectural layers, facilitating a clear separation of responsibilities:

- **Domain Layer**  
  The `FigureCategory` entity encapsulates the business logic and state representation of each category, including the distinction between active and inactive statuses.

- **Persistence Layer**  
  The feature leverages the `FigureCategoryRepository` interface to retrieve categories from either the in-memory or JPA-backed data source. Methods such as `findAll()` and `findActiveCategories()` are utilized depending on the view mode.

- **Infrastructure Layer**  
  The `RepositoryProvider` component handles the dynamic resolution of the correct repository implementation (e.g., for testing or production).

- **Controller Layer**  
  The `GetFigureCategoriesController` mediates between the UI and the repository, exposing clean methods to fetch either all categories or only active ones.

- **UI Layer**  
  The CLI-based UI interacts with the controller, handles the presentation of the data, and manages edge cases such as an empty result list.

---

### 6.2 Demo Walkthrough (CLI)

The functionality is demonstrated through a simple and intuitive CLI flow, which includes the following steps:

1. **UI Activation**  
   The user selects the option “List Figure Categories” from the main menu. This triggers the `ListAllFigureCategoriesUI`.

2. **Data Retrieval**  
   The UI uses `GetFigureCategoriesController.getAllFigureCategories()` to fetch the list of categories.

3. **Condition Handling**
    - If no categories exist in the system, the user receives an immediate error message indicating that the list is empty.
    - If categories are present, the system proceeds to format and display them.

4. **Display Output**  
   All retrieved categories are printed to the console using a shared utility function that handles formatting. Both active and inactive categories are displayed, each clearly marked with its status via the `toString()` override.

5. **Read-Only Nature**  
   No user input or modification is required beyond selecting the action. This makes the feature purely consultative and safe for all users.

## 7. Observations

There are no additional observations, limitations, or open questions for this User Story at this time.
