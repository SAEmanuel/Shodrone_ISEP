### 4. Tests

#### Integration Tests
- **Scenario**: Simulate a full drone simulation cycle where drones move over multiple ticks, and `collisionDetection` is called at each tick to detect collisions. Verify that collisions are logged correctly in the `Collision_Stamp` array and reported via `Report`.
- **Test**: Initialize a `historyOfRadar` array with drone movements over 5 ticks, call `collisionDetection` for each tick, and check the resulting `Collision_Stamp` entries.
- **Expected Outcome**: Collisions are detected at the correct timestamps, and the `Report` structure contains the correct number of collisions.

#### Validation Procedures
- **Edge Case**: All drones are terminated (`terminated = 1`). The system should not detect any collisions.
- **Stress Test**: Simulate 1,000 drones at the same position to test the performance of `collisionDetection` and memory management of `Collision_Stamp`.

---

### 5. Construction (Implementation)

#### Controller/Service Class Names
- **Module**: `src/collisionDetection.c`
- **Header**: `include/data.h`, `include/drone.h`, `include/report.h`
- **Main Function**: `collisionDetection`

#### Implementation Strategy
- **Data Structure**: Use a 2D array (`historyOfRadar[numberOfDrones][total_ticks]`) to store the state of drones over time. A dynamic array (`Collision_Stamp`) logs collisions.
- **Collision Detection**:
    - Calculate the distance between each pair of drones using `calculateDistance`.
    - Compare the distance against the combined collision radius (based on `biggestDimension` and `COLLISION_RADIUS_EXTRA`).
    - If a collision is detected, log it using `fill_stamp_moment` and send `SIGUSR1` to the involved drones.
- **Signal Handling**: Use `kill` to send `SIGUSR1` to drones, and `waitpid` to handle their termination.
- **Patterns Used**: None explicitly, but the implementation follows a modular design with separation of concerns (collision detection, logging, signal handling).

#### Code Snippets

**`src/collisionDetection.c` (Provided Earlier, Repeated for Completeness)**

```x-csrc
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/select.h>
#include <math.h>
#include <signal.h>
#include "include/data.h"
#include "include/report.h"
#include "include/drone.h"

pid_t *add_drone_to_list(pid_t drone, pid_t *drones_that_collied, int *size);
int exist_in_array(pid_t drone, pid_t *drones_that_collied, int size);
void fill_stamp_moment(Radar droneA, Radar droneB, int timeStamp, Collision_Stamp** stamps, int *stamps_capacity, int *stamps_count);

const float COLLISION_RADIUS_EXTRA = 0.5f;
volatile sig_atomic_t number_of_collision = 0;

float calculateDistance(Position a, Position b) {
    float dx = a.x - b.x;
    float dy = a.y - b.y;
    float dz = a.z - b.z;
    return sqrtf(dx * dx + dy * dy + dz * dz);
}

int collisionDetection(int numberOfDrones, int total_ticks, Radar historyOfRadar[numberOfDrones][total_ticks], int timeStamp, Collision_Stamp **stamps, int *stamps_capacity, int *stamps_count) {
    int array_size = 0;
    pid_t *drones_that_collied = NULL;

    for (int i = 0; i < numberOfDrones; i++) {
        Radar droneA = historyOfRadar[i][timeStamp];
        if (droneA.terminated) continue;

        float radiusA = (droneA.droneInformation.biggestDimension / 200.0f) + COLLISION_RADIUS_EXTRA;

        for (int j = i + 1; j < numberOfDrones; j++) {
            Radar droneB = historyOfRadar[j][timeStamp];
            if (droneB.terminated) continue;

            float radiusB = (droneB.droneInformation.biggestDimension / 200.0f) + COLLISION_RADIUS_EXTRA;
            float distance = calculateDistance(droneA.position, droneB.position);
            float combinedRadius = radiusA + radiusB;

            if (distance < combinedRadius) {
                fill_stamp_moment(droneA, droneB, timeStamp, stamps, stamps_capacity, stamps_count);
                number_of_collision++;

                char msg[100];
                int len = snprintf(msg, sizeof(msg), "💥 Collision detected between drone [%d] and [%d] at time - %d\n",
                                   droneA.droneInformation.id, droneB.droneInformation.id, timeStamp);
                write(STDOUT_FILENO, msg, len);

                pid_t drone1 = droneA.position.pid;
                pid_t drone2 = droneB.position.pid;

                kill(drone1, SIGUSR1);
                kill(drone2, SIGUSR1);

                drones_that_collied = add_drone_to_list(drone1, drones_that_collied, &array_size);
                drones_that_collied = add_drone_to_list(drone2, drones_that_collied, &array_size);
            }
        }
    }

    if (number_of_collision > 0) {
        for (int i = 0; i < array_size; i++) {
            waitpid(drones_that_collied[i], NULL, WUNTRACED);
            kill(drones_that_collied[i], SIGCONT);
            waitpid(drones_that_collied[i], NULL, 0);
        }
    }

    free(drones_that_collied);
    
    int collision_copy = number_of_collision;
    number_of_collision = 0;
    return collision_copy;
}

pid_t *add_drone_to_list(pid_t drone, pid_t *drones_that_collied, int *size) {
    if (!exist_in_array(drone, drones_that_collied, *size)) {
        pid_t *temp = realloc(drones_that_collied, sizeof(pid_t) * (*size + 1));
        if (!temp) {
            perror("Failed to realloc drones_that_collied array");
            free(drones_that_collied);
            exit(EXIT_FAILURE);
        }
        temp[*size] = drone;
        (*size)++;
        return temp;
    }
    return drones_that_collied;
}

int exist_in_array(pid_t drone, pid_t *drones_that_collied, int size) {
    for (int i = 0; i < size; i++) {
        if (drones_that_collied[i] == drone) {
            return 1;
        }
    }
    return 0;
}

void fill_stamp_moment(Radar droneA, Radar droneB, int timeStamp, Collision_Stamp** stamps, int *stamps_capacity, int *stamps_count) {
    if (*stamps_count >= *stamps_capacity) {
        int new_capacity = (*stamps_capacity == 0) ? 10 : *stamps_capacity * 2;
        Collision_Stamp *temp = realloc(*stamps, new_capacity * sizeof(Collision_Stamp));
        if (!temp) {
            perror("Erro ao realocar stamps");
            exit(EXIT_FAILURE);
        }
        *stamps = temp;
        *stamps_capacity = new_capacity;
    }

    (*stamps)[*stamps_count].id_drone1 = droneA.droneInformation.id;
    (*stamps)[*stamps_count].id_drone2 = droneB.droneInformation.id;
    (*stamps)[*stamps_count].collision_time = timeStamp;
    (*stamps_count)++;
}
```

---

### 6. Observations

#### Design Decisions
- Used a simple pairwise comparison for collision detection to prioritize correctness and readability over performance.
- Integrated signal handling to simulate real-time termination of drones, aligning with the “real-time” requirement.
- Kept the implementation lightweight by avoiding external libraries, consistent with the project’s CLI-based nature.

---
