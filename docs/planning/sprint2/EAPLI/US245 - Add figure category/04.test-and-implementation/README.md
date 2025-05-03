
# US245 - Add Figure Category

## 4. Tests

The test suite for **US245** ensures robust validation of both **domain logic** (inside `FigureCategory`) and the **application controller** (`AddFigureCategoryController`). Additionally, tests cover persistence logic using the **in-memory repository** implementation.

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

### 4.2. Application Logic: `AddFigureCategoryController`

These unit tests mock the repository to test the controller logic in isolation:

- **testAddFigureCategoryWithNameAndDescription_Success**: verifies successful creation with full parameters.
- **testAddFigureCategoryWithNameOnly_Success**: ensures fallback to default description works.
- **testAddFigureCategory_SaveFails_ReturnsEmpty**: simulates repository failure (e.g., duplicate category).

```java
class AddFigureCategoryControllerTest {

    private AddFigureCategoryController controller;
    private FigureCategoryRepository mockRepository;

    private final Name name = new Name("Test Category");
    private final Description description = new Description("Test description");
    private final Email createdBy = new Email("user@shodrone.app");

    @BeforeEach
    void setUp() {
        mockRepository = mock(FigureCategoryRepository.class);
        RepositoryProvider.injectFigureCategoryRepository(mockRepository);
        controller = new AddFigureCategoryController();
    }

    @Test
    void testAddFigureCategoryWithNameAndDescription_Success() {
        FigureCategory fakeCategory = new FigureCategory(name, description, createdBy);
        when(mockRepository.save(any(FigureCategory.class))).thenReturn(Optional.of(fakeCategory));

        Optional<FigureCategory> result = controller.addFigureCategoryWithNameAndDescription(name, description, createdBy);

        assertTrue(result.isPresent());
        assertEquals(fakeCategory, result.get());
        verify(mockRepository).save(any(FigureCategory.class));
    }

    @Test
    void testAddFigureCategoryWithNameOnly_Success() {
        FigureCategory fakeCategory = new FigureCategory(name, createdBy);
        when(mockRepository.save(any(FigureCategory.class))).thenReturn(Optional.of(fakeCategory));

        Optional<FigureCategory> result = controller.addFigureCategoryWithName(name, createdBy);

        assertTrue(result.isPresent());
        assertEquals(fakeCategory, result.get());
        verify(mockRepository).save(any(FigureCategory.class));
    }

    @Test
    void testAddFigureCategory_SaveFails_ReturnsEmpty() {
        when(mockRepository.save(any(FigureCategory.class))).thenReturn(Optional.empty());

        Optional<FigureCategory> result = controller.addFigureCategoryWithNameAndDescription(name, description, createdBy);

        assertTrue(result.isEmpty());
    }
}
```

---

### 4.3. Persistence: `InMemoryFigureCategoryRepository`

This class is tested for its ability to persist, retrieve, update, and filter figure categories entirely in-memory.

```java

public class InMemoryFigureCategoryRepositoryTest {

    private InMemoryFigureCategoryRepository repository;
    private final Email creator = new Email("test@shodrone.app");

    @BeforeEach
    void setUp() {
        repository = new InMemoryFigureCategoryRepository();
    }

    @Test
    void testSaveNewCategory() {
        FigureCategory cat = new FigureCategory(new Name("NewCat"), new Description("desc123"), creator);
        Optional<FigureCategory> result = repository.save(cat);
        assertTrue(result.isPresent());
    }

    @Test
    void testSaveDuplicateReturnsEmpty() {
        FigureCategory cat = new FigureCategory(new Name("Dup"), new Description("desc123"), creator);
        repository.save(cat);
        Optional<FigureCategory> result = repository.save(cat);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByName() {
        FigureCategory cat = new FigureCategory(new Name("FindMe"), new Description("desc123"), creator);
        repository.save(cat);
        Optional<FigureCategory> found = repository.findByName("FindMe");
        assertTrue(found.isPresent());
    }

    @Test
    void testFindAllReturnsAllSaved() {
        repository.save(new FigureCategory(new Name("A"), new Description("desc123"), creator));
        repository.save(new FigureCategory(new Name("B"), new Description("desc123"), creator));
        List<FigureCategory> all = repository.findAll();
        assertEquals(2, all.size());
    }

    @Test
    void testFindActiveCategoriesReturnsOnlyActive() {
        FigureCategory active = new FigureCategory(new Name("Active"), new Description("desc123"), creator);
        FigureCategory inactive = new FigureCategory(new Name("Inactive"), new Description("desc123"), creator);
        repository.save(active);
        repository.save(inactive);
        inactive.toggleState();
        List<FigureCategory> activeList = repository.findActiveCategories();
        assertEquals(1, activeList.size());
        assertEquals("Active", activeList.get(0).identity());
    }
}
```

