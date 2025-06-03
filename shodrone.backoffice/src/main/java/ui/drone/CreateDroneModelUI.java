package ui.drone;

import domain.entity.DroneModel;
import domain.valueObjects.Description;
import domain.valueObjects.DroneModelID;
import domain.valueObjects.DroneName;
import session.DatabaseSync;
import utils.Utils;
import controller.drone.CreateDroneModelController;

import java.util.Optional;

import static more.ColorfulOutput.*;

/**
 * A user interface class responsible for guiding the user through the process of creating a new Drone Model.
 * This class implements the {@link Runnable} interface to allow execution as a standalone task.
 */
public class CreateDroneModelUI implements Runnable {

    /**
     * The controller responsible for handling the business logic of creating a Drone Model.
     */
    private final CreateDroneModelController controller = new CreateDroneModelController();

    /**
     * Executes the process of creating a new Drone Model based on user input.
     *
     */
    @Override
    public void run() {
        Utils.printDroneCenteredTitle("Create Drone Model");

        Utils.printCenteredSubtitleV2("Drone Model ID");
        Utils.showModelIDRules();
        DroneModelID modelID = Utils.rePromptWhileInvalid("Enter the Drone Model ID", DroneModelID::new);

        Utils.printCenteredSubtitleV2("Drone Model Name");
        Utils.showDroneNameRules();
        DroneName name = Utils.rePromptWhileInvalid("Enter the Drone Model name", DroneName::new);

        Optional<DroneModel> result;
        Description description = null;

        boolean hasDescription = Utils.confirm("Do you want to add a description? (y/n)");

        if (hasDescription) {
            Utils.printCenteredSubtitleV2("Drone Model Description");
            Utils.showDescriptionRules();
            description = Utils.rePromptWhileInvalid("Enter the Drone Model description", Description::new);
        } else {
            Utils.printAlterMessage("Description skipped...");
        }

        Utils.printCenteredSubtitleV2("Operational Wind Limit");
        Utils.showMaxWindRule();
        double maxWindSpeed = Utils.readPositiveDoubleFromConsole("Enter the operational wind limit " + ANSI_ORANGE + "(in m/s)" + ANSI_RESET);

        if (hasDescription) {
            result = controller.createDroneModelWithDescription(modelID, name, description, maxWindSpeed);
        } else {
            result = controller.createDroneModelNoDescription(modelID, name, maxWindSpeed);
        }

        if (result.isPresent()) {
            System.out.println();
            Utils.printSuccessMessage("Drone Model created successfully!");
            DatabaseSync.sync();
        } else {
            System.out.println();
            Utils.printFailMessage("A Drone Model with that ID already exists!");
        }
    }
}