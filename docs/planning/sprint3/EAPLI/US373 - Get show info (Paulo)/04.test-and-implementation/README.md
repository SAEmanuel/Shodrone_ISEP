# US234 - Decommission Figure

## 1. Overview

The **Decommission Figure** feature allows authorized users to mark a figure as decommissioned (inactive), reflecting that it is no longer in use or available. This functionality updates the figure's state and audit metadata accordingly.

---

## 2. Tests

The test suite for **US234** focuses on validating the controller logic for decommissioning figures, ensuring correct interaction with the repository and proper handling of both success and failure cases.

---

### 2.1. Controller: `DecommissionFigureControllerTest`

Unit tests mock the `FigureRepository` to isolate controller logic:

- Tests successful decommissioning of a figure.
- Tests failure scenarios where the repository returns empty (e.g., figure not found or update fails).

```java
package controllers;

import authz.Email;
import controller.figure.DecommissionFigureController;
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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for DecommissionFigureController.
 * Mocks FigureRepository to simulate persistence layer behavior.
 * Tests successful and unsuccessful figure decommissioning scenarios.
 */
class DecommissionFigureControllerTest {
    private DecommissionFigureController controller;
    private FigureRepository mockRepository;

    // Sample FigureCategory to be used in tests
    private final FigureCategory category = new FigureCategory(
            new domain.valueObjects.Name("Geometry"),
            new Description("Common geometric shapes"),
            new Email("show_designer@shodrone.app")
    );

    // Sample Costumer for Figure constructor
    private final Costumer costumer = new Costumer(
            new Name("Sample Costumer"),
            new Address("123 Main St"),
            new Email("costumer@example.com"),
            new Phone("1234567890")
    );

    private final Figure activeFigure = new Figure(
            new Code("GEO123"),
            new Name("Triangle"),
            new Description("A basic geometric figure"),
            category,
            costumer,
            new Email("show_designer@shodrone.app")
    );

    @BeforeEach
    void setUp() {
        mockRepository = mock(FigureRepository.class);
        RepositoryProvider.injectFigureRepository(mockRepository);
        controller = new DecommissionFigureController();
    }

    @Test
    void testDecommissionFigure_Success() {
        when(mockRepository.changeStatus(any(Figure.class))).thenReturn(Optional.of(activeFigure));

        Optional<Figure> result = controller.decommissionFigure(activeFigure);

        assertTrue(result.isPresent());
    }

    @Test
    void testDecommissionFigure_Failure() {
        when(mockRepository.changeStatus(any(Figure.class))).thenReturn(Optional.empty());

        Optional<Figure> result = controller.decommissionFigure(activeFigure);

        assertTrue(result.isEmpty());
    }
}
```

## 3. Construction (Implementation)

### 3.1. Controller

The controller handles the business logic of decommissioning a figure by delegating status change requests to the repository.

```java
public class DecommissionFigureController {

    private final FigureRepository repository;

    public DecommissionFigureController() {
        this.repository = RepositoryProvider.figureRepository();
    }

    /**
     * Decommissions (toggles status) of the given figure.
     * @param figure the figure to decommission
     * @return Optional of updated figure if successful, empty otherwise
     */
    public Optional<Figure> decommissionFigure(Figure figure) {
        return repository.changeStatus(figure);
    }
}
```
### 3.2. Repository Interface

The repository interface includes a method to toggle the status of a figure.

```java
public interface FigureRepository {

    // Other repository methods...

    /**
     * Changes the status (active/inactive) of a figure.
     * @param figure the figure whose status will be toggled
     * @return Optional containing updated figure or empty if not found/failure
     */
    Optional<Figure> changeStatus(Figure figure);
}
```

### 3.3. Persistence Implementation (In-memory example)

The in-memory repository toggles the figure status and updates audit metadata.

```java
public class InMemoryFigureRepository implements FigureRepository {

    private final Map<String, Figure> store = new HashMap<>();

    @Override
    public Optional<Figure> changeStatus(Figure figure) {
        Optional<Figure> found = findByCode(figure.code().toString());
        if (found.isEmpty()) {
            return Optional.empty();
        }
        Figure existing = found.get();
        existing.toggleState();
        existing.updateTime();
        existing.setUpdatedBy(new Email(AuthUtils.getCurrentUserEmail()));
        return Optional.of(existing);
    }

    // Other repository methods...

    public Optional<Figure> findByCode(String code) {
        return Optional.ofNullable(store.get(code.toUpperCase()));
    }
}
```

## 4. Summary of Classes Involved

- **Controller**: `DecommissionFigureController`
- **Domain Entities**: `Figure`, `FigureCategory`, `Costumer`
- **Repository Interface**: `FigureRepository`
- **In-Memory Repository Implementation**: `InMemoryFigureRepository`
- **Repository Provider**: `RepositoryProvider`

---

## 5. Integration and Usage

- The decommission operation is typically triggered via the UI layer (not shown here), which calls the controller to toggle the figure's status.
- The repository handles persistence and ensures data integrity.
- Audit fields (`updatedBy`, `updatedOn`) are updated accordingly.
- The status toggle reflects the figure's active/inactive lifecycle.

---

## 6. Observations

- The design maintains consistency with the application's layered architecture.
- Testing isolates the controller logic by mocking the repository, allowing clear verification of business rules without persistence dependencies.
- Extensible to support JPA or other persistent storage mechanisms by implementing the `FigureRepository` interface.

---
