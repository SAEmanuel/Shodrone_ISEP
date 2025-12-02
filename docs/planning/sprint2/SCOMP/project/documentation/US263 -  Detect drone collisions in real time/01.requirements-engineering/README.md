# US263 - Detect Drone Collisions in Real Time

## 1. Requirements Engineering

### 1.1. User Story Description

As a Drone Operator, I want the system to detect potential drone collisions in real time during a show, so that I can take immediate corrective actions to prevent accidents and ensure the safety of the operation. The system will continuously monitor the positions of all drones involved in a show, calculate distances between them, and issue alerts if a collision is detected based on predefined criteria. This ensures that safety is maintained throughout the show, allowing the operator to respond promptly to any risks.

### 1.2. Customer Specifications and Clarifications

#### Customer Specifications
- **Shodrone Project Requirements (Shodrone_Specs_v1.2, Section 4.3)**:
    - "The system must monitor the positions of drones during a show and detect collisions in real time."
    - "A collision is considered to occur when the distance between two drones is less than the sum of their safety radii, which includes their largest dimension scaled down and an additional safety margin of 0.5 meters."
    - "Upon detecting a collision, the system must notify the operator immediately and log the event for future analysis."

#### Clarifications Based on Implementation
- The `collisionDetection` method defines a collision as occurring when the distance between two drones is less than the sum of their safety radii, calculated as `(droneA.droneInformation.biggestDimension / 200.0f) + COLLISION_RADIUS_EXTRA` (where `COLLISION_RADIUS_EXTRA` is 0.5m).
- When a collision is detected, the system:
    - Outputs a message to the terminal with the IDs of the drones involved and the timestamp (e.g., "💥 Collision detected between drone [1] and [2] at time - 5").
    - Sends a `SIGUSR1` signal to the involved drones to pause their operations.
    - Logs the collision event in a `Collision_Stamp` structure, capturing the drone IDs and timestamp.
- Drones that are marked as `terminated` are excluded from collision detection to avoid unnecessary processing.

#### Customer Forum Clarifications (Hypothetical, Based on Context)
- *Q: Should the system automatically stop the show if a collision is detected?*
    - *A: No, the system should only alert the operator and pause the involved drones. The operator decides the next steps. (Customer Rep, Hypothetical Response)*
- *Q: How should the system handle drones that stop reporting their positions?*
    - *A: Drones that stop reporting should be marked as terminated and excluded from collision detection. (Customer Rep, Hypothetical Response)*

### 1.3. Acceptance Criteria

- **AC1**: The system must detect a collision when the distance between any two drones is less than the sum of their safety radii, where the safety radius of a drone is calculated as `(drone.biggestDimension / 200.0f) + 0.5` meters.
- **AC2**: Upon detecting a collision, the system must display a visual alert in the terminal, including the IDs of the drones involved and the timestamp of the collision (e.g., "💥 Collision detected between drone [1] and [2] at time - 5").
- **AC3**: Each collision event must be logged in a `Collision_Stamp` structure, storing the IDs of the two drones involved (`id_drone1`, `id_drone2`) and the timestamp of the collision (`collision_time`).
- **AC4**: Drones involved in a collision must be sent a `SIGUSR1` signal to pause their operations, and the system must wait for them to stop before resuming their processes with a `SIGCONT` signal.
- **AC5**: The system must exclude drones marked as `terminated` from collision detection calculations to optimize performance.
- **AC6**: The system must support the simultaneous monitoring of multiple drones, iterating over all possible pairs of active drones in each timestamp.
- **AC7**: The system must maintain a global counter (`number_of_collision`) to track the total number of collisions detected in a given timestamp, resetting it after each detection cycle.
- **AC8**: The system must dynamically manage a list of drones that have collided (`drones_that_collied`) to ensure proper handling of pause and resume operations without duplicates.

### 1.4. Found out Dependencies

- **US220 - Monitor Show Execution**:
    - Dependency: US263 relies on US220 to provide real-time position data for all drones during a show. The `historyOfRadar` array, which contains position data for each drone at each timestamp, is assumed to be populated by the monitoring system established in US220.
- **US230 - Register Show Request**:
    - Dependency: US263 requires the context of a show, including the number of drones involved and their initial configurations (e.g., `biggestDimension`), which are provided by the show request registered in US230.
- **US245 - Drone Telemetry** (Hypothetical Dependency):
    - Dependency: The position data (x, y, z coordinates) and drone metadata (e.g., `biggestDimension`) are assumed to be provided by a telemetry system, which could be part of US245.

### 1.5. Input and Output Data

#### Input Data
- **Position Data**:
    - Source: `Radar historyOfRadar[numberOfDrones][total_ticks]`
    - Description: A 2D array containing the position (x, y, z) of each drone at each timestamp. Each `Radar` entry includes:
        - `position`: A `Position` struct with `x`, `y`, `z` coordinates and the drone's `pid`.
        - `droneInformation`: Contains the drone's `id` and `biggestDimension`.
        - `terminated`: A flag indicating if the drone is no longer active.
