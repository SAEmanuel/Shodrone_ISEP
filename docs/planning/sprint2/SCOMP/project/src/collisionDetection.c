#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/select.h>
#include <math.h>
#include <signal.h>
#include "data.h"
#include "report.h"
#include "drone.h"

const float COLLISION_RADIUS_EXTRA = 0.5f;
volatile sig_atomic_t number_of_collision = 0;

float calculateDistance(Position a, Position b) {
    float dx = a.x - b.x;
    float dy = a.y - b.y;
    float dz = a.z - b.z;
    return sqrtf(dx * dx + dy * dy + dz * dz);
}

int collisionDetection(int numberOfDrones, int total_ticks, Radar historyOfRadar[numberOfDrones][total_ticks],int timeStamp) {
    for (int i = 0; i < numberOfDrones; i++) {
        Radar droneA = historyOfRadar[i][timeStamp];
        if (historyOfRadar[i][timeStamp].terminated) {
            continue;
        }

        float radiusA = (droneA.droneInformation.biggestDimension / (2.0f * 100)) + COLLISION_RADIUS_EXTRA;

        for (int j = i + 1; j < numberOfDrones; j++) {
            Radar droneB = historyOfRadar[j][timeStamp];
            if (historyOfRadar[j][timeStamp].terminated) {
                continue;
            }

            float radiusB = (droneB.droneInformation.biggestDimension / (2.0f * 100)) + COLLISION_RADIUS_EXTRA;
            float distance = calculateDistance(droneA.position, droneB.position);
            float combinedRadius = radiusA + radiusB;

            if (distance < combinedRadius) {
                char collisionMSG[100];
                int len = snprintf(collisionMSG,sizeof(collisionMSG),"💥 Collision detected between drone [%d] and [%d] at time - %d\n",droneA.droneInformation.id,droneB.droneInformation.id,timeStamp);
                write(STDOUT_FILENO,collisionMSG,len);

                kill(historyOfRadar[i][timeStamp].position.pid, SIGUSR1);
                kill(historyOfRadar[j][timeStamp].position.pid, SIGUSR1);
                number_of_collision+=1;
            }
        }
    }
    return number_of_collision; 
}
