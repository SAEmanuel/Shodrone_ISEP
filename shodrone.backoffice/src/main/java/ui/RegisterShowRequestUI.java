package ui;

import controller.RegisterShowRequestController;
import domain.entity.Figure;
import domain.entity.ShowRequest;
import domain.valueObjects.Location;
import utils.Utils;

import java.time.format.DateTimeFormatter;

import static more.ColorfulOutput.ANSI_BRIGHT_BLACK;
import static more.ColorfulOutput.ANSI_RESET;
import static more.TextEffects.ITALIC;

/**
 * UI class responsible for guiding the user through the process of registering a new {@link ShowRequest}.
 *
 * <p>This class interacts with the {@link RegisterShowRequestController} to collect all necessary inputs
 * (customer, figures, description, location, date, drone count, and duration), create a new show request,
 * and display a confirmation summary.</p>
 *
 * <p>The UI is designed to run in a CLI (Command-Line Interface) environment and provides user feedback
 * and error handling through utility methods from the {@link Utils} class.</p>
 */
public class RegisterShowRequestUI implements Runnable {

    /** The controller responsible for handling the show request registration logic. */
    private final RegisterShowRequestController registerShowcontroller;

    /**
     * Constructs a new {@code RegisterShowRequestUI} instance and initializes its controller.
     */
    public RegisterShowRequestUI() {
        registerShowcontroller = new RegisterShowRequestController();
    }

    /**
     * Returns the controller responsible for show request registration.
     *
     * @return the {@link RegisterShowRequestController} instance
     */
    private RegisterShowRequestController getRegisterShowcontroller() {
        return registerShowcontroller;
    }

    /**
     * Runs the UI workflow for registering a new show request.
     *
     * <p>This method follows a sequential process of gathering:
     * <ul>
     *     <li>Costumer information</li>
     *     <li>Figures for the show</li>
     *     <li>Description</li>
     *     <li>Location</li>
     *     <li>Show date</li>
     *     <li>Drone details</li>
     *     <li>Show duration</li>
     * </ul>
     * It concludes by displaying a resume of the registered request or printing an error message if any step fails.</p>
     */
    @Override
    public void run() {
        Utils.printCenteredTitle("REGISTER SHOW REQUEST");
        try {
            Utils.printCenteredSubtitle("Costumer information");
            getRegisterShowcontroller().foundCostumerForRegistration();

            Utils.printCenteredSubtitle("Figures selection for show");
            getRegisterShowcontroller().foundFiguresForRegistration();

            Utils.printCenteredSubtitle("Show description");
            getRegisterShowcontroller().getDescriptionsForRegistration(
                    Utils.readLineFromConsole(ANSI_BRIGHT_BLACK + ITALIC + "• Enter the description of the show" + ANSI_RESET)
            );

            Utils.printCenteredSubtitle("Show location");
            requestLocationInformation();

            Utils.printCenteredSubtitle("Show date");
            getRegisterShowcontroller().getDateForShow();

            Utils.printCenteredSubtitle("Drone information");
            getRegisterShowcontroller().getNumberOfDrones();

            Utils.printCenteredSubtitle("Show duration");
            getRegisterShowcontroller().getShowDuration();

            ShowRequest registeredShowRequest = getRegisterShowcontroller().registerShowRequest();
            Utils.printShowRequestResume(registeredShowRequest);
            Utils.printSuccessMessage("\n✅ Show request successfully registered!");
            Utils.waitForUser();

        } catch (Exception e) {
            Utils.printAlterMessage(e.getMessage());
        }
    }

    /**
     * Requests the location information for the show by invoking the appropriate controller method.
     */
    private void requestLocationInformation() {
        Utils.printSubTitle("Location information");
        getRegisterShowcontroller().getLocationOfShow();
    }
}
