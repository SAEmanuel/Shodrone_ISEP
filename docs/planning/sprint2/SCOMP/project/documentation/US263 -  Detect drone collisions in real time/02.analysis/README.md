# US263 - Detect Drone Collisions in Real Time

## 2. Analysis

### 2.1. Relevant Domain Model Excerpt

The domain model for detecting drone collisions in real time involves entities that capture drone information, their positions, radar data, and collision events. Below is the PlantUML representation of the relevant domain model excerpt:

![Domain Model](svg/us263-domain-model-Domain_Model_Excerpt___US263_Detect_Drone_Collisions_in_Real_Time.svg)


#### Explanation of the Domain Model
- **Drone**:
    - Represents a drone in the Shodrune system.
    - Attributes:
        - `id`: Unique identifier for the drone.
        - `biggestDimension`: The largest physical dimension of the drone (in some unit, e.g., centimeters), used to calculate its safety radius.
    - Methods:
        - `getId()`: Returns the drone’s ID.
        - `getBiggestDimension()`: Returns the drone’s largest dimension.
- **Position**:
    - Captures the 3D coordinates of a drone at a specific point in time.
    - Attributes:
        - `x`, `y`, `z`: Coordinates in a 3D space.
        - `pid`: The process ID of the drone, used for signaling (e.g., `SIGUSR1`).
- **Radar**:
    - Aggregates data about a drone at a specific timestamp, used for collision detection.
    - Attributes:
        - `droneInformation`: Reference to the `Drone` entity.
        - `position`: The drone’s position at the timestamp.
        - `timeStamp`: The specific time unit in the simulation.
        - `terminated`: A flag indicating whether the drone is no longer active.
    - Relationship: A `Drone` can have multiple `Radar` entries (one-to-many), representing its state over time.
- **Collision_Stamp**:
    - Logs a collision event between two drones.
    - Attributes:
        - `id_drone1`, `id_drone2`: IDs of the two drones involved in the collision.
        - `collision_time`: The timestamp when the collision occurred.
    - Relationship: A `Collision_Stamp` is associated with two `Radar` entries (the states of the drones at the time of collision).

### 2.2. Other Remarks

#### Assumptions
- **Position Data Availability**: It is assumed that the `historyOfRadar` array is populated with accurate position data for all drones at each timestamp, provided by an external telemetry system (e.g., via US245 - Drone Telemetry).
- **Collision Radius Calculation**: The safety radius of a drone is calculated as `(biggestDimension / 200.0f) + 0.5` meters, as defined in the `collisionDetection` method. This assumes that `biggestDimension` is in centimeters, scaled down to meters for calculation.
- **Termination Handling**: Drones marked as `terminated` in the `Radar` entity are assumed to be out of operation (e.g., due to a previous collision or manual shutdown) and are excluded from collision detection.
- **Single Timestamp Processing**: The system processes collisions for a single timestamp at a time, as dictated by the `collisionDetection` method’s signature (`int timeStamp`).

#### Potential Challenges
- **Performance with Many Drones**:
    - The `collisionDetection` method iterates over all pairs of drones (`O(n^2)` complexity), which could lead to performance issues with a large number of drones (e.g., 50 or more). For example, with 50 drones, the system would need to perform 1225 pair-wise comparisons per timestamp.
    - As of 11:20 AM WEST on Friday, May 16, 2025, performance testing should be conducted during today’s scheduled drone shows to ensure the system can handle the expected load without delays.
- **Real-Time Constraints**:
    - The method does not explicitly control the frequency of position updates (e.g., every 500ms). This responsibility lies with the calling code (e.g., in `main.c`). If updates are too frequent or too slow, collision detection accuracy could be affected.
- **Signal Handling**:
    - The system sends `SIGUSR1` signals to pause drones involved in a collision. If the signal handler in the drone process (`drone.c`) is not properly implemented, this could lead to unexpected behavior (e.g., drones not pausing correctly).
- **Memory Management**:
    - The dynamic allocation of the `drones_that_collied` list and the `Collision_Stamp` array (`fill_stamp_moment`) could lead to memory leaks if not handled properly, especially in long-running shows with frequent collisions.

#### Implementation Considerations
- **Collision Detection**:
    - The `collisionDetection` method calculates the Euclidean distance between drones using `calculateDistance` and compares it against the combined safety radii. This is a static check (based on current positions) and does not account for future trajectories or velocities.
    - To improve accuracy, the system could be extended to calculate relative velocities and predict potential collisions within a short time window (e.g., 2 seconds).
- **Alert Mechanism**:
    - The current implementation outputs a visual alert to the terminal (`"💥 Collision detected..."`). For a real-time system, this should be enhanced with:
        - Audible alerts (e.g., a beep sound using `write(STDOUT_FILENO, "\a", 1)`).
        - Integration with a UI component to display alerts more prominently (e.g., a flashing red icon).
- **Logging**:
    - Collision events are logged in the `Collision_Stamp` structure, which is dynamically resized as needed. To ensure durability, these events should also be persisted to a file or database in real time, allowing for post-show analysis.
- **Error Handling**:
    - The system should handle cases where position data is unavailable (e.g., pipe read failures). Currently, drones are marked as `terminated` if no data is received, but a more explicit "untracked" state could be introduced to differentiate between intentional termination and data loss.
- **Scalability Optimization**:
    - To reduce the `O(n^2)` complexity, a spatial partitioning technique (e.g., a grid or octree) could be used to only compare drones that are in close proximity, reducing the number of distance calculations.

#### Security and Safety Considerations
- **Signal Safety**:
    - The use of `SIGUSR1` and `SIGCONT` signals to pause and resume drones must be carefully managed to avoid race conditions. The `drone.c` implementation should ensure that signal handlers are robust and do not interfere with other operations (e.g., position updates).
- **Data Integrity**:
    - The `historyOfRadar` array must be protected against concurrent modifications, especially if multiple threads or processes access it. Synchronization mechanisms (e.g., mutexes) may be needed if the system evolves to a multi-threaded architecture.
- **Operator Safety**:
    - Since the system does not automatically stop the show upon detecting a collision, the operator must be trained to respond quickly to alerts. A more automated response (e.g., emergency stop) could be considered for future iterations to enhance safety.

#### Future Enhancements
- **Trajectory Prediction**:
    - Incorporate velocity data into the `Radar` entity to predict potential collisions based on the drones’ trajectories, allowing for earlier warnings (e.g., "Collision risk detected in 2 seconds").
- **Advanced Alerting**:
    - Integrate with a graphical user interface (GUI) to display collision alerts more effectively, such as highlighting the involved drones on a 3D map of the show area.
- **Collision Avoidance**:
    - Extend the system to suggest corrective actions to the operator (e.g., "Adjust Drone 1’s trajectory by moving 1m to the left") based on the current positions and velocities.
- **Historical Analysis**:
    - Use the logged `Collision_Stamp` data to generate reports on collision patterns, helping to improve drone choreography and reduce risks in future shows.