Each test above ensures that the repository behaves correctly in both typical and edge cases.


## 5. Construction (Implementation)

The implementation of the *Add Figure Category* feature follows a layered architecture with separation of concerns across the **UI**, **controller**, **domain**, and **persistence** layers. It is fully integrated with the existing repository infrastructure that allows runtime switching between **in-memory** and **JPA-backed** persistence.

### 5.1. UI

The user interaction starts in a dedicated UI class responsible for printing prompts and reading input for the figure category name and optional description. Input validation is handled via utility functions that prevent invalid entries (e.g., empty name, overly short description). The UI delegates the actual business logic to the controller and shows success/error messages based on the result.
```java
public class AddFigureCategoryUI implements Runnable {

    private final AddFigureCategoryController controller = new AddFigureCategoryController();

    @Override
    public void run() {
        Utils.printCenteredTitle("Add Figure Category");

        Utils.showNameRules();
        Name name = Utils.rePromptWhileInvalid("Enter the Category name: ", Name::new);
        Description description;

        boolean option = Utils.confirm("Do you want to add a description? (y/n)");

        Optional<FigureCategory> result;

        Email createdBy = new Email(AuthUtils.getCurrentUserEmail());
        if (!option) {
            Utils.printAlterMessage("Description skipped...");
            result = controller.addFigureCategoryWithName(name, createdBy);

        } else {
            Utils.showDescriptionRules();
            description = Utils.rePromptWhileInvalid("Enter the Category description: ", Description::new);
            result = controller.addFigureCategoryWithNameAndDescription(name, description, createdBy);
        }

        if (result.isPresent()) {
            Utils.printSuccessMessage("Category added successfully!");
        } else {
            Utils.printFailMessage("A category with that name already exists!");
        }
    }

}
```


### 5.2. Controller

The controller class handles the main business logic:
- It receives validated input from the UI.
- It constructs the domain object (`FigureCategory`) using either the full constructor or the one with only name and creator.
- It delegates persistence to the repository, handling both success and failure (e.g., duplicate name).

The controller shields the UI from the details of the domain rules and repository implementation.
```java
public class AddFigureCategoryController {
    private final FigureCategoryRepository repository;

    public AddFigureCategoryController() {
        repository = RepositoryProvider.figureCategoryRepository();
    }

    public Optional<FigureCategory> addFigureCategoryWithNameAndDescription(Name name, Description description, Email createdBy) {
        FigureCategory category = new FigureCategory(name, description, createdBy);
        return repository.save(category);
    }

    public Optional<FigureCategory> addFigureCategoryWithName(Name name, Email createdBy) {
        FigureCategory category = new FigureCategory(name, createdBy);
        return repository.save(category);
    }
}
```

### 5.3. Domain

