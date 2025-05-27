# US312 - Add Figure to a Proposal

## 1. Requirements Engineering

### 1.1. User Story Description

As a CRM Collaborator, I want to add one of the available figures to a show proposal so that I can customize the show for the customer. The process must allow selecting active figures from the catalogue, specifying their occurrence in the sequence, and mapping drone types in the figure to drone models in the proposal, ensuring compatibility with the inventory and avoiding consecutive repetitions of the same figure.

### 1.2. Customer Specifications and Clarifications

The following specifications are derived from the requirements document (Section 3.1.4 and 3.1.3, Pages 10 and 9) and the DDD model:

- A `ShowProposal` must include:
  - **Figure Selection**: The CRM Collaborator can add active figures from the catalogue (`Figure.isActive = true`), which can be public or exclusive to the customer (per Section 3.1.3, Page 9).
  - **Sequence Configuration**: Figures can be added multiple times in the sequence (`FigureExecution`), but not consecutively (e.g., "Circle -> Spiral -> Circle" is valid, but "Circle -> Circle" is not).
  - **Drone Type Mapping**: For each figure, the CRM Collaborator must map drone types in the figure to drone models in the proposal, ensuring a surjective function (every drone type is assigned at least one model).
  - **Inventory Compatibility**: The number of drones of a given model in the proposal must not exceed the number of active drones of that model in the inventory (per US311, Page 17).
  - **Workflow Information**: The action is logged with the author (`ShowProposalAuthor`) and a timestamp.

**Clarifications**:
- **Q: What happens if a figure is inactive or unavailable?**
  - A: The system rejects the action with an error: "Selected figure is inactive or unavailable."
- **Q: How should drone type mapping be handled?**
  - A: The system provides a UI to select drone models from the inventory, ensuring compatibility with the figure’s drone types and validating against inventory limits.
- **Q: Can a figure be added with no drone model mapping?**
  - A: No, all drone types in the figure must be mapped to models; otherwise, the system rejects the action with an error: "All drone types must be mapped to models."

**Forum Questions** (Adapted for US312):
> **Question:** Concorda que a validação de inventário deve ocorrer ao adicionar uma figura à proposta?
>
> **Answer:** Sim, a validação deve verificar se o número de drones do modelo não excede o inventário ativo.
>
> **Question:** É necessário impedir a repetição consecutiva de figuras na sequência?
>
> **Answer:** Sim, conforme os requisitos, uma figura não pode aparecer duas vezes seguidas na sequência.
>
> **Question:** O mapeamento de tipos de drones deve ser manual ou automático?
>
> **Answer:** Deve ser manual, com suporte da UI para selecionar modelos compatíveis do inventário.

### 1.3. Acceptance Criteria

- **AC1**: The CRM Collaborator must be authenticated and authorized to add figures to a proposal (role-based access, per NFR08).
- **AC2**: The selected figure must be active (`Figure.isActive = true`) and either public (`Figure.isPublic = true`) or exclusive to the customer (`Figure.exclusivity` matches the customer); otherwise, the system rejects the action with an error.
- **AC3**: The figure must not be the same as the previously added figure in the sequence; otherwise, the system rejects the action with an error: "Consecutive repetition of the same figure is not allowed."
- **AC4**: All drone types in the figure must be mapped to drone models from the proposal, with the total number of drones per model not exceeding the inventory count (checked via US311).
- **AC5**: The `ShowProposal` must be updated with the new `FigureExecution`, including the drone type mapping, author (`ShowProposalAuthor`), and timestamp.
- **AC6**: The updated `ShowProposal` must be persisted in RDBMS (NFR07).
- **AC7**: After successful addition, the system must display a confirmation message: "Figure [Figure.code] added to proposal [ShowProposal.id] successfully."

### 1.4. Found out Dependencies
- **US210**: Authentication and authorization – Required to authenticate the CRM Collaborator and set the `ShowProposalAuthor` (only CRM Collaborators can modify proposals, per NFR08).
- **US231**: List public figures in the catalogue – Needed to retrieve active figures for selection.
- **US311**: Add drones to a proposal – Required to validate inventory compatibility and provide drone models for mapping.
- **US110**: Iterative updates to the DDD model – The `ShowProposal`, `FigureExecution`, and drone type mapping entities must be defined in the DDD model.

### 1.5. Input and Output Data

**Input Data:**
- Typed data:
  - Figure code or ID (string, e.g., "FIG-001" from the catalogue).
  - Drone type mapping (list of pairs: `DroneType` to `DroneModel`, e.g., {TypeA: ModelX, TypeB: ModelY}).
- Selected data:
  - Figure (from a list of active public or customer-exclusive figures, based on `Figure.isActive` and `Figure.exclusivity`).
- Automatic data:
  - Author of the action (CRM Collaborator doing the addition).
  - Timestamp of the action.
  - Proposal ID (existing `ShowProposal.id`).

**Output Data:**
- Updated `ShowProposal` ID (e.g., `ShowProposal.id = "SP-001"`).
- Confirmation message (e.g., "Figure FIG-001 added to proposal SP-001 successfully").

### 1.6. System Sequence Diagram (SSD)

Below is the PlantUML source code for the System Sequence Diagram (SSD) of US312, showing the interaction between the CRM Collaborator, the system, and external entities (e.g., authentication, figure/inventory data).

![System Sequence Diagram](./puml/us230-sequence-diagram.svg)

### 1.7. Other Relevant Remarks

- The system should enforce real-time validation of inventory limits during drone type mapping to prevent over-allocation.
- The UI should provide a dropdown or similar mechanism to select figures and map drone types, improving usability for the CRM Collaborator.
- Future user stories (e.g., US315 - Add video of simulation) may depend on successful figure addition for simulation integration.
- The team should consider adding a preview feature to show the sequence impact before finalizing the addition, which could be addressed in a future iteration.
