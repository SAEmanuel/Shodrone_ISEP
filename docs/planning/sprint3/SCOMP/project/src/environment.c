#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <unistd.h>
#include <environment.h>
#include "drone.h"
#include "position.h"


void transfer_environmental_effects(Environment* environment, int pipe_fd) {
    ssize_t bytes_written = write(pipe_fd, environment, sizeof(Environment));
    if (bytes_written == -1) {
        perror("Father failed to write to pipe");
        exit(EXIT_FAILURE);
    }

    if (bytes_written != sizeof(Environment)) {
        fprintf(stderr, "Incomplete write to pipe (expected %zu, got %zd bytes)\n",
                sizeof(Environment), bytes_written);
        exit(EXIT_FAILURE);
    }
}


void read_enviroment_info(Environment* environment) {
    FILE* file = fopen("environment/environment.txt", "r");
    if (!file) {
        perror("Failed to open environment.txt");
        exit(EXIT_FAILURE);
    }

    char line[256];
    int wind_status[4] = {-1, -1, -1, -1}; 
    int rain_on = 0; 

    while (fgets(line, sizeof(line), file)) {
        line[strcspn(line, "\n")] = 0;

        if (strstr(line, "Wind direction:")) {
            char dir;
            char status[8];
            if (sscanf(line, "Wind direction: %c [%7[^]]", &dir, status) == 2) {
                int index = -1;
                if (dir == 'N') index = 0;
                else if (dir == 'S') index = 1;
                else if (dir == 'E') index = 2;
                else if (dir == 'W') index = 3;

                if (index != -1 && strcmp(status, "ON") == 0) {
                    wind_status[index] = 0; 
                }
            }
        }

        if (strstr(line, "Wind speed")) {
            char dir;
            int speed;
            if (sscanf(line, "Wind speed %c: %d", &dir, &speed) == 2) {
                if (dir == 'N' && wind_status[0] == 0) environment->north = speed;
                else if (dir == 'S' && wind_status[1] == 0) environment->south = speed;
                else if (dir == 'E' && wind_status[2] == 0) environment->east = speed;
                else if (dir == 'W' && wind_status[3] == 0) environment->west = speed;
            }
        }

        if (strstr(line, "Rain:")) {
            char rain_status[8];
            if (sscanf(line, "Rain: [%7[^]]", rain_status) == 1) {
                if (strcmp(rain_status, "ON") == 0) {
                    rain_on = 1;
                } else {
                    rain_on = 0;
                    environment->rain = -1;
                }
            }
        }

        if (strstr(line, "Rain intensity:")) {
            int intensity;
            if (sscanf(line, "Rain intensity: %d", &intensity) == 1 && rain_on) {
                environment->rain = intensity;
            }
        }
    }

    if (wind_status[0] != 0) environment->north = -1;
    if (wind_status[1] != 0) environment->south = -1;
    if (wind_status[2] != 0) environment->east = -1;
    if (wind_status[3] != 0) environment->west = -1;

    fclose(file);
}


int rand_inclusive(int max) {
    if (max <= 0) return 0;
    return rand() % (max + 1);
}

void apply_environment_effects(Environment* env, Position* pos) {
    if (env->north > 0)
        pos->y += rand_inclusive(env->north);

    if (env->south > 0)
        pos->y -= rand_inclusive(env->south);

    if (env->east > 0)
        pos->x += rand_inclusive(env->east);

    if (env->west > 0)
        pos->x -= rand_inclusive(env->west);

    if (env->rain > 0)
        pos->z -= rand_inclusive(env->rain);
}