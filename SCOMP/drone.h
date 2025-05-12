#ifndef DRONE_H
#define DRONE_H

#define MAX_EXPR_LEN 50
#define MAX_TIMESTEPS 5

typedef struct {
    int timestep;
    int x, y, z;
    int drone_id;
} PositionUpdate;

typedef struct {
    int x0, y0, z0;               // posição inicial
    char fx[MAX_EXPR_LEN];       // expressão de x(t)
    char fy[MAX_EXPR_LEN];       // expressão de y(t)
    char fz[MAX_EXPR_LEN];       // expressão de z(t)
} Drone;

#endif