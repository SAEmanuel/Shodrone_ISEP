#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include "data.h"
#include "report.h"
#include "drone.h"

int main(int argc, char* argv[]) {
    if (argc < 2) {
        fprintf(stderr, "Usage: %s <script_x_drones.txt>\n", argv[0]);
        return 1;
    }

    char filename[256];
    snprintf(filename, sizeof(filename), "scripts/%s", argv[1]);

    ShowProposal proposal;
    Drone* drones = NULL;

    if (load_script_data(filename, &proposal, &drones) != 0) {
        fprintf(stderr, "Failed to load script data from '%s'.\n", filename);
        return 1;
    }

    printf("Script '%s' loaded successfully.\n", argv[1]);
    printf("Number of drones: %d\n", proposal.num_drones);
    printf("Total simulation ticks: %d\n", proposal.total_ticks);

    // simulate_show(&proposal);
    // generate_report(&proposal, "output_report.txt");

    for (int i = 0; i < proposal.num_drones; i++) {
        free(drones[i].script);
    }
    free(drones);

    return 0;
}