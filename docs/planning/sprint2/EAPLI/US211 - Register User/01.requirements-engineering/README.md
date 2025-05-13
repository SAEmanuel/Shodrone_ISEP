# US211 - Register users

## 1. Requirements Engineering

### 1.1. User Story Description

As an Administrator, I want to be able to register users of the backoffice, so that they can access the system with the correct role and credentials.
### 1.2. Customer Specifications and Clarifications

- Each user must have a unique email from the Shodrone domain (@showdrone.com), a name, and a phone number.

- The user must be assigned one of the available roles: Admin, CRM Manager, CRM Collaborator, Show Designer, Drone Tech, or Customer.

- Each user may only have one role (multiple roles may be supported in the future).

- The system must prevent registration of users with duplicate emails.

- A user can be registered manually via the backoffice by an Admin or automatically via a bootstrap process (initialization script or seed data).

**Clarifications**

Q: Can users be registered with emails from other domains?
A: No. Only valid Shodrone emails (ending in @showdrone.com) are accepted.

Q: Can a user be registered without a role?
A: No. Every user must have a role assigned at the time of registration.

### 1.3. Acceptance Criteria

* AC1: Admins can register users by providing name, email, phone number, and role.
* AC2: The system must validate that the email is in the correct domain (@showdrone.com).
* AC3: The system must reject duplicate emails.
* AC4: The user must be persisted in the system's RDBMS with their role and status set to active.
* AC5: The system must support user creation via a bootstrap script.
* AC6: Only Admins can access the user registration functionality via the backoffice.

### 1.4. Found out Dependencies

* Depends on US210 – Authentication and authorization (users must be able to log in after being registered).
* Related to US212 (enabling/disabling users) and US213 (listing users).
* NFR08: Role-based access control must be enforced.

### 1.5 Input and Output Data

**Input Data:**

* Name
* Email (must end in @showdrone.com)
* Phone number
* Role (predefined set)

**Output Data:**

* Success message and user ID if registration is successful
* Error message if email is invalid or already used

### 1.6. System Sequence Diagram (SSD)

![System Sequence Diagram](svg/us211-system-sequence-diagram.svg)

### 1.7 Other Relevant Remarks

None