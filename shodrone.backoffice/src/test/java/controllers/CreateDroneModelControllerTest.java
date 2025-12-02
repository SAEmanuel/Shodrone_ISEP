package controllers;

import controller.drone.CreateDroneModelController;
import domain.entity.DroneModel;
import domain.valueObjects.Description;
import domain.valueObjects.DroneModelID;
import domain.valueObjects.DroneName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.RepositoryProvider;
import persistence.DroneModelRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateDroneModelControllerTest {

    private CreateDroneModelController controller;
    private DroneModelRepository mockRepository;

    private DroneModelID id;
    private DroneName name;
    private Description description;
    private double maxWind;

    @BeforeEach
    void setUp() {
        mockRepository = mock(DroneModelRepository.class);
        RepositoryProvider.injectDroneModelRepository(mockRepository);

        controller = new CreateDroneModelController();

        id = new DroneModelID("DJI_PRO_2020");
        name = new DroneName("Test Model");
        description = new Description("Test Description");
        maxWind = 15.0;
    }

    @Test
    void createDroneModelWithDescriptionShouldReturnModelWhenSavedSuccessfully() {
        DroneModel expected = new DroneModel(id, name, description, maxWind);

        when(mockRepository.save(any())).thenReturn(Optional.of(expected));

        Optional<DroneModel> result = controller.createDroneModelWithDescription(id, name, description, maxWind);

        assertTrue(result.isPresent());
        assertEquals(expected, result.get());
        verify(mockRepository).save(any(DroneModel.class));
    }

    @Test
    void createDroneModelNoDescriptionShouldReturnModelWhenSavedSuccessfully() {
        DroneModel expected = new DroneModel(id, name, maxWind);

        when(mockRepository.save(any())).thenReturn(Optional.of(expected));

        Optional<DroneModel> result = controller.createDroneModelNoDescription(id, name, maxWind);

        assertTrue(result.isPresent());
        assertEquals(expected, result.get());
        verify(mockRepository).save(any(DroneModel.class));
    }

    @Test
    void createDroneModelShouldReturnEmptyIfIdAlreadyExists() {
        when(mockRepository.save(any())).thenReturn(Optional.empty());

        Optional<DroneModel> resultWithDescription = controller.createDroneModelWithDescription(id, name, description, maxWind);
        Optional<DroneModel> resultWithoutDescription = controller.createDroneModelNoDescription(id, name, maxWind);

        assertTrue(resultWithDescription.isEmpty());
        assertTrue(resultWithoutDescription.isEmpty());

        verify(mockRepository, times(2)).save(any(DroneModel.class));
    }
}
