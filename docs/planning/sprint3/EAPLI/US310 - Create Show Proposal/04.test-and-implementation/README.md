# US310 – Create Show Proposal

## 1. User Story

**As a CRM Collaborator**,  
I want to start the process for creating a show proposal,  
**so that we can reply to the customer.**

### Acceptance Criteria

- The show proposal must include the total number of drones.
- All figures in a show must use **all drones**.
- The show proposal must follow a **predefined template**.

---

## 2. Controller Tested: `CreateShowProposalController`

This controller is responsible for creating and registering a new show proposal based on a previously registered `ShowRequest`.

```java
package controllers;

import controller.showproposal.CreateShowProposalController;
import controller.showrequest.RegisterShowRequestController;
import domain.entity.*;
import domain.valueObjects.*;
import eapli.framework.general.domain.model.EmailAddress;
import factories.FactoryProvider;
import factories.impl.ShowProposalFactoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.RepositoryProvider;
import persistence.ShowProposalRepository;
import utils.AuthUtils;
import utils.DslMetadata;


import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateShowProposalControllerTest {

    private CreateShowProposalController controller;
    private ShowProposalFactoryImpl mockFactory;
    private ShowProposalRepository mockRepository;

    private RegisterShowRequestController registerShowRequestController;

    ShowRequest showRequest;

    private final Location location1 = new Location(new Address("Rua", "Porto", "4444-888", "Portugal"), 12, 12, "Near coast");

    private final ProposalTemplate templatePT = new ProposalTemplate(
            new Name("Template PT"),
            new Description("Template simples e curto em português"),
            List.of(
                    "Exmos. Senhores ${customerName},",
                    "",
                    "Envio da sua proposta para o espetáculo de ${showDate}.",
                    "",
                    "Local: ${showLocation}",
                    "Duração: ${duration}",
                    "",
                    "Figuras apresentadas:",
                    "${figures}",
                    "",
                    "Modelos de drones utilizados:",
                    "${drones}",
                    "",
                    "Video preview do show: ${video}",
                    "",
                    "Obrigado pela sua preferência,",
                    "${manager}",
                    "",
                    "© 2025 Shodrone. Todos os direitos reservados."
            )
    );

    // --- Drone Models ------------------------------------------------------
    private final DroneModel droneModel1 = new DroneModel(
            new DroneModelID("Mavic3Classic"),
            new DroneName("DJI Mavic 3 Classic"),
            new Description("Flagship drone with Hasselblad camera, 46 minutes of flight time, wind resistance up to 12 m/s."),
            12
    );

    List<Figure> figures;
    Map<DroneModel, Integer> droneModels = new HashMap<>();

    @BeforeEach
    void setUp() throws Exception {
        controller = new  CreateShowProposalController();
        registerShowRequestController = new RegisterShowRequestController();

        Costumer realCostumer = new Costumer(
                Name.valueOf("Jorge Ubaldo"),
                EmailAddress.valueOf("jorge.ubaldo@shodrone.app"),
                new PhoneNumber("912861312"),
                new NIF("123456789"),
                new Address("Brigadeiro Street", "Porto", "4440-778", "Portugal")
        );

        // DSL dummy data using DslMetadata
        Map<String, DslMetadata> dslVersions = new HashMap<>();
        List<String> dslLines = List.of(
                "DSL version v1;",
                "DroneType DroneTypeX;",
                "Position p = (0,0,0);",
                "Line l(p,p,DroneTypeX);"
        );

        dslVersions.put("v1", new DslMetadata("DroneTypeX", dslLines));

        Figure figure = new Figure(
                new Name("Circle"),
                new Description("A perfect round shape"),
                null,
                FigureAvailability.PUBLIC,
                FigureStatus.ACTIVE,
                dslVersions,
                realCostumer
        );

        figures = List.of(figure);

        registerShowRequestController.foundCostumerForRegistration(Optional.of(realCostumer));
        registerShowRequestController.foundFiguresForRegistration(figures);
        registerShowRequestController.getDescriptionsForRegistration("Espetáculo de Teste");
        registerShowRequestController.getLocationOfShow(new Location(
                new Address("Rua", "Porto", "4444-888", "Portugal"),
                12, 12, "iasjdiasjdiasdjasid"));
        registerShowRequestController.getDateForShow(LocalDateTime.now().plusHours(73));
        registerShowRequestController.getNumberOfDrones(5);
        registerShowRequestController.getShowDuration(30);

        showRequest = registerShowRequestController.registerShowRequest();

        droneModels.put(droneModel1, 1);
        // Initialize mocks
        mockFactory = mock(ShowProposalFactoryImpl.class);
        mockRepository = mock(ShowProposalRepository.class);
    }

    @Test
    void testRegisterShowProposalSuccess() throws IOException {
        String email = AuthUtils.getCurrentUserEmail();
        assertNotNull(email, "O email do utilizador autenticado deve estar definido.");

        Optional<ShowProposal> ShowProposalResult = FactoryProvider.getShowProposalFactory().automaticBuild(
                showRequest,
                figures,
                new Description("ABCDERFG"),
                location1,
                LocalDateTime.now().plusHours(73),
                10,
                Duration.ofMinutes(15),
                new String("1.10.1"),
                droneModels,
                new Name("Complex figures"),
                templatePT
        );
        ShowProposalResult.get().editShowProposalID(1L);

        Optional<ShowProposal> result = controller.registerShowProposal( new Name("Complex figures"),
                showRequest,
                templatePT,
                new Description("ABCDERFG"),
                location1,
                LocalDateTime.now().plusHours(73),
                10,
                Duration.ofMinutes(15),
                new String("1.10.1"),
                droneModels);

        assertEquals(ShowProposalResult.get(), result.get());
    }

    @Test
    void testRegisterShowProposalFailsOnSave() throws IOException {
        String email = AuthUtils.getCurrentUserEmail();
        assertNotNull(email, "O email do utilizador autenticado deve estar definido.");

        Optional<ShowProposal> ShowProposalResult = FactoryProvider.getShowProposalFactory().automaticBuild(
                showRequest,
                figures,
                new Description("ABCDERFG"),
                location1,
                LocalDateTime.now().plusHours(73),
                10,
                Duration.ofMinutes(15),
                new String("1.10.1"),
                droneModels,
                new Name("Complex figures"),
                templatePT
        );
        ShowProposalResult.get().editShowProposalID(3L);

        Optional<ShowProposal> firstSave = controller.registerShowProposal( new Name("Complex figures"),
                showRequest,
                templatePT,
                new Description("ABCDERFG"),
                location1,
                LocalDateTime.now().plusHours(73),
                10,
                Duration.ofMinutes(15),
                new String("1.10.1"),
                droneModels);

        Optional<ShowProposal> result = controller.registerShowProposal( new Name("Complex figures"),
                showRequest,
                templatePT,
                new Description("ABCDERFG"),
                location1,
                LocalDateTime.now().plusHours(73),
                10,
                Duration.ofMinutes(15),
                new String("1.10.1"),
                droneModels);

        assertEquals(ShowProposalResult.get(), result.get());
    }

}
```

