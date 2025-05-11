package ui.drone;

import controller.GetDroneModelsController;
import controller.GetDronesController;
import domain.entity.Drone;
import domain.entity.DroneModel;
import utils.Utils;

import java.util.List;
import java.util.Optional;

/**
 * A user interface class responsible for listing active drones in the inventory.
 */
public class ListActiveDronesUI implements Runnable {

    /**
     * The controller for retrieving drones.
     */
    private final GetDronesController controller = new GetDronesController();

    /**
     * The controller for retrieving drone models.
     */
    private final GetDroneModelsController getModelController = new GetDroneModelsController();

    /**
     * Executes the process of listing active drones in the inventory.
     *
     * @return void
     */
    @Override
    public void run() {
        try {
            Utils.printDroneCenteredTitle("List Drones in the Inventory");

            Optional<List<DroneModel>> droneModelsOptional = getModelController.getAllModels();

            if (droneModelsOptional.isEmpty()) {
                Utils.printFailMessage("No drone models in the system yet...");
                Utils.dropLines(1);
                return;
            }

            int option = showSelectionMethodMenu();
            Optional<List<Drone>> dronesOptional;

            if (option == 0) {
                dronesOptional = showListDroneModel(droneModelsOptional.get());
            } else {
                dronesOptional = enterDroneModel();
            }

            showResultMessage(dronesOptional);

        } catch (RuntimeException ignored) {}
    }

    /**
     * Displays a menu to select the method for listing drones.
     *
     * @return the selected option as an integer
     */
    private int showSelectionMethodMenu() {
        return Utils.showSelectionMethodMenu(List.of("Select a Drone Model", "Enter a Drone Model"));
    }

    /**
     * Lists drones by selecting a drone model from a list.
     *
     * @param droneModelsOptional the list of available drone models
     * @return an Optional containing a list of matching drones, or empty if none found
     */
    private Optional<List<Drone>> showListDroneModel(List<DroneModel> droneModelsOptional) {
        int modelIndex = Utils.selectDroneModelIndex(droneModelsOptional);
        return controller.getDroneByModel(droneModelsOptional.get(modelIndex));
    }

    /**
     * Lists drones by manually entering a drone model ID.
     *
     * @return an Optional containing a list of matching drones, or empty if none found
     */
    private Optional<List<Drone>> enterDroneModel() {
        Utils.printCenteredSubtitleV2("Drone Model");
        Utils.showModelIDRules();
        Optional<DroneModel> dronesModelOptional = askDroneModel();

        return controller.getDroneByModel(dronesModelOptional.get());
    }

    /**
     * Prompts the user for a drone model ID and retrieves the corresponding model.
     *
     * @return an Optional containing the selected drone model, or empty if not found
     */
    private Optional<DroneModel> askDroneModel() {
        while (true) {
            String modelID = Utils.readLineFromConsole("Enter the Drone Model");
            Optional<DroneModel> selectedDroneModel = getModelController.getModelById(modelID);

            if (selectedDroneModel.isEmpty()) {
                Utils.printFailMessage("No drone model with that ID found in the system!");
                return Optional.empty();
            }

            Utils.dropLines(1);
            boolean confirmed = Utils.showDroneModelDetails(selectedDroneModel);
            if (confirmed) {
                return selectedDroneModel;
            }

            Utils.dropLines(1);
        }
    }

    /**
     * Displays the result message based on the list of active drones.
     *
     * @param result the Optional containing the list of active drones, or empty if none found
     * @return void
     */
    private void showResultMessage(Optional<List<Drone>> result) {
        System.out.println();

        if (result.isPresent()) {
            List<Drone> listOfDrones = result.get();
            Utils.dropLines(1);
            Utils.showListPartially(listOfDrones, "Active Drones");

        } else {
            Utils.printFailMessage("There are no drones of this model available in the system.");
            Utils.silentExit();
        }
    }
}