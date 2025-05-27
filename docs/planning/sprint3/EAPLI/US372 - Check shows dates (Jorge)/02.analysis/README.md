# US236 - Edit a Show Request

## 2. Analysis

### 2.1. Relevant Domain Model Excerpt

![Domain Model](puml/us236-domain-model-Register_Show_Request_Domain_Model_Template.svg)

### 2.2. Other Remarks

- The domain model should include relationships between `ShowRequest`, `Costumer`, and `ShowProposal` to enforce the constraint that a `ShowRequest` cannot be edited once a `ShowProposal` is associated with it.
- The `ShowRequest` entity should contain attributes such as `id`, `costumer`, `numberOfDrones`, `duration`, `date`, `location`, `description`, `figures`, and a list of `ShowRequestStatus` to track changes (e.g., "Created", "Edited").
- The `ShowProposal` entity should have a reference to the `ShowRequest` it is based on, enabling the system to check for the existence of a proposal before allowing edits.
- Additional validation logic may be required in the domain layer to ensure that edited dates remain at least 72 hours in the future, consistent with US230 constraints.
