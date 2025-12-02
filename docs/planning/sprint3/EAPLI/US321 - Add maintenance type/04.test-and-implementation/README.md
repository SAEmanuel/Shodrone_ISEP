## US321 - Add Maintenance Type

## 4. **Tests**

- Unit tests for:
   - `MaintenanceTypeName` (value object)
   - `AddMaintenanceTypeController`
   - `EditMaintenanceTypeController`
   - `GetMaintenanceTypeController`

> These are the main tests related to the creation of maintenance types.  
> Additional tests, such as those for entities and repositories, are located within their respective classes.

### **Test Cases**

1. **Unit Test: MaintenanceTypeName rejects null and empty input**

   * **Description**: Ensure constructor throws an exception if null or empty string is passed.
   * **Expected Outcome**: `IllegalArgumentException` is thrown.
   * **Test**:
     ```java
     @Test
     void ensureNullIsNotAllowed() {
         assertThrows(IllegalArgumentException.class, () -> new MaintenanceTypeName(null));
     }

     @Test
     void ensureEmptyStringIsNotAllowed() {
         assertThrows(IllegalArgumentException.class, () -> new MaintenanceTypeName("   "));
     }
     ```

2. **Unit Test: MaintenanceTypeName accepts valid input**

   * **Description**: Ensure a valid name is accepted and stored correctly.
   * **Expected Outcome**: Object is created and name is correctly stored.
   * **Test**:
     ```java
     @Test
     void validNameIsAccepted() {
         MaintenanceTypeName name = new MaintenanceTypeName("Preventive Check");
         assertEquals("Preventive Check", name.name());
     }
     ```

3. **Unit Test: AddMaintenanceTypeController creates MaintenanceType with and without description**

   * **Description**: Test both creation methods: with and without description.
   * **Expected Outcome**: `Optional<MaintenanceType>` is returned successfully.
   * **Test**:
     ```java
     @Test
     void shouldCreateMaintenanceTypeWithDescription() {
         MaintenanceTypeName name = new MaintenanceTypeName("Corrective");
         Description description = new Description("Repair operations");

         Optional<MaintenanceType> result = controller.createMaintenanceType(name, description);

         assertTrue(result.isPresent());
         assertEquals("Corrective", result.get().name().name());
         assertEquals("Repair operations", result.get().description().description());
     }

     @Test
     void shouldCreateMaintenanceTypeWithoutDescription() {
         MaintenanceTypeName name = new MaintenanceTypeName("Preventive");

         Optional<MaintenanceType> result = controller.createMaintenanceTypeWithoutDescription(name);

         assertTrue(result.isPresent());
         assertEquals("Preventive", result.get().name().name());
         assertNull(result.get().description());
     }
     ```

4. **Unit Test: EditMaintenanceTypeController updates name and description**

   * **Description**: Check if both name and description of an existing type can be changed.
   * **Expected Outcome**: Updated object is returned with new values.
   * **Test**:
     ```java
     @Test
     void shouldUpdateNameAndDescription() {
         MaintenanceType original = new MaintenanceType(new MaintenanceTypeName("Old"), new Description("Old Desc"));

         MaintenanceTypeName newName = new MaintenanceTypeName("Updated");
         Description newDesc = new Description("Updated Desc");

         Optional<MaintenanceType> result = controller.editMaintenanceType(original, newName, newDesc);

         assertTrue(result.isPresent());
         assertEquals("Updated", result.get().name().name());
         assertEquals("Updated Desc", result.get().description().description());
     }
     ```

5. **Unit Test: GetMaintenanceTypeController returns all or empty list**

   * **Description**: Ensure `getAllMaintenanceTypes()` returns a non-empty list when data exists, and `Optional.empty()` otherwise.
   * **Expected Outcome**: Proper behavior based on repository state.
   * **Test**:
     ```java
     @Test
     void getAllMaintenanceTypes_ShouldReturnList_WhenRepositoryHasElements() {
         MaintenanceType mt1 = new MaintenanceType(new MaintenanceTypeName("MT1"), new Description("Desc1"));
         MaintenanceType mt2 = new MaintenanceType(new MaintenanceTypeName("MT2"), new Description("Desc2"));

         when(mockRepository.findAll()).thenReturn(Arrays.asList(mt1, mt2));

         Optional<List<MaintenanceType>> result = controller.getAllMaintenanceTypes();

         assertTrue(result.isPresent());
         assertEquals(2, result.get().size());
     }

     @Test
     void getAllMaintenanceTypes_ShouldReturnEmpty_WhenRepositoryIsEmpty() {
         when(mockRepository.findAll()).thenReturn(Collections.emptyList());

         Optional<List<MaintenanceType>> result = controller.getAllMaintenanceTypes();

         assertTrue(result.isEmpty());
     }
     ```

---
