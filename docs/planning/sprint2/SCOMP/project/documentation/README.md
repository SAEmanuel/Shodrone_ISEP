## 1. Diagram of solution components

--- 

## 2. Example of a script used by drones

### Script for 10 drones (example)

```
0 - 3x4x5
8 5 5
7 0 1
7 0 4
8 3 11
10 1 10
1 8 5
9 9 1
0 3 3
10 4 1
5 10 2
1 - 2x3x4
9 4 4
3 4 2
6 5 2
1 5 15
8 7 2
4 1 2
5 5 4
6 8 10
3 10 4
8 7 3
2 - 3x4x5
7 0 2
4 9 4
7 9 1
0 5 5
8 8 1
4 8 1
7 4 1
4 0 5
9 9 2
1 10 5
3 - 4x3x1
2 8 2
3 6 1
6 2 2
6 7 1
0 5 6
1 7 1
9 7 2
6 1 9
9 5 1
9 4 2
4 - 1x4x6
1 5 5
7 0 5
4 3 1
3 3 1
4 4 1
10 8 3
1 7 8
9 0 4
10 7 2
4 2 4
5 - 6x1x4
1 0 4
3 8 5
6 9 2
8 7 5
0 4 4
6 6 4
8 4 2
2 1 3
4 2 4
6 5 4
6 - 5x7x5
6 1 4
2 6 5
10 10 2
0 9 3
8 3 8
1 2 5
7 6 1
2 5 2
1 1 10
5 2 5
7 - 8x2x1
10 8 9
5 7 5
1 10 5
8 3 1
8 5 5
8 1 1
4 6 1
5 8 1
6 7 4
8 1 4
8 - 3x4x2
1 7 1
8 2 1
10 9 5
4 4 2
7 1 1
10 6 2
3 9 1
2 3 2
3 5 3
8 3 3
9 - 2x3x4
3 4 2
6 9 1
3 4 2
6 0 4
8 2 1
1 3 1
3 0 5
10 1 4
1 1 2
6 8 3
```

### Explanation of the Script Format

#### **Header Line**

- Example: `0 - 3x4x5`
    - **`0`**: Drone ID (unique identifier for the drone).
    - **`3x4x5`**: Dimensions of the drone (width x height x depth in meters). Used for collision detection.

#### **Coordinate Lines**

- Each subsequent line represents the drone's position at a specific **tick** (time unit):
    - Example: `8 5 5`
        - **`8`**: X-coordinate (horizontal axis).
        - **`5`**: Y-coordinate (vertical axis).
        - **`5`**: Z-coordinate (depth axis).
    - Positions are listed in chronological order (tick 1, tick 2, etc.).

#### Key Notes:

1. **Ticks**:
    - The number of coordinate lines determines the total simulation ticks for that drone.
    - Drones with fewer positions than the simulation's total ticks will be marked as **inactive** after their last
      tick.
2. **Collision Detection**:
    - The drone's dimensions (e.g., `3x4x5`) are used to calculate its collision radius.
    - Collision checks account for the drone's physical size and a safety margin (`COLLISION_RADIUS_EXTRA`).


### Script File Rules

- **File Location**: Scripts must be placed in the `scripts/` directory.
- **File Extension**: `.txt`.
- **Validation**:
    - Invalid coordinates (e.g., negative values) will default to `(-1, -1, -1)`, marking the drone as inactive.
    - Missing or malformed headers will cause the simulation to terminate with an error.

For a complete example, see the provided script for 10 drones above.

---

## 3. Description of the approach taken in each US

### US261 - Initiate Simulation for a Figure

#### Key Code Implementation (`main.c` and `utils.c`)

**1. Script Parsing and Initialization**  


```
// main.c (run_simulation)
char filename;
snprintf(filename, sizeof(filename), "scripts/%s", argv);
int num_drones = get_drone_number_from_file(filename); // From utils.c
int total_ticks = get_total_ticks_from_file(filename); // From utils.c

// utils.c
int get_drone_number_from_file(char* file_name) {
int n;
sscanf(file_name, "scripts/script_%d_drones.txt", &n);
return n;
}
```

- Parses script filename to determine number of drones
- Validates file format (`script_x_drones.txt`)