The domain layer is represented by the `FigureCategory` aggregate root:
- It encapsulates the logic for creating and validating a figure category.
- It ensures the name and description follow business rules (minimum length, non-null, etc.).
- It automatically sets audit metadata (createdBy, createdOn).
- It provides public methods to later change the name and description, enforcing internal rules.
```java
public interface FigureCategoryRepository {

    Optional<FigureCategory> save(FigureCategory category);

    Optional<FigureCategory> findByName(String name);

    List<FigureCategory> findAll();

    List<FigureCategory> findActiveCategories();

    Optional<FigureCategory> editChosenCategory(FigureCategory category, Name newName, Description newDescription);

    Optional<FigureCategory> changeStatus(FigureCategory category);


}
```

### 5.4. Persistence

The system supports both **in-memory** and **JPA** implementations of the `FigureCategoryRepository` interface:
- The `InMemoryFigureCategoryRepository` is useful for fast testing and does not require a database.
```java
public class InMemoryFigureCategoryRepository implements FigureCategoryRepository {
    private final Map<String, FigureCategory> store = new HashMap<>();

    @Override
    public Optional<FigureCategory> save(FigureCategory category) {
        String key = category.identity().toLowerCase();
        if (store.containsKey(key)) {
            return Optional.empty();
        } else {
            store.put(key, category);
            return Optional.of(category);
        }
    }

    @Override
    public Optional<FigureCategory> findByName(String name) {
        return Optional.ofNullable(store.get(name.toLowerCase()));
    }

    @Override
    public List<FigureCategory> findAll() {
        return new ArrayList<>(store.values());
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

- The `FigureCategoryJPAImpl` provides durable storage backed by a relational database.
```java
public class FigureCategoryJPAImpl extends JpaBaseRepository<FigureCategory, Long>
        implements FigureCategoryRepository {


    @Override
    public Optional<FigureCategory> save(FigureCategory category) {
        Optional<FigureCategory> checkExistence = findByName(category.identity());
        if (checkExistence.isEmpty()) {
            FigureCategory saved = this.add(category);
            return Optional.ofNullable(saved);
        }
        return Optional.empty();
    }


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

Both repositories implement the interface methods, ensuring consistent behavior across backends.

### 5.5. Repository Selection

The repository to use is selected dynamically through a factory/provider class:

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

    public static FigureRepository figureRepository() {
        if (figureRepository == null) {
            if (isInMemory()) {
                figureRepository = new InMemoryFigureRepository();
            } else {
                figureRepository = new FigureRepositoryJPAImpl();
            }
        }
        return figureRepository;
    }

    public static CostumerRepository costumerRepository() {
        if (costumerRepository == null) {
            if (isInMemory()) {
                costumerRepository = new InMemoryCustomerRepository();
            } else {
                costumerRepository = new CostumerJPAImpl();
            }
        }
        return costumerRepository;
    }

    public static ShowRequestRepository showRequestRepository() {
        if (showRequestRepository == null) {
            if (isInMemory()) {
                showRequestRepository = new InMemoryShowRequestRepository();
            } else {
                showRequestRepository = new ShowRequestJPAImpl();
            }
        }
        return showRequestRepository;
    }

    public static AuthenticationRepository authenticationRepository() {
        if (authenticationRepository == null) {
            if (isInMemory()) {
                authenticationRepository = new InMemoryAuthenticationRepository();
            } else {
                authenticationRepository = new AuthenticationRepositoryJPAImpl();
            }
        }
        return authenticationRepository;
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

This mechanism hides the implementation detail from the rest of the application and allows easy toggling between in-memory and JPA for different environments (e.g., testing vs. production).

### 5.6. Summary of Classes Involved

- **UI**: `AddFigureCategoryUI`
- **Controller**: `AddFigureCategoryController`
- **Domain**: `FigureCategory`, `Name`, `Description`, `Email`
- **Persistence Interface**: `FigureCategoryRepository`
- **In-Memory Implementation**: `InMemoryFigureCategoryRepository`
- **JPA Implementation**: `FigureCategoryJPAImpl`
- **Factory**: `RepositoryProvider`

All components are designed following the SOLID principles, ensuring low coupling and high cohesion, which makes the feature robust, testable, and extensible.


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
