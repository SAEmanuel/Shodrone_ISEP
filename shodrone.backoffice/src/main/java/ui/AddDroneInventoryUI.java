package ui;

import controller.AddDroneInventoryController;
import controller.GetDroneModelsController;
import controller.GetDronesController;
import domain.entity.Drone;
import domain.entity.DroneModel;
import domain.valueObjects.DroneStatus;
import domain.valueObjects.SerialNumber;
import utils.Utils;

import java.rmi.server.UID;
import java.util.List;
import java.util.Optional;

public class AddDroneInventoryUI implements Runnable {

    private final AddDroneInventoryController controller = new AddDroneInventoryController();
    private final GetDroneModelsController getModelController = new GetDroneModelsController();
    private final GetDronesController getDronesController = new GetDronesController();

    @Override
    public void run() {
        try {
            Utils.printDroneCenteredTitle("Add Drone to Inventory");

            Optional<List<DroneModel>> droneModelsOptional = getModelController.getAllModels();
            if (droneModelsOptional.isEmpty()) {
                Utils.printFailMessage("No drone models in the system yet...");
                return;
            }

            int option = showAddMethodMenu();
            Optional<Drone> result = Optional.empty();

            if (option == 0) {
                result = handleNewDrone(droneModelsOptional.get());
            } else if (option > 0) {
                result = handleExistingDrone();
            }

            showResultMessage(result);

        } catch (RuntimeException e) {}
    }

    private int showAddMethodMenu() {
        List<String> options = List.of("Create a new Drone", "Add an existing Drone");

        int option = Utils.showAndSelectIndexCustomOptions(options, "What do you want to do?");
        if (option < 0) {
            Utils.silentExit();
        }

        return option;
    }

    private Optional<Drone> handleNewDrone(List<DroneModel> droneModels) {
        Utils.dropLines(1);
        Utils.printCenteredSubtitleV2("Drone Model Selection");

        int index = Utils.showAndSelectIndexPartially(droneModels, "Select the desired drone model:");
        if (index < 0) {
            Utils.printFailMessage("No drone model selected.");
            Utils.silentExit();
        }

        DroneModel chosenModel = droneModels.get(index);

        Utils.printCenteredSubtitleV2("Drone Serial Number");
        Utils.showDroneSerialNumberRules();
        SerialNumber sn = Utils.rePromptWhileInvalid("Enter Drone Serial Number: ", SerialNumber::new);

        return controller.addDroneInventory(chosenModel, sn);
    }

    private Optional<Drone> handleExistingDrone() {
        Optional<List<Drone>> dronesOptional = getDronesController.getAllDrones();
        if (dronesOptional.isEmpty()) {
            Utils.dropLines(1);
            Utils.printFailMessage("No drones in the system yet...");
            Utils.silentExit();
        }


        Utils.printCenteredSubtitleV2("Drone Serial Number");
        Utils.showDroneSerialNumberFormat();

        Optional<Drone> selectedDrone = askForSerialNumber();
        return controller.addExistingDroneInventory(selectedDrone);

    }

    private Optional<Drone> askForSerialNumber() {
        while (true) {
            String serialNumber = Utils.readLineFromConsole("Enter the Drone Serial Number");
            Optional<Drone> selectedDrone = getDronesController.getDroneBySN(serialNumber);

            if (selectedDrone.isEmpty()) {
                Utils.printFailMessage("No drone with that Serial Number found in the system!");
                return Optional.empty();
            }

            Drone drone = selectedDrone.get();
            if (drone.droneStatus() == DroneStatus.AVAILABLE) {
                Utils.printFailMessage("This drone is already in the inventory");
                return Optional.empty();
            }

            boolean confirmed = Utils.showDroneDetails(selectedDrone);
            if (confirmed) {
                return selectedDrone;
            }

            Utils.dropLines(1);
        }
    }

    private void showResultMessage(Optional<Drone> result) {
        System.out.println();
        if (result.isPresent()) {
            Utils.printSuccessMessage("Drone added successfully!");
        } else {
            Utils.printFailMessage("A Drone with that Serial Number already exists in the system!");
        }
    }
}
