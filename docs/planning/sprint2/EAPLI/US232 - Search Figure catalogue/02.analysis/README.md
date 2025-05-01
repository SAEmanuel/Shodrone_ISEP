# US232 - Search Figure catalogue

## 2. Analysis

### 2.1. Relevant Domain Model Excerpt

![Domain Model](svg/us232-domain-model.svg)

This diagram presents the updated domain model for figure and category management, tailored specifically to support the listing of **public figures** in the **Figure catalogue**. It integrates all relevant data required to fulfill the user story and meet catalog listing needs, including references to category data and figure visibility.

While a general domain model focuses on high-level entities and their relationships, this specific excerpt dives deeper into the internal structure of the **Figure** entity. It highlights the key attributes needed to support real-world catalogue functionality, such as `availability`, `Category Name`, and `status`. These elements are essential for controlling visibility, tracking figure evolution, and managing lifecycle states.

Including this level of detail is important because:

- It helps meet both functional and non-functional requirements for managing figures in the catalogue, such as determining whether a figure is publicly accessible or under revision.
- It lays the groundwork for enforcing rules around figure visibility, state transitions, and content accuracy.
- It ensures the system can support consistent figure listings while remaining flexible for future enhancements like advanced filtering or publishing workflows.

#### **Explanation of the model elements**

- **Figure** (`<<AggregateRoot>>`): Central entity in the Figure catalogue. Each figure includes essential metadata used for display and filtering in the catalogue, such as:
    - `id`: Unique identifier for the figure.
    - `name`: User-facing name.
    - `description`: Description of the figure's purpose or context.
    - `version`: Indicates the current version of the figure 
    - `availability`: Enum indicating if the figure is public (e.g., *Public*, *Exclusive*).
    - `dsl`: Domain-specific language reference used to define the figure.
    - `status`: Operational status (e.g., *Active*).
    - `category`: Association to the `Category` entity, from which the `category.name` is exposed.
    - **Category** (`<<AggregateRoot>>`): Each figure belongs to exactly one category, which provides organizational structure and classification for listing. From the figure perspective, the `category.name` is highly relevant in this context, as it’s used for display and filtering.

This domain view ensures that all required data for public figure listing is readily accessible, while maintaining clean separation of responsibilities between the Figure and Category aggregates.

### 2.2. Other Remarks

This version of the domain model emphasizes read-optimized access to figure data, including category references. The design ensures:
- **Fast and reliable retrieval** of all fields required by the UI for the catalogue.
- **Clear separation of concerns**, maintaining category ownership of classification logic.
- **Future extensibility**, such as support for filtering by availability or status.

The model is now prepared to support additional features like search, filtering, and version tracking in the figure catalogue.
w