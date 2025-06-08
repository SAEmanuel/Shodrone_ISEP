# US316_US371 -  Send_Accept_Reject show proposal

## 2. Analysis

### 2.1. Relevant Domain Model Excerpt

![Domain Model](svg/us245-domain-model.svg)

The following diagram illustrates the updated and complete domain model for the **Figure Category** functionality. It includes all relevant attributes and associations required for implementation, traceability, and auditing.

#### **Explanation of the model elements**

- **FigureCategory** (`<<AggregateRoot>>`):  
  The aggregate root representing a category of figures. It encapsulates all rules and logic necessary for managing the lifecycle of a category.

    - `pk`: Primary key (surrogate, auto-generated).
    - `version`: Used for optimistic concurrency control.
    - `createdOn` / `updatedOn`: Audit fields for timestamps.
    - `active`: Boolean flag indicating availability (status).

- **Name** (`<<ValueObject>>`):  
  Value object representing the name of the category. Enforces constraints such as length, allowed characters, and uniqueness.

- **Description** (`<<ValueObject>>`):  
  Optional value object used to provide additional context about the category. Subject to validation constraints (length, non-empty, meaningful content).

- **Email** (`<<ValueObject>>`):  
  Used both as the `createdBy` and `updatedBy` identifiers, referencing the authenticated user responsible for actions on the category.  
  Must be a valid email from the `@shodrone.app` domain.

- **Figure** (`<<AggregateRoot>>`):  
  Represents a shape or form created by a customer. Each `Figure` is associated with exactly one `FigureCategory`, supporting classification and filtering of figures.

---

This detailed model supports:

-  Full **auditability** of each category (who created or modified it, and when).
-  Enforcement of **domain constraints** (e.g., email domain, name rules, optional descriptions).
-  Easy **extensibility** for future figure or category features.
-  Complete **traceability** from figures back to their respective categories.

> This version of the domain model reflects the most up-to-date understanding of requirements and ensures compliance with business rules for category and figure management.