package controllers;

import controller.drone.GetMaintenanceTypeController;
import domain.entity.MaintenanceType;
import domain.valueObjects.Description;
import domain.valueObjects.MaintenanceTypeName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.MaintenanceTypeRepository;
import persistence.RepositoryProvider;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetMaintenanceTypeControllerTest {

    private MaintenanceTypeRepository mockRepository;
    private GetMaintenanceTypeController controller;

    @BeforeEach
    void setUp() {
        mockRepository = mock(MaintenanceTypeRepository.class);
        RepositoryProvider.injectMaintenanceTypeRepository(mockRepository);
        controller = new GetMaintenanceTypeController();
    }

    @Test
    void getAllMaintenanceTypes_ShouldReturnList_WhenRepositoryHasElements() {
        MaintenanceType mt1 = new MaintenanceType(new MaintenanceTypeName("MT1"), new Description("Description 1"));
        MaintenanceType mt2 = new MaintenanceType(new MaintenanceTypeName("MT2"), new Description("Description 2"));

        when(mockRepository.findAll()).thenReturn(Arrays.asList(mt1, mt2));

        Optional<List<MaintenanceType>> result = controller.getAllMaintenanceTypes();

        assertTrue(result.isPresent());
        assertEquals(2, result.get().size());
        assertEquals("MT1", result.get().get(0).name().name());
        assertEquals("MT2", result.get().get(1).name().name());

        verify(mockRepository).findAll();
    }

    @Test
    void getAllMaintenanceTypes_ShouldReturnEmpty_WhenRepositoryIsEmpty() {
        when(mockRepository.findAll()).thenReturn(Collections.emptyList());

        Optional<List<MaintenanceType>> result = controller.getAllMaintenanceTypes();

        assertTrue(result.isEmpty());
        verify(mockRepository).findAll();
    }
}
