// simulation.c
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>

#include <signal.h>

#include "drone.h"
#include "parser.h"

#include "simulation.h"
#include "principal_structs.h"

typedef struct {
    int droneNumber;
    int x, y, z;
    int timeOfPosition;
} DroneStruct;

#define MAX_TIMESTEPS 5

// Signal handler para colisão com bloqueio de outros sinais
void handle_collision(int signo) {
    printf("🚨 Drone received SIGUSR1 – collision detected!\n");
    sleep(1);
}

void simulateDroneMovement(int id, const char *filepath, int write_fd) {
    struct sigaction sig;
    sig.sa_handler = handle_collision;
    sigemptyset(&sig.sa_mask);
    sig.sa_flags = 0;
    sigaction(SIGUSR1, &sig, NULL);
    
    Drone drone;
    PositionUpdate space[MAX_TIMESTEPS];

    if (!parse_drone_script(filepath, &drone)) {
        fprintf(stderr, "❌ Failed to load drone script: %s\n", filepath);
        exit(EXIT_FAILURE);
    }
    
    generate_movements(&drone, space, id, MAX_TIMESTEPS);

    for (int i = 0; i < MAX_TIMESTEPS; i++) {
        write(write_fd, &space[i], sizeof(PositionUpdate));
        sleep(1);
    }
}


/*void simulateDroneMovement(int droneNumber, Position *positions, int numberOfSteps, int pipefd) {
    for (int i = 0; i < numberOfSteps; i++) {
        DroneStruct buffer;
        buffer.droneNumber = droneNumber;
        buffer.timeOfPosition = positions[i].timeOfPosition;
        buffer.x = positions[i].x;
        buffer.y = positions[i].y;
        buffer.z = positions[i].z;
        write(pipefd, &buffer, sizeof(buffer));
    }

    close(pipefd);
    exit(0);
}*/