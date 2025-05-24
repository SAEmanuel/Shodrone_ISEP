#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/select.h>
#include <string.h>
#include <time.h>
#include <semaphore.h>
#include <sys/mman.h>
#include <pthread.h>
#include "data.h"
#include "report.h"
#include "drone.h"
#include "simulation.h"
#include "ui.h"
#include "environment.h"


int run_simulation(char* argv, float percentage)
{
    Environment environment;
    read_enviroment_info(&environment);


    char filename[256];
    snprintf(filename, sizeof(filename), "scripts/%s", argv);

    int total_drones = get_drone_number_from_file(filename);
    int max_collisions = calculate_acceptable_collision_number(total_drones, percentage);
    int collision_counter = 0;

    if (total_drones <= 0) {
        fprintf(stderr, "Invalid or unreadable script file: %s\n", filename);
        return 1;
    }

    DroneInformation *drones_info = calloc(total_drones, sizeof(DroneInformation));
    fill_info(filename, drones_info, total_drones);

    int total_ticks = get_total_ticks_from_file(filename);
    if (total_ticks <= 0) {
        fprintf(stderr, "Failed to determine total ticks\n");
        return 1;
    }

    int fd;
    Shared_data* shared_mem;
    init_shared_zone(&fd, &shared_mem);

    sem_t* sem_child[total_drones];
    sem_t* sem_parent;

    char sem_name[32];

    sem_unlink("/sem_parent");

    if ((sem_parent = sem_open("/sem_parent", O_CREAT | O_EXCL, 0666, 0)) == SEM_FAILED) {
        perror("sem_open (parent)");
        exit(1);
    }

    for (int i = 0; i < total_drones; i++) {
        snprintf(sem_name, sizeof(sem_name), "/sem_child_%d", i);

        sem_unlink(sem_name);

        sem_child[i] = sem_open(sem_name, O_CREAT | O_EXCL, 0666, 0);
        if (sem_child[i] == SEM_FAILED) {
            perror("sem_open (child)");
            exit(1);
        }
    }
 
    pid_t pids[total_drones];
    Collision_Stamp *stamps = NULL;
    int stamps_capacity = 0;
    int stamps_count = 0;

    for(int drone_number = 0; drone_number < total_drones; drone_number++) {
        pids[drone_number] = fork();

        if(pids[drone_number] == -1) {
            perror("Error forking");
            exit(EXIT_FAILURE);
        }

        if (pids[drone_number] == 0) {

            srand(time(NULL) ^ getpid());
            simulate_drone(filename, drones_info[drone_number].id, total_ticks, shared_mem, sem_child[drone_number], sem_parent);
            exit(EXIT_SUCCESS);
        }
    }

    DroneInformation* drones_terminated = NULL;
    int drones_terminated_size = 0;
    int drones_terminated_capacity = 0;
    int* drone_crash_printed = calloc(total_drones, sizeof(int));

    
    pthread_t collision_thread;
    pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
    pthread_cond_t cond_tick = PTHREAD_COND_INITIALIZER;
    pthread_cond_t cond_done = PTHREAD_COND_INITIALIZER;
    int stop_simulation = 0;
    int collision_tick_ready = 0;
    int collision_tick_done = 0;

    CollisionThreadArgs collision_args = {
        .total_drones = total_drones,
        .shared_mem = shared_mem,
        .drones_terminated = &drones_terminated,
        .drones_terminated_size = &drones_terminated_size,
        .drones_terminated_capacity = &drones_terminated_capacity,
        .stamps = &stamps,
        .stamps_capacity = &stamps_capacity,
        .stamps_count = &stamps_count,
        .collision_counter = &collision_counter,
        .stop_simulation = &stop_simulation,
        .mutex = &mutex,
        .cond_tick = &cond_tick,
        .cond_done = &cond_done,
        .collision_tick_ready = &collision_tick_ready,
        .collision_tick_done = &collision_tick_done
    };

    pthread_create(&collision_thread, NULL, collision_thread_func, &collision_args);


    for(int timeStamp = 0; timeStamp < total_ticks; timeStamp++) {
        printTimeOfSimulation(timeStamp, total_ticks);
        shared_mem->tick = timeStamp;

        transfer_environmental_effects(&environment, shared_mem);

        for (int i = 0; i < total_drones; i++) {
            sem_post(sem_child[i]);
        }

        for (int i = 0; i < total_drones; i++) {
            sem_wait(sem_parent);
        }

        pthread_mutex_lock(&mutex);
        collision_tick_ready = 1;
        collision_tick_done = 0;
        pthread_cond_signal(&cond_tick);

    
        while (!collision_tick_done) {
            pthread_cond_wait(&cond_done, &mutex);
        }
        pthread_mutex_unlock(&mutex);

        for (int i = 0; i < total_drones; i++) {
            if (!is_drone_crashed(shared_mem->drones_state[i].drone_info.id, drones_terminated, drones_terminated_size)
            && !in_final_position(shared_mem->drones_state[i].position.x, shared_mem->drones_state[i].position.y, shared_mem->drones_state[i].position.z)) {
                printPositionDrone(shared_mem->drones_state[i].position, shared_mem->drones_state[i].drone_info.id);
            }
        }

        for (int i = 0; i < total_drones; i++) {
            if (!is_drone_crashed(shared_mem->drones_state[i].drone_info.id, drones_terminated, drones_terminated_size) 
            && in_final_position(shared_mem->drones_state[i].position.x, shared_mem->drones_state[i].position.y, shared_mem->drones_state[i].position.z)) {
                printDroneInEnd(shared_mem->drones_state[i].drone_info.id);

            } else if (is_drone_crashed(shared_mem->drones_state[i].drone_info.id, drones_terminated, drones_terminated_size) 
            && not_showed_yet(shared_mem->drones_state[i].drone_info.id, drone_crash_printed)) {
                printDroneCrash(shared_mem->drones_state[i].drone_info.id);
                drone_crash_printed[shared_mem->drones_state[i].drone_info.id] = 1;
            }
        }

        if (collision_counter > max_collisions) {
            print_max_collisions_reached(max_collisions);
            break;
        }
    }

    pthread_mutex_lock(&mutex);
    stop_simulation = 1;
    pthread_cond_signal(&cond_tick);
    pthread_mutex_unlock(&mutex);

    pthread_join(collision_thread, NULL);


    free(drones_info);
    free(drones_terminated);
    free(drone_crash_printed);
    free(stamps);

    munmap(shared_mem, sizeof(Shared_data));
    close(fd);

    sem_close(sem_parent);
    for (int i = 0; i < total_drones; i++) {
        sem_close(sem_child[i]);
    }

    shm_unlink("/shm");
    sem_unlink("/sem_parent");
    for (int i = 0; i < total_drones; i++) {
        char sem_name[32];
        snprintf(sem_name, sizeof(sem_name), "/sem_child_%d", i);
        sem_unlink(sem_name);
    }

    return 0;
}




