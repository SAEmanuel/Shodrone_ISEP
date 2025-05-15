#include <stdio.h>
#include <time.h>
#include "report.h"
#include "data.h"

void generate_report(Report* proposal, const char* filename) {
    FILE* file = fopen(filename, "w");
    if (!file) {
        perror("❌ Failed to create report file");
        return;
    }

    time_t now = time(NULL);
    struct tm *t = localtime(&now);
    char datetime[64];
    strftime(datetime, sizeof(datetime), "%Y-%m-%d %H:%M:%S", t);

    fprintf(file, "╔══════════════════════════════════════════════════╗\n");
    fprintf(file, "║                DRONE SIMULATION REPORT           ║\n");
    fprintf(file, "╠══════════════════════════════════════════════════╣\n");
    fprintf(file, "║ Date & Time:  %s\n", datetime);
    fprintf(file, "║ Simulation:   %s\n", proposal->simulation_name);
    fprintf(file, "╠══════════════════════════════════════════════════╣\n");
    fprintf(file, "║ Drones:         %d\n", proposal->num_drones);
    fprintf(file, "║ Total Ticks:    %d\n", proposal->total_ticks);
    fprintf(file, "║ Collisions:     %d\n", proposal->collisions);
    fprintf(file, "║ Final Status:   %s\n", proposal->passed ? "✅ APPROVED" : "❌ REJECTED");
    fprintf(file, "╚══════════════════════════════════════════════════╝\n\n");

    fprintf(file, "─── Detailed Timeline ───\n");
    for (int tick = 0; tick < proposal->total_ticks; tick++) {
        fprintf(file, "\nTick %d:\n", tick + 1);
        for (int i = 0; i < proposal->num_drones; i++) {
            Position pos = proposal->timeline[tick][i];
            fprintf(file, "  🚁 Drone %d: (%d, %d, %d)%s\n", 
                i, pos.x, pos.y, pos.z, 
                (pos.x == -1) ? " (INACTIVE)" : ""
            );
        }
    }

    fclose(file);
}
