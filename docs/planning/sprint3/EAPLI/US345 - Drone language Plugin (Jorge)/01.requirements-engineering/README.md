# US345 - Drone Language Plugin

## 1. Requirements Engineering

### 1.1. User Story Description

**As a Drone Tech**, I want to deploy and configure a plugin to be used by the system to analyze/validate a drone program. This functionality ensures that each drone model's code is properly validated according to its specific programming language, improving reliability and safety before the code is used in simulation or execution.

### 1.2. Customer Specifications and Clarifications

The following specifications are based on the official project document (Section 4.3, Page 19):

* The system must allow a Drone Tech to **register and configure plugins** that validate drone code.
* **Each drone programming language** must be supported by a dedicated plugin.
* A plugin is responsible for validating the **syntax and structure** of drone programs.
* The system must use the correct plugin based on the programming language assigned to the drone model.
* A plugin must clearly indicate the programming language it supports and provide meaningful error messages in case of validation failure.

**Clarifications**:

* **Q: Where are plugins registered?**
  A: Plugins can be registered either via an internal configuration interface or by loading implementations dynamically. They will be stored in a plugin registry (e.g., a `Map<String, DroneLangPlugin>`).
* **Q: What is the core responsibility of a plugin?**
  A: To validate the drone program syntax and expose language identification and error reporting functionality.
* **Q: Does the system support multiple languages simultaneously?**
  A: Yes, the plugin architecture must support multiple languages, each with its own plugin implementation.
* **Q: Is validation mandatory before simulation?**
  A: Yes, no figure or show simulation can proceed unless the code for each drone passes validation using the appropriate plugin.

### 1.3. Acceptance Criteria

* **AC1**: The user must be authenticated and have Drone Tech privileges (see NFR08).
* **AC2**: The system must support registering plugins for specific programming languages.
* **AC3**: Each plugin must implement a common interface (e.g., `DroneLangPlugin`) with methods such as `validateCode(String code)`, `getLanguageName()`, and `getErrorMessage()`.
* **AC4**: The system must allow each drone model to be assigned a programming language.
* **AC5**: When validating code, the system must identify the language and invoke the appropriate plugin.
* **AC6**: If validation fails, the plugin must return a detailed error message to the user, and the operation must be aborted.
* **AC7**: The system must support replacing/updating plugins, provided the interface contract is respected.

### 1.4. Found Out Dependencies

* **US210**: Required for user authentication and role-based access control (Drone Tech authorization).
* **US253**: Required for configuring the language associated with each drone model.
* **US344**: The drone program is generated from the DSL and must be validated using a registered plugin.
* **US346**: The plugin created in US345 is directly used to validate the code of individual drones.
* **NFR08**: Ensures proper role-based access to register or use drone language plugins.

### 1.5. Input and Output Data

**Input Data**:

* Plugin class or file implementing the `DroneLangPlugin` interface.
* Programming language identifier (e.g., `"Python"`, `"Lua"`, `"DroneScript"`).
* Drone program code (as a `String`) to be validated.

**Output Data**:

* Validation result: `true` (valid) or `false` (invalid).
* Error message if validation fails.
* Confirmation that the plugin was successfully registered.
* Validation log messages (optional for debugging purposes).

### 1.6. System Sequence Diagram (SSD)

![System Sequence Diagram](svg/us345-system-sequence-diagram.svg)

### 1.7. Other Relevant Remarks

* The `DroneLangPlugin` interface should be designed to be extensible, with clearly defined contracts for validation and metadata retrieval.
* The system can support automatic loading of plugins from a predefined folder (e.g., `plugins/`) using reflection or a plugin manager (e.g., Java SPI).
* Plugin removal and updates could be addressed in a future user story (e.g., US349 – Manage Plugins).
* Exception handling must be robust; for example, plugins should throw domain-specific exceptions like `InvalidDroneCodeException`.
* Plugins should be tested using unit tests (JUnit or equivalent), covering both valid and invalid code scenarios.
* In future iterations, plugins could also validate semantics or constraints beyond syntax (e.g., drone capabilities, battery constraints, execution limits).
