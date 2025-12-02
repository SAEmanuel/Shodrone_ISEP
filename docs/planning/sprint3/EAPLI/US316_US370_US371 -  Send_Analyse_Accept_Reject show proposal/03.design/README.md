# US316_US371 - Send_Accept_Reject Show Proposal

## 3. Design

### 3.1. Design Overview

The design for the **Send / Accept / Reject Show Proposal** functionality adopts a layered, modular, and domain-driven approach. It aligns with the system's architectural style and promotes separation of concerns between UI, controller logic, domain model, network communication, and persistence.

The process is initiated by the **Customer** via the UI in the **Customer App**, which:
- Retrieves proposals available for their account from the server.
- Prompts the customer to accept or reject a selected proposal, optionally with feedback.
- Sends the accept/reject decision to the server via TCP, using a **ProposalResponseController**.

The server:
- Processes the customer’s decision via the **AnalyseProposalResponse** controller.
- Validates the existence of the proposal.
- Updates the proposal’s status to **ACCEPTED** or **REJECTED** in the database, attaching any feedback provided.
- Responds with success or failure messages, packaged in a **ResponseDTO**.

The system ensures that:
- The **ShowProposal** entity is treated as an **aggregate root**, encapsulating its full lifecycle and domain rules (like status updates).
- All audit data (`createdBy`, `updatedBy`, `createdOn`, `updatedOn`) is preserved.
- The repository pattern (`ShowProposalRepository`) abstracts data access, supporting both in-memory and JPA-based persistence.
- Clear feedback is sent back to the UI, ensuring a smooth and transparent customer experience.

This modular design promotes:
- Extensibility for future enhancements (e.g., proposal revision requests).
- Maintainability and testability by isolating responsibilities.
- Robustness through clear domain and application layer separation.

### 3.2. Sequence Diagram

![Sequence Diagram Full](svg/us316_371-sequence-diagram-full.svg)

The diagram illustrates:
- **Customer** initiates the action from the **Customer App**.
- **AnalyseProposalUI** interacts with **GetMyProposalsController** and **ProposalResponseController** for communication.
- The **AuthenticationController** acts as the TCP client to communicate with the server.
- On the **Server**, **HandleClientsController** receives the TCP request and delegates to **AnalyseProposalResponse**.
- The proposal is fetched from the repository.
- If found, the status is updated and persisted.
- Feedback is included if provided.
- The server sends a confirmation response back to the **Customer App**.
- The **Customer App** UI displays the operation result to the customer.

### 3.3. Design Patterns

- **Repository Pattern**: Abstracts data access, decoupling domain logic from the persistence layer (e.g., `ShowProposalRepository`).
- **Factory Pattern**: `RepositoryProvider` dynamically provides the appropriate repository implementation (e.g., JPA or in-memory).
- **Aggregate Root (DDD)**: `ShowProposal` is an aggregate root that encapsulates all domain logic and lifecycle events.
- **Controller Pattern**:
    - On the **Customer App**, `ProposalResponseController` and `GetMyProposalsController` centralize business logic.
    - On the **Server**, `AnalyseProposalResponse` handles domain-driven updates.
- **Separation of Concerns**: The system layers are clearly defined: UI, Controller, Domain, and Persistence.
- **Network Protocol Abstraction**: Communication between the **Customer App** and the **Server** is done via TCP, abstracted by the `AuthenticationController`.
- **DTO Pattern**: `ShowProposalDTO`, `ObjectDTO`, and `ResponseDTO` standardize data transfer between layers and across the network.
- **SOLID Principles**: The design ensures flexibility, maintainability, and testability.

This design provides a robust, extensible, and maintainable foundation to support the US316_US371 user stories, ensuring customer interaction and feedback with show proposals are transparent and auditable.