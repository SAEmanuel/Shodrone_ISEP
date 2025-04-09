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
    int active;
    int collided;
    int pipe_fd;
    pid_t pid;
} Drone;



#endif
