#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/select.h>
#include "data.h"
#include "report.h"
#include "drone.h"
#include "simulation.h"
#include "ui.h"

void printTimeOfSimulation(int timeStamp);
void printPositionDrone(Position, int id);

int run_simulation(char* argv)
{
    char filename[256];
    snprintf(filename, sizeof(filename), "scripts/%s", argv);

    int num_drones = get_drone_number_from_file(filename);
    int max_collisions = calculate_acceptable_collision_number(num_drones);
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
        return 1;
    }

    int pipes[num_drones][2];
    pid_t pids[num_drones];


    for (int droneNumber = 0; droneNumber < num_drones; droneNumber++) {
        if (pipe(pipes[droneNumber]) == -1) {
            perror("Error creating pipe");
            exit(EXIT_FAILURE);
        }

        pids[droneNumber] = fork();
        if (pids[droneNumber] == -1) {
            perror("Error forking!");
            exit(EXIT_FAILURE);
        }

        if (pids[droneNumber] == 0) {
            close(pipes[droneNumber][0]);

            simulate_drone(filename, drones_info[droneNumber].id, pipes[droneNumber][1], getpid());

            close(pipes[droneNumber][1]);
            exit(EXIT_SUCCESS);
        }


        close(pipes[droneNumber][1]);
    }

    Radar historyOfRadar[num_drones][total_ticks];


    for (int timeStamp = 0; timeStamp < total_ticks; timeStamp++) {
        printTimeOfSimulation(timeStamp);

        for (int childNumber = 0; childNumber < num_drones; childNumber++) {
            Position current_pos;
            ssize_t bytes_read = read(pipes[childNumber][0], &current_pos, sizeof(current_pos));

            if (bytes_read == -1) {
                perror("Father failed to read from pipe\n");
                exit(EXIT_FAILURE);
            }
            

            if (bytes_read == sizeof(Position)) {
                int is_terminated = (timeStamp > 0)
                    ? historyOfRadar[childNumber][timeStamp - 1].terminated
                    : 0;

                if(!is_terminated){
                    printPositionDrone(current_pos,drones_info[childNumber].id);
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

        int collisions_in_tick = collisionDetection(num_drones, total_ticks, historyOfRadar, timeStamp);
        collision_counter+= collisions_in_tick;

        if (collision_counter > max_collisions) {
            printf("\n⚠️ Maximum number of collisions reached! [%d collisions allowed] ⚠️\nAll drones will now be immediately shut down to ensure the safety of the show.\n", max_collisions);

            for (int i = 0; i < num_drones; i++) {
                kill(pids[i], SIGUSR1);
            }
            break;
        }
    }


    for (int i = 0; i < num_drones; i++) {
        waitpid(pids[i], NULL, 0);
        close(pipes[i][0]);
    }

    free(drones_info);
    return 0;
}

void printTimeOfSimulation(int timeStamp){
    char simulationTimeMSG[100];
    int len = snprintf(simulationTimeMSG,sizeof(simulationTimeMSG),"\n%s═════| %sSIMULATION TIME - %d time units %s|═════%s\n\n",ANSI_BRIGHT_BLACK,ANSI_BRIGHT_WHITE,timeStamp,ANSI_BRIGHT_BLACK,ANSI_RESET);
    write(STDOUT_FILENO,simulationTimeMSG,len);
}

void printPositionDrone(Position position, int id){
    char dronePositionMSG[100];
    int len = snprintf(dronePositionMSG,sizeof(dronePositionMSG),"🚁 Drone with ID [%d] - 📍Located in coordinates (x = %d, y = %d, z = %d)\n",id,position.x,position.y,position.z);
    write(STDOUT_FILENO,dronePositionMSG,len);
}