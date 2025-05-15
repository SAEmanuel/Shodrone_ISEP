#ifndef REPORT_H
#define REPORT_H
#include "drone.h"


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
    int stamps_count;
    char simulation_name[64];          
} Report;

void generate_report(Report* proposal, const char* filename);

#endif