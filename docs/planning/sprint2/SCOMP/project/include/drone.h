#ifndef DRONE_H
#define DRONE_H

typedef struct {
    int x, y, z;
} Position;


typedef struct {
    int id;
    Position* script; 
    int total_steps;  
    int current_step;  
} Drone;


void simulate_drone(const char* filename, int drone_id, int pipe_fd);

#endif