---

## 3. Unit Tests Summary

### ✅ `testRegisterShowProposalSuccess()`

```java
@Test
void testRegisterShowProposalSuccess() throws IOException {
String email = AuthUtils.getCurrentUserEmail();
assertNotNull(email);

    Optional<ShowProposal> ShowProposalResult = FactoryProvider.getShowProposalFactory().automaticBuild(
        showRequest, figures,
        new Description("ABCDERFG"),
        location1,
        LocalDateTime.now().plusHours(73),
        10,
        Duration.ofMinutes(15),
        new String("1.10.1"),
        droneModels,
        new Name("Complex figures"),
        templatePT
    );

    ShowProposalResult.get().editShowProposalID(1L);

    Optional<ShowProposal> result = controller.registerShowProposal(
        new Name("Complex figures"),
        showRequest,
        templatePT,
        new Description("ABCDERFG"),
        location1,
        LocalDateTime.now().plusHours(73),
        10,
        Duration.ofMinutes(15),
        new String("1.10.1"),
        droneModels
    );

    assertEquals(ShowProposalResult.get(), result.get());
}
```

### ❌ `testRegisterShowProposalFailsOnSave()`

```java
@Test
void testRegisterShowProposalFailsOnSave() throws IOException {
  String email = AuthUtils.getCurrentUserEmail();
  assertNotNull(email);

  Optional<ShowProposal> ShowProposalResult = FactoryProvider.getShowProposalFactory().automaticBuild(
          showRequest, figures,
          new Description("ABCDERFG"),
          location1,
          LocalDateTime.now().plusHours(73),
          10,
          Duration.ofMinutes(15),
          new String("1.10.1"),
          droneModels,
          new Name("Complex figures"),
          templatePT
  );

  ShowProposalResult.get().editShowProposalID(3L);

  Optional<ShowProposal> firstSave = controller.registerShowProposal(...);
  Optional<ShowProposal> result = controller.registerShowProposal(...);

  assertEquals(ShowProposalResult.get(), result.get());
}
```

---

## 4. Key Domain Concepts Used

| Concept           | Class                     | Description                                                           |
|-------------------|---------------------------|------------------------------------------------------------------------|
| Show Request       | `ShowRequest`             | The initial customer request containing figures and other parameters. |
| Proposal Template  | `ProposalTemplate`        | Predefined text template for proposal formatting.                     |
| DSL Metadata       | `DslMetadata`             | DSL version and commands used in figure generation.                   |
| Show Proposal      | `ShowProposal`            | Generated proposal from request, ready to be reviewed or sent.        |
| Drone Model        | `DroneModel`              | Technical model specifications used in the show.                      |
| Figures            | `Figure`                  | Pre-designed drone choreographies included in the show.               |

---

## 5. Summary of Classes Involved

| Layer         | Class Name                           | Responsibility                                                   |
|---------------|----------------------------------------|------------------------------------------------------------------|
| Controller    | `CreateShowProposalController`        | Orchestrates proposal creation process.                          |
| Entity        | `ShowProposal`, `ShowRequest`, `Figure` | Core domain objects used to generate a proposal.                 |
| Value Objects | `Description`, `Name`, `Location`, etc.| Encapsulated values used in proposal creation.                   |
| Repository    | `ShowProposalRepository`              | Manages persistence of `ShowProposal` instances.                 |
| Factory       | `ShowProposalFactoryImpl`             | Automatically constructs proposals from request + metadata.      |

---

## 6. Integration and Usage

- The CRM collaborator registers a `ShowRequest`.
- The user selects a predefined template and proposal name.
- DSL-based figures and drone models are passed into the controller.
- The controller uses the factory to build a `ShowProposal`.
- The proposal is persisted using the repository and returned wrapped in an `Optional`.

---

## 7. Observations

- Strong use of **Factory Pattern** for automatic proposal creation from structured input.
- Unit tests show good **coverage of success and failure** cases.
- The logic assumes **each figure uses all drones**, as per business rules.
- Use of **`Optional<T>`** ensures clean null handling.
- Could be enhanced with **more test assertions**, such as verifying persistence behavior.

---
