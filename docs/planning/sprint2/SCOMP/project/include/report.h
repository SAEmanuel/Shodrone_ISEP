#ifndef REPORT_H
#define REPORT_H
#include "drone.h"

typedef struct {
    int num_drones;         
    int total_ticks;        
    int collisions;         
    int passed; 
    Drone* drones;          
    Position** timeline;  
    char simulation_name[64];          
} Report;

void generate_report(Report* proposal, const char* filename);

#endif