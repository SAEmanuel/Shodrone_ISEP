# Figure Code Simulation System

## Objective

This project simulates the execution of a drone figure, step by step, to ensure drones do not collide before approving the figure for real-world use in a drone show.

Each drone has its own predefined movement script. The simulation engine analyzes all drones in parallel and determines whether the figure is safe to use.

---

## Current Structure

The code is organized into modules:

```
scomp_project/
├── include/         # Header files (.h)
│   ├── drone.h
│   ├── data.h
│   ├── report.h
├── obj/             # Compiled object files (.o) used during build
│   ├── drone.o
│   ├── main.o
│   ├── report.o
│   ├── sync.o
│   ├── utils.o
├── scripts/         # Input scripts for figures
│   ├── script_x_drones.txt
│   ├── script_y_drones.txt
│   ├── ...
├── src/             # Source code (.c)
│   ├── drone.c
│   ├── main.c
│   ├── report.c
│   ├── sync.c
│   ├── utils.c
├── Makefile
├── simulator        # Executable generated after compilation
```

---

## Core Structures

### `Position`

Represents a point in 3D space:

```c
typedef struct {
    int x, y, z;
} Position;
```

---

### `Drone`

Represents an individual drone in the simulation:

```c
typedef struct {
    int id;
    Position* script;     // Movement path (per time step)
    int total_steps;
    int current_step;
    int active;
    int collided;
    int pipe_fd;          // To be used in simulation
    pid_t pid;            // To be used in simulation
} Drone;
```

---

### `ShowProposal`

Holds information about the entire drone figure:

```c
typedef struct {
    Drone* drones;         // All drones involved
    int num_drones;
    int total_ticks;       // Maximum number of steps across all drones
    Position*** timeline;  // [tick][drone_id] = Position (used during simulation)
    int collisions;        // Total number of detected collisions
    int passed;            // 1 = approved, 0 = failed
} ShowProposal;
```

---

## Current Status

- [x] File reading and parsing of drone scripts
- [x] Dynamic allocation of drones and their movements
- [x] Initialization of the `ShowProposal` structure
- [x] CLI file path handling
- [ ] Simulation logic (forks, pipes, signals)
- [ ] Collision detection
- [ ] Reporting system

---

## Compilation

Use `make compile` to compile the project:

```bash
make compile
```

Run the simulation manually by providing the script file name:

```bash
./simulator script_x_drones.txt
```

> Note: The program looks for scripts inside the `scripts/` directory.

---

## To Do (Next Steps)

- Implement drone simulation logic using `fork()`
- Create inter-process communication with `pipe()`
- Detect and signal collisions
- Synchronize drones by simulation ticks
- Generate simulation result reports

---

## Authors

- Romeu Xu
- Jorge Rodrigues
- Emanuel Almeida
- Paulo Mendes
- Francisco Santos
