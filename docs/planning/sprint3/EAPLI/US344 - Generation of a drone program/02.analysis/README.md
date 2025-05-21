# US212 - Disable/enable users

## 2. Analysis

### 2.1. Relevant Domain Model Excerpt 

![Domain Model](svg/us212-domain-model.svg)

### 2.2. Other Remarks

* Access Control: Only users with the Admin role are allowed to disable or enable other users. This must be enforced both at the UI and service layer using role-based access control (RBAC).
* Status Attribute: Each user has an active (or enabled) status field stored in the database. This boolean attribute determines whether the user is allowed to authenticate into the system. By default, users are created as active (US211).
* Authentication Integration: The authentication mechanism (US210) must respect the user’s status. Inactive users must be prevented from logging into the system, even with valid credentials.
* Error Handling: The system must return clear error messages.
* User Feedback: Upon successful toggling of a user’s status, a confirmation message must be displayed. In the case of errors, the system must inform the Admin of the specific reason.
