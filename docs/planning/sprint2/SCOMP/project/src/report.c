#include <stdio.h>
#include "report.h"
#include "data.h"
#include "drone.h"


void generate_report(Report* proposal, const char* filename) {
    FILE* file = fopen(filename, "w");
    if (!file) {
        perror("Failed to create report file");
        return;
    }

    fprintf(file, "=== Simulation Report ===\n");
    fprintf(file, "Drones: %d\n", proposal->num_drones);
    fprintf(file, "Total ticks: %d\n", proposal->total_ticks);
    fprintf(file, "Collisions detected: %d\n", proposal->collisions);
    fprintf(file, "Final status: %s\n\n", proposal->passed ? "APPROVED" : "REJECTED");

    fprintf(file, "Detailed Timeline:\n");
    for (int tick = 0; tick < proposal->total_ticks; tick++) {
        fprintf(file, "Tick %d:\n", tick + 1);
        for (int i = 0; i < proposal->num_drones; i++) {
            Position pos = proposal->timeline[tick][i];
            fprintf(file, "  Drone %d: (%d, %d, %d)%s\n", 
                i, pos.x, pos.y, pos.z, 
                (pos.x == -1) ? " (INACTIVE)" : ""
            );
        }
        fprintf(file, "\n");
    }

    fclose(file);
}
