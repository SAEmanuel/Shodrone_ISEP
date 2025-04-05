# Domain Model Report – Drone Show Management Platform

## Introduction

This report presents a detailed analysis and explanation of the Domain Model designed for a **Drone Show Management Platform**. The objective is to describe all core business concepts, their relationships, and responsibilities within the system using **Domain-Driven Design (DDD)** principles.

All modeling decisions, terminology, and structure are directly based on the **client's business documentation**. The goal is to ensure that the software reflects the client's mental model and supports their workflows as accurately as possible.

We will explore each **Aggregate**, **Entity**, **Value Object**, and **Association**, and explain how they represent the business logic behind the creation, planning, proposal, and execution of drone shows.

Throughout this document, we will follow a structured, step-by-step approach to ensure clarity and completeness. Each section will be guided by contextual insights and domain-specific reasoning. We will also reflect on naming choices, model boundaries, and responsibilities to ensure that the model accurately expresses the **Ubiquitous Language** of the domain.

Let’s begin with a high-level overview of the domain and gradually dive into its components.

---

## Aggregate: Exclusivity

### Description

The `Exclusivity` aggregate represents the concept of a **time-based exclusivity** assigned to a `Figure`. This means that certain figures cannot be reused or assigned elsewhere during a specific period.

This requirement comes directly from the client's business documentation, where figures can be declared exclusive for a defined time range.

### Root Entity: `Exclusivity`

- Represents the exclusivity assigned to a figure.
- Acts as the aggregate root.

### Value Object: `ExclusivityTime`

- Defines the time period during which the exclusivity is valid.
- Composed of a start and end date.

### Association

- `Figure → Exclusivity`.

---

## Aggregate: Category

### Description

The `Category` aggregate represents a classification used to group figures. Categories are predefined and exist independently from other aggregates.

This structure reflects the client's documentation, which states that **each figure belongs to a specific category**, but categories themselves are managed separately.

### Root Entity: `Category`

- Represents a category to which figures can belong.
- Acts as the aggregate root.
- Exists independently from `Figure`.

### Association

- `Category → Figure`.

---

## Aggregate: Figure

### Description

The `Figure` aggregate represents a complete and identifiable formation used in drone shows. It encapsulates the key components that define a figure, including its elements, description, version, and classification data.

According to the client's documentation, figures are central to the domain and are used throughout the planning and execution of shows.


### Root Entity: `Figure`

- Represents the main concept of a drone show formation.
- Acts as the aggregate root.
- Associated with one `Category` and optionally with an `Exclusivity`.


### Internal Structure

- `Element` (Entity):  
  Represents the individual elements that compose the figure. Multiple elements can belong to a single figure.

- `KeyWord` (Value Object):  
  Defines keywords used to tag and search for figures.

- `FigureDescription` (Value Object):  
  Provides a textual description of the figure.

- `Version` (Value Object):  
  Identifies the version of the figure. A figure may have multiple versions over time.

- `DslCode` (Value Object):  
  Contains the DSL representation of the figure's internal logic or structure.

### Associations

- `Category → Figure`.
- `ShowProposal → Figure`.
- `ShowRequest → Figure`.
- `ShowFigure → Figure`.
- `Figure → Client`.
- `FigureProposal → Figure`.
- `FigureProposal → Figure`.
- `Figure → Exclusivity`.

---

## Aggregate: Client

### Description

The `Client` aggregate represents a company that contracts drone shows from Shodrone. It encapsulates all relevant information about the client, including legal identifiers, contact information, and internal representatives.

This structure follows the business documentation, which identifies clients as distinct entities with multiple associated representatives.

### Root Entity: `Client`

- Represents the client (company).
- Acts as the aggregate root.

### Internal Structure

- `ClientStatus` (Value Object):  
  Represents the current status of the client (e.g., active, inactive).

- `VatNumber` (Value Object):  
  Represents the client's VAT identification number.

- `Address` (Value Object):  
  Contains the official address of the client.

- `Representative` (Entity):  
  Represents an individual acting on behalf of the client. A client can have multiple representatives.

  - `Email` (Value Object): Contact email of the representative.
  - `Name` (Value Object): Full name of the representative.
  - `Position` (Value Object): The representative’s role or position within the client’s company.

### Associations

- `Client → Show`
- `Figure → Client`
- `Representative → ShowProposal`
- `Representative → ShowRequest`

---

## Aggregate: Show

### Description

The `Show` aggregate represents the **final, confirmed drone show** that will be executed. It is created after the negotiation and proposal phases are complete.

According to the client’s documentation, a `Show` consolidates the agreed-upon details, including the selection and order of figures to be performed.


### Root Entity: `Show`

- Represents the finalized drone show.
- Acts as the aggregate root.


### Internal Structure

- `ShowFigure` (Entity):  
  Represents a figure selected for inclusion in the show.

- `Sequence` (Value Object):  
  Defines the order in which figures appear in the show. Associated with `ShowFigure`.


### Associations

- `Client → Show`
- `Show → Drone`
- `ShowProposal → Show`

---

## Aggregate: Drone

### Description

The `Drone` aggregate represents an individual drone unit used in drone shows. Each drone has a unique identity and operational status, and is assigned to a specific drone model.

This aggregate captures essential information needed to manage and allocate drones for show execution.


### Root Entity: `Drone`

- Represents a physical drone device.
- Acts as the aggregate root.


### Internal Structure

- `DroneStatus` (Value Object):  
  Represents the current operational status of the drone (e.g., available, under maintenance).

- `SerialNumber` (Value Object):  
  Uniquely identifies the drone hardware.

### Associations

- `Drone → DroneModel`
- `Show → Drone`

---

## Aggregate: DroneModel

### Description

