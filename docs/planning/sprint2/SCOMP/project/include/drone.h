#ifndef DRONE_H
#define DRONE_H
#include <signal.h>
#include "environment.h"
#include "position.h"

typedef struct {
    int id;
    Position* script; 
    int total_steps;  
    int current_step;  
} Drone;


void simulate_drone(const char* filename, int drone_id, int position_pipes, pid_t pid, int environment_pipes);
void handle_sigusr1(int sig);

#endif
