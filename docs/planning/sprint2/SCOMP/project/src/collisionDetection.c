#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/select.h>
#include <math.h>
#include "data.h"
#include "report.h"
#include "drone.h"

// Raio adicional que pode ser ajustado facilmente
const float COLLISION_RADIUS_EXTRA = 3.0f; 

float calculateDistance(Position a, Position b) {
    float dx = a.x - b.x;
    float dy = a.y - b.y;
    float dz = a.z - b.z;
    return sqrtf(dx * dx + dy * dy + dz * dz);
}

int collisionDetection(int numberOfDrones, int total_ticks, Radar historyOfRadar[numberOfDrones][total_ticks], int timeStamp) {
    for (int i = 0; i < numberOfDrones; i++) {
        Radar droneA = historyOfRadar[i][timeStamp];
        float radiusA = (droneA.droneInformation.biggestDimension / 2.0f) + COLLISION_RADIUS_EXTRA;

        for (int j = i + 1; j < numberOfDrones; j++) {
            Radar droneB = historyOfRadar[j][timeStamp];
            float radiusB = (droneB.droneInformation.biggestDimension / 2.0f) + COLLISION_RADIUS_EXTRA;

            float distance = calculateDistance(droneA.position, droneB.position);
            float combinedRadius = radiusA + radiusB;

            //Enviar sinais TODO 
            if (distance < combinedRadius) {
                printf("Collision detected between drone %d and %d at time %d\n", 
                       droneA.droneInformation.id, 
                       droneB.droneInformation.id, 
                       timeStamp);
                return 1;
            }
        }
    }
    return 0; // No collision detected
}

