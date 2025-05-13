#ifndef DATA_H
#define DATA_H
#include "drone.h"
#include "report.h"

typedef struct{
    DroneInformation droneInformation;
    int timeStamp;
    Position position;
}Radar;

typedef struct{
    int id;
    int biggestDimension;
}DroneInformation;

int get_drone_number_from_file(char* file_name);
int get_total_ticks_from_file(const char* filename);
void trim(char* str);
void fill_ids(const char* filename, int* dronesIDs, int num_drones);



#endif