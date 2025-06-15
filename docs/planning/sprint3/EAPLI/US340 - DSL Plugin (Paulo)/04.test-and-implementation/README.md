# US340 - DSL Plugin

## 1. Overview

The **DSL Plugin** feature enables support for managing multiple DSL (Domain-Specific Language) versions within a `Figure`. Each version is associated with metadata, including the targeted drone model and corresponding DSL instructions. This enhancement allows flexible, version-controlled storage and execution of figure definitions.

---

## 2. Tests

The test suite for **US340** ensures correct behavior when interacting with DSL metadata in figures, including version registration, access control, and immutability guarantees.

---

### 2.1. Domain Entity: `FigureTest`

Focused unit tests verify DSL-related behaviors:

- Ensures a `Figure` can be created with valid DSL metadata.
- Validates addition of new DSL versions.
- Confirms immutability of returned DSL metadata.
- Tests correct association between version tags, drone models, and DSL instructions.

```java
@Test
void testCreationWithValidValues() {
    assertEquals(Set.of("1.0"), figure.dslVersions().keySet());
    assertEquals("TestDrone", figure.dslVersions().get("1.0").getDroneModel());
    assertEquals(metadata.getDslLines(), figure.dslVersions().get("1.0").getDslLines());
}

@Test
void testAddDslVersion() {
    figure.addDslVersion("2.0", "DroneX", List.of("Point A = (1,2,3);"));
    assertTrue(figure.dslVersions().containsKey("2.0"));
    assertEquals("DroneX", figure.dslVersions().get("2.0").getDroneModel());
    assertEquals(List.of("Point A = (1,2,3);"), figure.dslVersions().get("2.0").getDslLines());
}

@Test
void testDslVersionsAreUnmodifiable() {
    Map<String, DslMetadata> dsl = figure.dslVersions();
    assertThrows(UnsupportedOperationException.class, () -> dsl.put("2.0", metadata));
}
```

## 3. Construction (Implementation)

### 3.1. DSL Metadata Management in `Figure`

The `Figure` class holds a map of DSL version strings to `DslMetadata` objects, supporting operations to access and extend the versioned metadata.

```java
private final Map<String, DslMetadata> dslVersions;

public Map<String, DslMetadata> dslVersions() {
    return Collections.unmodifiableMap(dslVersions);
}

public void addDslVersion(String version, String droneModel, List<String> dslLines) {
    this.dslVersions.put(version, new DslMetadata(droneModel, dslLines));
}
```

### 3.2. `DslMetadata` Class (Structure)

Each DSL version is described by:

- `droneModel`: The type of drone the instructions are intended for.
- `dslLines`: A list of string instructions that compose the DSL logic.

```java
public class DslMetadata {
    private final String droneModel;
    private final List<String> dslLines;

    public DslMetadata(String droneModel, List<String> dslLines) {
        this.droneModel = droneModel;
        this.dslLines = List.copyOf(dslLines); // Immutable
    }

    public String getDroneModel() {
        return droneModel;
    }

    public List<String> getDslLines() {
        return dslLines;
    }
}
```

## 4. Summary of Classes Involved

- **Domain Entity**: `Figure` (enhanced with DSL versions support)
- **Value Object**: `DslMetadata` (holds DSL version details)
- **Supporting Entities**: `FigureCategory`, `Costumer`
- **Domain Value Objects**: `Name`, `Description`, `Email`, `PhoneNumber`, `NIF`, `Address`
- **Enums**: `FigureAvailability`, `FigureStatus`

---

## 5. Integration and Usage

- Figures can now hold multiple DSL versions, each identified by a version string (e.g., `"1.0"`, `"2.0"`).
- Each DSL version encapsulates the drone model and a list of DSL instructions.
- The DSL metadata is immutable and accessible through the figure entity.
- Figures can be updated to add new DSL versions dynamically via the `addDslVersion` method.
- DSL versions are exposed as an unmodifiable map to prevent external mutation.
- This design supports evolving DSL instructions tied to figures for different drone models and versions.
- Typical use involves the UI or service layer calling domain methods to manage DSL data within figures.

---

## 6. Observations

- The DSL plugin feature integrates DSL logic into the domain model without impacting existing figure attributes.
- The immutability of `DslMetadata` ensures thread safety and consistency of DSL instructions.
- Tests cover creation, updating, and immutability aspects of DSL metadata within figures.
- The approach supports extensibility for future DSL enhancements or integration with drone control systems.
- Separation of concerns is maintained by encapsulating DSL data in its own value object rather than cluttering the figure entity.
