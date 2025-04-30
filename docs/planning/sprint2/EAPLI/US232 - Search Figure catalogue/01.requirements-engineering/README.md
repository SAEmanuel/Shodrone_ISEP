# US232 - Search Figure Catalogue

## 1. Requirements Engineering

### 1.1. User Story Description

As a CRM Collaborator and Show Designer, I want to search the figure catalogue using keywords, categories, and availability (public or exclusive) so that I can quickly find relevant figures for inclusion in a show request. This allows efficient browsing and improves show configuration accuracy and speed.

### 1.2. Customer Specifications and Clarifications

The following specifications are derived from the requirements document (Section 3.2.2, Page 13) and the DDD model:

- The figure catalogue includes both:
    - **Public figures** (`Figure.isPublic = true`)
    - **Exclusive figures** (`Figure.exclusivity = customerId`)
- The system must allow filtering/searching by:
    - **Name or keyword** (full or partial match)
    - **Category** (optional field)
    - **Availability** (public or exclusive)
- The results must only show **active** figures (`Figure.status = Active`).
- If a customer is selected, only **public** and **customer-exclusive** figures should appear.

**Clarifications**:
- **Q: Is full-text search required?**
    - A: No, partial match on name or description is sufficient (e.g., searching "spir" matches "Spiral").
- **Q: Can multiple filters be combined?**
    - A: Yes, the user can filter by name, category, and availability simultaneously.
- **Q: Can this be used independently of a show request?**
    - A: Yes, but typically accessed during figure selection in a request.

**Forum Questions**:
> **Question:** Figuras exclusivas de cliente devem aparecer em pesquisas se o cliente estiver selecionado?
>
> **Answer:** Sim. Devem aparecer figuras públicas e exclusivas do cliente (com `exclusivity = customerId`).

### 1.3. Acceptance Criteria

- **AC1**: Only authenticated CRM Collaborators and Show Designer can access the figure search.
- **AC2**: The search supports filtering by:
    - Name (partial match)
    - Category
    - Availability (public or exclusive)
- **AC3**: If a customer is selected, the system must include:
    - Public figures
    - Figures where `Figure.exclusivity = selectedCustomerId`
- **AC4**: Only active figures are included in results (`Figure.status = Active`).
- **AC5**: The search must be paginated (default: 20 results per page).
- **AC6**: If no results match, system displays: "No figures match the search criteria."
- **AC7**: Results include: `id`, `name`, `description`, `duration`, `category`, and `dsl`.

### 1.4. Found out Dependencies

- **US210**: Authentication and user management – Required to verify the identity and role of the CRM Collaborator.
- **US231**: List public figures – Provides the base listing capability without filters.
- **US233**: Add Figure to the catalogue – Source of searchable data.
- **US234**: Decommission Figure – Impacts the visibility of figures (`status = Active`).
- **US245–US248**: Figure category management – Used as filter options.

### 1.5 Input and Output Data

**Input Data:**

- Search parameters:
    - `nameKeyword` (string, optional, partial match)
    - `categoryId` (integer, optional)
    - `availability` (enum: `public`, `exclusive`, `both`)
    - `customerId` (integer, optional)
    - `page` (integer, default: 1)
    - `pageSize` (integer, default: 20)

**Output Data:**

- List of matching `Figure` records, each with:
    - `Figure.id`
    - `Figure.name`
    - `Figure.description`
    - `Figure.duration`
    - `Figure.category.name`
    - `Figure.previewUrl`

### 1.6. System Sequence Diagram (SSD)

![System Sequence Diagram](svg/us232-sequence-diagram.svg)

### 1.7 Other Relevant Remarks

<!-- Add any additional remarks or notes here -->
