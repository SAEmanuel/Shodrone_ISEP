#ifndef DATA_H
#define DATA_H
#include "report.h"
#include "structs.h"


int get_drone_number_from_file(char* file_name);
int get_total_ticks_from_file(const char* filename);
void trim(char* str);
int calculate_acceptable_collision_number(int numberOfDrones, float percentage);
void fill_info(const char* filename, DroneInformation* dronesIDs, int num_drones);
void init_shared_zone(int *fd, Shared_data **shared_data);
int collisionDetection(int total_drones, Collision_Stamp** stamps, int* stamps_capacity, int* stamps_count, Shared_data* shared_mem, DroneInformation** drones_terminated, int* drones_terminated_size, int* capacity);
void* collision_thread_func(void* arg);
void add_terminated_drone(int id, int crashed, DroneInformation** drones_terminated, int* size, int* capacity);
int is_drone_crashed(int id, DroneInformation* drones_terminated, int size);
int in_final_position(int pos_x, int pos_y, int pos_z);
int not_showed_yet(int drone_id, int* drone_crash_printed);
void printDroneCrash(int id);
void print_max_collisions_reached(int max_collisions);
void fill_stamp_moment(Drone_state droneA, Drone_state droneB, int timeStamp, Collision_Stamp** stamps, int *stamps_capacity, int *stamps_count);
void printTimeOfSimulation(int timeStamp,int);
void printPositionDrone(Position, int id);
void printDroneInEnd(int id);
void play_sound(const char* path);



#endif