# US235 - List Show Requests of Client

## 4. Tests

This section documents **unit tests**, **integration tests**, and **validation procedures** for the functionality of listing show requests of a client.

### Test Cases

1. **Unit Test: List Show Requests for a Costumer with Requests**
    - **Description**: Tests the retrieval of show requests for a costumer who has existing requests.
    - **Scenario**: A costumer with two show requests is selected.
    - **Expected Outcome**: A list containing the two show requests is returned.

2. **Unit Test: List Show Requests for a Costumer with No Requests**
    - **Description**: Tests the behavior when a costumer has no show requests.
    - **Scenario**: A costumer with no show requests is selected.
    - **Expected Outcome**: An `IllegalArgumentException` is thrown with the message "No show requests found for the given costumer."

3. **Unit Test: List Show Requests with Invalid Costumer Selection**
    - **Description**: Tests the behavior when no costumer is selected.
    - **Scenario**: The costumer selection process fails (e.g., user cancels or no costumer is found).
    - **Expected Outcome**: An `IllegalArgumentException` is thrown with the message "No customer selected."

4. **Integration Test: Full Flow from UI to Repository**
    - **Description**: Verifies the entire flow from the UI (`ListShowRequestByCostumerUI`) to the repository (`ShowRequestRepository`) through the controller (`ListShowRequestByCostumerController`).
    - **Scenario**: A costumer with show requests is selected, and the requests are displayed in the UI.
    - **Expected Outcome**: The UI displays the show requests with their details using `Utils.printShowRequestResume`.

5. **Edge Case: Costumer with Many Show Requests**
    - **Description**: Tests performance and display for a costumer with a large number of show requests.
    - **Scenario**: A costumer with 50 show requests is selected.
    - **Expected Outcome**: All 50 show requests are listed without performance issues, displayed sequentially with `Utils.printShowRequestResume`.

### Screenshots
![Unit Tests for Show Request](img/UnitTestShowRequest.png)
![Unit Tests for Show Request Repository](img/InMemoryShowRequestRepositoryTests.png)


## 5. Construction (Implementation)

This section describes the implementation logic for listing show requests of a client.

- **Controller**: `ListShowRequestByCostumerController`
    - Coordinates the process by using `FoundCostumerUI` to select a costumer and `ShowRequestRepository` to retrieve the show requests.
- **UI**: `ListShowRequestByCostumerUI`
    - Handles user interaction, displays the costumer selection options, and lists the show requests using `Utils.printShowRequestResume`.
- **Repository**: `ShowRequestRepository`
    - Provides the `findByCostumer` method to retrieve show requests for a given costumer.
- **Implementation Strategy**:
    - The user initiates the process through the CLI (`ListShowRequestByCostumerUI`).
    - The controller (`ListShowRequestByCostumerController`) uses `FoundCostumerUI` to select a costumer by ID, NIF, or from a list.
    - The controller retrieves the list of show requests using `ShowRequestRepository.findByCostumer`.
    - The UI displays each show request’s details.
- **Patterns Used**:
    - **Repository Pattern**: Used to abstract data access (`ShowRequestRepository`).
    - **Controller Pattern**: `ListShowRequestByCostumerController` orchestrates the flow between UI and data layers.

## 6. Integration and Demo

### Integration Points
- **Authentication**: Integrates with `AuthenticationRepository` (via `AuthUtils`) to ensure the user (CRM Collaborator) is authenticated.
- **Costumer Selection**: Uses `FoundCostumerUI` and `CostumerRepository` to select a costumer.
- **Show Request Retrieval**: Uses `ShowRequestRepository` to fetch the requests.

### Demo Walkthrough
- In the CLI, the user selects the "List Show Requests of Costumer" option.
- The system prompts the user to select a costumer using one of three options: by ID, by NIF, or from a list of all costumers.
- Once a costumer is selected, the system displays all associated show requests with details (ID, submission date, status, etc.) using `Utils.printShowRequestResume`.
- The user is prompted to press a key to continue (`Utils.waitForUser`).

## 7. Observations

- **Known Limitations**:
    - The current implementation does not support pagination, which could be an issue for costumers with many show requests (addressed in future user stories like US239).
    - Filtering by status or date range is not supported yet.
- **Design Decisions**:
    - The system throws an exception if no show requests are found, prompting the user to try again or register new requests.
    - `Utils.printShowRequestResume` is reused from US230 to ensure consistent output formatting.
- **Open Questions**:
    - Should there be an option to filter show requests by status or date directly in this user story?
    - How should performance be optimized for costumers with a very large number of show requests?
