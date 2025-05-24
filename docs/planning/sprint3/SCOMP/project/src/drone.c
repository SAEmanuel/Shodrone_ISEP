#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <signal.h>
#include <ctype.h>
#include <semaphore.h>
#include "drone.h"
#include "data.h"
#include "environment.h"
#include <time.h>




void simulate_drone(const char* filename, int drone_id, int total_ticks, Shared_data* shared_mem, sem_t* sem_child, sem_t* sem_parent) {
    FILE* file = fopen(filename, "r");
    if (!file) {
        perror("Drone failed to open script file");
        exit(EXIT_FAILURE);
    }

    Drone drone = {
        .id = drone_id,
        .positions = NULL,
        .total_steps = 0,
        .current_step = 0
    };

    char line[128];
    int found_section = 0;
    int capacity = 10;

    while (fgets(line, sizeof(line), file)) {
        trim(line);
        if (strlen(line) == 0)
            continue;
        int id, dim1, dim2, dim3;
        if (sscanf(line, "%d - %dx%dx%d", &id, &dim1, &dim2, &dim3) == 4 && id == drone.id) {
            found_section = 1;
            break;
        }
    }

    if (!found_section) {
        fprintf(stderr, "Drone %d: Section not found in script\n", drone.id);
        fclose(file);
        exit(EXIT_FAILURE);
    }

    drone.positions = malloc(sizeof(Position) * capacity);
    if (!drone.positions) {
        perror("Failed to allocate initial script");
        fclose(file);
        exit(EXIT_FAILURE);
    }

    while (fgets(line, sizeof(line), file)) {
        trim(line);
        if (strlen(line) == 0)
            continue;

        int id, dim1, dim2, dim3;
        if (sscanf(line, "%d - %dx%dx%d", &id, &dim1, &dim2, &dim3) == 4)
            break;

        if (drone.total_steps >= capacity) {
            capacity *= 2;
            Position* new_script = realloc(drone.positions, sizeof(Position) * capacity);
            if (!new_script) {
                perror("Failed to realloc script");
                free(drone.positions);
                fclose(file);
                exit(EXIT_FAILURE);
            }
            drone.positions = new_script;
        }
        
        if (sscanf(line, "%d %d %d",
                   &drone.positions[drone.total_steps].x,
                   &drone.positions[drone.total_steps].y,
                   &drone.positions[drone.total_steps].z) != 3) {
            fprintf(stderr, "Drone %d: Invalid coordinate line: %s\n", drone.id, line);
            continue;
        }
        drone.total_steps++;
    }

    fclose(file);

    if (drone.total_steps < total_ticks) {
        drone.positions = realloc(drone.positions, sizeof(Position) * total_ticks);
        if (!drone.positions) {
            perror("Failed to realloc for padding");
            exit(EXIT_FAILURE);
        }
        for (int i = drone.total_steps; i < total_ticks; i++) {
            drone.positions[i].x = -1;
            drone.positions[i].y = -1;
            drone.positions[i].z = -1;
        }
        drone.total_steps = total_ticks;
    }

    for (int t = 0; t < total_ticks; t++) {
        sem_wait(sem_child);
        if (!in_final_position(drone.positions[t].x, drone.positions[t].y, drone.positions[t].z))
            apply_environment_effects(&shared_mem->environment, &drone.positions[t]);
        shared_mem->drones_state[drone_id].position = drone.positions[t];
        shared_mem->drones_state[drone_id].drone_info.id = drone_id;
        
        usleep(100000); 
        sem_post(sem_parent);
    }

    free(drone.positions);
}

