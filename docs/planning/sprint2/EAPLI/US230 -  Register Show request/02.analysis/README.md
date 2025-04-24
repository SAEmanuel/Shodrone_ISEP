# US230 - Register Show request

## 2. Analysis

### 2.1. Relevant Domain Model Excerpt 

![Domain Model](./puml/us230-domain-model-Register_Show_Request_Domain_Model_Template.svg)

### 2.2. Other Remarks

- **Implementation Considerations**:
    - The registration of a `ShowRequest` should leverage the domain-driven design patterns, such as Entities, Value Objects, and Repositories. For example, the `ShowRequest` entity should be persisted using a `ShowRequestRepository`, which supports both in-memory and RDBMS modes (NFR07).
    - Input validation is critical to ensure data integrity. The system must enforce constraints like non-empty `place`, valid `time` format ("YYYY-MM-DD HH:MM"), positive `numberOfDrones`, and valid `duration`. These validations should be implemented in the application layer (e.g., `RegisterShowRequestService`) before persisting the `ShowRequest`.
    - The `ShowRequestAuthor` should automatically capture the authenticated CRM Collaborator’s details (e.g., `userId`, `username`) using the authentication context provided by US210 (EAPLI’s `AuthFacade`).

- **Business Rule Enforcement**:
    - The system must validate that the selected `Customer` has a `status` of "active" or "VIP" (Section 3.1.2, Page 9). This check should be performed in the application layer, querying the `CustomerRepository` to retrieve the `Customer` entity.

- **Future Considerations**:
    - The `ShowRequest`’s `status` will need to support transitions beyond "Created" (e.g., "Under Review", "Proposed"), as seen in US236 and the proposed US237 (Create Show Proposal). The `ShowRequestStatus` entity is designed to handle this, but future user stories may require additional status values.
    - A notification mechanism (e.g., email to the customer) after registration could enhance the workflow. This could be addressed in a future user story (e.g., US240 in Sprint 3), as noted in the requirements document.

- **Alignment with Non-Functional Requirements**:
    - **NFR02 (Documentation)**: The domain model (`us230-domain-model-Register_Show_Request_Domain_Model_Template.svg`) and this analysis document are part of the required documentation, stored in the repository.
    - **NFR03 (Test-Driven Development)**: Unit tests should be written for the `RegisterShowRequestService`, covering validation of customer status, figure exclusivity, and persistence. End-to-end tests should verify the entire workflow, including the confirmation message.
    - **NFR08 (Role-Based Access)**: The system must ensure that only authenticated CRM Collaborators can register a show request, enforced via EAPLI’s authentication module (US210).



