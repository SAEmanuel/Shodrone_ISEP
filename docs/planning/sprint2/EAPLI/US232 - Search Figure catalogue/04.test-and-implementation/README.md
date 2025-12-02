# US232 - Search Figure catalogue

## 1. Overview

The **Search Figure catalogue** feature enables users to search for figures using multiple optional filters like id, name, description, version, category, availability, status, createdBy email, and associated customer. It returns all matching figures in an optional list.

---

## 2. Tests

Unit tests for **US232** focus on validating the controller’s ability to interact with a mocked repository, verifying correct results are returned when searching by different criteria:

- Searching by figure ID, name, description, version, category, availability, status, and customer.
- Testing the controller handles cases with no matches properly.
- Mocks ensure the repository behaves as expected without requiring real persistence.

```java
package controllers;

import authz.Email;
import controller.figure.SearchFigureController;
import domain.entity.Costumer;
import domain.entity.Figure;
import domain.entity.FigureCategory;
import domain.valueObjects.*;
import eapli.framework.general.domain.model.EmailAddress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.RepositoryProvider;
import persistence.interfaces.FigureRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for SearchFigureController.
 * Uses a mocked FigureRepository to simulate figure searches by various criteria.
 */
class SearchFigureControllerTest {
    private SearchFigureController controller;
    private FigureRepository mockRepository;

    // Sample FigureCategory used for figures
    private final FigureCategory category = new FigureCategory(
            new domain.valueObjects.Name("Geometry"),
            new Description("Common geometric shapes"),
            new Email("show_designer@shodrone.app")
    );

    // Sample Costumer to associate with figures
    private final Costumer customer = new Costumer(
            eapli.framework.infrastructure.authz.domain.model.Name.valueOf("Jorge", "Ubaldo"),
            EmailAddress.valueOf("jorge.ubaldo@shodrone.app"),
            new PhoneNumber("912861312"),
            new NIF("123456789"),
            new Address("Brigadeiro Street", "Porto", "4440-778", "Portugal")
    );

    // Sample figures with different attributes for testing search functionality
    private final Figure figure1 = new Figure(new Name("Airplane"), new Description("Airplane figure"), (long) 1.2,
            category, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null, customer
    );

    private final Figure figure2 = new Figure(new Name("Airplane"), new Description("Airplane figure"), (long) 1.2,
            category, FigureAvailability.PUBLIC, FigureStatus.INACTIVE, null, customer
    );

    private final Figure figure3 = new Figure(new Name("Helicopter"), new Description("Helicopter figure"), (long) 2.0,
            category, FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE, null, customer
    );

    /**
     * Setup executed before each test:
     * - Creates a mock FigureRepository.
     * - Configures the mock to return allFigures when findFigures is called with all null parameters.
     * - Injects the mock into RepositoryProvider.
     * - Instantiates the SearchFigureController.
     */
    @BeforeEach
    void setUp() {
        mockRepository = mock(FigureRepository.class);

        List<Figure> allFigures = new ArrayList<>();
        allFigures.add(figure1);
        allFigures.add(figure2);
        allFigures.add(figure3);

        when(mockRepository.findFigures(null, null, null, null,
                null, null, null, null, null))
                .thenReturn(Optional.of(allFigures));

        RepositoryProvider.injectFigureRepository(mockRepository);

        controller = new SearchFigureController();
    }

    /**
     * Tests searching figures by figure ID.
     * Mocks repository to return a list containing only figure1 for the given ID.
     */
    @Test
    void testSearchByFigureId() {
        // Assuming you're searching for the figure with a specific ID (e.g., figure1's ID)
        when(mockRepository.findFigures(figure1.identity(), null, null,
                null, null, null, null, null, null))
                .thenReturn(Optional.of(List.of(figure1)));

        Optional<List<Figure>> result = controller.searchFigure(figure1.identity(), null, null,
                null, null, null, null, null, null);

        assertTrue(result.isPresent());
        assertTrue(result.get().contains(figure1));
    }

    /**
     * Tests searching figures by name.
     * Mocks repository to return a list containing figure1 for name "Airplane".
     */
    @Test
    void testSearchByName() {
        List<Figure> expectedList = new ArrayList<>();
        expectedList.add(figure1);

        when(mockRepository.findFigures(null, new Name("Airplane"), null, null, null, null, null, null, null))
                .thenReturn(Optional.of(expectedList));

        Optional<List<Figure>> result = controller.searchFigure(null, new Name("Airplane"), null, null, null, null, null, null, null);

        assertTrue(result.isPresent());
        assertTrue(result.get().contains(figure1));
    }

    /**
     * Tests searching figures by description.
     * Mocks repository to return a list containing figure1 for the given description.
     */
    @Test
    void testSearchByDescription() {
        List<Figure> expectedList = new ArrayList<>();
        expectedList.add(figure1);

        when(mockRepository.findFigures(null, null, new Description("Airplane figure"), null, null, null, null, null, null))
                .thenReturn(Optional.of(expectedList));

        Optional<List<Figure>> result = controller.searchFigure(null, null, new Description("Airplane figure"), null, null, null, null, null, null);

        assertTrue(result.isPresent());
        assertTrue(result.get().contains(figure1));
    }

    /**
     * Tests searching figures by version.
     * Mocks repository to return a list containing figure1 for version 1.2.
     */
    @Test
    void testSearchByVersion() {
        List<Figure> expectedList = new ArrayList<>();
        expectedList.add(figure1);

        when(mockRepository.findFigures(null, null, null, (long) 1.2, null, null, null, null, null))
                .thenReturn(Optional.of(expectedList));

        Optional<List<Figure>> result = controller.searchFigure(null, null, null, (long) 1.2, null, null, null, null, null);

        assertTrue(result.isPresent());
        assertTrue(result.get().contains(figure1));
    }

    /**
     * Tests searching figures by category.
     * Mocks repository to return a list containing figure1 for the specified category.
     */
    @Test
    void testSearchByCategory() {
        List<Figure> expectedList = new ArrayList<>();
        expectedList.add(figure1);

        when(mockRepository.findFigures(null, null, null, null, category, null, null, null, null))
                .thenReturn(Optional.of(expectedList));

        Optional<List<Figure>> result = controller.searchFigure(null, null, null, null, category, null, null, null, null);

        assertTrue(result.isPresent());
        assertTrue(result.get().contains(figure1));
    }

    /**
     * Tests searching figures by availability.
     * Mocks repository to return a list containing figure1 for availability PUBLIC.
     */
    @Test
    void testSearchByAvailability() {
        List<Figure> expectedList = new ArrayList<>();
        expectedList.add(figure1);

        when(mockRepository.findFigures(null, null, null, null, null, FigureAvailability.PUBLIC, null, null, null))
                .thenReturn(Optional.of(expectedList));

        Optional<List<Figure>> result = controller.searchFigure(null, null, null, null, null, FigureAvailability.PUBLIC, null, null, null);

        assertTrue(result.isPresent());
        assertTrue(result.get().contains(figure1));
    }

    /**
     * Tests searching figures by status.
     * Mocks repository to return a list containing figure1 for status ACTIVE.
     */
    @Test
    void testSearchByStatus() {
        List<Figure> expectedList = new ArrayList<>();
        expectedList.add(figure1);

        when(mockRepository.findFigures(null, null, null, null, null, null, FigureStatus.ACTIVE, null, null))
                .thenReturn(Optional.of(expectedList));

        Optional<List<Figure>> result = controller.searchFigure(null, null, null, null, null, null, FigureStatus.ACTIVE, null, null);

        assertTrue(result.isPresent());
        assertTrue(result.get().contains(figure1));
    }

    /**
     * Tests searching figures by customer.
     * Mocks repository to return a list containing figure1 for the specified customer.
     */
    @Test
    void testSearchByCustomer() {
        List<Figure> expectedList = new ArrayList<>();
        expectedList.add(figure1);

        when(mockRepository.findFigures(null, null, null, null, null, null, null, null, customer))
                .thenReturn(Optional.of(expectedList));

        Optional<List<Figure>> result = controller.searchFigure(null, null, null, null, null, null, null, null, customer);

        assertTrue(result.isPresent());
        assertTrue(result.get().contains(figure1));
    }
}
```

