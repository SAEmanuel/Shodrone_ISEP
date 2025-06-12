package controllers;

import controller.showrequest.EditShowRequestController;
import domain.entity.Costumer;
import domain.entity.Figure;
import domain.entity.ShowRequest;
import domain.valueObjects.*;
import eapli.framework.general.domain.model.EmailAddress;
import factories.FactoryProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.RepositoryProvider;
import utils.DslMetadata;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class EditShowRequestControllerTest {

    private EditShowRequestController controller;
    private ShowRequest originalRequest;
    private Costumer costumer;

    @BeforeEach
    void setUp() {
        controller = new EditShowRequestController();

        costumer = new Costumer(
                Name.valueOf("Maria Silva"),
                EmailAddress.valueOf("maria.silva@shodrone.app"),
                new PhoneNumber("933333333"),
                new NIF("123456789"),
                new Address("Rua das Flores", "Lisboa", "1000-001", "Portugal")
        );
        RepositoryProvider.costumerRepository().saveInStore(costumer, costumer.nif());

        Map<String, DslMetadata> dslVersions = new HashMap<>();
        dslVersions.put("v1", new DslMetadata("DroneTypeX", List.of("Position p = (0,0,0);")));

        Figure figure1 = new Figure(new Name("Circle"), new Description("A perfect round shape"), null,
                FigureAvailability.PUBLIC, FigureStatus.ACTIVE, dslVersions, costumer);
        Figure figure2 = new Figure(new Name("Airplane"), new Description("A fixed-wing flying vehicle"), null,
                FigureAvailability.PUBLIC, FigureStatus.ACTIVE, dslVersions, costumer);

        List<Figure> figureList = List.of(figure1, figure2);

        originalRequest = FactoryProvider.getShowRequestFactory().automaticBuild(
                costumer,
                figureList,
                new Description("Empty"),
                new Location(new Address("Rua", "Porto", "4444-000", "Portugal"), 12, 12, "referência"),
                LocalDateTime.now().plusHours(73),
                12,
                Duration.ofMinutes(12)
        ).get();

        RepositoryProvider.showRequestRepository().saveInStore(originalRequest);
    }

    @Test
    void testEditShowRequest_Success() {
        Map<String, DslMetadata> dslVersions = new HashMap<>();
        dslVersions.put("v1", new DslMetadata("DroneTypeX", List.of("Position p = (1,1,1);")));

        Figure figure2 = new Figure(new Name("Airplane"), new Description("A fixed-wing flying vehicle"), null,
                FigureAvailability.PUBLIC, FigureStatus.ACTIVE, dslVersions, costumer);

        List<Figure> figureList = List.of(figure2);

        ShowRequest updated = new ShowRequest(
                originalRequest.identity(),
                originalRequest.getSubmissionDate(),
                originalRequest.getStatus(),
                originalRequest.getSubmissionAuthor(),
                originalRequest.getCostumer(),
                figureList,
                new Description("Show Atualizado"),
                originalRequest.getLocation(),
                originalRequest.getShowDate(),
                333,
                originalRequest.getShowDuration()
        );

        Optional<ShowRequest> result = controller.editShowRequest(originalRequest, updated);

        assertTrue(result.isPresent());
        ShowRequest edited = result.get();
        assertEquals("Show Atualizado", edited.getDescription().toString());
        assertEquals(costumer.identity(), edited.getCostumer().identity());
        assertNotNull(edited.getModificationDate());
        assertNotNull(edited.getModificationAuthor());
    }

    @Test
    void testEditShowRequest_FailsWithInvalidUpdate() {
        Map<String, DslMetadata> dslVersions = new HashMap<>();
        dslVersions.put("v1", new DslMetadata("DroneTypeX", List.of("invalid = true;")));

        Figure fakeFigure = new Figure(new Name("FakeFigure"), new Description("Fake Desc"), null,
                FigureAvailability.PUBLIC, FigureStatus.ACTIVE, dslVersions, costumer);

        List<Figure> figureList = List.of(fakeFigure);

        ShowRequest fakeOld = new ShowRequest(
                999L,
                LocalDateTime.now().minusDays(5),
                ShowRequestStatus.PENDING,
                "FakeAuthor",
                costumer,
                figureList,
                new Description("Fake Show"),
                new Location(new Address("Rua Falsa", "Nowhere", "0000-000", "Neverland"), 0, 0, "Ref"),
                LocalDateTime.now().plusDays(1),
                10,
                Duration.ofMinutes(30)
        );

        ShowRequest fakeUpdate = new ShowRequest(
                0L,
                LocalDateTime.now(),
                ShowRequestStatus.PENDING,
                "FakeAuthor2",
                costumer,
                figureList,
                new Description("Update inválido"),
                new Location(new Address("Rua Falsa 2", "Nowhere", "0000-001", "Neverland"), 1, 1, "Ref2"),
                LocalDateTime.now().plusDays(2),
                20,
                Duration.ofMinutes(60)
        );

        Optional<ShowRequest> result = controller.editShowRequest(fakeOld, fakeUpdate);
        assertTrue(result.isEmpty(), "A edição devia falhar para pedidos inexistentes");
    }

    @Test
    void testModificationDataIsSet() {
        ShowRequest updated = new ShowRequest(
                originalRequest.identity(),
                originalRequest.getSubmissionDate(),
                originalRequest.getStatus(),
                originalRequest.getSubmissionAuthor(),
                originalRequest.getCostumer(),
                originalRequest.getFigures(),
                new Description("Nova descrição"),
                originalRequest.getLocation(),
                originalRequest.getShowDate(),
                originalRequest.getNumberOfDrones() + 1,
                originalRequest.getShowDuration()
        );

        Optional<ShowRequest> result = controller.editShowRequest(originalRequest, updated);

        assertTrue(result.isPresent());
        ShowRequest edited = result.get();

        assertNotNull(edited.getModificationDate());
        assertNotNull(edited.getModificationAuthor());
        assertFalse(edited.getModificationAuthor().isBlank());
    }
}