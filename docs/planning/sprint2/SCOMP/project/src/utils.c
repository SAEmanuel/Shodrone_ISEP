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


        int id, dim1, dim2, dim3;
        if (sscanf(line, "%d - %dx%dx%d", &id, &dim1, &dim2, &dim3) == 4) {

            if (in_drone_section && steps > max_steps) {
                max_steps = steps;
            }
            steps = 0;
            in_drone_section = 1;
        } else {
            int x, y, z;
            if (sscanf(line, "%d %d %d", &x, &y, &z) == 3) {
                steps++;
            }
        }
    }

    if (in_drone_section && steps > max_steps) {
        max_steps = steps;
    }

    fclose(file);
    return max_steps;
}

void fill_info(const char* filename, DroneInformation* dronesIDs, int num_drones) {
    FILE* file = fopen(filename, "r");
    if (!file) {
        perror("Failed to open script file");
        return;
    }

    char line[128];
    int ids_found = 0;

    while (fgets(line, sizeof(line), file) && ids_found < num_drones) {
        trim(line);
        if (strlen(line) == 0) 
            continue;

        
        int id, dim1, dim2, dim3;
        if (sscanf(line, "%d - %dx%dx%d", &id, &dim1, &dim2, &dim3) == 4) {
           
            dronesIDs[ids_found].id = id;
            
            int max_dim = dim1;
            if (dim2 > max_dim) max_dim = dim2;
            if (dim3 > max_dim) max_dim = dim3;
            dronesIDs[ids_found].biggestDimension = max_dim;
            ids_found++;
        }
    }

    fclose(file);
}

