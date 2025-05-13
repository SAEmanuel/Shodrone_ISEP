#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/select.h>
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

    
    int num_drones = get_drone_number_from_file(filename);
    if (num_drones <= 0) {
        fprintf(stderr, "Invalid or unreadable script file: %s\n", filename);
        return 1;
    }

    DroneInformation *drones_info = calloc(num_drones,sizeof(DroneInformation));
    fill_info(filename, drones_info, num_drones);
    

    int total_ticks = get_total_ticks_from_file(filename);
    if (total_ticks <= 0) {
        fprintf(stderr, "Failed to determine total ticks\n");
        return 1;
    }

    //------------------------------------//

    int pipes[num_drones][2];
    pid_t pids[num_drones];

    for(int droneNumber = 0; droneNumber < num_drones; droneNumber++){
        pids[droneNumber] = fork();

        if(pids[droneNumber] == -1){
            perror("Error forking!\n");
            exit(EXIT_FAILURE);
        }

        if(pids[droneNumber] == 0){
            close(pipes[droneNumber][0]);

            simulate_drone(filename,drones_info[droneNumber].id,pipes[droneNumber][0]);

            close(pipes[droneNumber][1]);
            exit(EXIT_SUCCESS);
        }

        close(pipes[droneNumber][1]);
    }

    int status;
    Radar historyOfRadar[num_drones][total_ticks];

    for(int timeStamp = 0; timeStamp < total_ticks; timeStamp++){
        
        for(int childNumber = 0; childNumber < num_drones; childNumber++){
            waitpid(pids[childNumber],&status,WUNTRACED);
            
            Position current_pos;
            ssize_t bytes_read = read(pipes[childNumber][0],&current_pos,sizeof(current_pos));
            
            if (bytes_read == -1) {
                perror("Father failed to read from pipe\n");
                exit(EXIT_FAILURE);
            }
        
            Radar radarOfDrone = {
                .timeStamp = timeStamp,
                .position = current_pos
            };

            historyOfRadar[childNumber][timeStamp] = radarOfDrone;
        }
        
        collisionDetection();
    }

   

    return 0;
}
