#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#include "drone.h"
#include "tinyexpr.h"

int parse_drone_script(const char *filename, Drone *drone) {
    FILE *f = fopen(filename, "r");
    if (!f) return 0;

    char line[100];
    while (fgets(line, sizeof(line), f)) {
        if (strncmp(line, "p0:", 3) == 0) {
            sscanf(line + 3, "%d %d %d", &drone->x0, &drone->y0, &drone->z0);
        } else if (strncmp(line, "fx:", 3) == 0) {
            sscanf(line + 3, "%[^\n]", drone->fx);
        } else if (strncmp(line, "fy:", 3) == 0) {
            sscanf(line + 3, "%[^\n]", drone->fy);
        } else if (strncmp(line, "fz:", 3) == 0) {
            sscanf(line + 3, "%[^\n]", drone->fz);
        }
    }
    
    fclose(f);
    return 1;
}
