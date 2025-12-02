# US317 - Mark Show Proposal as Accepted

## 3. Design

### 3.1. Design Overview

The design for US317 enables a CRM Collaborator to accept a show proposal that was previously approved by a customer. This acceptance confirms internal acknowledgment and triggers the creation of a scheduled show.
The authentication process follows these high-level steps:

1. **User Interaction**: The CRM Collaborator uses the AcceptShowProposalUI to select a proposal with status CUSTOMER_APPROVED.

2. **Validation**: The system uses VerifyShowProposalStatusController to ensure that the proposal is indeed eligible for acceptance (it has been approved by the customer).

3. **Status Update and Show Creation**: If valid, the proposal status is updated to COLLABORATOR_APPROVED, and a new Show is created using the CreateShowController.

4. **Persistence**: The updated proposal is saved to the database via the ShowProposalRepository.

5. **Feedback**: The UI informs the user whether the operation was successful or not.

This functionality adheres to a layered architecture with clear separation of concerns:

- **UI Layer**: AcceptShowProposalUI handles user interaction and delegates the core logic to the controller.

- **Application Layer**: AcceptProposalAndCreateShowController coordinates validation, proposal status update, and show creation/persistence.

- **Domain Layer**: Contains the ShowProposal aggregate, ShowProposalStatus enumeration, and Show entity.

- **Persistence Layer**: ShowProposalRepository and ShowRepository abstract the storage and retrieval of domain entities.

- **Infrastructure Layer**: Role-based access is enforced via the CRMCollaboratorUI, which restricts this functionality to users with the CRM Collaborator role.

### 3.2. Sequence Diagrams

3.2.1. Class Diagram
The class diagram below represents the main components involved in US317:

![Class Diagram](svg/class_diagram.svg)

3.2.2. Sequence Diagram (SD)
The sequence diagram below illustrates the message flow between the user and the system:

![Sequence Diagram](svg/us317-sequence-diagram.svg)

### 3.3. Design Patterns (if any)

Domain-Driven Design (DDD) Patterns
- Application Service
  AcceptProposalAndCreateShowController serves as the application service orchestrating all domain operations involved in proposal acceptance and show creation.
- Repository
  ShowProposalRepository and ShowRepository abstract data access and provide persistence operations for proposals and shows, respectively.
- Aggregate Root
  ShowProposal is the aggregate root representing the full state of a proposal, including its current status and associated show request.
- Value Objects
  ShowProposalStatus is an enumeration-based value object encapsulating valid state transitions and business rules for a proposal.
- Validation Controller
  VerifyShowProposalStatusController acts as a validation helper to encapsulate the eligibility check for the show proposal.
