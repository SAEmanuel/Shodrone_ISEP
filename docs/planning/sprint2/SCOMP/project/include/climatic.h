#ifndef CLIMATIC_H
#define CLIMATIC_H

void chooseEfects(float x, float y, float z);

void apply_climatic_effects(float x, float y, float z,
                            int use_rain, int use_wind, int use_lightning,
                            int wind_direction[3], float wind_intensity[3],
                            float rain_intensity, int lightning_frequency);

#endif
