#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/select.h>
#include <string.h>
#include <time.h>
#include "data.h"
#include "report.h"
#include "drone.h"
#include "simulation.h"
#include "ui.h"
#include "environment.h"
#include "position.h"

int run_simulation(char* argv, float percentage)
{
    Environment environment;
    read_enviroment_info(&environment);

    char filename[256];
    snprintf(filename, sizeof(filename), "scripts/%s", argv);

    int num_drones = get_drone_number_from_file(filename);
    int max_collisions = calculate_acceptable_collision_number(num_drones, percentage);
    int collision_counter = 0;

    if (num_drones <= 0) {
        fprintf(stderr, "Invalid or unreadable script file: %s\n", filename);
        return 1;
    }

    DroneInformation *drones_info = calloc(num_drones, sizeof(DroneInformation));
    fill_info(filename, drones_info, num_drones);

    int total_ticks = get_total_ticks_from_file(filename);
    if (total_ticks <= 0) {
        fprintf(stderr, "Failed to determine total ticks\n");
        free(drones_info);
        return 1;
    }

    int position_pipes[num_drones][2];
    int environment_pipes[num_drones][2];
    pid_t pids[num_drones];

    Report report_of_simulation = {
        .num_drones = num_drones,
        .total_ticks = total_ticks,
        .collisions = 0,
        .passed = 1,
        .max_collisions = max_collisions
    };

    Collision_Stamp *stamps = NULL;
    int stamps_capacity = 0;
    int stamps_count = 0;

    strncpy(report_of_simulation.simulation_name, argv, sizeof(report_of_simulation.simulation_name));
    
    report_of_simulation.timeline = malloc(total_ticks * sizeof(Position*));
    for (int i = 0; i < total_ticks; i++) {
        report_of_simulation.timeline[i] = malloc(num_drones * sizeof(Position));
        for (int j = 0; j < num_drones; j++) {
            report_of_simulation.timeline[i][j].x = -1;
            report_of_simulation.timeline[i][j].y = -1;
            report_of_simulation.timeline[i][j].z = -1;
        }
    }   

    for (int droneNumber = 0; droneNumber < num_drones; droneNumber++) {
        if (pipe(position_pipes[droneNumber]) == -1) {
            perror("Error creating position pipe");
            exit(EXIT_FAILURE);
        }

        if (pipe(environment_pipes[droneNumber]) == -1) {
            perror("Error creating environment pipe");
            exit(EXIT_FAILURE);
        }

        pids[droneNumber] = fork();
        if (pids[droneNumber] == -1) {
            perror("Error forking!");
            exit(EXIT_FAILURE);
        }

        if (pids[droneNumber] == 0) {
          
            srand(time(NULL) + getpid()); 
            close(position_pipes[droneNumber][0]);     
            close(environment_pipes[droneNumber][1]);  

            simulate_drone(filename,drones_info[droneNumber].id,position_pipes[droneNumber][1],getpid(),environment_pipes[droneNumber][0], total_ticks);

            close(position_pipes[droneNumber][1]);
            close(environment_pipes[droneNumber][0]);
            free(drones_info);
            exit(EXIT_SUCCESS);
        }

        close(position_pipes[droneNumber][1]);      
        close(environment_pipes[droneNumber][0]);   
        transfer_environmental_effects(&environment, environment_pipes[droneNumber][1]);
        close(environment_pipes[droneNumber][1]);   
    }

    Radar historyOfRadar[num_drones][total_ticks];

    for (int timeStamp = 0; timeStamp < total_ticks; timeStamp++) {
        printTimeOfSimulation(timeStamp);

        for (int childNumber = 0; childNumber < num_drones; childNumber++) {
            Position current_pos;
            ssize_t bytes_read = read(position_pipes[childNumber][0], &current_pos, sizeof(current_pos));

            if (bytes_read == -1) {
                perror("Father failed to read from pipe\n");
                exit(EXIT_FAILURE);
            }
            
            report_of_simulation.timeline[timeStamp][childNumber] = current_pos;

            if (bytes_read == sizeof(Position)) {
                int is_terminated = (timeStamp > 0)
                    ? historyOfRadar[childNumber][timeStamp - 1].terminated
                    : 0;

                int returned_to_base = (timeStamp > 0 &&
                    current_pos.x == -1 &&
                    current_pos.y == -1 &&
                    current_pos.z == -1) ? 1 : 0;

                 if (current_pos.x == -1 && current_pos.y == -1 && current_pos.z == -1) {
                    historyOfRadar[childNumber][timeStamp].terminated = 1;
                }

                if (!is_terminated && !returned_to_base) {
                    printPositionDrone(current_pos, drones_info[childNumber].id);
                } else if (returned_to_base) {
                    printDroneInBase(drones_info[childNumber].id);
                }

                Radar radarOfDrone = {
                    .droneInformation = drones_info[childNumber],
                    .timeStamp = timeStamp,
                    .position = current_pos,
                    .terminated = is_terminated
                };

                historyOfRadar[childNumber][timeStamp] = radarOfDrone;

            } else if (bytes_read == 0) {
                historyOfRadar[childNumber][timeStamp].terminated = 1;
            }
        }

        int collisions_in_tick = collisionDetection(num_drones, total_ticks, historyOfRadar, timeStamp,&stamps, &stamps_capacity, &stamps_count);
        report_of_simulation.stamps = stamps;
        report_of_simulation.stamps_count = stamps_count;

        collision_counter += collisions_in_tick;

        if (collision_counter > max_collisions) {
            char maxCollisionMSG[408];
            int len = snprintf(
                maxCollisionMSG, sizeof(maxCollisionMSG),
                "\n%s      ⚠️  Maximum number of collisions reached! [%d collisions allowed] ⚠️\nAll drones will now be immediately shut down to ensure the safety of the show.%s\n──────────────────────────────────────────────────────────────────────────────\n\n",
                ANSI_BRIGHT_RED, max_collisions, ANSI_RESET
            );
            write(STDOUT_FILENO, maxCollisionMSG, len);

            report_of_simulation.passed = 0;

            for (int i = 0; i < num_drones; i++) {
                kill(pids[i], SIGUSR1);
                waitpid(pids[i], NULL, WUNTRACED);
            }
            break;
            
        }
    }

    for (int i = 0; i < num_drones; i++) {
        kill(pids[i], SIGCONT);
        waitpid(pids[i], NULL, 0);
        close(position_pipes[i][0]);
    }

    free(drones_info);

    report_of_simulation.collisions = collision_counter;
    report_of_simulation.environment = &environment;
    char output_filename[128];
    snprintf(output_filename, sizeof(output_filename), "./reports/report_%s_%d.txt", argv, collision_counter);
    generate_report(&report_of_simulation, output_filename);

    for (int i = 0; i < total_ticks; i++) {
        free(report_of_simulation.timeline[i]);
    }
    free(report_of_simulation.stamps);
    free(report_of_simulation.timeline);

    return 0;
}

