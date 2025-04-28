# US248 - Inactivate/Activate a figure category

## 1. Requirements Engineering

### 1.1. User Story Description

As a Show Designer, I want to inactivate or activate a figure category in the catalogue, so that categories no longer in use can be disabled and, if needed, re-enabled for future use.

### 1.2. Customer Specifications and Clarifications

- Only users with the Show Designer role can inactivate or activate categories.
- Inactivated categories must remain in the system for historical and audit purposes, but cannot be used in new figure associations.
- It must be possible to reactivate a previously inactivated category.
- The system must provide clear feedback (success or error) after each operation.
- All status changes (activation/inactivation) must be auditable (who performed the change and when).
- Inactivated categories should not appear in the default list for selection or association.

### 1.3. Acceptance Criteria

- [ ] Only Show Designers can inactivate or activate categories.
- [ ] The status of the category is updated to ACTIVE or INACTIVE as requested.
- [ ] The user and timestamp of the status change are recorded.
- [ ] Inactivated categories are excluded from the default list for association with figures.
- [ ] A success or error message is shown after the operation.
- [ ] It is possible to reactivate an inactivated category.
- [ ] The system prevents redundant operations (e.g., trying to inactivate an already inactive category).

### 1.4. Found out Dependencies

- Depends on the user authentication and authorization module.
- Relies on the persistence infrastructure for updating category status.
- Integration with the listing functionality to ensure inactivated categories are excluded from default views.

### 1.5 Input and Output Data

**Input Data:**

* Category identifier (ID) to select the category.
* Requested action (activate or inactivate).

**Output Data:**

* Confirmation of successful status change (success message and updated category details), or
* Error message indicating reason for failure (e.g., category not found, already in requested status, insufficient permissions).

### 1.6. System Sequence Diagram (SSD)

![System Sequence Diagram](svg/us248-system-sequence-diagram.svg)

### 1.7 Other Relevant Remarks

- All status changes should be auditable (who changed, when).
- The system should be ready for future enhancements, such as providing a history of status changes.
- Inactivated categories must remain in the system for historical and reporting purposes.
- The UI should clearly indicate the current status of each category and prevent invalid operations.
