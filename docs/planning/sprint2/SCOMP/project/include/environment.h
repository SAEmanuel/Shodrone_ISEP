#ifndef CLIMATIC_H
#define CLIMATIC_H
#include "position.h"

typedef struct {
    int north;
    int south;
    int east;
    int west;
    int rain;
} Environment;

void read_enviroment_info(Environment* environment);
void transfer_environmental_effects(Environment* enviroment, int pipe);
void apply_environment_effects(Environment* env, Position* pos);

#endif
