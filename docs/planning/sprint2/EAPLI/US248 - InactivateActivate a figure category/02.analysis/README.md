# US248 - Inactivate/Activate a figure category

## 2. Analysis

### 2.1. Relevant Domain Model Excerpt

![Domain Model](svg/us248-domain-model.svg)

The current domain model already includes all the necessary attributes to support the inactivation and activation of figure categories. The `Category` aggregate contains a `CategoryStatus` field to represent whether a category is active or inactive, as well as audit fields (`UpdatedOn`, `UpdatedBy`) to track status changes. No structural changes to the domain model were required for this user story.

This model ensures that all requirements for status management, auditability, and future extensibility are met.

### 2.2. Other Remarks

No changes to the domain model were necessary for US248, as all relevant attributes and relationships were already present.
