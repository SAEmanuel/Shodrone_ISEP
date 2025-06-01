## US322 - List Maintenance Types

## 4. **Tests**

- Unit tests for:
    - `GetMaintenanceTypeController`

> This is the main test related to the listing of maintenance types.  
> Additional related tests, such as those for the repository behavior, are located within their respective classes.

### **Test Cases**

1. **Unit Test: getAllMaintenanceTypes_ShouldReturnList_WhenRepositoryIsNotEmpty**

    * **Description**: Verifies that the controller returns a non-empty optional list when the repository contains maintenance types.
    * **Scenario**: The repository has one or more `MaintenanceType` entries.
    * **Expected Outcome**: The method `getAllMaintenanceTypes()` returns an `Optional` with a non-empty list.
    * **Test**:
      ```java
      @Test
      void getAllMaintenanceTypes_ShouldReturnList_WhenRepositoryIsNotEmpty() {
          MaintenanceType sample = new MaintenanceType(new MaintenanceTypeName("Oil Change"), new Description("Regular engine oil change"));
          when(repository.findAll()).thenReturn(List.of(sample));
 
          Optional<List<MaintenanceType>> result = controller.getAllMaintenanceTypes();
 
          assertTrue(result.isPresent());
          assertEquals(1, result.get().size());
          assertEquals("Oil Change", result.get().get(0).name().name());
      }
      ```

2. **Unit Test: getAllMaintenanceTypes_ShouldReturnEmptyOptional_WhenRepositoryIsEmpty**

    * **Description**: Ensures that an empty optional is returned when the repository contains no maintenance types.
    * **Scenario**: The repository returns an empty list.
    * **Expected Outcome**: The method returns `Optional.empty()`.
    * **Test**:
      ```java
      @Test
      void getAllMaintenanceTypes_ShouldReturnEmptyOptional_WhenRepositoryIsEmpty() {
          when(repository.findAll()).thenReturn(Collections.emptyList());
 
          Optional<List<MaintenanceType>> result = controller.getAllMaintenanceTypes();
 
          assertTrue(result.isEmpty());
      }
      ```
