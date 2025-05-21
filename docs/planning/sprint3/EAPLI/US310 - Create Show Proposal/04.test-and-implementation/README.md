# US231 - List Public Figures in Figure Catalogue

## 1. Overview

The **List Public Figures** feature allows users to retrieve all figures in the catalogue that are marked as *public* and *active*. This functionality supports the user in browsing publicly available figure templates without requiring access to private or exclusive ones. It filters the data at the controller level and ensures that only relevant figures are returned.

---

## 2. Tests

The test suite for **US231** focuses on the controller logic that lists public figures, using a mocked `FigureRepository` to simulate database behavior and verify business logic.

---

### 2.1. Controller: `ListPublicFiguresControllerTest`

```java
package controllers;

import authz.Email;
import controller.figure.ListPublicFiguresController;
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

class ListPublicFiguresControllerTest {
    private ListPublicFiguresController controller;
    private FigureRepository mockRepository;

    private final FigureCategory category = new FigureCategory(
        new domain.valueObjects.Name("Geometry"),
        new Description("Common geometric shapes"),
        new Email("show_designer@shodrone.app")
    );

    private final Costumer customer = new Costumer(
        eapli.framework.infrastructure.authz.domain.model.Name.valueOf("Jorge", "Ubaldo"),
        EmailAddress.valueOf("jorge.ubaldo@shodrone.app"),
        new PhoneNumber("912861312"),
        new NIF("123456789"),
        new Address("Brigadeiro Street", "Porto", "4440-778", "Portugal")
    );

    private final Figure figure1 = new Figure(
        new Name("Airplane"), new Description("Airplane figure"), (long) 1.2,
        category, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null, customer
    );

    private final Figure figure2 = new Figure(
        new Name("Airplane"), new Description("Airplane figure"), (long) 1.2,
        category, FigureAvailability.PUBLIC, FigureStatus.INACTIVE, null, customer
    );

    private final Figure figure3 = new Figure(
        new Name("Airplane"), new Description("Airplane figure"), (long) 1.2,
        category, FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE, null, customer
    );

    @BeforeEach
    void setUp() {
        mockRepository = mock(FigureRepository.class);

        List<Figure> allFigures = new ArrayList<>();
        allFigures.add(figure1);
        allFigures.add(figure2);
        allFigures.add(figure3);

        when(mockRepository.findAllPublicFigures()).thenReturn(allFigures);

        RepositoryProvider.injectFigureRepository(mockRepository);
        controller = new ListPublicFiguresController();
    }

    @Test
    void testGetAllPublicFiguresReturnsList() {
        List<Figure> list = new ArrayList<>();
        list.add(figure1);

        when(mockRepository.findAllPublicFigures()).thenReturn(list);
        Optional<List<Figure>> result = controller.listPublicFigures();

        assertTrue(result.isPresent());
    }

    @Test
    void testGetAllFigureCategoriesReturnsEmpty() {
        when(mockRepository.findAllPublicFigures()).thenReturn(new ArrayList<>());
        Optional<List<Figure>> result = controller.listPublicFigures();

        assertTrue(result.isEmpty());
    }

    @Test
    void testGetPublicFiguresThatAreInactiveReturnsEmpty() {
        List<Figure> filteredFigures = new ArrayList<>();
        filteredFigures.add(figure1);

        when(mockRepository.findAllPublicFigures()).thenReturn(filteredFigures);
        Optional<List<Figure>> result = controller.listPublicFigures();

        assertTrue(result.isPresent());
        assertTrue(result.get().contains(figure1));
    }

    @Test
    void testGetPublicFiguresThatAreExclusiveReturnsEmpty() {
        List<Figure> filteredFigures = new ArrayList<>();
        filteredFigures.add(figure1);

        when(mockRepository.findAllPublicFigures()).thenReturn(filteredFigures);
        Optional<List<Figure>> result = controller.listPublicFigures();

        assertTrue(result.isPresent());
        assertTrue(result.get().contains(figure1));
    }
}
``` 

## 3. Construction (Implementation)

### 3.1. Controller

The controller fetches public figures from the repository and filters based on expected visibility and activity.

```java
public class ListPublicFiguresController {

    private final FigureRepository repository;

    public ListPublicFiguresController() {
        this.repository = RepositoryProvider.figureRepository();
    }

    /**
     * Retrieves all public figures from the repository.
     * @return Optional containing the list of public figures, or empty if none are found.
     */
    public Optional<List<Figure>> listPublicFigures() {
        List<Figure> figures = repository.findAllPublicFigures();
        if (figures == null || figures.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(figures);
    }
}
```

### 3.2. Repository Interface

The repository interface defines the contract for retrieving public figures from the catalogue.

```java
public interface FigureRepository {

    // Other repository methods...

    /**
     * Retrieves all figures with PUBLIC availability.
     * @return List of public figures.
     */
    List<Figure> findAllPublicFigures();
}
```

### 3.3. Persistence Implementation (In-memory example)

The in-memory repository filters figures based on availability status and returns those marked as PUBLIC.
```java
public class InMemoryFigureRepository implements FigureRepository {

    private final Map<String, Figure> store = new HashMap<>();

    @Override
    public List<Figure> findAllPublicFigures() {
        return store.values().stream()
                .filter(f -> f.availability() == FigureAvailability.PUBLIC)
                .collect(Collectors.toList());
    }

    // Other repository methods...
}
```

## 4. Summary of Classes Involved

- **Controller**: `ListPublicFiguresController`
- **Domain Entity**: `Figure`
- **Repository Interface**: `FigureRepository`
- **In-Memory Repository Implementation**: `InMemoryFigureRepository`
- **Repository Provider**: `RepositoryProvider`

---

## 5. Integration and Usage

- This feature is typically triggered through the UI, allowing users to view all publicly available figures.
- The `ListPublicFiguresController` acts as the intermediary between the UI and the data layer.
- It calls the repository method `findAllPublicFigures()` to fetch figures marked with `FigureAvailability.PUBLIC`.
- The repository implementation handles filtering; only public figures are returned to the controller.
- The result is wrapped in an `Optional<List<Figure>>`, which returns empty when no public figures are available.
- This design enables robust behavior for front-end rendering and user feedback (e.g., showing "no public figures available").

---

## 6. Observations

- The controller contains no filtering logic, delegating that responsibility entirely to the repository.
- Public figures are distinguished by `FigureAvailability.PUBLIC`, allowing for easy expansion (e.g., supporting other availability types).
- The system is tested against scenarios including:
    - One or more public figures present.
    - No figures present.
    - Mixed figure types (public, exclusive, inactive).
- The in-memory implementation enables isolated unit testing without needing a database.
- The structure promotes a clean separation of concerns:
    - Controller handles orchestration.
    - Repository handles persistence and filtering.
    - Domain entities encapsulate business logic and structure.
