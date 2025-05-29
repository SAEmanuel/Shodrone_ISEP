package controllers;

import controller.showrequest.EditShowRequestController;
import domain.entity.Costumer;
import domain.entity.Figure;
import domain.entity.ShowRequest;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.Name;
import factories.FactoryProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.RepositoryProvider;
import domain.valueObjects.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class EditShowRequestControllerTest {

    private EditShowRequestController controller;
    private ShowRequest originalRequest;
    private Costumer costumer;

    @BeforeEach
    void setUp() {
        controller = new EditShowRequestController();

        // Criar cliente real
        costumer = new Costumer(
                Name.valueOf("Maria", "Silva"),
                EmailAddress.valueOf("maria.silva@shodrone.app"),
                new PhoneNumber("933333333"),
                new NIF("123456789"),
                new Address("Rua das Flores", "Lisboa", "1000-001", "Portugal")
        );
        RepositoryProvider.costumerRepository().saveInStore(costumer, costumer.nif());

        // Criar figuras
        Figure figure1 = new Figure(new domain.valueObjects.Name("Circle"), new Description("A perfect round shape"), 1L, null, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null, costumer);
        Figure figure2 = new Figure(new domain.valueObjects.Name("Airplane"), new Description("A fixed-wing flying vehicle"), 2L, null, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null, costumer);
        List<Figure> figureList = new ArrayList<>();
        figureList.add(figure1);
        figureList.add(figure2);

        // Criar pedido original
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
        Figure figure2 = new Figure(new domain.valueObjects.Name("Airplane"), new Description("A fixed-wing flying vehicle"), 2L, null, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null, costumer);
        List<Figure> figureList = new ArrayList<>();
        figureList.add(figure2);

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

        assertTrue(result.isPresent(), "O pedido atualizado deveria estar presente");

        ShowRequest edited = result.get();
        assertEquals("Show Atualizado", edited.getDescription().toString());
        assertEquals(costumer.identity(), edited.getCostumer().identity());
        assertNotNull(edited.getModificationDate());
        assertNotNull(edited.getModificationAuthor());
    }

    @Test
    void testEditShowRequest_FailsWithInvalidUpdate() {
        // Criar lista de figuras
        Figure figure = new Figure(new domain.valueObjects.Name("FakeFigure"), new Description("Fake Desc"), 99L, null, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null, costumer);
        List<Figure> figureList = new ArrayList<>();
        figureList.add(figure);

        // Criar pedidos falsos com ID não existente
        ShowRequest fakeOld = new ShowRequest(
                1L,
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

        assertNotNull(edited.getModificationDate(), "A data de modificação não deve ser nula");
        assertNotNull(edited.getModificationAuthor(), "O autor da modificação não deve ser nulo");
        assertFalse(edited.getModificationAuthor().isBlank(), "O autor da modificação não deve estar em branco");
    }
}
