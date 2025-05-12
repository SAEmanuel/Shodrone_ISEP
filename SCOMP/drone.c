#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#include "drone.h"

void generate_movements(Drone *drone, PositionUpdate *movs, int id, int steps) {
    for (int t = 0; t < steps; t++) {
        movs[t].timestep = t;
        movs[t].drone_id = id;
        movs[t].x = t;
        movs[t].y = t;
        movs[t].z = t;
    }
}