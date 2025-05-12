#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <ctype.h>
#include <sys/types.h>
#include <sys/wait.h>
#include "data.h"
#include "report.h"


void trim(char* str) {
    if (str == NULL) 
        return;

    char* start = str;
    while (isspace((unsigned char)*start)) 
        start++;

    char* end = str + strlen(str) - 1;
    while (end > start && isspace((unsigned char)*end)) 
        end--;

    *(end + 1) = '\0';

    memmove(str, start, end - start + 2);
}


int get_drone_number_from_file(char* file_name) {
    int n;

    int matched = sscanf(file_name, "scripts/script_%d_drones.txt", &n);

    if (matched == 1 && n > 0) {
        return n;
    } else {
        fprintf(stderr, "Wrong file name format!\n");
        fprintf(stderr, "File format must be: script_x_drones.txt, where x is the number of drones to be used.\n");
        return -1; 
    }
}


int get_total_ticks_from_file(const char* filename) {
    FILE* file = fopen(filename, "r");
    if (!file) {
        fprintf(stderr, "Could not open file: %s\n", filename);
        return -1;
    }

    int max_steps = 0;
    int steps = 0;
    int in_drone_section = 0;
    char line[128];

    while (fgets(line, sizeof(line), file)) {
        trim(line);
        if (strlen(line) == 0) continue;

        int id, x, y, z;
        if (sscanf(line, "%d", &id) == 1 && sscanf(line, "%d %d %d", &x, &y, &z) != 3) {
            if (in_drone_section && steps > max_steps) {
                max_steps = steps;
            }
            steps = 0;
            in_drone_section = 1;
        } else if (sscanf(line, "%d %d %d", &x, &y, &z) == 3) {
            steps++;
        }
    }
    if (in_drone_section && steps > max_steps) {
        max_steps = steps;
    }

    fclose(file);
    return max_steps;
}

