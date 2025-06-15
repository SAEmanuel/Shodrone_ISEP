# US316_US371 - Send_Accept_Reject Show Proposal

## 4. Tests

---

### 4.1. Unit Tests

*(To be defined)*

---

### 4.2. Application Logic: `AnalyseProposalResponse`

*(To be defined)*

---

### 4.3. Persistence: `ShowProposalRepository`

*(To be defined)*

---

## 5. Construction (Implementation)

The implementation of **Send / Accept / Reject Show Proposal** was carried out with a modular and domain-driven approach:

- **UI Layer**  
  Implemented in the `AnalyseProposalUI` class within the **Customer App**, providing a console-based interface for selecting proposals and submitting responses (accept/reject). The UI handles:
    - Loading proposals for the authenticated customer.
    - Presenting the options (accept/reject).
    - Optional feedback collection from the customer.
    - Delegating the response submission to the appropriate controller.

- **Application Layer (Client)**
    - `GetMyProposalsController`: Fetches proposals from the server for the authenticated customer.
    - `ProposalResponseController`: Sends the customer’s decision (accept/reject + optional feedback) to the server using TCP and JSON (via Gson).

- **Application Layer (Server)**
    - `HandleClientsController`: Accepts TCP connections from customers and dispatches requests (like `AnalyseProposalResponse`).
    - `AnalyseProposalResponse`: Processes the accept/reject decision, updating the proposal status and feedback in the domain model and persisting the changes.

- **Domain Layer**  
  The `ShowProposal` entity is part of the domain model and references a `ShowRequest`. It encapsulates:
    - The current status (`ShowProposalStatus`) and feedback.
    - Auditing fields (creation data and modification data).
    - It **is not an aggregate root**; instead, it is managed in the lifecycle of a `ShowRequest` (which *is* an aggregate root).

- **Persistence Layer**  
  Uses the `ShowProposalRepository` abstraction:
    - Either `InMemoryShowProposalRepository` for development/test environments.
    - Or `ShowProposalJPAImpl` for real-world scenarios with JPA/Hibernate.

- **Network Layer**  
  Communication between client and server uses TCP sockets with JSON-encoded messages (`ObjectDTO`, `ResponseDTO`, etc.), ensuring flexibility and easy extension for future use cases.

- **Auditability**  
  Changes in proposal status and feedback are auditable (who, when).

## 6. Integration and Demo

### 6.1 Integration Points

The implementation integrates with:

- **Domain Layer**  
  `ShowProposal` as an entity managed by the aggregate root `ShowRequest`, including the relevant business logic for proposal lifecycle.

- **Persistence Layer**  
  `ShowProposalRepository` abstraction, switching between in-memory and JPA-based implementations.

- **Authentication Module**
    - On the client side, the `AuthenticationController` verifies the identity of the logged-in customer.
    - On the server side, the customer’s representative relationship is verified before returning proposals.

- **Network Communication**  
  All communication is performed using TCP sockets (via Java Sockets API) with JSON serialization for interoperability and maintainability.

- **Infrastructure**  
  `RepositoryProvider` manages the repository instance for persistence logic.

### 6.2 Demo Walkthrough (UI)

The typical flow demonstrated in the CLI-based UI:

1. **Access the UI**  
   The customer accesses the “Analyse Show Proposal” option in the **Customer App**.

2. **Proposal Retrieval**  
   The system loads and displays the proposals for the authenticated customer using the **GetMyProposalsController**.

3. **Interaction**  
   The customer selects a proposal and chooses to either **accept** or **reject** it.
    - If the customer chooses to provide feedback, it is collected interactively.

4. **Response Submission**  
   The **ProposalResponseController** sends the customer’s decision to the server.

5. **Server Processing**  
   The **AnalyseProposalResponse** controller on the server updates the proposal status and feedback, persisting the changes.

6. **Feedback**  
   The server sends a success or error message back to the **Customer App**, which is displayed to the user.

## 7. Observations

There are no additional observations, limitations, or open questions for this User Story at this time.

---