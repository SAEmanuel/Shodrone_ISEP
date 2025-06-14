#ifndef CLIMATIC_H
#define CLIMATIC_H
#include <semaphore.h>
#include "structs.h"


void read_enviroment_info(Environment* environment);
void transfer_environmental_effects(Environment* environment, Shared_data* shared_mem);
void apply_environment_effects(Environment* env, Position* pos);

#endif
