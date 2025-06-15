# US310 – Create Show Proposal

## 2. Analysis

### 2.1. Relevant Domain Model Excerpt

![Domain Model](svg/us310-domain-model.svg)

This domain model represents the structure for creating a **Show Proposal**. It introduces the `ShowProposal` aggregate as the core entity, encapsulating all required data to initiate, describe, and track a proposal based on a customer's show request.

The model aligns with the business process of generating a proposal from a **Show Request**, defining a template, selecting figures, assigning drones, and optionally attaching a simulation video. The internal value objects provide strong typing and encapsulation for all critical fields.

#### **Explanation of the model elements**

- **ShowProposal** (`<<Entity>>`):  
  The central aggregate root representing a customer's drone show proposal. It includes:
  - A one-to-one reference to **Show Request** (`<<AggregateRoot>>`), representing the originating customer request.
  - A one-to-one association with **ProposalTemplate** (`<<AggregateRoot>>`), used as a blueprint for proposal creation.
  - One-to-many associations with **Figures** (`<<AggregateRoot>>`), representing the choreography or visual elements planned for the show.
  - One-to-many associations with **DroneModel** (`<<AggregateRoot>>`), defining the drone types assigned to the show.
  - Embedded value objects for **Description**, **Location**, and **Video**, which capture textual details, show location, and attached simulation video metadata respectively.
  - Embedded **ShowProposalStatus** value object managing the workflow state (e.g., Draft, StandBy), which governs permissions for video uploads and edits.
  - A **Name** value object representing the identifying title of the proposal.

- **Show Request** (`<<AggregateRoot>>`):  
  Represents the initial customer request that originates the show proposal.

- **ProposalTemplate** (`<<AggregateRoot>>`):  
  Template entity used to guide the structure and default contents of a proposal.

- **Figures** (`<<AggregateRoot>>`):  
  Collection of choreography or visual figures that form the drone show.

- **DroneModel** (`<<AggregateRoot>>`):  
  Drone models or types planned for deployment in the show.

- **Description**, **Location**, **Video**, **ShowProposalStatus**, **Name** (`<<ValueObject>>`):  
  Encapsulate key data points for the proposal’s textual description, geographic location, video attachment, lifecycle status, and identifying name.

This domain structure provides a clear, modular, and traceable foundation for creating show proposals aligned with business rules and workflows.

### 2.2. Other Remarks

Key design considerations include:

- **Separation of Concerns**: Independent management of figures and drones allows reuse, versioning, and controlled visibility without coupling to proposals.
- **Extensibility**: The model can evolve to support advanced features like proposal editing, audit logs, drone assignment validation, and customer feedback integration.
- **Robust Validation**: Value objects enforce business rules and immutability, ensuring only consistent and valid proposals are created.

This domain model ensures the show proposal creation process is both technically sound and aligned with customer expectations and internal system constraints.
