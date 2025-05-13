#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <signal.h>
#include <ctype.h>
#include "drone.h"
#include "data.h"


#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include "drone.h"

// Supondo que já tens a função trim(char*) implementada

void simulate_drone(const char* filename, int drone_id, int pipe_fd, pid_t pid) {
    FILE* file = fopen(filename, "r");
    if (!file) {
        perror("Drone failed to open script file");
        exit(EXIT_FAILURE);
    }

    // Inicializa a struct Drone
    Drone drone = {
        .id = drone_id,
        .script = NULL,
        .total_steps = 0,
        .current_step = 0
    };

    char line[128];
    int found_section = 0;
    int capacity = 10;

    // 1. Procura o cabeçalho do drone no ficheiro
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

    // 2. Aloca memória inicial para o script
    drone.script = malloc(sizeof(Position) * capacity);
    if (!drone.script) {
        perror("Failed to allocate initial script");
        fclose(file);
        exit(EXIT_FAILURE);
    }

    // 3. Lê todas as posições do script até ao próximo cabeçalho ou EOF
    while (fgets(line, sizeof(line), file)) {
        trim(line);
        if (strlen(line) == 0)
            continue;

        // Se encontrar um novo cabeçalho, termina o bloco deste drone
        int id, dim1, dim2, dim3;
        if (sscanf(line, "%d - %dx%dx%d", &id, &dim1, &dim2, &dim3) == 4)
            break;

        // Expande o array se necessário
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

        // Lê e armazena a posição
        if (sscanf(line, "%d %d %d",
                   &drone.script[drone.total_steps].x,
                   &drone.script[drone.total_steps].y,
                   &drone.script[drone.total_steps].z) != 3) {
            fprintf(stderr, "Drone %d: Invalid coordinate line: %s\n", drone.id, line);
            continue;
        }
        drone.total_steps++;
    }

    // 4. Envia as posições via pipe
    for (drone.current_step = 0; drone.current_step < drone.total_steps; drone.current_step++) {
        Position current_pos = drone.script[drone.current_step];
        current_pos.pid = pid;
        ssize_t bytes_written = write(pipe_fd, &current_pos, sizeof(Position));
        if (bytes_written == -1) {
            perror("Drone failed to write to pipe");
            free(drone.script);
            fclose(file);
            exit(EXIT_FAILURE);
        }
        //raise(SIGSTOP);
        usleep(100000); // 100ms entre passos (opcional)
    }

    // 5. Limpeza final
    free(drone.script);
    fclose(file);
    close(pipe_fd);
    exit(EXIT_SUCCESS);
}

