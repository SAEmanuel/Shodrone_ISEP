#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <unistd.h>

// Forward declarations
void apply_climatic_effects(float x, float y, float z,
                            int use_rain, int use_wind, int use_lightning,
                            int wind_direction[3], float wind_intensity[3],
                            float rain_intensity, int lightning_frequency);

void chooseEfects(float x, float y, float z) {
    FILE* file = fopen("climatic.txt", "r");
    if (!file) {
        perror("Failed to open climatic.txt");
        return;
    }

    char line[128];
    int use_rain = 0, use_wind = 0, use_lightning = 0;
    int wind_direction[3] = {1,1,1};
    float wind_intensity[3] = {0,0,0};
    float rain_intensity = 0;
    int lightning_frequency = 0;

    while (fgets(line, sizeof(line), file)) {
        if (strncmp(line, "wind x:", 7) == 0) {
            sscanf(line + 7, "%d", &wind_direction[0]);
            use_wind = 1;
        } else if (strncmp(line, "wind x intensity:", 17) == 0) {
            sscanf(line + 17, "%f", &wind_intensity[0]);
            use_wind = 1;
        } else if (strncmp(line, "wind y:", 7) == 0) {
            sscanf(line + 7, "%d", &wind_direction[1]);
            use_wind = 1;
        } else if (strncmp(line, "wind y intensity:", 17) == 0) {
            sscanf(line + 17, "%f", &wind_intensity[1]);
            use_wind = 1;
        } else if (strncmp(line, "wind z:", 7) == 0) {
            sscanf(line + 7, "%d", &wind_direction[2]);
            use_wind = 1;
        } else if (strncmp(line, "wind z intensity:", 17) == 0) {
            sscanf(line + 17, "%f", &wind_intensity[2]);
            use_wind = 1;
        } else if (strncmp(line, "rain intensity:", 15) == 0) {
            sscanf(line + 15, "%f", &rain_intensity);
            use_rain = 1;
        } else if (strncmp(line, "lightning frequency:", 20) == 0) {
            char *p = strchr(line, ':');
            if (p) {
                lightning_frequency = atoi(p + 1);
                use_lightning = 1;
            }
        }

    }

    fclose(file);

    apply_climatic_effects(x, y, z,
                           use_rain, use_wind, use_lightning,
                           wind_direction, wind_intensity,
                           rain_intensity, lightning_frequency);
}

void apply_climatic_effects(float x, float y, float z,
                            int use_rain, int use_wind, int use_lightning,
                            int wind_direction[3], float wind_intensity[3],
                            float rain_intensity, int lightning_frequency) {
    if (use_rain && rain_intensity > 0.05) {
        char msg[100];
        int len = snprintf(msg, sizeof(msg),
                           " - Rain: intensity %.2f at (%.2f, %.2f, %.2f)\n",
                           rain_intensity, x, y + rain_intensity, z);
        write(STDOUT_FILENO, msg, len);

        if (rain_intensity > 0.7f) {
            float damage_chance = (float)rand() / RAND_MAX;
            if (damage_chance < 0.15f) {
                len = snprintf(msg, sizeof(msg),
                               "💧 Drone damaged by heavy rain at (%.2f, %.2f, %.2f)!\n", x, y, z);
                write(STDOUT_FILENO, msg, len);
            }
        }
    }

    if (use_wind) {
        float total_intensity = wind_intensity[0] + wind_intensity[1] + wind_intensity[2];
        if (total_intensity > 0.05f) {
            char msg[150];
            float new_x = x + wind_direction[0] * wind_intensity[0];
            float new_y = y + wind_direction[1] * wind_intensity[1];
            float new_z = z + wind_direction[2] * wind_intensity[2];
            int len = snprintf(msg, sizeof(msg),
                               "\n - Wind: direction vector (%d, %d, %d), intensity vector (%.2f, %.2f, %.2f)\n"
                               "   Wind effect at new position: (%.2f, %.2f, %.2f)\n",
                               wind_direction[0], wind_direction[1], wind_direction[2],
                               wind_intensity[0], wind_intensity[1], wind_intensity[2],
                               new_x, new_y, new_z);
            write(STDOUT_FILENO, msg, len);

            if (total_intensity > 0.6f) {
                float damage_chance = (float)rand() / RAND_MAX;
                if (damage_chance < 0.2f) {
                    len = snprintf(msg, sizeof(msg),
                                   "🌪️ Drone damaged by strong wind at (%.2f, %.2f, %.2f)!\n", x, y, z);
                    write(STDOUT_FILENO, msg, len);
                }
            }
        }
    }

    if (use_lightning && lightning_frequency > 0) {
        char msg[100];
        int len = snprintf(msg, sizeof(msg),
                           " - Lightning: %d strike(s) per second\n", lightning_frequency);
        write(STDOUT_FILENO, msg, len);

        for (int i = 0; i < lightning_frequency; ++i) {
            float strike_chance = (float)rand() / RAND_MAX;
            if (strike_chance < 0.1f) {
                len = snprintf(msg, sizeof(msg),
                               "🌩️ Drone has been struck by lightning at (%.2f, %.2f, %.2f)!\n", x, y, z);
                write(STDOUT_FILENO, msg, len);
            }
        }
    }
}
