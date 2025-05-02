# US210 - Authentication and authorization

## 1. Requirements Engineering

### 1.1. User Story Description

As a Project Manager, I want the system to support and apply authentication and authorization for all its users and functionalities, so that system access is secured and role-specific operations are properly controlled.

### 1.2. Customer Specifications and Clarifications

- Authentication ensures that only users with valid credentials can access the system.

- Authorization controls what authenticated users can do, based on their role (Admin, CRM Manager, CRM Collaborator, etc.).

- Users are identified by a valid email (Shodrone domain: @showdrone.com), name, and phone number.

- Only one role per user for now (multiple roles may be supported in the future).

**Clarifications**

Q: What happens if the user tries to log in with an invalid email or password?
A: Access is denied, and the system displays: "Invalid UserId and/or Password"

Q: Is role assignment static?
A: Yes, during Sprint 2. The user has a single role, defined at registration.

### 1.3. Acceptance Criteria

* AC1: Users must log in with their email and password.
* AC2: Only authenticated users can access any system functionality.
* AC3: Each functionality is only available to users with appropriate roles (only Admins can register new users).
* AC4: The system must reject login attempts with incorrect credentials.
* AC5: Upon successful login, the session must persist until logout or timeout.
* AC6: The system must support at least these roles: Admin, CRM Manager, CRM Collaborator, Show Designer, Drone Tech, and Customer.
* AC7: User credentials and roles must be persisted in an RDBMS (NFR07).

### 1.4. Found out Dependencies

* NFR08: Enforces the use of authentication and authorization across all user stories.
* US211–US213: Deal with user registration, listing, enabling/disabling.
* US220, US230, etc.: Depend on user roles for access (only CRM Collaborators can register a ShowRequest).

### 1.5 Input and Output Data

**Input Data:**

* Email and password (for login)
* User role (assigned at registration)

**Output Data:**

* Access granted or denied
* Role-specific access to system functionalities

### 1.6. System Sequence Diagram (SSD)

![System Sequence Diagram](svg/us210-system-sequence-diagram.svg)

### 1.7 Other Relevant Remarks

None