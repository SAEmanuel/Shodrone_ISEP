// simulation.h
#ifndef SIMULATION_H
#define SIMULATION_H

//#include "principal_structs.h"
#include "drone.h"
#include "parser.h"

#define LINE_LEN 256

void simulateDroneMovement(int id, const char *filepath, int write_fd);
void generate_movements(Drone *drone, PositionUpdate *movs, int id, int steps);

#endif
