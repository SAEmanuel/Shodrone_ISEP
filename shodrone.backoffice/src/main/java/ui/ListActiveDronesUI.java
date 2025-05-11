package ui;

import controller.GetDroneModelsController;
import controller.GetDronesController;
import domain.entity.Drone;
import domain.entity.DroneModel;
import utils.Utils;

import java.util.List;
import java.util.Optional;

public class ListActiveDronesUI implements Runnable {

    private final GetDronesController controller = new GetDronesController();
    private final GetDroneModelsController getModelController = new GetDroneModelsController();

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

        }catch (RuntimeException ignored) {}
    }


    private int showSelectionMethodMenu() {
        return Utils.showSelectionMethodMenu(List.of("Select a Drone Model", "Enter a Drone Model"));
    }

    private Optional<List<Drone>> showListDroneModel(List<DroneModel> droneModelsOptional) {
        int modelIndex = Utils.selectDroneModelIndex(droneModelsOptional);
        return controller.getDroneByModel(droneModelsOptional.get(modelIndex)) ;
    }

    private Optional<List<Drone>> enterDroneModel() {
        Utils.printCenteredSubtitleV2("Drone Model");
        Utils.showModelIDRules();
        Optional<DroneModel> dronesModelOptional = askDroneModel();

        return controller.getDroneByModel(dronesModelOptional.get());
    }

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
