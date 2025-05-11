package ui;

import controller.AddDroneInventoryController;
import controller.GetDroneModelsController;
import controller.GetDronesController;
import domain.entity.Drone;
import domain.entity.DroneModel;
import domain.valueObjects.DroneStatus;
import domain.valueObjects.SerialNumber;
import utils.Utils;

import java.util.List;
import java.util.Optional;

/**
 * A user interface class responsible for adding a drone to the inventory.
 */
public class AddDroneInventoryUI implements Runnable {

    /**
     * The controller for adding drones to the inventory.
     */
    private final AddDroneInventoryController controller = new AddDroneInventoryController();

    /**
     * The controller for retrieving drone models.
     */
    private final GetDroneModelsController getModelController = new GetDroneModelsController();

    /**
     * The controller for retrieving drones.
     */
    private final GetDronesController getDronesController = new GetDronesController();

    /**
     * Executes the process of adding a drone to the inventory.
     *
     * @return void
     */
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

        } catch (RuntimeException ignored) {}
    }

    /**
     * Displays a menu to select the method for adding a drone.
     *
     * @return the selected option as an integer
     */
    private int showAddMethodMenu() {
        return Utils.showSelectionMethodMenu(List.of("Create a new Drone", "Add an existing Drone"));
    }

    /**
     * Handles the creation and addition of a new drone to the inventory.
     *
     * @param droneModels the list of available drone models
     * @return an Optional containing the added Drone, or empty if the serial number already exists
     */
    private Optional<Drone> handleNewDrone(List<DroneModel> droneModels) {
        int modelIndex = Utils.selectDroneModelIndex(droneModels);
        DroneModel chosenModel = droneModels.get(modelIndex);

        Utils.printCenteredSubtitleV2("Drone Serial Number");
        Utils.showDroneSerialNumberRules();
        SerialNumber sn = Utils.rePromptWhileInvalid("Enter Drone Serial Number: ", SerialNumber::new);

        return controller.addDroneInventory(chosenModel, sn);
    }

    /**
     * Handles the addition of an existing drone to the inventory.
     *
     * @return an Optional containing the added Drone, or empty if the drone is already in the inventory
     */
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

    /**
     * Prompts the user for a drone serial number and retrieves the corresponding drone.
     *
     * @return an Optional containing the selected Drone, or empty if not found or already in inventory
     */
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

    /**
     * Displays the result message based on the outcome of adding a drone.
     *
     * @param result the Optional containing the added Drone, or empty if the operation failed
     * @return void
     */
    private void showResultMessage(Optional<Drone> result) {
        System.out.println();
        if (result.isPresent()) {
            Utils.printSuccessMessage("Drone added successfully!");
        } else {
            Utils.printFailMessage("A Drone with that Serial Number already exists in the system!");
        }
    }
}