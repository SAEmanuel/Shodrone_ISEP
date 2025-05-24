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

const float COLLISION_RADIUS_EXTRA = 0.5f;
volatile sig_atomic_t number_of_collision = 0;

float calculateDistance(Position a, Position b) {
    float dx = a.x - b.x;
    float dy = a.y - b.y;
    float dz = a.z - b.z;
    return sqrtf(dx * dx + dy * dy + dz * dz);
}

int collisionDetection(int total_drones, Collision_Stamp** stamps, int* stamps_capacity, int* stamps_count, Shared_data* shared_mem, DroneInformation** drones_terminated, int* drones_terminated_size, int* capacity) {
    int number_of_collision = 0;
   

    int timeStamp = shared_mem->tick;

    for (int i = 0; i < total_drones; i++) {
        Drone_state posA = shared_mem->drones_state[i];

        if (is_drone_crashed(posA.drone_info.id, *drones_terminated, *drones_terminated_size))
            continue;

        // Ignorar drones inativos (todas as coordenadas -1)
        if (posA.position.x == -1 && posA.position.y == -1 && posA.position.z == -1)
            continue;

        // Colisão com o chão
        if (posA.position.z <= 0) {
            posA.drone_info.has_crashed = 1;
            add_terminated_drone(posA.drone_info.id, 1, drones_terminated, drones_terminated_size, capacity);

            // Preenche stamp de colisão com o chão
            fill_stamp_moment(posA, posA, timeStamp, stamps, stamps_capacity, stamps_count);

            char msg[150];
            int len = snprintf(msg, sizeof(msg), "❗ Drone [%d] collided with the ground\n", i);
            write(STDOUT_FILENO, msg, len);

            
            number_of_collision++;
            continue;
        }

        float radiusA = (posA.drone_info.biggestDimension / 200.0f) + COLLISION_RADIUS_EXTRA;

        for (int j = i + 1; j < total_drones; j++) {
            Drone_state posB = shared_mem->drones_state[j];

            if (is_drone_crashed(posB.drone_info.id, *drones_terminated, *drones_terminated_size))
                continue;

            if (posB.position.x == -1 && posB.position.y == -1 && posB.position.z == -1)
                continue;

            float radiusB = (posB.drone_info.biggestDimension / 200.0f) + COLLISION_RADIUS_EXTRA;
            float distance = calculateDistance(posA.position, posB.position);
            float combinedRadius = radiusA + radiusB;

            if (distance < combinedRadius) {
                add_terminated_drone(posA.drone_info.id, 1, drones_terminated, drones_terminated_size, capacity);
                add_terminated_drone(posB.drone_info.id, 1, drones_terminated, drones_terminated_size, capacity);

                fill_stamp_moment(posA, posB, timeStamp, stamps, stamps_capacity, stamps_count);
                number_of_collision++;

                char msg[100];
                int len = snprintf(msg, sizeof(msg), "💥 Collision detected between drone [%d] and [%d]\n", i, j);
                write(STDOUT_FILENO, msg, len);


            }
        }
    }

    return number_of_collision;
}



void fill_stamp_moment(Drone_state droneA, Drone_state droneB, int timeStamp, Collision_Stamp** stamps, int *stamps_capacity, int *stamps_count) {
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

    (*stamps)[*stamps_count].id_drone1 = droneA.drone_info.id;
    (*stamps)[*stamps_count].id_drone2 = droneB.drone_info.id;
    (*stamps)[*stamps_count].collision_time = timeStamp;
    (*stamps_count)++;
 }


int is_drone_crashed(int id, DroneInformation* drones_terminated, int size) {
    for (int i = 0; i < size; i++) {
        if (drones_terminated[i].id == id && drones_terminated[i].has_crashed)
            return 1;
    }
    return 0;
}


void add_terminated_drone(int id, int crashed, DroneInformation** drones_terminated, int* size, int* capacity) {
    for (int i = 0; i < *size; i++) {
        if ((*drones_terminated)[i].id == id) {
            if (crashed)
                (*drones_terminated)[i].has_crashed = 1;
            return;
        }
    }

    if (*size >= *capacity) {
        *capacity = (*capacity == 0) ? 10 : *capacity * 2;
        *drones_terminated = realloc(*drones_terminated, *capacity * sizeof(DroneInformation));
        if (!*drones_terminated) {
            perror("realloc");
            exit(EXIT_FAILURE);
        }
    }
    (*drones_terminated)[*size].id = id;
    (*drones_terminated)[*size].has_crashed = crashed ? 1 : 0;
    (*size)++;
}


void printDroneCrash(int id) {
    char buffer[100];
    int len = snprintf(buffer, sizeof(buffer), "⛔ Drone [%d] leaving the show due to collision\n", id);
    write(STDOUT_FILENO, buffer, len);
}

int in_final_position(int pos_x, int pos_y, int pos_z) {
    if (pos_x == -1 && pos_y == -1 && pos_z == -1)
        return 1;

    return 0;
}

int not_showed_yet(int drone_id, int* drone_crash_printed) {
    return !drone_crash_printed[drone_id];
}





