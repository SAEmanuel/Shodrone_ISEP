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

int run_simulation(char* argv, float percentage) {

// ----------------- Simulation Parameters and Shared Resource Initialization ----------------- //
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
    // ----------------- ########################################### ----------------- //

    // ----------------- Semaphores Father-Children ----------------- //
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
    // ----------------- ########################### ----------------- //
    
    // ----------------- Fork of the drone processes ----------------- //
    pid_t pids[total_drones];

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
    // ----------------- ########################### ----------------- //

    // ----------------- Helpers for reports and prints ----------------- //

    Collision_Stamp *stamps = NULL;
    int stamps_capacity = 0;
    int stamps_count = 0;

    DroneInformation* drones_terminated = NULL;
    int drones_terminated_size = 0;
    int drones_terminated_capacity = 0;
    int* drone_crash_printed = calloc(total_drones, sizeof(int));
    // ----------------- ########################### ----------------- //

    // ------------------   Report Inicilization   ------------------ //
        Report report_of_simulation = {
        .num_drones = total_drones,
        .total_ticks = total_ticks,
        .collisions = 0,
        .passed = 1,
        .max_collisions = max_collisions
    };
    strncpy(report_of_simulation.simulation_name, argv, sizeof(report_of_simulation.simulation_name));

    report_of_simulation.timeline = malloc(total_ticks * sizeof(Position*));
    for (int i = 0; i < total_ticks; i++) {
        report_of_simulation.timeline[i] = malloc(total_drones * sizeof(Position));
        for (int j = 0; j < total_drones; j++) {
            report_of_simulation.timeline[i][j].x = -1;
            report_of_simulation.timeline[i][j].y = -1;
            report_of_simulation.timeline[i][j].z = -1;
        }
    }

    // ----------------- ########################### --------------- //

    // ----------------- Shared declarations report -> collisions ----------------- //
    pthread_mutex_t mutex_report = PTHREAD_MUTEX_INITIALIZER;
    pthread_cond_t cond_tick_report = PTHREAD_COND_INITIALIZER;
    pthread_cond_t cond_done_report = PTHREAD_COND_INITIALIZER;
    int report_tick_ready = 0;
    int report_tick_done = 0;
    // ----------------- ########################### --------------- //

    // ----------------- Collision Thread related ----------------- //
    pthread_t collision_thread;
    pthread_mutex_t mutex_collision = PTHREAD_MUTEX_INITIALIZER;
    pthread_cond_t cond_tick_collision = PTHREAD_COND_INITIALIZER;
    pthread_cond_t cond_done_collision = PTHREAD_COND_INITIALIZER;
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
        .mutex = &mutex_collision,
        .cond_tick = &cond_tick_collision,
        .cond_done = &cond_done_collision,
        .collision_tick_ready = &collision_tick_ready,
        .collision_tick_done = &collision_tick_done,
        .mutex_report = &mutex_report,
        .cond_tick_report = &cond_tick_report,
        .cond_done_report = &cond_done_report,
        .report_tick_ready = &report_tick_ready,
        .report_tick_done = &report_tick_done
    };
    pthread_create(&collision_thread, NULL, collision_thread_func, &collision_args);
    // ----------------- ########################### ----------------- //

    // ----------------- Environment Thread related ----------------- //
    pthread_t environment_thread;
    pthread_mutex_t mutex_environment = PTHREAD_MUTEX_INITIALIZER;
    pthread_cond_t cond_tick_environment = PTHREAD_COND_INITIALIZER;
    pthread_cond_t cond_done_environment = PTHREAD_COND_INITIALIZER;
    int environment_tick_ready = 0;
    int environment_tick_done = 0;
    int stop_environment_thread = 0;

    EnvironmentThreadArgs environment_args = {
        .environment = &environment,
        .shared_mem = shared_mem,
        .mutex = &mutex_environment,
        .stop_simulation = &stop_environment_thread,
        .cond_tick = &cond_tick_environment,
        .cond_done = &cond_done_environment,
        .environment_tick_ready = &environment_tick_ready,
        .environment_tick_done = &environment_tick_done
    };
    pthread_create(&environment_thread, NULL, environment_thread_func, &environment_args);
    // ----------------- ########################### ----------------- //

    // ----------------- Report Thread related ----------------- //
    pthread_t report_thread;
    int report_passed = 1;
    int stop_report_thread = 0;

    char output_filename[128];
    snprintf(output_filename, sizeof(output_filename), "./reports/report_%s_%d.txt", argv, collision_counter);

    ReportThreadArgs report_args = {
        .report = &report_of_simulation,
        .environment = &environment,
        .stamps = &stamps,
        .stamps_count = &stamps_count,
        .shared_mem = shared_mem,
        .mutex = &mutex_report,
        .stop_simulation = &stop_report_thread,
        .cond_tick = &cond_tick_report,
        .cond_done = &cond_done_report,
        .passed = &report_passed,
        .report_tick_ready = &report_tick_ready,
        .report_tick_done = &report_tick_done,
        .collisions = &collision_counter,
        .max_collisions = &max_collisions,
        .num_drones = &total_drones,
        .total_ticks = &total_ticks
    };

    strncpy(report_args.output_filename, output_filename, sizeof(report_args.output_filename));
    report_args.output_filename[sizeof(report_args.output_filename) - 1] = '\0';

    pthread_create(&report_thread, NULL, report_thread_func, &report_args);
    // ----------------- ########################### ----------------- //


    // -----------------  Main Simulation cicle  ------------------ //
    for(int timeStamp = 0; timeStamp < total_ticks; timeStamp++) {
        printTimeOfSimulation(timeStamp, total_ticks);
        shared_mem->tick = timeStamp;

        pthread_mutex_lock(&mutex_environment);
        environment_tick_ready = 1;
        environment_tick_done = 0;
        pthread_cond_signal(&cond_tick_environment);
        while(!environment_tick_done) {
            pthread_cond_wait(&cond_done_environment, &mutex_environment);
        }
        pthread_mutex_unlock(&mutex_environment);

        for (int i = 0; i < total_drones; i++) {
            sem_post(sem_child[i]);
        }
        for (int i = 0; i < total_drones; i++) {
            sem_wait(sem_parent);
        }

        pthread_mutex_lock(&mutex_collision);
        collision_tick_ready = 1;
        collision_tick_done = 0;
        pthread_cond_signal(&cond_tick_collision);

        while (!collision_tick_done) {
            pthread_cond_wait(&cond_done_collision, &mutex_collision);
        }
        pthread_mutex_unlock(&mutex_collision);

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

            } 
            if (is_drone_crashed(shared_mem->drones_state[i].drone_info.id, drones_terminated, drones_terminated_size) 
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
    // ----------------- ########################### ----------------- //

    // -------------------   Cleaning resources and ending threads   ------------------- //
    pthread_mutex_lock(&mutex_collision);
    stop_simulation = 1;
    pthread_cond_signal(&cond_tick_collision);
    pthread_mutex_unlock(&mutex_collision);
    pthread_join(collision_thread, NULL);

    pthread_mutex_lock(&mutex_environment);
    stop_environment_thread = 1;
    pthread_cond_signal(&cond_tick_environment);
    pthread_mutex_unlock(&mutex_environment);
    pthread_join(environment_thread, NULL);

    pthread_mutex_lock(&mutex_report);
    report_passed = (collision_counter <= max_collisions) ? 1 : 0;
    stop_report_thread = 1;
    pthread_cond_signal(&cond_tick_report);
    pthread_mutex_unlock(&mutex_report);
    pthread_join(report_thread, NULL);

    pthread_mutex_destroy(&mutex_collision);
    pthread_cond_destroy(&cond_tick_collision);
    pthread_cond_destroy(&cond_done_collision);

    pthread_mutex_destroy(&mutex_environment);
    pthread_cond_destroy(&cond_tick_environment);
    pthread_cond_destroy(&cond_done_environment);

    pthread_mutex_destroy(&mutex_report);
    pthread_cond_destroy(&cond_tick_report);
    pthread_cond_destroy(&cond_done_report);

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
