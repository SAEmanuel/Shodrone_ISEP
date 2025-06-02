#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <ctype.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <math.h>
#include "data.h"
#include "report.h"
#include<fcntl.h>
#include<sys/mman.h>
#include<sys/stat.h>
#include "ui.h"




void trim(char* str) {
    if (str == NULL) 
        return;

    char* start = str;
    while (isspace((unsigned char)*start)) 
        start++;

    char* end = str + strlen(str) - 1;
    while (end > start && isspace((unsigned char)*end)) 
        end--;

    *(end + 1) = '\0';

    memmove(str, start, end - start + 2);
}


int get_drone_number_from_file(char* file_name) {
    int n;

    int matched = sscanf(file_name, "scripts/script_%d_drones.txt", &n);

    if (matched == 1 && n > 0) {
        return n;
    } else {
        fprintf(stderr, "Wrong file name format!\n");
        fprintf(stderr, "File format must be: script_x_drones.txt, where x is the number of drones to be used.\n");
        return -1; 
    }
}


int get_total_ticks_from_file(const char* filename) {
    FILE* file = fopen(filename, "r");
    if (!file) {
        fprintf(stderr, "Could not open file: %s\n", filename);
        return -1;
    }

    int max_steps = 0;
    int steps = 0;
    int in_drone_section = 0;
    char line[128];

    while (fgets(line, sizeof(line), file)) {
        trim(line);
        if (strlen(line) == 0) continue;


        int id, dim1, dim2, dim3;
        if (sscanf(line, "%d - %dx%dx%d", &id, &dim1, &dim2, &dim3) == 4) {

            if (in_drone_section && steps > max_steps) {
                max_steps = steps;
            }
            steps = 0;
            in_drone_section = 1;
        } else {
            int x, y, z;
            if (sscanf(line, "%d %d %d", &x, &y, &z) == 3) {
                steps++;
            }
        }
    }

    if (in_drone_section && steps > max_steps) {
        max_steps = steps;
    }

    fclose(file);
    return max_steps;
}

void init_shared_zone(int *fd, Shared_data **shared_data) {

    shm_unlink("/shm");
    *fd = shm_open("/shm",O_CREAT | O_EXCL | O_RDWR, S_IRUSR | S_IWUSR);
    if (*fd == -1) {
        perror("shm_open");
        exit(1);
    }

    if (ftruncate(*fd, sizeof(Shared_data)) == -1) {
        perror("ftruncate");
        exit(2);
    }

    *shared_data = (Shared_data *)mmap(NULL, sizeof(Shared_data),PROT_READ | PROT_WRITE, MAP_SHARED, *fd, 0);
    if (*shared_data == MAP_FAILED) {
        perror("mmap");
        exit(3);
    }

    memset(*shared_data, 0, sizeof(Shared_data));
    
}

void fill_info(const char* filename, DroneInformation* dronesIDs, int num_drones) {
    FILE* file = fopen(filename, "r");
    if (!file) {
        perror("Failed to open script file");
        return;
    }

    char line[128];
    int ids_found = 0;

    while (fgets(line, sizeof(line), file) && ids_found < num_drones) {
        trim(line);
        if (strlen(line) == 0) 
            continue;

        
        int id, dim1, dim2, dim3;
        if (sscanf(line, "%d - %dx%dx%d", &id, &dim1, &dim2, &dim3) == 4) {
           
            dronesIDs[ids_found].id = id;
            
            int max_dim = dim1;
            if (dim2 > max_dim) max_dim = dim2;
            if (dim3 > max_dim) max_dim = dim3;
            dronesIDs[ids_found].biggestDimension = max_dim;
            ids_found++;
        }
    }

    fclose(file);
}


int calculate_acceptable_collision_number(int numberOfDrones, float percentage) {
    return (int) floor(numberOfDrones * percentage);
}

void printTimeOfSimulation(int timeStamp,int totalTicks) {
    char simulationTimeMSG[100];
    if(timeStamp < totalTicks){
        int len = snprintf(
            simulationTimeMSG, sizeof(simulationTimeMSG),
            "\n%s═════| %sSIMULATION TIME - %d time units %s|═════%s\n\n",
            ANSI_BRIGHT_BLACK, ANSI_BRIGHT_WHITE, timeStamp, ANSI_BRIGHT_BLACK, ANSI_RESET
        );
        write(STDOUT_FILENO, simulationTimeMSG, len);
    }else{
        int len = snprintf(
            simulationTimeMSG, sizeof(simulationTimeMSG),

            "\n%s═════| %s        END OF SIMULATION       %s|═════%s\n\n",
            ANSI_BRIGHT_BLACK, ANSI_BRIGHT_WHITE, ANSI_BRIGHT_BLACK, ANSI_RESET
        );
        write(STDOUT_FILENO, simulationTimeMSG, len);
    }

}

void printPositionDrone(Position position, int id) {
    char dronePositionMSG[150];
    if (position.z <= 0) {
        int len = snprintf(
            dronePositionMSG, sizeof(dronePositionMSG),
            "🚁 Drone with ID [%d] - 📍Located in coordinates (x = %d, y = %d, z = %s%d%s)\n",
            id, position.x, position.y, ANSI_BRIGHT_RED, position.z, ANSI_RESET
        );
        write(STDOUT_FILENO, dronePositionMSG, len);
    } else {
        int len = snprintf(
            dronePositionMSG, sizeof(dronePositionMSG),
            "🚁 Drone with ID [%d] - 📍Located in coordinates (x = %d, y = %d, z = %d)\n",
            id, position.x, position.y, position.z
        );
        write(STDOUT_FILENO, dronePositionMSG, len);
    }
}

