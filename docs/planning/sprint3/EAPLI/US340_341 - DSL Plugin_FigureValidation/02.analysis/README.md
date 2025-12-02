# US340 - Add Figure with DSL Validation

## 2. Analysis

### 2.1. Relevant UI Sequence Diagram Excerpt

![UI Sequence Diagram](svg/us340-domain-model.svg)

This diagram illustrates the detailed UI interaction flow for adding a figure with DSL validation in the system. It captures the communication between the user, UI components, controllers, and validation plugins required to successfully add a figure into the catalogue with the necessary metadata and domain-specific language content.

The process encompasses user input for essential figure attributes such as name, description, category, availability, and status. A crucial part of this flow is the looped DSL input and validation, enabling users to iteratively add and correct DSL scripts defining the figure, leveraging validation plugins and drone model data to ensure correctness.

The sequence also supports optional customer selection and handles success and error scenarios on submission.

Including this level of detail is important because:

- It ensures a robust UI flow that validates all required inputs before submission.
- It supports interactive DSL editing with validation feedback loops, improving user experience and data integrity.
- It coordinates backend calls to retrieve categories, customers, and drone models dynamically to keep the UI responsive and up to date.
- It provides clear user feedback on success or failure of figure addition.

#### **Explanation of the model elements**

- **Show Designer (Actor):** The user initiating and interacting with the add figure UI.
- **AddFigureUI:** The UI component managing the entire add figure workflow, coordinating prompts and validation.
- **Name, Description:** Participants responsible for prompting and validating these attributes.
- **GetFigureCategoriesController:** Retrieves available figure categories to present for selection.
- **FigureAvailability, FigureStatus:** Enum validators for figure availability and status inputs.
- **FigureValidationPlugin:** Core validator for DSL content, providing error feedback.
- **DSLTextBoxUtils:** Utility extracting DSL lines from user input for validation.
- **GetDroneModelsController:** Supplies drone model data necessary for validation.
- **AddFigureController:** Backend controller managing the actual figure creation logic.
- **CostumerSelectionController:** Retrieves optional customer data related to the figure.

### 2.2. Other Remarks

This UI sequence design emphasizes:

- Interactive and user-friendly input validation, especially for DSL scripts that may require multiple iterations.
- Dynamic data retrieval for categories, customers, and drone models, ensuring accurate and current selection options.
- Clear separation of concerns between UI, validation, and backend control logic.
- Robust error handling and user feedback to guide users smoothly through the figure addition process.

This sequence prepares the system to support complex figure definitions in the catalogue, improving both data quality and user experience.
