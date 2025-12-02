# US323 - Edit a Maintenance Type

## 4. **Tests**

- Unit tests for:
    - `EditMaintenanceTypeController`

> These tests ensure that maintenance types can be updated with new names and/or descriptions correctly.  
> Additional validation tests are handled in the value objects and entity classes.

### **Test Cases**

1. **Unit Test: editMaintenanceType_ShouldUpdateNameAndDescription**

    * **Description**: Ensures both name and description are correctly updated when valid values are provided.
    * **Scenario**: A valid `MaintenanceType` is edited with a new name and new description.
    * **Expected Outcome**: The name and description of the entity are updated, and the repository returns the updated entity.
    * **Test**:
      ```java
      @Test
      void editMaintenanceType_ShouldUpdateNameAndDescription() {
          MaintenanceType maintenanceType = new MaintenanceType(new MaintenanceTypeName("Old Name"), new Description("Old Description"));
          MaintenanceTypeName newName = new MaintenanceTypeName("New Name");
          Description newDescription = new Description("New Description");
 
          when(repository.updateMaintenanceType(any(), any())).thenReturn(Optional.of(maintenanceType));
 
          Optional<MaintenanceType> result = controller.editMaintenanceType(maintenanceType, newName, newDescription);
 
          assertTrue(result.isPresent());
          assertEquals("New Name", maintenanceType.name().name());
          assertEquals("New Description", maintenanceType.description().description());
      }
      ```

2. **Unit Test: editMaintenanceType_ShouldUpdateOnlyName_WhenDescriptionIsNull**

    * **Description**: Ensures that only the name is updated when the description is null.
    * **Scenario**: The description is not provided (null), only the name should change.
    * **Expected Outcome**: Only the name is updated, and the description remains unchanged.
    * **Test**:
      ```java
      @Test
      void editMaintenanceType_ShouldUpdateOnlyName_WhenDescriptionIsNull() {
          MaintenanceType maintenanceType = new MaintenanceType(new MaintenanceTypeName("Old Name"), new Description("Same Description"));
          MaintenanceTypeName newName = new MaintenanceTypeName("New Name");
 
          when(repository.updateMaintenanceType(any(), any())).thenReturn(Optional.of(maintenanceType));
 
          Optional<MaintenanceType> result = controller.editMaintenanceType(maintenanceType, newName, null);
 
          assertTrue(result.isPresent());
          assertEquals("New Name", maintenanceType.name().name());
          assertEquals("Same Description", maintenanceType.description().description());
      }
      ```

3. **Unit Test: editMaintenanceType_ShouldUpdateOnlyDescription_WhenNameIsNull**

    * **Description**: Ensures that only the description is updated when the name is null.
    * **Scenario**: The name is not provided (null), only the description should change.
    * **Expected Outcome**: Only the description is updated, and the name remains unchanged.
    * **Test**:
      ```java
      @Test
      void editMaintenanceType_ShouldUpdateOnlyDescription_WhenNameIsNull() {
          MaintenanceType maintenanceType = new MaintenanceType(new MaintenanceTypeName("Same Name"), new Description("Old Description"));
          Description newDescription = new Description("Updated Description");
 
          when(repository.updateMaintenanceType(any(), any())).thenReturn(Optional.of(maintenanceType));
 
          Optional<MaintenanceType> result = controller.editMaintenanceType(maintenanceType, null, newDescription);
 
          assertTrue(result.isPresent());
          assertEquals("Same Name", maintenanceType.name().name());
          assertEquals("Updated Description", maintenanceType.description().description());
      }
      ```
