# Shodrone – Simulation Engine

## Component Diagram – Application Workflow

This section explains how the Shodrone simulation system works using a multi-process and multi-threaded architecture.

---

### 1. **Startup**
- The **main process** begins the simulation.
- It reads:
  - `environment.txt` → Loads wind and rain conditions.
  - `scripts/script_x_drones.txt` → Identifies the number of drones and their movement scripts.

---

### 2. **Shared Memory Initialization**
- A shared memory segment is created (`Shared_data`).
- It contains:
  - The current **position and state** of each drone.
  - The current **environmental conditions**.
  - The current **tick** of the simulation.

---

### 3. **Process Creation**
- The main process creates **one child process per drone** using `fork()`.
- Each **drone process**:
  - Loads its movement instructions.
  - Waits for synchronization at each simulation tick.
  - Applies environmental effects (wind/rain).
  - Writes its updated position to shared memory.

---

### 4. **Thread Initialization (Main Process)**
- The main process spawns **three internal threads**:
  - `Environment Thread`: Applies environmental changes to drone positions.
  - `Collision Detection Thread`: Detects and logs collisions.
  - `Report Thread`: Collects position data and writes the final report.

---

### 5. **Simulation Loop**
At every **tick**:
1. Main process sets the `tick` value in shared memory.
2. Environment thread updates environment effects.
3. Drone processes calculate and update positions.
4. Collision thread checks for any collisions.
5. Report thread logs current state.
6. If the number of collisions exceeds the allowed threshold, the simulation stops early.

---

### 6. **Shutdown & Report Generation**
- After completing all ticks or reaching the collision limit:
  - All threads and drone processes are terminated.
  - The `Report Thread` generates a file:  
    `reports/report_script_x_drones.txt`  
    containing:
    - Drone timeline
    - Environmental conditions
    - Collision summary
    - Final result (Passed/Failed)

---

This architecture combines **multiprocessing** (for isolated drone control) and **multithreading** (for concurrent simulation logic), using **shared memory** to coordinate everything in real-time.



### Key Components

- **Main Process**
  - Reads the configuration file (`environment.txt`)
  - Creates one child process per drone using `fork()`
  - Spawns the following threads:
    - **Environment Thread**: Handles applying environmental effects to drones
    - **Collision Detection Thread**: Checks for collisions at each tick
    - **Report Thread**: Logs simulation progress and generates a final report

- **Drone Processes (created via `fork()`)**
  - Each process:
    - Reads its section of the movement script from the `scripts/` directory
    - Applies environmental effects to its movement
    - Writes its current position to **shared memory**
    - Waits for the main process to advance the simulation tick

- **Threads (in Main Process)**
  - **Environment Thread**  
    Periodically updates environmental effects and applies them to drone movements using data from shared memory.

  - **Collision Detection Thread**  
    Monitors drone positions per tick, detects collisions (drone-to-drone or drone-to-ground), and flags affected drones.

  - **Report Thread**  
    Listens for collision events and tick updates to compile real-time simulation information. Generates the final simulation report at the end.

---

## Shared Memory

The simulation uses a shared memory segment (`Shared_data`) to coordinate drone processes and monitor the simulation state in real time. It includes the following components:

### Drone States (`drones_state[MAX_DRONES]`)

An array holding the real-time state of each drone. Each element (`Drone_state`) contains:

- **Position (`position`)**
  - The drone's current coordinates: `x`, `y`, `z`
  - The process ID (`pid`) for tracking and signaling purposes

- **Drone Information (`drone_info`)**
  - `id`: Unique identifier of the drone
  - `biggestDimension`: Used in calculating the drone’s safety radius for collision detection
  - `has_crashed`: Flag indicating if the drone was involved in a collision

### Environment (`environment`)

A structure containing weather-related simulation data, loaded from `environment.txt` at startup:

- Wind speed values for:
  - North
  - South
  - East
  - West
- Rain intensity level

These values influence how drones modify their movement during each tick.

### Tick Counter (`tick`)

A global integer representing the current tick (or time step) of the simulation:

- Used to synchronize all drone processes and helper threads
- Ensures that drones move in lockstep and threads execute logic at consistent time intervals

---

This shared memory architecture replaces the previous `pipe`-based system, allowing:
- Simplified real-time communication
- Centralized access to drone and environment data
- Easier synchronization across threads and processes

---

This shared memory model enables real-time coordination and communication between multiple drone processes and managing
threads like the collision detector or report generator.  
It replaces inter-process pipes from previous iterations of the architecture, simplifying synchronization and state
access.

- **Files**
    - `scripts/*.txt`: Movement scripts for drones
    - `environment/environment.txt`: Wind and rain data
    - `reports/report_*.txt`: Simulation results

---

## Example of a Drone Movement Script

```txt
0 - 3x4x5
8 5 5
7 0 1
7 0 4
8 3 11
10 1 10
```

### Format Breakdown:

- `0 - 3x4x5`:
    - `0`: Drone ID
    - `3x4x5`: Width × Height × Depth (used for collision radius)

- The following lines represent tick-by-tick positions:
    - `x y z` coordinates for each simulation tick

> ⚠Drones that run out of defined positions become inactive. Invalid positions default to (-1, -1, -1).

---

## Approach Per User Story

### US261 – Initiate Simulation

- The parent process reads a script file.
- It parses the number of drones and simulation ticks.
- Forks one child process per drone.
- Shared memory is allocated for position tracking.

### US262 – Capture Drone Movements

- Each drone loads its coordinates from the script.
- On each tick, it writes its position into shared memory.
- Synchronization ensures all drones advance together.

### US263 – Collision Detection

- A thread in the parent process checks all drone positions at every tick.
- If two drones are too close or touch the ground, a collision is registered.
- Drones may be marked as terminated via shared flags.

### US264 – Tick Synchronization

- The simulation uses semaphores.
- Drones wait until the tick advances.
- The parent only proceeds when all drones have reported their positions.

### US265 – Report Generation

- A thread in the parent generates a structured report:
    - Includes collisions, timeline, environment, final result
- Saved in the `reports/` directory

### US266 – Environmental Effects

- Wind and rain values are read from `environment.txt`
- Effects are applied using random variation:
    - Wind alters `x`/`y`
    - Rain affects `z`

---

## 👥 Group Member Commitment (Self-Assessment)

| Name             | Student Number | Commitment (%) |
|------------------|:--------------:|:--------------:|
| Francisco Santos |    1230564     |      100%      |
| Emanuel Almeida  |    1230839     |      100%      |
| Jorge Rodrigues  |    1231274     |      100%      |
| Paulo Mendes     |    1231498     |      100%      |
| Romeu Xu         |    1230444     |      100%      |

> All members contributed equally to design, implementation, debugging, and documentation.

---

## 🛠️ Compilation & Execution

```bash
make compile
./simulator
```

To clean:

```bash
make clean
```

Ensure `scripts/` and `environment/` directories exist with valid files before execution.
