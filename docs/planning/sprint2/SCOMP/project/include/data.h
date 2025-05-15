#ifndef DATA_H
#define DATA_H
#include "drone.h"
#include "report.h"

typedef struct{
    int id;
    int biggestDimension;
} DroneInformation;

typedef struct{
    DroneInformation droneInformation;
    int timeStamp;
    Position position;
    int terminated;
} Radar;


int get_drone_number_from_file(char* file_name);
int get_total_ticks_from_file(const char* filename);
void trim(char* str);
void fill_info(const char* filename, DroneInformation* dronesIDs, int num_drones);
int collisionDetection(int numberOfDrones, int total_ticks, Radar historyOfRadar[numberOfDrones][total_ticks], int timeStamp,  Collision_Stamp **stamps, int *stamps_capacity, int *stamps_count);
int calculate_acceptable_collision_number(int numberOfDrones);
void printTimeOfSimulation(int timeStamp);
void printPositionDrone(Position, int id);



#endif