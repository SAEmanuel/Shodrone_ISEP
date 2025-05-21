# US213 - List users

## 2. Analysis

### 2.1. Relevant Domain Model Excerpt 

![Domain Model](svg/us213-domain-model.svg)

### 2.2. Other Remarks

* Access Control: Only users with the Admin role are authorized to access the user listing feature. This must be enforced both at the UI and the service layers using role-based access control (RBAC).
* Output Structure: The listing must include the following fields for each user:
- Name
- Email
- Status (Active/Inactive)
* User Status Display: Each user's status must be clearly shown in the interface, to help administrators easily identify inactive accounts.
* Persistence Abstraction: The source of data may vary depending on the configured persistence mode:
* In in-memory mode, users are retrieved from an internal collection.
* In database mode, users are fetched through the repository (PA/Hibernate).