# US212 - Disable/enable users

## 1. Requirements Engineering

### 1.1. User Story Description

As an Administrator, I want to be able to disable or enable users of the backoffice, so that I can manage who has access to the system at any given time.

### 1.2. Customer Specifications and Clarifications

- A user can either be active or inactive.
- An inactive user cannot access the system (login attempts must be rejected).
- The active/inactive status must be persisted in the system's database.
- Only users with the "Admin" role can perform this action.
- An Admin cannot disable themselves.
- Users can be re-enabled at any time.
- Physical deletion of users is not allowed; only logical deactivation is supported.

**Clarifications**

Q: Can inactive users be listed?
A: Yes, their status must be clearly shown (see US213).

Q: Can users be disabled automatically?
A: No. This must be a manual action performed by an Admin.

### 1.3. Acceptance Criteria

* AC1: Only Admins can enable/disable users.
* AC2: The system must allow toggling a user's status between active and inactive.
* AC3: The user's status must be stored in the relational database.
* AC4: Inactive users must not be allowed to log in.
* AC5: The system must provide clear feedback (success/error messages) to the Admin.

### 1.4. Found out Dependencies

* Depends on US210 – Authentication and authorization (login must honor user status).
* Related to US211 – User registration.
* Related to US213 – User listing (must reflect status).
* NFR08: Role-based access control must be enforced.

### 1.5 Input and Output Data

**Input Data:**

* User ID to be enabled/disabled.
* Action: enable or disable.

**Output Data:**

* Success message with updated user status.
* Error message if the action is not allowed.

### 1.6. System Sequence Diagram (SSD)

![System Sequence Diagram](svg/us212-system-sequence-diagram.svg)

### 1.7 Other Relevant Remarks

None
