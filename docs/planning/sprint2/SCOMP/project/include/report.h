#ifndef REPORT_H
#define REPORT_H
#include "drone.h"

typedef struct {
    Drone* drones;           
    int num_drones;          
    int total_ticks;         
    Position*** timeline;  
    int collisions;          
    int passed;              
} ShowProposal;

#endif