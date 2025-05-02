# US235 - List Show Requests of Client

## 2. Analysis

### 2.1. Relevant Domain Model Excerpt

![Domain Model](puml/us23-domain-model-Domain_Model_Template.svg)

### 2.2. Other Remarks

- **Implementation Considerations**:
    - The listing functionality will use the EAPLI framework’s domain-driven design patterns, specifically Repositories and Entities. The `ShowRequestRepository` will be used to query `ShowRequest` entities by `Customer`, leveraging a method like `findByCustomer(customerId)` to retrieve the list.
    - Sorting by `creationDate` in descending order (newest first) should be implemented at the repository level (e.g., using a JPA query with `ORDER BY creationDate DESC` in RDBMS mode, or a `Comparator` in in-memory mode).
    - The `ShowRequestStatus` entity tracks status history. To display the current status, the system should retrieve the most recent `ShowRequestStatus` entry for each `ShowRequest`, based on the `timestamp`.

- **Business Rule Enforcement**:
    - The system must list show requests for any `Customer`, regardless of their `status` (active, VIP, or inactive), as US235 does not impose restrictions on the client’s status for listing (unlike US230, which requires active or VIP for registration).
    - The displayed coordinates (`latitude`, `longitude`) in the `ShowDescription` must be formatted as a pair in decimal degrees (e.g., "(38.736946, -9.138611)") for readability in the console UI.

- **Potential Risks and Mitigations**:
    - **Dependency on US230**: If no `ShowRequest` entities exist (i.e., US230 is not implemented or no requests have been registered), the list will always be empty. Mitigation: Ensure US230 is implemented and tested first, and seed test data in the system for US235 development (e.g., create sample `ShowRequest` entities for various customers).
    - **Performance with Large Datasets**: Listing show requests for a client with many requests (e.g., hundreds) may be slow in RDBMS mode (NFR07), especially since it involves joins with `ShowDescription` and `ShowRequestStatus`. Mitigation: Optimize the query in `ShowRequestRepository` (e.g., use eager fetching for `ShowDescription` and `ShowRequestStatus`, or implement pagination as suggested in Section 1.7).
    - **Role-Based Access Issues**: If US210 (authentication) is not fully implemented, role-based access (NFR08) cannot be enforced, allowing unauthorized users to list show requests. Mitigation: Prioritize US210 implementation and verify role checks in integration tests.

- **Future Considerations**:
    - As noted in Section 1.7, pagination should be considered for clients with many show requests. This could be implemented in a future user story (e.g., US239 in Sprint 3), allowing the user to navigate through pages of results.
    - A "view details" option to display the full `ShowDescription` (including the sequence of figures via `FigureExecution`) could be added in a future user story (e.g., US238), as the current list omits this information for brevity.
    - The system could support filtering by status or date range, enhancing usability for CRM Collaborators managing many requests.

- **Alignment with Non-Functional Requirements**:
    - **NFR02 (Documentation)**: This analysis document and the domain model (`us235-domain-model.svg`) fulfill the documentation requirements, stored in the repository.
    - **NFR03 (Test-Driven Development)**: Unit tests should be written for the `ShowRequestRepository`’s `findByCustomer` method, covering scenarios like empty lists, sorting, and retrieving the current status. End-to-end tests should verify the entire listing flow, including the "no requests" message.
    - **NFR04 (Version Control)**: All code and documentation for US235 will be committed to GitHub, linking commits to the corresponding issue (e.g., #102).
    - **NFR07 (EAPLI Framework)**: The implementation will use EAPLI’s persistence mechanisms (`ShowRequestRepository`, `CustomerRepository`) to support both in-memory and RDBMS modes.
    - **NFR08 (Role-Based Access)**: The system must ensure that only authenticated CRM Collaborators or CRM Managers can list show requests, enforced via EAPLI’s `AuthFacade` (US210).