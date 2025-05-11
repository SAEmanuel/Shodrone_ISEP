## US241 - Register Drone

## 4. **Tests**

This section documents the **unit tests** created for the `Drone` entity. These tests validate the correct behavior and constraints of drone creation and manipulation logic.

### **Test Cases**

1. **Unit Test: Valid Drone Should Be Created**

    * **Description**: Verifies the correct creation of a drone with a valid serial number and model.
    * **Expected Outcome**: The drone is created with correct values and default status.
    * **Test**:
      ```java
      @Test
      void validDroneShouldBeCreated() {
          Drone drone = new Drone(validSerial, validModel);
          assertEquals(validSerial.toString(), drone.identity());
          assertEquals(validModel, drone.droneModel());
          assertEquals(DroneStatus.AVAILABLE, drone.droneStatus());
          assertTrue(drone.droneRemovalLogs().isEmpty());
      }
      ```

2. **Unit Test: Null Serial Number Should Throw**

    * **Description**: Ensures that creating a drone with a null serial number throws an exception.
    * **Expected Outcome**: `NullPointerException` is thrown.
    * **Test**:
      ```java
      @Test
      void nullSerialNumberShouldThrow() {
          NullPointerException thrown = assertThrows(NullPointerException.class, () -> {
              new Drone(null, validModel);
          });
          assertNotNull(thrown.getMessage());
      }
      ```

3. **Unit Test: Null Drone Model Should Throw**

    * **Description**: Ensures that creating a drone with a null drone model throws an exception.
    * **Expected Outcome**: `NullPointerException` is thrown.
    * **Test**:
      ```java
      @Test
      void nullDroneModelShouldThrow() {
          NullPointerException thrown = assertThrows(NullPointerException.class, () -> {
              new Drone(validSerial, null);
          });
          assertNotNull(thrown.getMessage());
      }
      ```

4. **Unit Test: Change Drone Status Should Update Status**

    * **Description**: Verifies that calling `changeDroneStatusTo` updates the drone's status.
    * **Expected Outcome**: Status changes as expected.
    * **Test**:
      ```java
      @Test
      void changeDroneStatusShouldUpdateStatus() {
          Drone drone = new Drone(validSerial, validModel);
          drone.changeDroneStatusTo(DroneStatus.UNAVAILABLE);
          assertEquals(DroneStatus.UNAVAILABLE, drone.droneStatus());
      }
      ```

5. **Unit Test: Add Drone Removal Log Should Increase Log List Size**

    * **Description**: Verifies that a new log entry is added correctly.
    * **Expected Outcome**: Log list size increases and contains the new log.
    * **Test**:
      ```java
      @Test
      void addDroneRemovalLogShouldIncreaseLogListSize() {
          Drone drone = new Drone(validSerial, validModel);
          DroneRemovalLog log = new DroneRemovalLog("Removed for maintenance");
          drone.addDroneRemovalLog(log);
          assertEquals(1, drone.droneRemovalLogs().size());
          assertTrue(drone.droneRemovalLogs().contains(log));
      }
      ```

6. **Unit Test: Drones With Same Identity Should Be Equal**

    * **Description**: Ensures equality is based on serial number.
    * **Expected Outcome**: Drones with the same serial number are equal.
    * **Test**:
      ```java
      @Test
      void dronesWithSameIdentityShouldBeEqual() {
          Drone drone1 = new Drone(validSerial, validModel);
          Drone drone2 = new Drone(validSerial, validModel);
          assertEquals(drone1, drone2);
          assertEquals(drone1.hashCode(), drone2.hashCode());
      }
      ```

7. **Unit Test: Drones With Different Serials Should Not Be Equal**

    * **Description**: Verifies that drones with different serial numbers are not equal.
    * **Expected Outcome**: `equals()` returns false.
    * **Test**:
      ```java
      @Test
      void dronesWithDifferentSerialsShouldNotBeEqual() {
          Drone drone1 = new Drone(new SerialNumber("SN-11111"), validModel);
          Drone drone2 = new Drone(new SerialNumber("SN-22222"), validModel);
          assertNotEquals(drone1, drone2);
      }
      ```

8. **Unit Test: Has Identity Should Return True For Matching Serial**

    * **Description**: Checks the correct behavior of `hasIdentity` with a matching serial.
    * **Expected Outcome**: Returns `true`.
    * **Test**:
      ```java
      @Test
      void hasIdentityShouldReturnTrueForMatchingSerial() {
          Drone drone = new Drone(validSerial, validModel);
          assertTrue(drone.hasIdentity("SN-12345"));
      }
      ```

9. **Unit Test: Has Identity Should Return False For Different Serial**

    * **Description**: Checks the correct behavior of `hasIdentity` with a different serial.
    * **Expected Outcome**: Returns `false`.
    * **Test**:
      ```java
      @Test
      void hasIdentityShouldReturnFalseForDifferentSerial() {
          Drone drone = new Drone(validSerial, validModel);
          assertFalse(drone.hasIdentity("SN-00000"));
      }
      ```

10. **Unit Test: To List String Should Return Summary**

    * **Description**: Ensures that `toListString` returns a summary containing key attributes.
    * **Expected Outcome**: Output string includes serial, model ID and name.
    * **Test**:
      ```java
      @Test
      void toListStringShouldReturnSummary() {
          Drone drone = new Drone(validSerial, validModel);
          String listString = drone.toListString();
          assertTrue(listString.contains("SN-12345"));
          assertTrue(listString.contains("Phantom"));
          assertTrue(listString.contains("DM_001"));
      }
      ```

---

> **Note:** This suite of tests ensures robustness in the core entity behavior of the drone lifecycle.
