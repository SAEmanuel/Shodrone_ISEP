package ui.showrequest;

import controller.service.ServiceForValidSequenceFiguresForShow;
import controller.showrequest.RegisterShowRequestController;
import domain.entity.Costumer;
import domain.entity.Figure;
import domain.entity.ShowRequest;
import factories.FactoryProvider;
import ui.customer.FoundCostumerUI;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private final FoundCostumerUI foundCostumerUI;
    private final ListFiguresByCostumerUI listFiguresByCostumerUI;


    /**
     * Constructs a new {@code RegisterShowRequestUI} instance and initializes its controller.
     */
    public RegisterShowRequestUI() {
        registerShowcontroller = new RegisterShowRequestController();
        foundCostumerUI = new FoundCostumerUI();
        listFiguresByCostumerUI = new ListFiguresByCostumerUI();
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
            Optional<Costumer> costumerSelected = foundCostumerUI.foundCustomersUI();
            getRegisterShowcontroller().foundCostumerForRegistration(costumerSelected);

            Utils.printCenteredSubtitle("Figures selection for show");

            if(costumerSelected.isEmpty()) {
                throw new Exception("Costumer not found");
            }

            getRegisterShowcontroller().foundFiguresForRegistration(ServiceForValidSequenceFiguresForShow.getListFiguresUIWithRepetitions(costumerSelected.get(),new ArrayList<>()));

            Utils.printCenteredSubtitle("Show description");
            getRegisterShowcontroller().getDescriptionsForRegistration(
                    Utils.readLineFromConsole(ANSI_BRIGHT_BLACK + ITALIC + "• Enter the description of the show" + ANSI_RESET)
            );

            Utils.printCenteredSubtitle("Show location");
            requestLocationInformation();

            Utils.printCenteredSubtitle("Show date");
            getRegisterShowcontroller().getDateForShow(Utils.readDateFromConsole("Enter the show date (yyyy-MM-dd HH:mm)"));

            Utils.printCenteredSubtitle("Drone information");
            getRegisterShowcontroller().getNumberOfDrones(Utils.readIntegerFromConsolePositive("Enter the number of drones"));

            Utils.printCenteredSubtitle("Show duration");
            getRegisterShowcontroller().getShowDuration(Utils.readIntegerFromConsolePositive("Enter the show duration (minutes)"));

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
        getRegisterShowcontroller().getLocationOfShow(FactoryProvider.getLocationFactoryImpl().createLocationObject());
    }
}