The `DroneModel` aggregate represents a specific type or configuration of drone used in shows. Each model defines its technical capabilities, supported language, and available plugins.

This aggregate is used to differentiate drone behavior and compatibility, particularly when assigning figures or elements that require certain model features.


### Root Entity: `DroneModel`

- Represents a type of drone with specific capabilities.
- Acts as the aggregate root.


### Internal Structure

- `DroneCode` (Entity):  
  Represents the language or code system supported by the drone model.

- `PlugIn` (Entity):  
  Represents additional features or extensions supported by the drone code.


### Associations

- `Drone → DroneModel`
- `Element → DroneModel`  

---


## Aggregate: ProposalTemplate

### Description

The `ProposalTemplate` aggregate represents a predefined structure for creating show proposals. It defines standard formats or settings that can be reused when generating new proposals.

It exists independently in the system and is not tied to a specific client or request.


### Root Entity: `ProposalTemplate`

- Represents a reusable template for show proposals.
- Acts as the aggregate root.


### Associations

- `ShowProposal → ProposalTemplate`  
  A show proposal is based on a selected proposal template.


---

## Aggregate: ShowRequest

### Description

The `ShowRequest` aggregate represents the initial request made by a client to organize a drone show. It collects all relevant details required for planning, analysis, and negotiation.

A request can lead to one or more `ShowProposal` entities, which formalize possible ways to execute the requested show.


### Root Entity: `ShowRequest`

- Acts as the aggregate root.
- Represents the full client request for a drone show.


### Internal Structure

#### Value Objects (related to ShowRequest):

- `ShowRequestDescription`:  
  Describes the purpose or context of the requested show.

- `ShowRequestDate`:  
  Date when the request was submitted.

- `NumberDrones`:  
  Number of drones expected in the show.

- `ShowRequestLocation`:  
  Location(s) where the show is expected to occur.

- `ShowDate`:  
  Proposed date for the show to happen.

- `ShowRequestDuration`:  
  Estimated duration of the show.

- `ShowDescription`:  
  Additional narrative describing the event.

- `ShowRequestStatus`:  
  Current status of the request (e.g., pending, accepted, declined).


#### Entities:

- `FigureProposal`:  
  Suggested figures proposed by the client.

- `ShowProposal`:  
  Represents one possible formal response to the show request.


#### Value Objects (related to ShowProposal):

- `ShowProposalDescriptionDsl`:  
  DSL description of the proposed show.

- `Video`:  
  Video material associated with the proposal.

- `ShowProposalDate`:  
  Date the proposal was created.

- `ShowProposalDescription`:  
  Description of the proposal content.

- `ShowProposalAcceptanceDate`:  
  Date the proposal was accepted (if applicable).

- `ShowProposalLocation`:  
  Proposed location for executing the show.

- `ShowProposalDuration`:  
  Proposed duration of the show.

- `ShowProposalNumberDrones`:  
  Number of drones proposed for use in this specific show proposal.


### Associations

- `ShowRequest → Figure`
- `Representative → ShowRequest`
- `ShowProposal → Figure`
- `ShowProposal → ProposalTemplate`
- `ShowProposal → Show`
- `Representative → ShowProposal`  

---

## Business Domain Summary – Drone Show Management Platform

The Shodrone platform provides a complete workflow for organizing, negotiating, planning, and executing drone shows for clients. The domain model reflects this process through a set of well-defined aggregates, each aligned with the concepts described in the client’s business documentation.

### Clients and Requests

The process begins when a **Client**, represented as a company, submits a **ShowRequest**. Each client may have multiple **Representatives**, who act on behalf of the company during the request and negotiation phases. The show request includes all key parameters such as preferred **ShowDate**, **number of drones**, **location**, **duration**, and a **description** of the intended event. It may also include a **FigureProposal**, suggesting specific figures to be performed.

### Proposals and Negotiation

In response to a request, Shodrone can generate one or more **ShowProposals**. These are formal offers that detail how the show could be executed. A proposal includes a **DSL-based description**, **video preview**, and specifies the proposed **date**, **location**, **duration**, and **number of drones**. Each proposal is structured using a reusable **ProposalTemplate** and is tied to the originating `ShowRequest`.

Proposals are negotiated with the client’s representative. Once accepted, a `Show` is created based on the details from the selected proposal.

### Shows and Execution

A **Show** is the confirmed and final version of the planned event. It includes one or more **ShowFigures**, which reference specific **Figures** (the formations to be flown). Each figure is executed in a defined **Sequence**, and the show is linked to the drones that will perform it.

### Figures and Exclusivity

**Figures** are core reusable formations composed of multiple **Elements**. Each element can be linked to a specific **DroneModel**, ensuring compatibility during execution. Figures include metadata such as **Description**, **Version**, and **DslCode**. They can also be associated with **KeyWords** for easier search and filtering.

Figures are grouped by **Category** and can be assigned a period of **Exclusivity**, during which they are reserved for a specific client and cannot be reused.

### Drones and Technical Structure

The execution of a show depends on the availability and configuration of **Drones**. Each drone is a physical device identified by a **SerialNumber** and current **DroneStatus**. Drones are categorized under a **DroneModel**, which defines its capabilities, supported code (**DroneCode**), and any available **Plugins**.

This ensures that elements in a figure are matched only with compatible drones during planning and execution.

### Domain Integrity

The model ensures that:
- All information flows from client intent to technical execution.
- Business rules such as figure exclusivity, drone compatibility, and proposal approval are clearly modeled.
- The process supports traceability from initial request to final show execution.
- Aggregates are cleanly separated, allowing for modular evolution of the domain logic.

This domain model provides a solid foundation for implementing a robust, flexible, and business-aligned platform for drone show management.
