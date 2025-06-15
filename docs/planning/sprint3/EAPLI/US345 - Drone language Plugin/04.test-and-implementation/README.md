# US345 - Drone Language Plugin

## 4. Tests

This section documents **unit tests**, **integration tests**, and **validation procedures** for the functionality of registering and using drone language plugins.

### Test Cases

1. **Unit Test: Register Valid Plugin**

    * **Description**: Tests that a valid plugin (implements `DroneLangPlugin`) can be registered.
    * **Scenario**: A `PythonPlugin` is instantiated and passed to `PluginManager.registerPlugin`.
    * **Expected Outcome**: Plugin is stored and retrievable by `getPlugin("Python")`.

2. **Unit Test: Register Plugin with Duplicate Language**

    * **Description**: Tests behavior when a plugin is registered for a language that already has a plugin.
    * **Scenario**: Two plugins are registered with `languageName = "Python"`.
    * **Expected Outcome**: The second registration overwrites the first or is rejected, depending on design (log warning or throw exception).

3. **Unit Test: Validate Valid Code with Correct Plugin**

    * **Description**: Tests that valid code passes validation using the correct plugin.
    * **Scenario**: A valid Python drone program is validated using the `PythonPlugin`.
    * **Expected Outcome**: `validateCode(code)` returns `true`.

4. **Unit Test: Validate Invalid Code with Correct Plugin**

    * **Description**: Tests that syntactically incorrect code fails validation.
    * **Scenario**: A Python program with missing `def` statement is passed to `validateCode`.
    * **Expected Outcome**: `validateCode` returns `false` and `getErrorMessage()` provides a reason.

5. **Unit Test: Validate Code with Missing Plugin**

    * **Description**: Tests system behavior when no plugin exists for the drone’s language.
    * **Scenario**: A drone uses the language `"Clojure"`, but no plugin is registered for it.
    * **Expected Outcome**: A `PluginNotFoundException` or `IllegalArgumentException` is thrown.

6. **Integration Test: Full Flow from Program to Validation**

    * **Description**: Tests the full flow: a drone program is created, plugin is retrieved, and code is validated.
    * **Scenario**: `DroneProgramValidator.validate(program)` is invoked.
    * **Expected Outcome**: The correct plugin is used and validation result is returned.

---

## 5. Construction (Implementation)

This section describes the implementation logic for registering and applying drone language plugins.

* **Interface**: `DroneLangPlugin`

    * Defines the contract for plugins with methods:

        * `getLanguageName()`
        * `validateCode(String code)`
        * `getErrorMessage()`

* **Concrete Plugins**:

    * `PythonPlugin`, `LuaPlugin`, etc., implement the interface for specific languages.

* **Manager Class**: `PluginManager`

    * Maintains a `Map<String, DroneLangPlugin>` to register and retrieve plugins by language.
    * Methods:

        * `registerPlugin(DroneLangPlugin plugin)`
        * `getPlugin(String language)`

* **Validation Orchestrator**: `DroneProgramValidator`

    * Given a `DroneProgram`, determines the `DroneModel` and language, fetches the plugin, and validates the code.

* **Usage Flow**:

    1. Drone Tech registers plugins via system setup or a registration function.
    2. When a drone program is created/generated, the validator resolves the plugin via language.
    3. Validation is executed and either passes or fails with feedback.

* **Patterns Used**:

    * **Strategy Pattern**: Each plugin represents a strategy for validating a language.
    * **Service Locator Pattern**: `PluginManager` provides access to registered plugins.
    * **Factory Pattern (optional future use)**: Dynamic loading of plugins.
    * **Application Service Pattern**: Coordination through `DroneProgramValidator`.

---

## 6. Integration and Demo

### Integration Points

* **Drone Model**: Each `DroneModel` defines the programming language it uses (`languageName`).
* **Drone Program**: Validation is triggered before a program is simulated or deployed.
* **Authentication**: Only authenticated `DroneTech` users (via `AuthFacade`) can register plugins or initiate validation (US210).
* **Plugin Registry**: Centralized component where all plugins are registered and retrieved.

### Demo Walkthrough

1. The Drone Tech logs into the system.
2. Through the setup or admin interface, the Drone Tech registers the `PythonPlugin` using `PluginManager.registerPlugin(...)`.
3. A drone program is created for a drone model that uses the `"Python"` language.
4. When the user initiates a simulation, the system invokes `DroneProgramValidator.validate(program)`.
5. The system:

    * Retrieves the language from the drone model.
    * Resolves the plugin via `PluginManager`.
    * Validates the code and either proceeds or reports validation errors.

---

## 7. Observations

### Known Limitations

* **Dynamic Plugin Loading**: Currently, plugins must be registered manually or at system startup; dynamic discovery (e.g., from JAR files) is not supported.
* **No Semantic Validation**: Plugins currently validate only syntax; semantic rules (e.g., capability constraints) are not enforced.
* **No UI for Plugin Management**: Plugin registration is backend-only; no interactive menu or console interface exists for adding/removing plugins.

### Design Decisions

* The system uses a **centralized plugin registry** (`PluginManager`) to simplify access and avoid tight coupling.
* Plugins must declare their language name explicitly and handle their own error reporting (`getErrorMessage()`).
* The validation is **strict**: missing plugins cause failure; there is no fallback behavior.

### Open Questions

* Should plugins be auto-discovered from a specific folder at runtime (e.g., via Java Reflection or SPI)?
* Should we support **versioning** of plugins or languages (e.g., Python 2 vs Python 3)?
* Should drone code validation support **inline linting** or just pass/fail validation?
* Should plugins be persisted or re-registered on each system boot?
