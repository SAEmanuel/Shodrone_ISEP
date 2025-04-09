#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include "data.h"
#include "report.h"

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

int load_script_data(char* filename, ShowProposal* proposal, Drone** drones) {
    int num_drones = get_drone_number_from_file(filename);
    if (num_drones <= 0) return -1;

    FILE* file = fopen(filename, "r");
    if (!file) {
        perror("Failed to open script file");
        return -1;
    }

    proposal->num_drones = num_drones;
    proposal->collisions = 0;
    proposal->passed = 1;
    proposal->total_ticks = 0;

    *drones = calloc(num_drones, sizeof(Drone));
    if (!*drones) {
        perror("Failed to allocate drone array");
        fclose(file);
        return -1;
    }

    int current_id = -1;
    int pos = 0;
    char line[128];

    while (fgets(line, sizeof(line), file)) {
        if (strlen(line) < 2) continue;

        int id_test;
        if (sscanf(line, "%d", &id_test) == 1 && strspn(line, "0123456789\n") == strlen(line)) {
            current_id = id_test;

            if (current_id < 0 || current_id >= num_drones) {
                fprintf(stderr, "Invalid drone ID: %d\n", current_id);
                fclose(file);
                return -1;
            }

            Drone* d = &(*drones)[pos++];
            d->id = current_id;
            d->current_step = 0;
            d->active = 1;
            d->collided = 0;
            d->total_steps = 0;

            int capacity = 10;
            d->script = malloc(sizeof(Position) * capacity);
            if (!d->script) {
                perror("Failed to allocate script");
                fclose(file);
                return -1;
            }

            while (fgets(line, sizeof(line), file)) {
                int x, y, z;
                if (sscanf(line, "%d %d %d", &x, &y, &z) == 3) {
                    if (d->total_steps >= capacity) {
                        capacity *= 2;
                        d->script = realloc(d->script, sizeof(Position) * capacity);
                        if (!d->script) {
                            perror("realloc failed");
                            fclose(file);
                            return -1;
                        }
                    }
                    d->script[d->total_steps++] = (Position){x, y, z};
                } else {
                    fseek(file, -strlen(line), SEEK_CUR);
                    break;
                }
            }

            if (d->total_steps > proposal->total_ticks) {
                proposal->total_ticks = d->total_steps;
            }
        }
    }

    fclose(file);
    proposal->drones = *drones;
    return 0;
}