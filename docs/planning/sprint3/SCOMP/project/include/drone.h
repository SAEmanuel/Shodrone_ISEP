#ifndef DRONE_H
#define DRONE_H
#include <signal.h>
#include <semaphore.h>
#include <sys/_types.h>
#include "data.h"
#include "structs.h"


void simulate_drone(const char* filename, int drone_id, int total_ticks, Shared_data* shared_mem, sem_t* sem_child, sem_t* sem_parent);

#endif
