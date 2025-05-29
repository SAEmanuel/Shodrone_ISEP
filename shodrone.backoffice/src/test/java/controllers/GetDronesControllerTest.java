package controllers;

import controller.drone.GetDronesController;
import domain.entity.Drone;
import domain.entity.DroneModel;
import domain.valueObjects.DroneModelID;
import domain.valueObjects.DroneName;
import domain.valueObjects.SerialNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.RepositoryProvider;
import persistence.DroneRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetDronesControllerTest {

    private GetDronesController controller;
    private DroneRepository mockRepository;

    private Drone drone1;
    private Drone drone2;
    private DroneModel model;

    @BeforeEach
    void setUp() {
        mockRepository = mock(DroneRepository.class);
        RepositoryProvider.injectDroneRepository(mockRepository);

        controller = new GetDronesController();

        model = new DroneModel(new DroneModelID("DJI_PRO"), new DroneName("DJI TESTE"), 15.0);
        drone1 = new Drone(new SerialNumber("SN-00001"), model);
        drone2 = new Drone(new SerialNumber("SN-00002"), model);
    }

    @Test
    void getAllDronesShouldReturnListWhenNotEmpty() {
        List<Drone> drones = Arrays.asList(drone1, drone2);
        when(mockRepository.findAll()).thenReturn(drones);

        Optional<List<Drone>> result = controller.getAllDrones();

        assertTrue(result.isPresent());
        assertEquals(2, result.get().size());
        verify(mockRepository).findAll();
    }

    @Test
    void getAllDronesShouldReturnEmptyWhenNoDrones() {
        when(mockRepository.findAll()).thenReturn(Collections.emptyList());

        Optional<List<Drone>> result = controller.getAllDrones();

        assertTrue(result.isEmpty());
        verify(mockRepository).findAll();
    }

    @Test
    void getDroneBySNShouldReturnDroneIfFound() {
        when(mockRepository.findByDroneSN("SN-00001")).thenReturn(Optional.of(drone1));

        Optional<Drone> result = controller.getDroneBySN("SN-00001");

        assertTrue(result.isPresent());
        assertEquals(drone1, result.get());
        verify(mockRepository).findByDroneSN("SN-00001");
    }

    @Test
    void getDroneBySNShouldReturnEmptyIfNotFound() {
        when(mockRepository.findByDroneSN("SN-00099")).thenReturn(Optional.empty());

        Optional<Drone> result = controller.getDroneBySN("SN-00099");

        assertTrue(result.isEmpty());
        verify(mockRepository).findByDroneSN("SN-00099");
    }

    @Test
    void getDroneByModelShouldReturnListIfFound() {
        List<Drone> drones = List.of(drone1, drone2);
        when(mockRepository.findByDroneModel(model)).thenReturn(Optional.of(drones));

        Optional<List<Drone>> result = controller.getDroneByModel(model);

        assertTrue(result.isPresent());
        assertEquals(2, result.get().size());
        verify(mockRepository).findByDroneModel(model);
    }

    @Test
    void getDroneByModelShouldReturnEmptyIfNoneFound() {
        when(mockRepository.findByDroneModel(model)).thenReturn(Optional.empty());

        Optional<List<Drone>> result = controller.getDroneByModel(model);

        assertTrue(result.isEmpty());
        verify(mockRepository).findByDroneModel(model);
    }
}