void* collision_thread_func(void* arg) {
    CollisionThreadArgs* args = (CollisionThreadArgs*)arg;
    while (1) {
        pthread_mutex_lock(args->mutex);
        while (!*(args->collision_tick_ready) && !*(args->stop_simulation)) {
            pthread_cond_wait(args->cond_tick, args->mutex);
        }
        if (*(args->stop_simulation)) {
            pthread_mutex_unlock(args->mutex);
            break;
        }
        
        int collisions_in_tick = collisionDetection(args->total_drones, args->stamps, args->stamps_capacity, args->stamps_count,
            args->shared_mem, args->drones_terminated, args->drones_terminated_size, args->drones_terminated_capacity);

        *(args->collision_counter) += collisions_in_tick;

        pthread_mutex_lock(args->mutex_report);
        *(args->report_tick_ready) = 1;
        *(args->report_tick_done) = 0;
        pthread_cond_signal(args->cond_tick_report);

        while (!*(args->report_tick_done)) {
            pthread_cond_wait(args->cond_done_report, args->mutex_report);
        }
        pthread_mutex_unlock(args->mutex_report);
        

        *(args->collision_tick_ready) = 0;
        *(args->collision_tick_done) = 1;
        pthread_cond_signal(args->cond_done);
        pthread_mutex_unlock(args->mutex);
    }
    return NULL;
}

void* environment_thread_func(void* arg) {
    EnvironmentThreadArgs* args = (EnvironmentThreadArgs*)arg;
    while(1) {
        pthread_mutex_lock(args->mutex);
        while(!*(args->environment_tick_ready) && !*(args->stop_simulation)) {
            pthread_cond_wait(args->cond_tick, args->mutex);
        }

        if(*(args->stop_simulation)) {
            pthread_mutex_unlock(args->mutex);
            break;
        }

        transfer_environmental_effects(args->environment, args->shared_mem);
        *(args->environment_tick_ready) = 0;
        *(args->environment_tick_done) = 1;
        pthread_cond_signal(args->cond_done);
        pthread_mutex_unlock(args->mutex);
    }

    return NULL;
}

void* report_thread_func(void* arg) {
    ReportThreadArgs* args = (ReportThreadArgs*) arg;
    while(1) {
        pthread_mutex_lock(args->mutex);
        while(!*(args->report_tick_ready) && !*(args->stop_simulation)) {
            pthread_cond_wait(args->cond_tick, args->mutex);
        }

        args->report->environment = args->environment;
        args->report->max_collisions = *(args->max_collisions);
        args->report->num_drones = *(args->num_drones);
        args->report->total_ticks = *(args->total_ticks);
        args->report->stamps = *(args->stamps);
        args->report->stamps_count = *(args->stamps_count);
        args->report->collisions = *(args->collisions);

        
        for(int drone_id = 0; drone_id < *(args->num_drones); drone_id++) {
            if (args->shared_mem->tick < args->report->total_ticks && drone_id < args->report->num_drones) {
                args->report->timeline[args->shared_mem->tick][drone_id] = args->shared_mem->drones_state[drone_id].position;
            }
        }  

        if (*(args->stop_simulation)) {
            args->report->passed = *(args->passed);
            generate_report(args->report, args->output_filename);
            pthread_mutex_unlock(args->mutex);
            break;
        }

        *(args->report_tick_ready) = 0;
        *(args->report_tick_done) = 1;
        pthread_cond_signal(args->cond_done);
        pthread_mutex_unlock(args->mutex);
    }
    return NULL;
}



void printDroneInEnd(int id) {
    char droneBaseMSG[100];
    int len = snprintf(
        droneBaseMSG, sizeof(droneBaseMSG),
        "🚁 Drone with ID [%d] - %s%s📍Has arrived to his final destination!%s\n",
        id,ANSI_BRIGHT_BLUE,ANSI_BOLD,ANSI_RESET
    );
    write(STDOUT_FILENO, droneBaseMSG, len);
}

void print_max_collisions_reached(int max_collisions) {
    char maxCollisionMSG[408];
    int len = snprintf(maxCollisionMSG, sizeof(maxCollisionMSG),
                "\n%s      ⚠️  Maximum number of collisions reached! [%d collisions allowed] ⚠️\nAll drones will now be immediately shut down to ensure the safety of the show.%s\n──────────────────────────────────────────────────────────────────────────────\n\n",
                ANSI_BRIGHT_RED, max_collisions, ANSI_RESET);
    write(STDOUT_FILENO, maxCollisionMSG, len);
}

void play_sound(const char* path) {
#ifdef __APPLE__
    char command[256];
    snprintf(command, sizeof(command), "afplay %s &", path);
    system(command);
#elif __linux__
    char command[256];
    snprintf(command, sizeof(command), "mpg123 \"%s\" > /dev/null 2>&1 &", path);;
    system(command);
#else
    printf("⚠️ Sound not supported on this OS.\n");
#endif
}

