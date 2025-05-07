package ui;

import controller.AddDroneInventoryController;
import controller.GetDroneModelsController;
import domain.entity.Drone;
import domain.entity.DroneModel;
import domain.valueObjects.SerialNumber;
import utils.Utils;

import java.util.List;
import java.util.Optional;

public class AddDroneInventoryUI implements Runnable {

    private final AddDroneInventoryController controller = new AddDroneInventoryController();
    private final GetDroneModelsController getModelController = new GetDroneModelsController();

    public void run() {
        Utils.printDroneCenteredTitle("Add Drone to Inventory");

        Optional<List<DroneModel>> droneModelsOptional = getModelController.getAllModels();

        if (droneModelsOptional.isPresent()) {
            Utils.printCenteredSubtitleV2("Drone Model Selection");

            int index = Utils.showAndSelectIndexPartially(droneModelsOptional.get(), "Select the desired drone model:");

            if (index < 0) {
                Utils.printFailMessage("No drone model selected.");
                return;
            }
            DroneModel chosenDroneModel = droneModelsOptional.get().get(index);

            Utils.printCenteredSubtitleV2("Drone Serial Number");
            Utils.showDroneSerialNumberRules();
            SerialNumber sn = Utils.rePromptWhileInvalid("Enter Drone Serial Number: ", SerialNumber::new);

            Optional<Drone> result = controller.addDroneInventory(chosenDroneModel, sn);

            if (result.isPresent()) {
                System.out.println();
                Utils.printSuccessMessage("Drone added successfully!");
            } else {
                System.out.println();
                Utils.printFailMessage("A Drone with that Serial Number already exists in the system!");
            }
        } else {
            Utils.printFailMessage("No drone models in the system yet...");
        }
    }
}
