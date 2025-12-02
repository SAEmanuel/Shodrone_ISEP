#ifndef POSITION_H
#define POSITION_H
#include <sys/types.h>

typedef struct {
    int x, y, z;
    pid_t pid;
} Position;

#endif
