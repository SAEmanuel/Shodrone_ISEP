package drone;

import domain.entity.DroneModel;

public class DroneModelInformation {

    private final DroneModel model;
    private final int usedQty;
    private final int maxAvailable;

    public DroneModelInformation(DroneModel model, int usedQty, int maxAvailable) {
        this.model = model;
        this.usedQty = usedQty;
        this.maxAvailable = maxAvailable;
    }

    public DroneModel model() {
        return model;
    }

    public int usedQty() {
        return usedQty;
    }

    public int maxAvailable() {
        return maxAvailable;
    }
}
