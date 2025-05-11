package ui;

import controller.GetDronesController;
import controller.RemoveDroneInventoryController;
import domain.entity.Drone;
import domain.valueObjects.DroneRemovalLog;
import domain.valueObjects.DroneStatus;
import utils.Utils;

import java.util.List;
import java.util.Optional;

/**
 * User interface for removing drones from the inventory system.
 */
public class RemoveDroneInventoryUI implements Runnable {
    private final RemoveDroneInventoryController controller = new RemoveDroneInventoryController();
    private final GetDronesController getDronesController = new GetDronesController();

    /**
     * Executes the drone removal process, including drone selection and removal reason input.
     */
    @Override
    public void run() {
        Utils.printDroneCenteredTitle("Remove Drone from Inventory");

        Optional<List<Drone>> dronesOptional = getDronesController.getAllDrones();

        if (dronesOptional.isEmpty()) {
            Utils.printFailMessage("No drones in the system yet...");
            return;
        }

        Utils.printCenteredSubtitleV2("Drone Serial Number");
        Utils.showDroneSerialNumberFormat();
        Optional<Drone> selectedDrone = askForSerialNumber();

        if (selectedDrone.isEmpty()) {
            return;
        }

        Utils.printCenteredSubtitleV2("Removal Reason");
        Utils.showDroneRemovalRules();
        DroneRemovalLog log = Utils.rePromptWhileInvalid("Enter removal justification: ", DroneRemovalLog::new);

        Optional<Drone> removedDrone = controller.removeDrone(selectedDrone.get(), log);

        Utils.dropLines(1);

        if (removedDrone.isEmpty()) {
            Utils.printFailMessage("Removal failed!");
        } else {
            Utils.printSuccessMessage("Drone removed successfully!");
        }
    }

    /**
     * Prompts the user to enter a drone serial number and validates it.
     *
     * @return an Optional containing the selected Drone if valid, empty otherwise
     */
    private Optional<Drone> askForSerialNumber() {
        while (true) {
            String serialNumber = Utils.readLineFromConsole("Enter the Drone Serial Number");
            Optional<Drone> selectedDrone = getDronesController.getDroneBySN(serialNumber);

            if (selectedDrone.isEmpty()) {
                Utils.printFailMessage("No drone with that Serial Number found in the system!");
                return Optional.empty();
            } else if (selectedDrone.get().droneStatus() != DroneStatus.AVAILABLE) {
                Utils.printFailMessage("This drone is no longer available in the inventory. Current status: " + selectedDrone.get().droneStatus());
                return Optional.empty();
            }

            boolean confirmed = Utils.showDroneDetails(selectedDrone);
            if (confirmed) {
                return selectedDrone;
            }

            Utils.dropLines(1);
        }
    }
}