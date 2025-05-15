#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/select.h>
#include <math.h>
#include <signal.h>
#include "data.h"
#include "report.h"
#include "drone.h"

pid_t *add_drone_to_list(pid_t drone, pid_t *drones_that_collied, int *size);
int exist_in_array(pid_t drone, pid_t *drones_that_collied, int size);

const float COLLISION_RADIUS_EXTRA = 0.5f;
volatile sig_atomic_t number_of_collision = 0;

float calculateDistance(Position a, Position b) {
    float dx = a.x - b.x;
    float dy = a.y - b.y;
    float dz = a.z - b.z;
    return sqrtf(dx * dx + dy * dy + dz * dz);
}

int collisionDetection(int numberOfDrones, int total_ticks, Radar historyOfRadar[numberOfDrones][total_ticks], int timeStamp) {
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
