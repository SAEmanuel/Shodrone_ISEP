package controllers;

import controller.drone.EditMaintenanceTypeController;
import domain.entity.MaintenanceType;
import domain.valueObjects.Description;
import domain.valueObjects.MaintenanceTypeName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import persistence.MaintenanceTypeRepository;
import persistence.RepositoryProvider;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EditMaintenanceTypeControllerTest {

    private MaintenanceTypeRepository mockRepository;
    private EditMaintenanceTypeController controller;

    @BeforeEach
    void setUp() {
        mockRepository = mock(MaintenanceTypeRepository.class);
        RepositoryProvider.injectMaintenanceTypeRepository(mockRepository);
        controller = new EditMaintenanceTypeController();
    }

    @Test
    void editMaintenanceType_ShouldUpdateNameAndDescription() {
        MaintenanceType original = new MaintenanceType(
                new MaintenanceTypeName("Initial Name"),
                new Description("Initial Desc")
        );

        MaintenanceTypeName newName = new MaintenanceTypeName("Updated Name");
        Description newDescription = new Description("Updated Desc");

        when(mockRepository.updateMaintenanceType(any(MaintenanceType.class), eq("Initial Name")))
                .thenReturn(Optional.of(original));

        Optional<MaintenanceType> result = controller.editMaintenanceType(original, newName, newDescription);

        assertTrue(result.isPresent());
        assertEquals("Updated Name", result.get().name().name());
        assertEquals("Updated Desc", result.get().description().toString());

        verify(mockRepository).updateMaintenanceType(any(MaintenanceType.class), eq("Initial Name"));
    }

    @Test
    void editMaintenanceType_ShouldUpdateOnlyName_WhenDescriptionIsNull() {
        MaintenanceType original = new MaintenanceType(
                new MaintenanceTypeName("Old Name"),
                new Description("Existing Desc")
        );

        MaintenanceTypeName newName = new MaintenanceTypeName("New Only Name");

        when(mockRepository.updateMaintenanceType(any(MaintenanceType.class), eq("Old Name")))
                .thenReturn(Optional.of(original));

        Optional<MaintenanceType> result = controller.editMaintenanceType(original, newName, null);

        assertTrue(result.isPresent());
        assertEquals("New Only Name", result.get().name().name());
        assertEquals("Existing Desc", result.get().description().toString());

        verify(mockRepository).updateMaintenanceType(any(MaintenanceType.class), eq("Old Name"));
    }

    @Test
    void editMaintenanceType_ShouldUpdateOnlyDescription_WhenNameIsNull() {
        MaintenanceType original = new MaintenanceType(
                new MaintenanceTypeName("Fixed Name"),
                new Description("Old Desc")
        );

        Description newDesc = new Description("Only New Desc");

        when(mockRepository.updateMaintenanceType(any(MaintenanceType.class), eq("Fixed Name")))
                .thenReturn(Optional.of(original));

        Optional<MaintenanceType> result = controller.editMaintenanceType(original, null, newDesc);

        assertTrue(result.isPresent());
        assertEquals("Fixed Name", result.get().name().name());
        assertEquals("Only New Desc", result.get().description().toString());

        verify(mockRepository).updateMaintenanceType(any(MaintenanceType.class), eq("Fixed Name"));
    }

    @Test
    void editMaintenanceType_ShouldNotChangeAnything_WhenBothInputsAreNull() {
        MaintenanceType original = new MaintenanceType(
                new MaintenanceTypeName("No Change"),
                new Description("Still the Same")
        );

        when(mockRepository.updateMaintenanceType(any(MaintenanceType.class), eq("No Change")))
                .thenReturn(Optional.of(original));

        Optional<MaintenanceType> result = controller.editMaintenanceType(original, null, null);

        assertTrue(result.isPresent());
        assertEquals("No Change", result.get().name().name());
        assertEquals("Still the Same", result.get().description().toString());

        verify(mockRepository).updateMaintenanceType(any(MaintenanceType.class), eq("No Change"));
    }

    @Test
    void editMaintenanceType_ShouldReturnEmptyOptional_WhenRepositoryFails() {
        MaintenanceType original = new MaintenanceType(
                new MaintenanceTypeName("Test Name"),
                new Description("Test Desc")
        );

        MaintenanceTypeName newName = new MaintenanceTypeName("Will Not Persist");

        when(mockRepository.updateMaintenanceType(any(MaintenanceType.class), eq("Test Name")))
                .thenReturn(Optional.empty());

        Optional<MaintenanceType> result = controller.editMaintenanceType(original, newName, null);

        assertTrue(result.isEmpty());
        verify(mockRepository).updateMaintenanceType(any(MaintenanceType.class), eq("Test Name"));
    }
}
