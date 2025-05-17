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
void fill_stamp_moment(Radar droneA, Radar droneB, int timeStamp, Collision_Stamp** stamps, int *stamps_capacity, int *stamps_count);

const float COLLISION_RADIUS_EXTRA = 0.5f;
volatile sig_atomic_t number_of_collision = 0;

float calculateDistance(Position a, Position b) {
    float dx = a.x - b.x;
    float dy = a.y - b.y;
    float dz = a.z - b.z;
    return sqrtf(dx * dx + dy * dy + dz * dz);
}

int collisionDetection(int numberOfDrones, int total_ticks, Radar historyOfRadar[numberOfDrones][total_ticks], int timeStamp,  Collision_Stamp **stamps, int *stamps_capacity, int *stamps_count) {
    int array_size = 0;
    pid_t *drones_that_collied = NULL;
    int number_of_collision = 0;

    for (int i = 0; i < numberOfDrones; i++) {
        Radar droneA = historyOfRadar[i][timeStamp];
        if (droneA.terminated) continue;

        if (droneA.position.z <= 0) {
            Radar ground = {
                .droneInformation = {.id = -1, .biggestDimension = 0},
                .timeStamp = timeStamp,
                .position = {.x = droneA.position.x, .y = droneA.position.y, .z = 0, .pid = 0},
                .terminated = 1
};
            fill_stamp_moment(droneA, ground, timeStamp, stamps, stamps_capacity, stamps_count);

            char msg[150];
            int len = snprintf(msg, sizeof(msg),"❗ Drone [%d] collided with the ground\n",droneA.droneInformation.id);
            write(STDOUT_FILENO, msg, len);

            pid_t drone1 = droneA.position.pid;
            kill(drone1, SIGUSR1);

            drones_that_collied = add_drone_to_list(drone1, drones_that_collied, &array_size);

            number_of_collision++;
            continue; 
        }

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

    return number_of_collision;
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

