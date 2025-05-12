#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

#include <signal.h>

#include <dirent.h> // For folder handling
#include <sys/stat.h>


//#include "principal_structs.h"
#include "simulation.h"
#include "parser.h"

#define MAX_DRONES 100
#define MAX_FILENAME 512
#define DRONE_FOLDER "simulation folder"
#define COLLISION_LIMIT 3

// Estrutura para guardar colisões
typedef struct {
    int t;
    int drone1;
    int drone2;
    int x, y, z;
} CollisionRecord;

int compare_filenames(const void *a, const void *b) {
    const char *fa = *(const char **)a;
    const char *fb = *(const char **)b;
    return strcmp(fa, fb);
}

int list_drone_files(const char *folder, char *filenames[MAX_DRONES]) {
    DIR *dir;
    struct dirent *entry;
    int count = 0;

    dir = opendir(folder);
    if (!dir) {
        perror("opendir");
        return -1;
    }

    while ((entry = readdir(dir)) != NULL) {
        if (entry->d_name[0] == '.') continue;
        char fullpath[MAX_FILENAME];
        snprintf(fullpath, sizeof(fullpath), "%s/%s", folder, entry->d_name);

        struct stat path_stat;
        stat(fullpath, &path_stat);

        if (S_ISREG(path_stat.st_mode)) {
            filenames[count] = strdup(fullpath);
            count++;
        }
    }

    closedir(dir);
    qsort(filenames, count, sizeof(char *), compare_filenames);
    return count;
}

int main() {
    /*
    Figure figure;

    // HAND ADDED FIGURE
    strcpy(figure.name, "Spiral");

    // Dynamically allocate memory for drones (3 drones)
    figure.drones = malloc(3 * sizeof(Drone));  // 3 drones

    // Initialize drone data with 5 positions each
    for (int i = 0; i < 3; i++) {
        figure.drones[i].droneNumber = i + 1;
        figure.drones[i].numberOfSteps = 5;  // Set the number of steps (positions) for this drone
        figure.drones[i].positions = malloc(figure.drones[i].numberOfSteps * sizeof(Position));  // Allocate positions memory dynamically

        for (int j = 0; j < figure.drones[i].numberOfSteps; j++) {
            figure.drones[i].positions[j].x = j + i;  // Set x position
            figure.drones[i].positions[j].y = j * 2 + i;  // Set y position
            figure.drones[i].positions[j].z = 0;  // Set z position (could be any value)
            figure.drones[i].positions[j].timeOfPosition = j;  // Set time of position as the index
        }
    }

    // Set up pipes and fork for each drone
    int droneCount = 3;  // 3 drones

    //int droneCount = parseDroneData(figure);

    if (droneCount == 0) {
        return 1;
    }*/

    char *filenames[MAX_DRONES];
    int droneCount = list_drone_files(DRONE_FOLDER, filenames);
    if (droneCount <= 0) {
        fprintf(stderr, "No drone scripts found.\n");
        exit(EXIT_FAILURE);
    }

    int pipes[droneCount][2];
    pid_t pids[droneCount];

    //Space
    //PositionUpdate matrix[MAX_DRONES + 1][MAX_TIMESTEPS];

    /*
    //collision section
    CollisionRecord collisions[100];
    int collision_count = 0;
    int total_collisions = 0;*/

    for (int i = 0; i < droneCount; i++) {
        if (pipe(pipes[i]) == -1) {
            perror("Pipe failed");
            exit(EXIT_FAILURE);
        }

        printf("🚁 Drone %d will use script: %s\n", i + 1, filenames[i]);

        pid_t pid = fork();
        
        if (pid == -1) {
            perror("Fork failed");
            exit(EXIT_FAILURE);
        }
        
        if (pid == 0) {
            close(pipes[i][0]);
            simulateDroneMovement(i + 1, filenames[i], pipes[i][1]);
            exit(0);
        } else {
            close(pipes[i][1]);
            pids[i] = pid;
        }
    }
    
    PositionUpdate dronePos;
    for (int i = 0; i < droneCount; i++) {      
        while (read(pipes[i][0], &dronePos, sizeof(dronePos)) > 0) {
            printf("Drone: %d  Time: %d  Coordenates: %d %d %d\n", dronePos.drone_id, dronePos.timestep, dronePos.x, dronePos.y, dronePos.z);
        }
        printf("\n");

        close(pipes[i][0]);
    }

    for (int i = 0; i < droneCount; i++) {
        wait(NULL);
    }

    return 0;
}