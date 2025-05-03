# US230 - Register Show Request

## 4. Tests

This section is intended for documenting **unit tests**, **integration tests**, or any other **validation procedures** developed for this User Story.

### Test Cases

1. **Unit Test: Register Show Request with Valid Data**
    - **Description**: Ensures a `ShowRequest` is created successfully with all required fields.
    - **Scenario**: Active client, public figures, future date, positive number of drones.
    - **Expected Outcome**: The `ShowRequest` is saved successfully, and a confirmation message is displayed.

2. **Unit Test: Register with Inactive Client**
    - **Description**: Attempts to register a show request for an inactive client.
    - **Scenario**: Client status set to "inactive".
    - **Expected Outcome**: An exception is thrown with the message "Only active or VIP customers can register show requests."

3. **Integration Test: Registration with Authentication**
    - **Description**: Verifies that a CRM Collaborator is authenticated and has the correct role.
    - **Scenario**: Authenticated CRM Collaborator registers a show request.
    - **Expected Outcome**: Registration completes successfully.

4. **Integration Test: Persistence in Memory and JPA**
    - **Description**: Confirms that a `ShowRequest` is saved correctly in both in-memory and JPA modes.
    - **Scenario**: Register a request in both persistence modes.
    - **Expected Outcome**: The request is saved and retrievable in both modes.

5. **Scenario Test: Invalid Date**
    - **Description**: Attempts to register a show request with a past date.
    - **Scenario**: Show date is earlier than the current date.
    - **Expected Outcome**: An error message is returned indicating an invalid date.

### Screenshots
- (Optional) Screenshots of test results passing in the console or IDE can be attached here.

---

## 5. Construction (Implementation)

---

## 6. Integration and Demo

---
## 7. Observations


