package controllers;

import controller.AddDroneInventoryController;
import domain.entity.Drone;
import domain.entity.DroneModel;
import domain.valueObjects.DroneModelID;
import domain.valueObjects.DroneName;
import domain.valueObjects.SerialNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.RepositoryProvider;
import persistence.interfaces.DroneRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddDroneInventoryControllerTest {

    private AddDroneInventoryController controller;
    private DroneRepository mockRepository;

    private SerialNumber serialNumber;
    private DroneModel droneModel;
    private Drone drone;

    @BeforeEach
    void setUp() {
        mockRepository = mock(DroneRepository.class);
        RepositoryProvider.injectDroneRepository(mockRepository);

        controller = new AddDroneInventoryController();

        serialNumber = new SerialNumber("SN-12345");
        droneModel = new DroneModel(new DroneModelID("DJI_RPO"), new DroneName("Drone Test"), 10.0);
        drone = new Drone(serialNumber, droneModel);
    }

    @Test
    void addDroneInventoryShouldReturnDroneIfSuccessful() {
        when(mockRepository.save(any())).thenReturn(Optional.of(drone));

        Optional<Drone> result = controller.addDroneInventory(droneModel, serialNumber);

        assertTrue(result.isPresent());
        assertEquals(drone, result.get());
        verify(mockRepository).save(any(Drone.class));
    }

    @Test
    void addDroneInventoryShouldReturnEmptyIfSerialAlreadyExists() {
        when(mockRepository.save(any())).thenReturn(Optional.empty());

        Optional<Drone> result = controller.addDroneInventory(droneModel, serialNumber);

        assertTrue(result.isEmpty());
        verify(mockRepository).save(any(Drone.class));
    }

    @Test
    void addExistingDroneInventoryShouldReturnDroneIfSuccessful() {
        when(mockRepository.addExistingDroneInventory(any())).thenReturn(Optional.of(drone));

        Optional<Drone> result = controller.addExistingDroneInventory(Optional.of(drone));

        assertTrue(result.isPresent());
        assertEquals(drone, result.get());
        verify(mockRepository).addExistingDroneInventory(drone);
    }

    @Test
    void addExistingDroneInventoryShouldThrowExceptionIfOptionalIsEmpty() {
        assertThrows(NoSuchElementException.class, () ->
                controller.addExistingDroneInventory(Optional.empty()));
    }
}
