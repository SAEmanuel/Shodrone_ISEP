package controllers;

import controller.drone.AddMaintenanceTypeController;
import domain.entity.MaintenanceType;
import domain.valueObjects.Description;
import domain.valueObjects.MaintenanceTypeName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.MaintenanceTypeRepository;
import persistence.RepositoryProvider;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AddMaintenanceTypeControllerTest {

    private MaintenanceTypeRepository mockRepository;
    private AddMaintenanceTypeController controller;

    @BeforeEach
    void setUp() {
        mockRepository = mock(MaintenanceTypeRepository.class);
        RepositoryProvider.injectMaintenanceTypeRepository(mockRepository);

        controller = new AddMaintenanceTypeController();
    }

    @Test
    void createMaintenanceType_WithValidNameAndDescription_ShouldSaveAndReturnOptional() {
        MaintenanceTypeName name = new MaintenanceTypeName("Preventive Maintenance");
        Description description = new Description("Maintenance done regularly to prevent issues");
        MaintenanceType expected = new MaintenanceType(name, description);

        when(mockRepository.save(any(MaintenanceType.class))).thenReturn(Optional.of(expected));

        Optional<MaintenanceType> result = controller.createMaintenanceType(name, description);

        assertTrue(result.isPresent());
        assertEquals(expected.name(), result.get().name());
        assertEquals(expected.description(), result.get().description());
        verify(mockRepository).save(any(MaintenanceType.class));
    }

    @Test
    void createMaintenanceTypeWithoutDescription_ShouldAssignDefaultDescriptionAndSave() {
        MaintenanceTypeName name = new MaintenanceTypeName("Corrective Maintenance");
        MaintenanceType expected = new MaintenanceType(name);  // Default description "Not Provided!"

        when(mockRepository.save(any(MaintenanceType.class))).thenReturn(Optional.of(expected));

        Optional<MaintenanceType> result = controller.createMaintenanceTypeWithoutDescription(name);

        assertTrue(result.isPresent());
        assertEquals("Not Provided!", result.get().description().toString());
        assertEquals(name.name(), result.get().name().name());
        verify(mockRepository).save(any(MaintenanceType.class));
    }

    @Test
    void createMaintenanceType_ShouldReturnEmptyOptionalIfRepositoryFails() {
        MaintenanceTypeName name = new MaintenanceTypeName("Inspection");
        Description description = new Description("Drone inspection routine");

        when(mockRepository.save(any(MaintenanceType.class))).thenReturn(Optional.empty());

        Optional<MaintenanceType> result = controller.createMaintenanceType(name, description);

        assertTrue(result.isEmpty());
        verify(mockRepository).save(any(MaintenanceType.class));
    }
}