- **Timestamp**:
    - Source: `int timeStamp`
    - Description: The current time unit in the simulation, used to index the `historyOfRadar` array and mark collision events.
- **Collision Storage**:
    - Source: `Collision_Stamp **stamps`, `int *stamps_capacity`, `int *stamps_count`
    - Description: Pointers to a dynamic array of `Collision_Stamp` structs, its capacity, and the current number of entries, used to log collision events.

#### Output Data
- **Collision Alert**:
    - Format: Terminal message (e.g., "💥 Collision detected between drone [1] and [2] at time - 5").
    - Description: A visual notification printed to `STDOUT` when a collision is detected, including the IDs of the drones and the timestamp.
- **Collision Log**:
    - Format: `Collision_Stamp` entry.
    - Description: Each collision event is logged with:
        - `id_drone1`: ID of the first drone involved.
        - `id_drone2`: ID of the second drone involved.
        - `collision_time`: The timestamp when the collision occurred.
- **Drone Signals**:
    - Format: `SIGUSR1` and `SIGCONT` signals.
    - Description: Drones involved in a collision receive a `SIGUSR1` signal to pause, and later a `SIGCONT` signal to resume after the operator's intervention.
- **Collision Count**:
    - Format: Integer return value.
    - Description: The `collisionDetection` function returns the number of collisions detected in the current timestamp.

### 1.6. System Sequence Diagram (SSD)

![System Sequence Diagram](svg/us263-system-sequence-diagram.svg)

### 1.7. Other Relevant Remarks

#### Implementation Details Based on `collisionDetection`
- **Collision Detection Logic**:
    - The `collisionDetection` function iterates over all pairs of drones (`i`, `j`) in the `historyOfRadar` array at the given `timeStamp`.
    - For each drone pair:
        - Skips drones marked as `terminated` to optimize processing.
        - Calculates the safety radius of each drone as `(droneInformation.biggestDimension / 200.0f) + COLLISION_RADIUS_EXTRA`, where `COLLISION_RADIUS_EXTRA` is 0.5 meters.
        - Uses `calculateDistance` to compute the Euclidean distance between the drones' positions (`sqrt(dx^2 + dy^2 + dz^2)`).
        - A collision is detected if the distance is less than the sum of the safety radii of the two drones.
- **Collision Handling**:
    - On collision detection:
        - Increments the global `number_of_collision` counter.
        - Logs the event using `fill_stamp_moment`, which dynamically allocates memory for the `Collision_Stamp` array as needed.
        - Prints a message to the terminal with the drone IDs and timestamp.
        - Sends a `SIGUSR1` signal to both drones to pause their operations.
        - Adds the drones' PIDs to a dynamic list (`drones_that_collied`) to manage their pause and resume process.
    - After processing all pairs, if collisions occurred:
        - Pauses each drone in `drones_that_collied` using `waitpid` with `WUNTRACED`.
        - Resumes them with a `SIGCONT` signal and waits for completion.
        - Frees the `drones_that_collied` list.
- **Helper Functions**:
    - `calculateDistance`: Computes the 3D Euclidean distance between two positions.
    - `add_drone_to_list`: Adds a drone's PID to the `drones_that_collied` list if not already present, dynamically resizing the list as needed.
    - `exist_in_array`: Checks if a drone's PID already exists in the `drones_that_collied` list.
    - `fill_stamp_moment`: Logs a collision event in the `Collision_Stamp` array, resizing it if necessary.

#### Operational Context
- As of the current date and time (11:09 AM WEST on Friday, May 16, 2025), this feature should be tested during today’s scheduled drone shows to validate its real-time performance.
- The system assumes that position data is updated at each timestamp, but the frequency of updates (e.g., every 500ms) is not explicitly controlled within the `collisionDetection` method. This should be managed by the calling code (e.g., in `main.c`).
- The `SIGUSR1` signal handling assumes that drones are implemented to pause their operations upon receiving this signal, as defined in the `drone.c` context.

#### Potential Limitations
- The current implementation does not consider velocity or trajectory projection, meaning it only detects collisions based on current positions, not future risks.
- There is no explicit handling for drones with unavailable position data beyond marking them as `terminated`. A more robust solution might introduce an "untracked" state.
- The visual alert is limited to a terminal message; additional alert mechanisms (e.g., audible beeps or GUI notifications) could enhance operator awareness.

#### Future Considerations
- Adding velocity-based collision prediction could improve safety by detecting potential collisions before they occur.
- Implementing a more sophisticated logging system (e.g., writing to a file in real time) could aid in post-show analysis.
- Introducing a mechanism to handle temporary data loss (e.g., GPS dropouts) could make the system more resilient.
