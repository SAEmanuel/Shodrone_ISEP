#include <stdio.h>
#include <time.h>
#include <stdlib.h>
#include <unistd.h>
#include "report.h"
#include "data.h"
#include "ui.h"

void play_sound(const char* path);

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
    fprintf(file, "║ Date & Time:    %s\n", datetime);
    fprintf(file, "║ Simulation:     %s\n", proposal->simulation_name);
    fprintf(file, "╠══════════════════════════════════════════════════╣\n");
    fprintf(file, "║ Drones:         %d\n", proposal->num_drones);
    fprintf(file, "║ Total Ticks:    %d\n", proposal->total_ticks);
    fprintf(file, "║ Max Collisions: %d\n", proposal->max_collisions);
    fprintf(file, "║ Collisions:     %d\n", proposal->collisions);
    fprintf(file, "║ Final Status:   %s\n", proposal->passed ? "✅ APPROVED" : "❌ REJECTED");
    fprintf(file, "╠══════════════════════════════════════════════════╣\n");

    fprintf(file, "║ Environmental Conditions:\n");
    Environment* env = proposal->environment;
    int any_wind = 0;
    if (env) {
        if (env->north > 0) {
            fprintf(file, "║   Wind North:  ON  (Intensity: %d)\n", env->north);
            any_wind = 1;
        }
        if (env->south > 0) {
            fprintf(file, "║   Wind South:  ON  (Intensity: %d)\n", env->south);
            any_wind = 1;
        }
        if (env->east > 0) {
            fprintf(file, "║   Wind East:   ON  (Intensity: %d)\n", env->east);
            any_wind = 1;
        }
        if (env->west > 0) {
            fprintf(file, "║   Wind West:   ON  (Intensity: %d)\n", env->west);
            any_wind = 1;
        }
        if (!any_wind) {
            fprintf(file, "║   Wind:        OFF\n");
        }
        if (env->rain > 0) {
            fprintf(file, "║   Rain:        ON  (Intensity: %d)\n", env->rain);
        } else {
            fprintf(file, "║   Rain:        OFF\n");
        }
    } else {
        fprintf(file, "║   No environment data available\n");
    }
    fprintf(file, "╚══════════════════════════════════════════════════╝\n\n");

    fprintf(file, "─── Detailed Timeline ───\n");

    int* base_printed = calloc(proposal->num_drones, sizeof(int));

    for (int tick = 0; tick < proposal->total_ticks; tick++) {
        fprintf(file, "\nTick %d:\n", tick);
        for (int i = 0; i < proposal->num_drones; i++) {
            Position pos = proposal->timeline[tick][i];
            fprintf(file, "  🚁 Drone %d: (%d, %d, %d)\n", i, pos.x, pos.y, pos.z);


            if (!base_printed[i] && pos.x == -1 && pos.y == -1 && pos.z == -1) {
                int prev_inactive = 0;
                if (tick > 0) {
                    Position prev = proposal->timeline[tick-1][i];
                    prev_inactive = (prev.x == -1 && prev.y == -1 && prev.z == -1);
                }

                if (!prev_inactive) {
                    fprintf(file, "🚁 Drone with ID [%d] -📍Has arrived to his final destination!\n", i);
                    base_printed[i] = 1;
                }
            }
        }

        for (int x = 0; x < proposal->stamps_count; x++) {
            if (proposal->stamps[x].collision_time == tick) {
                if (proposal->stamps[x].id_drone2 == -1) {
                    fprintf(file, "  ❗ Drone [%d] collided with the ground\n",
                            proposal->stamps[x].id_drone1);
                } else {
                    fprintf(file, "  💥 Collision detected between drone [%d] and [%d]\n",
                            proposal->stamps[x].id_drone1, proposal->stamps[x].id_drone2);
                }
            }
        }
    }
    free(base_printed);

    if (proposal->passed) {
        play_sound("sounds/success2.mp3");
    } else {
        play_sound("sounds/fail.mp3");
    }

    fclose(file);
}





