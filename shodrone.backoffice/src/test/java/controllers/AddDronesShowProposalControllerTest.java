package controllers;

import controller.drone.GetDroneModelsController;
import controller.showproposal.AddDronesShowProposalController;
import domain.entity.DroneModel;
import domain.entity.ShowProposal;
import domain.valueObjects.Description;
import domain.valueObjects.DroneModelID;
import domain.valueObjects.DroneName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.RepositoryProvider;
import persistence.ShowProposalRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddDronesShowProposalControllerTest {

    private AddDronesShowProposalController controller;
    private GetDroneModelsController mockedDroneModelsController;
    private ShowProposal proposal;
    private Map<DroneModel, Integer> used;
    private DroneModel modelA, modelB;

    @BeforeEach
    void setUp() {
        mockedDroneModelsController = mock(GetDroneModelsController.class);
        controller = new AddDronesShowProposalController(mockedDroneModelsController);

        modelA = new DroneModel(
                new DroneModelID("DM001"),
                new DroneName("ModelA"),
                new Description("Desc A"),
                10.5
        );

        modelB = new DroneModel(
                new DroneModelID("DM002"),
                new DroneName("ModelB"),
                new Description("Desc B"),
                9.0
        );

        proposal = mock(ShowProposal.class);
        used = mock(Map.class);
        when(proposal.getModelsUsed()).thenReturn(used);
    }

    @Test
    void testChangeDroneModelQuantityValid() {
        when(mockedDroneModelsController.getDroneModelQuantity())
                .thenReturn(Optional.of(Map.of(modelA, 5)));

        Optional<String> result = controller.changeDroneModelQuantity(proposal, modelA, 3);
        assertTrue(result.isEmpty());
        verify(used).put(modelA, 3);
    }

    @Test
    void testChangeDroneModelQuantityInvalidTooHigh() {
        when(mockedDroneModelsController.getDroneModelQuantity())
                .thenReturn(Optional.of(Map.of(modelA, 3)));

        Optional<String> result = controller.changeDroneModelQuantity(proposal, modelA, 5);
        assertTrue(result.isPresent());
        assertTrue(result.get().contains("Invalid quantity"));
    }

    @Test
    void testAddDroneModelsSuccess() {
        Map<DroneModel, Integer> inventory = new HashMap<>();
        inventory.put(modelA, 5);
        inventory.put(modelB, 2);

        when(mockedDroneModelsController.getDroneModelQuantity()).thenReturn(Optional.of(inventory));
        when(used.getOrDefault(modelA, 0)).thenReturn(2);
        when(used.getOrDefault(modelB, 0)).thenReturn(1);

        Map<DroneModel, Integer> modelsToAdd = new HashMap<>();
        modelsToAdd.put(modelA, 2);
        modelsToAdd.put(modelB, 1);

        Optional<String> result = controller.addDroneModels(proposal, modelsToAdd);
        assertTrue(result.isEmpty());

        verify(used).put(modelA, 4);
        verify(used).put(modelB, 2);
    }

    @Test
    void testAddDroneModelsFailsDueToLackOfStock() {
        Map<DroneModel, Integer> inventory = new HashMap<>();
        inventory.put(modelA, 3); // only 3 available

        when(mockedDroneModelsController.getDroneModelQuantity()).thenReturn(Optional.of(inventory));
        when(used.getOrDefault(modelA, 0)).thenReturn(2);

        Map<DroneModel, Integer> modelsToAdd = new HashMap<>();
        modelsToAdd.put(modelA, 2);

        Optional<String> result = controller.addDroneModels(proposal, modelsToAdd);
        assertTrue(result.isPresent());
        assertTrue(result.get().contains("Not enough drones"));
    }

    @Test
    void testRemoveDroneModelSuccess() {
        when(used.containsKey(modelA)).thenReturn(true);

        Optional<String> result = controller.removeDroneModel(proposal, modelA);
        assertTrue(result.isEmpty());
        verify(used).remove(modelA);
    }

    @Test
    void testRemoveDroneModelFails() {
        when(used.containsKey(modelA)).thenReturn(false);

        Optional<String> result = controller.removeDroneModel(proposal, modelA);
        assertTrue(result.isPresent());
        assertEquals("Drone model is not in the proposal.", result.get());
    }

    @Test
    void testSaveProposal() {
        ShowProposalRepository repo = mock(ShowProposalRepository.class);
        RepositoryProvider.injectShowProposalRepository(repo);

        when(proposal.getModelsUsed()).thenReturn(Map.of(modelA, 3, modelB, 2));
        when(repo.saveInStore(proposal)).thenReturn(Optional.of(proposal));

        Optional<ShowProposal> result = controller.saveProposal(proposal);
        assertTrue(result.isPresent());
        verify(proposal).setNumberOfDrones(5);
    }
}
