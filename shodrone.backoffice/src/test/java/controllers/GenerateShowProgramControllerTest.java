package controllers;

import controller.drone.GenerateShowProgramController;
import domain.entity.*;
import domain.valueObjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.RepositoryProvider;
import persistence.ShowProposalRepository;
import utils.DslMetadata;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GenerateShowProgramControllerTest {

    private GenerateShowProgramController controller;
    private ShowProposal proposal;
    private ShowProposalRepository mockRepo;

    private DroneModel model;
    private Figure figure;

    @BeforeEach
    void setUp() {
        controller = new GenerateShowProgramController();

        mockRepo = mock(ShowProposalRepository.class);
        RepositoryProvider.injectShowProposalRepository(mockRepo);

        model = new DroneModel(
                new DroneModelID("M001"),
                new DroneName("Mini4Pro"),
                new Description("Test drone"),
                10.0
        );

        // DSL content
        List<String> dslLines = List.of(
                "DSL version 1.1.2;",
                "DroneType Mini4Pro;",
                "Position aPos = (0,0,0);",
                "Velocity aVelocity = 6;",
                "Distance aLenght = 20;",
                "Rectangle aRectangle(aPos, aLenght, aLenght, Mini4Pro);"
        );

        figure = mock(Figure.class);
        Map<String, DslMetadata> dslVersions = new HashMap<>();
        dslVersions.put("1.1.2", new DslMetadata("Mini4Pro", dslLines));
        when(figure.dslVersions()).thenReturn(dslVersions);
        when(figure.name()).thenReturn(new Name("RectangleFigure"));

        Map<DroneModel, Integer> modelMap = new HashMap<>();
        modelMap.put(model, 1);

        proposal = new ShowProposal(
                new Name("Test Proposal"),
                mock(ShowRequest.class),
                mock(ProposalTemplate.class),
                List.of(figure),
                new Description("Test description"),
                mock(Location.class),
                LocalDateTime.now(),
                1,
                Duration.ofMinutes(10),
                "tester",
                LocalDateTime.now(),
                modelMap
        );
    }

    @Test
    void testGenerateProgramsForShow_Success() {
        when(mockRepo.saveInStore(any())).thenReturn(Optional.of(proposal));

        Optional<ShowProposal> result = controller.generateProgramsForShow(proposal);

        assertTrue(result.isPresent(), "Expected proposal to be saved successfully");
        assertNotNull(result.get().getDroneLanguageSpecifications(), "Expected generated language specifications");
        assertFalse(result.get().getDroneLanguageSpecifications().isEmpty(), "Expected at least one program generated");

        assertNotNull(result.get().getScript(), "Expected script to be generated");
        assertFalse(result.get().getScript().isEmpty(), "Expected script not to be empty");
        assertTrue(result.get().getScript().get(0).matches("\\[\\d+]"), "Script should start with number of drones");
    }

    @Test
    void testGenerateProgramsForShow_InvalidDSL_ShouldAbort() {
        List<String> badDsl = List.of("DSL version 1.1.2;", "INVALID LINE HERE;");
        when(figure.dslVersions()).thenReturn(Map.of("1.1.2", new DslMetadata("Mini4Pro", badDsl)));

        when(mockRepo.saveInStore(any())).thenReturn(Optional.of(proposal));

        Optional<ShowProposal> result = controller.generateProgramsForShow(proposal);

        assertTrue(result.isEmpty(), "Expected generation to fail and return empty result");
    }

}
