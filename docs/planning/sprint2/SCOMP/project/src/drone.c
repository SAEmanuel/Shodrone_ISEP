#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <signal.h>
#include <ctype.h>
#include "drone.h"
#include "data.h"


static volatile sig_atomic_t terminated = 0;


void handle_sigusr1(int sig) {
    terminated = 1; 
}


void simulate_drone(const char* filename, int drone_id, int pipe_fd, pid_t pid) {

    struct sigaction sa;
    memset(&sa, 0, sizeof(sa));
    sa.sa_handler = handle_sigusr1;
    sa.sa_flags = SA_RESTART;
    sigfillset(&sa.sa_mask);

    if (sigaction(SIGUSR1, &sa, NULL) == -1) {
        perror("Failed to set up SIGUSR1 handler");
        exit(EXIT_FAILURE);
    }

    FILE* file = fopen(filename, "r");
    if (!file) {
        perror("Drone failed to open script file");
        exit(EXIT_FAILURE);
    }

    Drone drone = {
        .id = drone_id,
        .script = NULL,
        .total_steps = 0,
        .current_step = 0
    };

    char line[128];
    int found_section = 0;
    int capacity = 10;

    // 1. Procurar cabeçalho do drone
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

    // 2. Alocar memória para o script
    drone.script = malloc(sizeof(Position) * capacity);
    if (!drone.script) {
        perror("Failed to allocate initial script");
        fclose(file);
        exit(EXIT_FAILURE);
    }

    // 3. Ler todas as posições do script até ao próximo cabeçalho ou EOF
    while (fgets(line, sizeof(line), file)) {
        trim(line);
        if (strlen(line) == 0)
            continue;

        int id, dim1, dim2, dim3;
        if (sscanf(line, "%d - %dx%dx%d", &id, &dim1, &dim2, &dim3) == 4)
            break;

        if (drone.total_steps >= capacity) {
            capacity *= 2;
            Position* new_script = realloc(drone.script, sizeof(Position) * capacity);
            if (!new_script) {
                perror("Failed to realloc script");
                free(drone.script);
                fclose(file);
                exit(EXIT_FAILURE);
            }
            drone.script = new_script;
        }

        if (sscanf(line, "%d %d %d",
                   &drone.script[drone.total_steps].x,
                   &drone.script[drone.total_steps].y,
                   &drone.script[drone.total_steps].z) != 3) {
            fprintf(stderr, "Drone %d: Invalid coordinate line: %s\n", drone.id, line);
            continue;
        }
        drone.total_steps++;
    }

    // 4. Enviar posições via pipe, parar se receber sinal
    for (drone.current_step = 0; drone.current_step < drone.total_steps && !terminated; drone.current_step++) {
        Position current_pos = drone.script[drone.current_step];
        current_pos.pid = pid;
        ssize_t bytes_written = write(pipe_fd, &current_pos, sizeof(Position));
        if (bytes_written == -1) {
            perror("Drone failed to write to pipe");
            free(drone.script);
            fclose(file);
            exit(EXIT_FAILURE);
        }
        usleep(100000); 

        if (terminated) {
            break;
        }
    }

    if (terminated) {
        char buffer[100];
        int len = snprintf(buffer, sizeof(buffer), "⛔ Drone [%d] leaving the show due to collision\n", drone.id);
        write(STDOUT_FILENO, buffer, len);
    }

    free(drone.script);
    fclose(file);
    close(pipe_fd);
    exit(EXIT_SUCCESS);
}

