package drone;

import Interface.DroneProgramGenerator;

public class DroneProgramGeneratorRegistry {

    private final DroneProgramGenerator generator = new DroneProgramsGenerator();

    public DroneProgramGenerator getGenerator() {
        return generator;
    }
}
