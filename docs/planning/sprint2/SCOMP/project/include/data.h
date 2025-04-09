#ifndef DATA_H
#define DATA_H
#include "drone.h"
#include "report.h"

int get_drone_number_from_file(char* file_name);
int load_script_data(char* filename, ShowProposal* proposal, Drone** drones);

#endif