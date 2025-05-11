## US240 - Create Drone Model

## 4. **Tests**

This section documents the **unit tests** created for the `DroneModel` entity within the context of registering a new drone model.

### **Test Cases**

1. **Unit Test: Valid Construction With All Fields**

   * **Description**: Verifies correct creation of a `DroneModel` with all provided fields.
   * **Scenario**: Create a `DroneModel` with `ID`, `Name`, `Description`, and `maxWindSpeed`.
   * **Expected Outcome**: All attributes should be correctly assigned.
   * **Test**:
     ```java
     @Test
     void validConstructionWithAllFields() {
         DroneModel model = new DroneModel(
             new DroneModelID("DM_001"),
             new DroneName("Falcon"),
             new Description("Fast drone"),
             20
         );
         assertEquals("Falcon", model.droneName().toString());
         assertEquals("Fast drone", model.description().toString());
         assertEquals(20, model.maxWindSpeed());
     }
     ```

2. **Unit Test: Valid Construction With Default Description**

   * **Description**: Verifies that a default description is used when not explicitly provided.
   * **Scenario**: Create a `DroneModel` using the constructor without description.
   * **Expected Outcome**: The description should default to `"Description not provided!"`.
   * **Test**:
     ```java
     @Test
     void validConstructionWithDefaultDescription() {
         DroneModel model = new DroneModel(
             new DroneModelID("DM_002"),
             new DroneName("Hawk"),
             15
         );
         assertEquals("Hawk", model.droneName().toString());
         assertEquals("Description not provided!", model.description().toString());
         assertEquals(15, model.maxWindSpeed());
     }
     ```

3. **Unit Test: Null DroneModelID Should Throw**

   * **Description**: Verifies that a `null` DroneModelID is not allowed.
   * **Scenario**: Attempt to create a `DroneModel` with a `null` ID.
   * **Expected Outcome**: Should throw `IllegalArgumentException`.
   * **Test**:
     ```java
     @Test
     void nullDroneModelIDShouldThrow() {
         assertThrows(IllegalArgumentException.class, () -> {
             new DroneModel(null, new DroneName("Sky"), new Description("desc"), 10);
         });
     }
     ```

4. **Unit Test: Null DroneName Should Throw**

   * **Description**: Verifies that a `null` DroneName is not allowed.
   * **Scenario**: Attempt to create a `DroneModel` with a `null` name.
   * **Expected Outcome**: Should throw `IllegalArgumentException`.
   * **Test**:
     ```java
     @Test
     void nullDroneNameShouldThrow() {
         assertThrows(IllegalArgumentException.class, () -> {
             new DroneModel(new DroneModelID("DM_003"), null, new Description("desc"), 10);
         });
     }
     ```

5. **Unit Test: Null Description Should Throw**

   * **Description**: Verifies that a `null` Description is not allowed when explicitly passed.
   * **Scenario**: Attempt to create a `DroneModel` with a `null` description.
   * **Expected Outcome**: Should throw `NullPointerException`.
   * **Test**:
     ```java
     @Test
     void nullDescriptionShouldThrow() {
         assertThrows(NullPointerException.class, () -> {
             new DroneModel(new DroneModelID("DM_004"), new DroneName("Wind"), null, 10);
         });
     }
     ```

6. **Unit Test: Negative Max Wind Speed Should Throw**

   * **Description**: Verifies that negative values for max wind speed are not allowed.
   * **Scenario**: Attempt to create a `DroneModel` with a negative `maxWindSpeed`.
   * **Expected Outcome**: Should throw `IllegalArgumentException`.
   * **Test**:
     ```java
     @Test
     void negativeMaxWindSpeedShouldThrow() {
         assertThrows(IllegalArgumentException.class, () -> {
             new DroneModel(new DroneModelID("DM_005"), new DroneName("Storm"), new Description("desc"), -5);
         });
     }
     ```

7. **Unit Test: Negative Max Wind Speed (With Default Constructor) Should Throw**

   * **Description**: Verifies that the overloaded constructor also disallows negative `maxWindSpeed`.
   * **Scenario**: Use the constructor without description and provide a negative wind speed.
   * **Expected Outcome**: Should throw `IllegalArgumentException`.
   * **Test**:
     ```java
     @Test
     void negativeMaxWindSpeedWithDefaultConstructorShouldThrow() {
         assertThrows(IllegalArgumentException.class, () -> {
             new DroneModel(new DroneModelID("DM_006"), new DroneName("Breeze"), -1);
         });
     }
     ```


## 5. Construction (Implementation)

### Key Implementation Details

1. **Validation Layer**:
   - Strict validation during object creation (fail-fast principle)
   - All value objects enforce business rules

2. **Repository Pattern**:
   - `InMemoryDroneModelRepository` provides persistence abstraction
   - Case-insensitive model lookup

3. **Wind Tolerance System**:
   - Tracks position tolerance under different wind conditions
   - Validates against model's maximum wind speed

### Patterns Used

- **Factory Method**: Used in value object creation
- **Repository**: Abstract data access pattern
- **Value Object**: Immutable domain primitives

### DDD Principles Applied

- **Bounded Context**: Clear separation of drone model concerns
- **Ubiquitous Language**: Method names match business terms
- **Aggregate Root**: DroneModel acts as root for related value objects