# US233 - Add Figure to the catalogue

## 1. Overview

The **Add Figure to the catalogue** feature allows authorized users to add new figures with detailed information such as name, description, version, category, availability, status, and associated customer. This functionality validates the input and persists the new figure entity in the repository.

---

## 2. Tests

The test suite for **US233** focuses on validating the controller logic for adding figures, ensuring proper interaction with the repository and correct handling of success and failure cases.

---

### 2.1. Controller: `AddFigureControllerTest`

Unit tests mock the `FigureRepository` to isolate controller logic:

- Tests successful addition of a figure with full parameters.
- Tests addition of a figure using only name, category, and customer with default availability and status.
- Tests failure case when saving the figure fails (repository returns empty).

```java
package controllers;

import authz.Email;
import controller.figure.AddFigureController;
import domain.entity.Costumer;
import domain.entity.Figure;
import domain.entity.FigureCategory;
import domain.valueObjects.*;
import eapli.framework.general.domain.model.EmailAddress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.RepositoryProvider;
import persistence.interfaces.FigureRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;


/**
 * Unit tests for AddFigureController.
 * Uses Mockito to mock the FigureRepository and verify controller behavior.
 */
class AddFigureControllerTest {

    private AddFigureController controller;
    private FigureRepository mockRepository;

    // Sample valid test data
    private final Name name = new Name("Test Figure");
    private final Description description = new Description("Test description");
    private final Long version = 1L;
    private final FigureCategory category = new FigureCategory(
            new domain.valueObjects.Name("Geometry"),
            new Description("Common geometric shapes"),
            new Email("show_designer@shodrone.app")
    );
    private final FigureAvailability figureAvailability = FigureAvailability.PUBLIC;
    private final FigureStatus figureStatus = FigureStatus.ACTIVE;

    // Sample customer used to associate with the figure
    private final Costumer customer = new Costumer(
            eapli.framework.infrastructure.authz.domain.model.Name.valueOf("Jorge", "Ubaldo"),
            EmailAddress.valueOf("jorge.ubaldo@shodrone.app"),
            new PhoneNumber("912861312"),
            new NIF("123456789"),
            new Address("Brigadeiro Street", "Porto", "4440-778", "Portugal")
    );

    /**
     * Setup method called before each test.
     * Initializes the mock repository and injects it into the RepositoryProvider.
     * Instantiates the controller to be tested.
     */
    @BeforeEach
    void setUp() {
        mockRepository = mock(FigureRepository.class);
        RepositoryProvider.injectFigureRepository(mockRepository);
        controller = new AddFigureController();
    }

    /**
     * Tests successful addition of a Figure with all parameters.
     * Mocks repository save to return an Optional containing the figure.
     * Asserts the controller returns the figure wrapped in Optional and that repository save was called.
     */
    @Test
    void testAddFigureSuccess() {
        Figure fakeFigure = new Figure(name, description, version, category, figureAvailability, figureStatus, null, customer);
        when(mockRepository.save(any(Figure.class))).thenReturn(Optional.of(fakeFigure));

        Optional<Figure> result = controller.addFigure(name, description, version, category, figureAvailability, figureStatus, null, customer);

        assertTrue(result.isPresent());
        assertEquals(fakeFigure, result.get());
        verify(mockRepository).save(any(Figure.class));
    }

    /**
     * Tests adding a Figure with only name, category, and customer, relying on default availability and status.
     * Mocks repository save to return an Optional containing the figure.
     * Asserts correct Optional result and repository interaction.
     */
    @Test
    void testAddFigureWithNameCategoryAndCostumerOnly_Success() {
        //Added manually the default Availability and Status
        Figure fakeFigure = new Figure(name, null, null, category, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null, customer);
        when(mockRepository.save(any(Figure.class))).thenReturn(Optional.of(fakeFigure));

        //Added manually the default Availability and Status
        Optional<Figure> result = controller.addFigure(name, null, null, category, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null, customer);

        assertTrue(result.isPresent());
        assertEquals(fakeFigure, result.get());
        verify(mockRepository).save(any(Figure.class));
    }

    /**
     * Tests the case where saving a Figure fails (repository returns empty Optional).
     * Asserts that the controller returns an empty Optional to indicate failure.
     */
    @Test
    void testAddFigure_SaveFails_ReturnsEmpty() {
        when(mockRepository.save(any(Figure.class))).thenReturn(Optional.empty());

        Optional<Figure> result = controller.addFigure(name, null,null, null, null, null, null, null);

        assertTrue(result.isEmpty());
    }

}
```

## 3. Construction (Implementation)

### 3.1. Controller

The controller handles the business logic of adding a new figure by delegating save requests to the repository.

```java
public class AddFigureController {

    private final FigureRepository repository;

    public AddFigureController() {
        this.repository = RepositoryProvider.figureRepository();
    }

    /**
     * Adds a new Figure to the catalogue.
     * @param name the figure's name
     * @param description the figure's description (nullable)
     * @param version the figure version (nullable)
     * @param category the figure category
     * @param availability the figure availability
     * @param status the figure status
     * @param extra any extra information (nullable)
     * @param customer associated customer
     * @return Optional containing the created Figure if successful, empty otherwise
     */
    public Optional<Figure> addFigure(Name name, Description description, Long version, FigureCategory category,
                                      FigureAvailability availability, FigureStatus status, Object extra,
                                      Costumer customer) {
        Figure figure = new Figure(name, description, version, category,
                availability != null ? availability : FigureAvailability.PUBLIC,
                status != null ? status : FigureStatus.ACTIVE,
                extra, customer);
        return repository.save(figure);
    }
}
```

### 3.2. Repository Interface

The repository interface includes a method to save a new figure.

```java
public interface FigureRepository {

    // Other repository methods...

    /**
     * Saves a new figure to the repository.
     * @param figure the figure to save
     * @return Optional containing the saved figure or empty if save fails
     */
    Optional<Figure> save(Figure figure);
}
```

### 3.3. Persistence Implementation (In-memory example)

The in-memory repository stores the figure and returns it upon successful save.

```java
public class InMemoryFigureRepository implements FigureRepository {

    private final Map<String, Figure> store = new HashMap<>();

    @Override
    public Optional<Figure> save(Figure figure) {
        if (store.containsKey(figure.name().toString().toLowerCase())) {
            return Optional.empty(); // Already exists
        }
        store.put(figure.name().toString().toLowerCase(), figure);
        return Optional.of(figure);
    }

    // Other repository methods...
}
```

## 4. Summary of Classes Involved

- **Controller**: `AddFigureController`
- **Domain Entities**: `Figure`, `FigureCategory`, `Costumer`
- **Repository Interface**: `FigureRepository`
- **In-Memory Repository Implementation**: `InMemoryFigureRepository`
- **Repository Provider**: `RepositoryProvider`

---

## 5. Integration and Usage

- The add figure operation is typically triggered via the UI layer, which calls the controller to persist the new figure.
- The repository handles persistence and data integrity.
- Default availability and status are applied if not explicitly provided.
- Input validation should be handled before calling the controller (not shown here).

---

## 6. Observations

- The design maintains clean separation between layers.
- Controller tests mock repository for isolation, enabling verification of business logic.
- Easily extensible for persistent databases by implementing the `FigureRepository` interface.
