package ui;

import domain.entity.DroneModel;
import domain.valueObjects.Description;
import domain.valueObjects.DroneModelID;
import domain.valueObjects.Name;
import utils.Utils;
import controller.CreateDroneModelController;

import java.util.Optional;

import static more.ColorfulOutput.*;

public class CreateDroneModelUI implements Runnable {

    private final CreateDroneModelController controller = new CreateDroneModelController();

    public void run() {
        Utils.printCenteredTitle("Create Drone Model");

        Utils.printDroneCenteredSubtitle("Drone Model ID");
        Utils.showModelIDRules();
        DroneModelID modelID = Utils.rePromptWhileInvalid("Enter the Drone Model ID", DroneModelID::new);

        Utils.printDroneCenteredSubtitle("Drone Model Name");
        Utils.showNameRules();
        Name name = Utils.rePromptWhileInvalid("Enter the Drone Model name", Name::new);

        Optional<DroneModel> result;
        Description description = null;

        boolean hasDescription = Utils.confirm("Do you want to add a description? (y/n)");

        if (hasDescription) {
            Utils.printDroneCenteredSubtitle("Drone Model Description");
            Utils.showDescriptionRules();
            description = Utils.rePromptWhileInvalid("Enter the Drone Model description", Description::new);
        } else {
            Utils.printAlterMessage("Description skipped...");
        }

        Utils.printDroneCenteredSubtitle("Operational Wind Limit");
        Utils.showMaxWindRule();
        int maxWindSpeed = Utils.readPositiveIntegerFromConsole("Enter the operational wind limit " + ANSI_ORANGE + "(in m/s)" + ANSI_RESET);

        if (hasDescription) {
            result = controller.createDroneModelWithDescription(modelID, name, description, maxWindSpeed);
        } else {
            result = controller.createDroneModelNoDescription(modelID, name, maxWindSpeed);
        }

        if (result.isPresent()) {
            System.out.println();
            Utils.printSuccessMessage("Drone Model created successfully!");
        } else {
            System.out.println();
            Utils.printFailMessage("A Drone Model with that ID already exists!");
        }

    }

}
