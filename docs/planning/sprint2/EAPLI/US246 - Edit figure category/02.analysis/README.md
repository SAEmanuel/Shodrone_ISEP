# US246 - Edit figure category

## 2. Analysis

### 2.1. Relevant Domain Model Excerpt

![Domain Model](svg/us246-domain-model.svg)

This excerpt of the domain model highlights the essential elements for supporting the editing of a figure category:

- **Category (`<<AggregateRoot>>`)**: The main entity representing a figure category. All edits are performed through this aggregate root to ensure consistency and encapsulation of business rules.
- **ID**: Unique identifier for each category, used to select the category to be edited.
- **Name**: Value object representing the category name. It must remain unique (case-insensitive) after editing, and all validations (e.g., not empty, length constraints) are enforced here.
- **Description**: Optional field that can be updated to clarify or further describe the category.
- **CategoryStatus**: Indicates whether the category is active or inactive. Edits are only permitted if the category is active, in line with the acceptance criteria.
- **CreatedOn/CreatedBy**: Audit fields that record when and by whom the category was created (not changed during edit). 
- **UpdatedOn/UpdatedBy**: Fields to record who performed the last edit and when, supporting traceability and compliance with good practices.

This detailed modeling ensures that all business rules for editing a category-such as name uniqueness, edit permissions, and auditability-can be enforced at the domain level. It also supports future extensibility, such as adding further audit fields or additional validation logic, without requiring changes to the core structure.

### 2.2. Other Remarks

- All validations (uniqueness, active status, input constraints) should be enforced within the aggregate to maintain domain integrity.