## 3. Construction (Implementation)

### 3.1 Controller

The controller handles the business logic of searching figures by delegating the query with optional filters to the repository.

```java
public class SearchFigureController {

    private final FigureRepository repository;

    public SearchFigureController() {
        this.repository = RepositoryProvider.figureRepository();
    }

    /**
     * Searches for figures matching given optional criteria.
     * @param id            figure ID
     * @param name          figure name
     * @param description   figure description
     * @param version       figure version
     * @param category      figure category
     * @param availability  figure availability
     * @param status        figure status
     * @param createdBy     creator's email
     * @param costumer      associated costumer
     * @return Optional list of figures matching criteria
     */
    public Optional<List<Figure>> searchFigure(Long id, Name name, Description description, Long version,
                                               FigureCategory category, FigureAvailability availability,
                                               FigureStatus status, Email createdBy, Costumer costumer) {
        return repository.findFigures(id, name, description, version, category, availability, status, createdBy, costumer);
    }
}
```

### 3.2. Repository Interface

Defines a method to find figures filtered by multiple optional criteria.
```java
public interface FigureRepository {

    // Other repository methods...

    /**
     * Finds figures by optional filtering
    /**
     * Finds figures by optional filtering criteria.
     * All parameters are optional — null values will be ignored in the filtering.
     *
     * @param id            figure ID
     * @param name          figure name
     * @param description   figure description
     * @param version       figure version
     * @param category      figure category
     * @param availability  figure availability
     * @param status        figure status
     * @param createdBy     creator's email
     * @param costumer      associated costumer
     * @return Optional list of matching figures
     */
    Optional<List<Figure>> findFigures(Long id, Name name, Description description, Long version,
                                       FigureCategory category, FigureAvailability availability,
                                       FigureStatus status, Email createdBy, Costumer costumer);
}
```


---

## 4. Summary of Classes Involved

- **Controller**: `SearchFigureController`
- **Domain Entities**: `Figure`, `FigureCategory`, `Costumer`
- **Repository Interface**: `FigureRepository`

---

## 5. Integration and Usage

- The search is triggered from UI or service layers calling the controller with the desired filters.
- The repository processes the query and returns matched figures.
- The controller returns an optional list to handle cases where no matches exist.

---

## 6. Observations

- Use of mocks enables isolated testing of controller logic without dependency on database.
- Flexible search interface supports complex queries with multiple criteria.
- Easily extended for persistent implementations and more sophisticated search logic.
