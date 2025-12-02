#ifndef STRUCTS_H
#define STRUCTS_H
#include <sys/types.h>
#include <pthread.h>

#define MAX_DRONES 200

typedef struct {
    int x, y, z;
    pid_t pid;
} Position;

typedef struct {
    int id;
    Position* positions; 
    int total_steps;  
    int current_step;  
} Drone;


typedef struct{
    int id;
    int biggestDimension;
    int has_crashed;
} DroneInformation;

typedef struct {
    Position position;
    DroneInformation drone_info;
} Drone_state;


typedef struct {
    int north;
    int south;
    int east;
    int west;
    int rain;
} Environment;

typedef struct {
    Drone_state drones_state[MAX_DRONES];
    Environment environment;
    int tick;
} Shared_data;

typedef struct {
    int id_drone1;
    int id_drone2;
    int collision_time;
} Collision_Stamp;

typedef struct {
    int num_drones;         
    int total_ticks;        
    int collisions;         
    int passed; 
    int max_collisions;       
    Position** timeline;
    Collision_Stamp* stamps;  
    Environment* environment;
    int stamps_count;
    char simulation_name[64];          
} Report;


typedef struct {
    int total_drones;
    Shared_data* shared_mem;
    DroneInformation** drones_terminated;
    int* drones_terminated_size;
    int* drones_terminated_capacity;
    Collision_Stamp** stamps;
    int* stamps_capacity;
    int* stamps_count;
    int* collision_counter;
    int* stop_simulation;
    pthread_mutex_t* mutex;
    pthread_cond_t* cond_tick;
    pthread_cond_t* cond_done;
    int* collision_tick_ready;
    int* collision_tick_done;
    pthread_mutex_t* mutex_report;
    pthread_cond_t* cond_tick_report;
    pthread_cond_t* cond_done_report;
    int* report_tick_ready;
    int* report_tick_done;
} CollisionThreadArgs;


typedef struct {
    Environment* environment;
    Shared_data* shared_mem;
    int number_of_collisions;
    pthread_mutex_t* mutex;
    pthread_cond_t* cond_tick;
    pthread_cond_t* cond_done;
    int* stop_simulation;
    int* environment_tick_ready;
    int* environment_tick_done;
} EnvironmentThreadArgs;

typedef struct {
    Report* report;
    char output_filename[128];
    Shared_data* shared_mem;
    Collision_Stamp** stamps;
    int* stamps_count;
    int* collisions;
    int* max_collisions;
    int* num_drones;
    int* total_ticks;
    Environment* environment;
    pthread_mutex_t* mutex;
    pthread_cond_t* cond_tick;
    pthread_cond_t* cond_done;
    int* passed;
    int* stop_simulation;
    int* report_tick_ready;
    int* report_tick_done;
} ReportThreadArgs;





#endif