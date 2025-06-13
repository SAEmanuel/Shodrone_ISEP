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