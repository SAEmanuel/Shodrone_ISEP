package controllers;

import controller.GetDroneModelsController;
import domain.entity.DroneModel;
import domain.valueObjects.Description;
import domain.valueObjects.DroneModelID;
import domain.valueObjects.DroneName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.RepositoryProvider;
import persistence.interfaces.DroneModelRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetDroneModelsControllerTest {

    private GetDroneModelsController controller;
    private DroneModelRepository mockRepository;

    private DroneModel model1;
    private DroneModel model2;

    @BeforeEach
    void setUp() {
        mockRepository = mock(DroneModelRepository.class);
        RepositoryProvider.injectDroneModelRepository(mockRepository);

        controller = new GetDroneModelsController();

        model1 = new DroneModel(new DroneModelID("DJI_PRO"), new DroneName("DJI TESTE"), new Description("Description 1"), 12.5);
        model2 = new DroneModel(new DroneModelID("DJI_PRO_2"), new DroneName("DJI TESTE 2"), new Description("Description 2"), 8.0);
    }

    @Test
    void getAllModelsShouldReturnListWhenNotEmpty() {
        List<DroneModel> models = Arrays.asList(model1, model2);
        when(mockRepository.findAll()).thenReturn(models);

        Optional<List<DroneModel>> result = controller.getAllModels();

        assertTrue(result.isPresent());
        assertEquals(2, result.get().size());
        verify(mockRepository).findAll();
    }

    @Test
    void getAllModelsShouldReturnEmptyWhenNoModels() {
        when(mockRepository.findAll()).thenReturn(Collections.emptyList());

        Optional<List<DroneModel>> result = controller.getAllModels();

        assertTrue(result.isEmpty());
        verify(mockRepository).findAll();
    }

    @Test
    void getModelByIdShouldReturnModelIfFound() {
        when(mockRepository.findByDroneModelID("SN-12345")).thenReturn(Optional.of(model1));

        Optional<DroneModel> result = controller.getModelById("SN-12345");

        assertTrue(result.isPresent());
        assertEquals(model1, result.get());
        verify(mockRepository).findByDroneModelID("SN-12345");
    }

    @Test
    void getModelByIdShouldReturnEmptyIfNotFound() {
        when(mockRepository.findByDroneModelID("SN-00000")).thenReturn(Optional.empty());

        Optional<DroneModel> result = controller.getModelById("SN-00000");

        assertTrue(result.isEmpty());
        verify(mockRepository).findByDroneModelID("SN-00000");
    }
}
