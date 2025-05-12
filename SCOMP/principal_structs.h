#ifndef PRINCIPAL_STRUCTS_H
#define PRINCIPAL_STRUCTS_H

#define LINE_LEN 256

typedef struct {
    int x, y, z;
    int timeOfPosition;
} Position;

typedef struct {
    Position *positions;
    int droneNumber;
    int numberOfSteps;
} DroneTESTE;

typedef struct {
    char name[100];
    Drone *drones;
} Figure;

#endif
