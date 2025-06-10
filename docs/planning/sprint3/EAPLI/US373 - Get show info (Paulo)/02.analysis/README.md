# US373 - Get Show Info

## 2. Analysis

### 2.1. Relevant Domain Model Excerpt

![Domain Model](svg/us373-domain-model.svg)

This diagram presents the updated domain model to support **retrieval of show information** by authorized users such as **Show Managers**. It outlines all relevant entities and their associations needed to gather detailed show data, including references to customers, assigned figures, and designers.

While a general domain model outlines high-level aggregates and responsibilities, this specific excerpt focuses on the relationships and attributes needed to fulfill the requirements of retrieving and reviewing **Show** data for planning and decision-making.

#### **Explanation of the model elements**

- **Show** (`<<AggregateRoot>>`): Core entity representing a scheduled drone light show. It encapsulates multiple facets of the performance, such as:
  - `ShowID`: Unique identifier for the show.
  - `Name`: User-facing title of the show.
  - `Description`: Textual description providing context and intent.
  - `Status`: Current lifecycle state of the show (e.g., *Scheduled*, *Completed*, *Cancelled*).
  - `ScheduledDateTime`: Time and date when the show is intended to occur.
  - `Progress`: Indicator of show readiness or completion.
  - `assignedFigures`: References to one or more `Figure` entities involved in the visual performance.
  - `assignedDesigners`: Users or staff responsible for crafting the show elements.

- **Costumer** (`<<AggregateRoot>>`): Entity representing the client commissioning the show. The relationship from `Show` to `Costumer` enables filtering and permission scoping:
  - `NIF`: Unique tax identifier used to verify and associate a customer with a representative.

- **Figure**: Represents individual animated elements designed for the drone show. These are referenced but not deeply modeled in this view.

- **Designer**: Staff members or creators responsible for the execution or artistic input of a show.

This domain model provides a precise structure for retrieving and displaying all necessary show information while maintaining clean and modular relationships between aggregates.

### 2.2. Other Remarks

This domain model is purpose-built to enable efficient querying and presentation of comprehensive show information in the UI. It supports:
- **Secure, permission-based access** based on customer or representative identity.
- **Detailed visibility** into show configuration for review and management.
- **Future enhancements**, such as integrating drone fleet details, support team allocation, or multi-stage scheduling.

This version ensures scalability and readiness for potential audit, analytics, or performance tracking features, while providing an intuitive and domain-compliant structure for current needs.