**2. Process Creation and Pipes**
```
// main.c
int pipes[num_drones];
pid_t pids[num_drones];
for (int droneNumber = 0; droneNumber < num_drones; droneNumber++) {
pipe(pipes[droneNumber]);
pids[droneNumber] = fork();

if (pids[droneNumber] == 0) {  // Child process (drone)
    close(pipes[droneNumber]);
    simulate_drone(filename, ..., pipes[droneNumber]);
    exit(EXIT_SUCCESS);
}
close(pipes[droneNumber]);  // Parent closes write end

```
- Creates pipe for each drone
- Forks child processes for parallel drone simulation

**3. Timeline Initialization**

```
// main.c
report_of_simulation.timeline = malloc(total_ticks * sizeof(Position*));
for (int i = 0; i < total_ticks; i++) {
report_of_simulation.timeline[i] = malloc(num_drones * sizeof(Position));
// Initialize to (-1, -1, -1) for inactive state
}
```

- Pre-allocates memory for storing drone positions
- Uses (-1, -1, -1) to mark inactive drones

**4. Position Reading and Tracking**
```
// main.c
for (int timeStamp = 0; timeStamp < total_ticks; timeStamp++) {
for (int childNumber = 0; childNumber < num_drones; childNumber++) {
ssize_t bytes_read = read(pipes[childNumber], &current_pos, sizeof(Position));

    if (bytes_read == sizeof(Position)) {
        report_of_simulation.timeline[timeStamp][childNumber] = current_pos;
        printPositionDrone(current_pos, ...);  // Real-time UI update
    }
}

```

- Reads positions from pipes at each tick
- Updates timeline matrix
- Provides real-time terminal output

**5. Utility Functions (`utils.c`)**

```
int get_total_ticks_from_file(const char* filename) {
// Counts maximum number of position lines in any drone section
}
void fill_info(const char* filename, DroneInformation* dronesIDs, int num_drones) {
// Extracts drone ID and dimensions from script headers
}
int calculate_acceptable_collision_number(int numberOfDrones) {
return (int)floor(numberOfDrones * 0.05f); // 5% threshold
}
```
- Handles script content parsing
- Calculates safety thresholds

#### Process Flow
1. User selects script via terminal UI
2. System validates and parses script file
3. Parent process creates:
    - Communication pipes
    - Timeline data structure
    - Child processes (one per drone)
4. Drones execute movement scripts in parallel
5. Parent aggregates positions and manages synchronization

---

### US262 - Capture and Process Drone Movements

Each drone process reads only its own section from the shared script file, loading its sequence of positions into memory.  
At every simulation tick, the drone sends its current position to the main process through a dedicated pipe.

The main process collects these positions from all drones and stores them in a two-dimensional array:  
`timeline[tick][drone_id]`  
This structure allows tracking and visualization of every drone's movement over time.

**Relevant code snippets:**

**In the drone process:**
```
for (drone.current_step = 0; drone.current_step < drone.total_steps && !terminated; drone.current_step++) {
Position current_pos = drone.script[drone.current_step];
current_pos.pid = pid;
write(pipe_fd, &current_pos, sizeof(Position));
usleep(100000); // 100ms between steps
}
```

- Each drone sends its position to the parent process at each tick.

**In the main process:**

```
for (int timeStamp = 0; timeStamp < total_ticks; timeStamp++) {
for (int childNumber = 0; childNumber < num_drones; childNumber++) {
Position current_pos;
read(pipes[childNumber], &current_pos, sizeof(current_pos));
report_of_simulation.timeline[timeStamp][childNumber] = current_pos;
}
}
```

- The main process reads all drone positions and fills the `timeline` matrix.

**Summary:**  
US262 ensures that, at every tick, the system collects and stores the positions of all drones, enabling analysis and visualization of their paths throughout the simulation.  
This is achieved via pipes for inter-process communication and a matrix for organized storage.

---

### US263 - Detect drone collisions in real time

Collision detection is performed after collecting all drones’ positions for each simulation tick. A collision is detected if the Euclidean distance between two active drones is less than the sum of their calculated safety radii. The safety radius is based on the largest drone dimension and a constant buffer. If a collision is found, both drones are sent a `SIGUSR1` signal, captured in a `Collision_Stamp`, and temporarily halted. Drones are resumed later with `SIGCONT`. The simulation is aborted if the number of collisions exceeds a dynamic threshold (5% of total drones).

---

### US264 - Synchronize Drone Execution with a Time Step

The simulation enforces step-by-step synchronization using **blocking I/O operations** on pipes. The parent process iterates through each tick and waits for all drones to report their positions before advancing to the next time unit.

