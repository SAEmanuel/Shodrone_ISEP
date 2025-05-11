package controllers;

import controller.RemoveDroneInventoryController;
import domain.entity.Drone;
import domain.entity.DroneModel;
import domain.valueObjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.interfaces.DroneRepository;
import persistence.RepositoryProvider;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RemoveDroneInventoryControllerTest {

    private RemoveDroneInventoryController controller;
    private DroneRepository mockRepository;

    private Drone sampleDrone;
    private DroneRemovalLog sampleLog;

    @BeforeEach

    void setUp() {
            mockRepository = mock(DroneRepository.class);
            RepositoryProvider.injectDroneRepository(mockRepository);
            controller = new RemoveDroneInventoryController();


        DroneModel model = new DroneModel(
                new DroneModelID("DM_001"),
                new DroneName("Eagle"),
                25
        );
        sampleDrone = new Drone(new SerialNumber("SN-12345"), model);
        sampleLog = new DroneRemovalLog("Removed due to damage");
    }

    @Test
    void removeDroneShouldReturnRemovedDroneWhenSuccessful() {
        when(mockRepository.removeDrone(sampleDrone, sampleLog)).thenReturn(Optional.of(sampleDrone));

        Optional<Drone> result = controller.removeDrone(sampleDrone, sampleLog);

        assertTrue(result.isPresent());
        assertEquals(sampleDrone, result.get());
        verify(mockRepository, times(1)).removeDrone(sampleDrone, sampleLog);
    }

    @Test
    void removeDroneShouldReturnEmptyOptionalWhenRepositoryFails() {
        when(mockRepository.removeDrone(sampleDrone, sampleLog)).thenReturn(Optional.empty());

        Optional<Drone> result = controller.removeDrone(sampleDrone, sampleLog);

        assertFalse(result.isPresent());
        verify(mockRepository, times(1)).removeDrone(sampleDrone, sampleLog);
    }
}
