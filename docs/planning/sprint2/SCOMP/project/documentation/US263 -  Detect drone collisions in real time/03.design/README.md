# US263 - Detect Drone Collisions in Real Time

## 3. Design

### 3.1. Design Overview

The design for detecting drone collisions in real time is centered around a modular and efficient system that processes drone position data, detects potential collisions, and notifies the operator accordingly. The solution leverages a time-step-based approach, where the system evaluates drone positions at discrete timestamps to identify collisions based on a safety radius calculation. The design is implemented in a C-based environment, utilizing process-based concurrency for drone simulation and signal handling for collision response.

The architecture consists of the following key components:
- **Data Layer**: Manages the `Radar` data structure, which aggregates drone information, positions, and timestamps from the `historyOfRadar` array.
- **Collision Detection Module**: Implements the `collisionDetection` function to calculate distances between drones and detect collisions when the distance is less than the combined safety radii.
- **Alert and Logging System**: Handles visual alerts via terminal output and logs collision events in the `Collision_Stamp` structure for later analysis.
- **Drone Control Interface**: Uses UNIX signals (`SIGUSR1` and `SIGCONT`) to pause and resume drones involved in collisions, ensuring operator intervention is possible.

The system is designed to be extensible, allowing for future enhancements such as velocity-based collision prediction or integration with a graphical user interface (GUI). As of 11:27 AM WEST on Friday, May 16, 2025, the design will be validated during today’s scheduled drone shows to ensure real-time performance under operational conditions. The design assumes a single-threaded execution model for simplicity, with potential multi-threading considerations for scalability in future iterations.

### 3.2. Sequence Diagrams

#### 3.2.1. Collision Detection Process
The following PlantUML diagram illustrates the sequence of actions when the system detects a collision:

![System Sequence Diagram](svg/us263-sequence-diagram-Collision_Detection_Sequence_Diagram___US263.svg)

##### Explanation
- **Actors and Participants**:
    - `Drone Operator`: Initiates and terminates the monitoring process.
    - `System`: The main orchestrator that delegates tasks.
    - `CollisionDetector`: Represents the `collisionDetection` function, responsible for distance calculations.
    - `Logger`: Handles logging of collision events into `Collision_Stamp`.
    - `DroneProcess`: Represents the drone processes that receive signals.
- **Sequence**:
    - The operator starts monitoring, triggering a loop over timestamps.
    - The `CollisionDetector` processes positions, calculates distances, and detects collisions.
    - If a collision is detected, the system logs the event, sends `SIGUSR1` to pause the drones, waits for operator action, and resumes with `SIGCONT`.
    - If no collision occurs, the system updates the operator with normal status.
- **Notes**: Includes the current date and key implementation details.

#### 3.2.2. Initialization and Cleanup
The following PlantUML diagram illustrates the initialization and cleanup process:

![System Sequence Diagram](svg/us263-ssd-Initialization_and_Cleanup_Sequence_Diagram___US263.svg)

##### Explanation
- **Actors and Participants**:
    - `Drone Operator`: Starts and stops the monitoring.
    - `System`: Manages the overall flow.
    - `MemoryManager`: Handles dynamic memory allocation and deallocation.
    - `DroneProcess`: Represents the spawned drone processes.
- **Sequence**:
    - The operator starts monitoring, triggering memory allocation for `historyOfRadar` and `stamps`, and spawning drone processes.
    - The collision detection loop runs (omitted for brevity, referencing the previous diagram).
    - Upon stopping, the system sends `SIGCONT`, waits for drone completion, and frees allocated memory.
- **Notes**: Highlights memory management and cleanup details.

### 3.3. Design Patterns (if any)

- **Observer Pattern (Implicit)**:
    - The design implicitly follows the Observer pattern, where the `System` acts as the subject that monitors drone positions and notifies the `Drone Operator` (observer) with collision alerts or position updates. This pattern supports the real-time notification requirement, although it is not fully formalized with a dedicated observer interface in the current C implementation.
- **Singleton Pattern (Potential)**:
    - The global variable `number_of_collision` acts as a shared state across the system, resembling a Singleton pattern. A more robust implementation could encapsulate this counter in a singleton object to ensure thread safety if the system evolves to multi-threaded execution.
- **Strategy Pattern (Future Consideration)**:
    - The collision detection logic could be designed using the Strategy pattern, allowing different algorithms (e.g., distance-based vs. velocity-based) to be swapped at runtime. Currently, it uses a fixed distance-based approach, but this pattern could be applied for future enhancements like trajectory prediction.