**Key Synchronization Mechanism:**

```
// main.c (Parent Process)
for (int timeStamp = 0; timeStamp < total_ticks; timeStamp++) {
for (int childNumber = 0; childNumber < num_drones; childNumber++) {
Position current_pos;
ssize_t bytes_read = read(pipes[childNumber], &current_pos, sizeof(Position)); // Blocks until data is available
// ... store position ...
}
// Process collisions / advance to next tick ONLY after all positions are received
}
```

**Drone Behavior:**

```
// drone.c
for (drone.current_step = 0; drone.current_step < drone.total_steps; drone.current_step++) {
write(pipe_fd, &current_pos, sizeof(Position)); // Send position
usleep(100000); // 100ms delay (optional pacing)
}
```

#### How It Works:
1. **Tick-Centric Loop**:  
   The parent process iterates through ticks sequentially. For each tick:
    - It reads positions from **all** drones' pipes.
    - The `read()` system call **blocks** until data is available, ensuring the parent waits for slower drones.

2. **Implicit Synchronization**:
    - Drones send positions as fast as they can, but the parent **never processes a new tick** until it has read all positions from the previous one.
    - This creates a "virtual barrier" between ticks.

3. **No Drone Left Behind**:  
   If a drone crashes or terminates early, the pipe returns `EOF` (read returns 0), and the parent marks it as inactive. The simulation continues with active drones only.

**Result**:  
All drones progress through the simulation at the same pace, with the parent guaranteeing lockstep execution via controlled I/O blocking.

---

### US265 - Generate a Simulation Report

At the end of each simulation run (whether completed or aborted due to excessive collisions), the system **automatically generates a detailed report** and saves it in the `./reports/` directory. This report is essential for validating the safety and correctness of the drone figure and for audit or debugging purposes.

#### What the Report Includes

- **Simulation Metadata:**
    - Simulation name (script used)
    - Date and time of the simulation
    - Number of drones and total ticks
    - Maximum allowed collisions and actual number of collisions
    - Final status: approved (no excessive collisions) or rejected

- **Detailed Timeline:**
    - For each tick, the report lists the position of every drone.
    - If a drone is inactive (e.g., due to collision), it is marked as such.

- **Collision Events:**
    - Every collision is explicitly listed with the IDs of the drones involved and the tick when it occurred.

#### Example Report Output

```
╔══════════════════════════════════════════════════╗
║ DRONE SIMULATION REPORT ║
╠══════════════════════════════════════════════════╣
║ Date & Time: 2025-05-16 16:00:00
║ Simulation: script_10_drones.txt
╠══════════════════════════════════════════════════╣
║ Drones: 10
║ Total Ticks: 12
║ Collisions: 2
║ Final Status: ❌ REJECTED
╚══════════════════════════════════════════════════╝
─── Detailed Timeline ───
Tick 1:
🚁 Drone 0: (8, 5, 5)
🚁 Drone 1: (9, 4, 4)
...
Tick 2:
🚁 Drone 0: (7, 0, 1)
...
💥 Collision detected between drone and 
...
```

#### Implementation Details

- The report is generated by the `generate_report` function at the end of the simulation:

```
char output_filename;
snprintf(output_filename, sizeof(output_filename), "./reports/report_%s_%d.txt", argv, collision_counter);
generate_report(&report_of_simulation, output_filename);
```
- The function writes all simulation data in a structured, human-readable format.
- The timeline and collision events are iterated and formatted for clarity.
- The report can be used for validation, debugging, and as a formal record of the simulation outcome.

#### Summary

This reporting mechanism ensures transparency and traceability for every simulation, making it easy to verify if a drone figure is safe for real-world execution and to analyze any issues or collisions that occurred during the test.



### US266 - Integrate environmental influences into simulation

---

## 4. Self-Assessment of Each Group Member's Commitment (0 – 100%)


| Name             | Student Number | Commitment (%) |
|------------------|:--------------:|:--------------:|
| Francisco Santos |    1230564     |      100%      |
| Emanuel Almeida  |    1230839     |      100%      |
| Jorge Rodrigues  |    1231274     |      100%      |
| Paulo Mendes     |    1231498     |      100%      |
| Romeu Xu         |    1230444     |      100%      |

All group members contributed equally to the implementation, testing, and documentation of the
solution, ensuring full commitment and balanced participation throughout the project.