void printTimeOfSimulation(int timeStamp) {
    char simulationTimeMSG[100];
    int len = snprintf(
        simulationTimeMSG, sizeof(simulationTimeMSG),
        "\n%s═════| %sSIMULATION TIME - %d time units %s|═════%s\n\n",
        ANSI_BRIGHT_BLACK, ANSI_BRIGHT_WHITE, timeStamp, ANSI_BRIGHT_BLACK, ANSI_RESET
    );
    write(STDOUT_FILENO, simulationTimeMSG, len);
}

void printPositionDrone(Position position, int id) {
    char dronePositionMSG[150];
    if (position.z <= 0) {
        int len = snprintf(
            dronePositionMSG, sizeof(dronePositionMSG),
            "🚁 Drone with ID [%d] - 📍Located in coordinates (x = %d, y = %d, z = %s%d%s)\n",
            id, position.x, position.y, ANSI_BRIGHT_RED, position.z, ANSI_RESET
        );
        write(STDOUT_FILENO, dronePositionMSG, len);
    } else {
        int len = snprintf(
            dronePositionMSG, sizeof(dronePositionMSG),
            "🚁 Drone with ID [%d] - 📍Located in coordinates (x = %d, y = %d, z = %d)\n",
            id, position.x, position.y, position.z
        );
        write(STDOUT_FILENO, dronePositionMSG, len);
    }
}

void printDroneInBase(int id) {
    char droneBaseMSG[100];
    int len = snprintf(
        droneBaseMSG, sizeof(droneBaseMSG),
        "🚁 Drone with ID [%d] has returned to base 🏠\n",
        id
    );
    write(STDOUT_FILENO, droneBaseMSG, len);
}

