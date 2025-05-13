#ifndef DRONE_H
#define DRONE_H
#include <signal.h>


typedef struct {
    int x, y, z;
    pid_t pid;
} Position;


typedef struct {
    int id;
    Position* script; 
    int total_steps;  
    int current_step;  
} Drone;


void simulate_drone(const char* filename, int drone_id, int pipe_fd, pid_t pid);

#endif
