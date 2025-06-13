package ui.showproposal;

import controller.drone.GetDroneModelsController;
import controller.showproposal.CreateShowProposalController;
import controller.showproposal.GetAllProposalTemplatesController;
import controller.showproposal.GetAllShowRequestsController;
import controller.showrequest.ListFiguresByCostumerController;
import domain.entity.*;
import domain.valueObjects.Description;
import domain.valueObjects.Location;
import domain.valueObjects.Name;
import factories.FactoryProvider;
import ui.drone.DroneModelSelectorUI;
import utils.Utils;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

import static java.time.Duration.ofMinutes;

public class CreateShowProposalUI implements Runnable{
    private final CreateShowProposalController controller;
    private final GetAllProposalTemplatesController getAllProposalTemplatesController;
    private final GetAllShowRequestsController getAllShowRequestsController;
    private final GetDroneModelsController getDroneModelsController;
    private final ListFiguresByCostumerController listFiguresByCostumerController;
    private static final int EXIT = -1;

    public CreateShowProposalUI(){
        controller = new CreateShowProposalController();
        getAllProposalTemplatesController = new GetAllProposalTemplatesController();
        getAllShowRequestsController = new GetAllShowRequestsController();
        getDroneModelsController = new GetDroneModelsController();
        listFiguresByCostumerController = new ListFiguresByCostumerController();
    }

    @Override
    public void run(){
        Utils.printCenteredTitle("Register Show Proposal");

        Name proposalName = Utils.rePromptWhileInvalid("Enter the proposal name", Name::new);

        Optional<List<ShowRequest>> listOfShowRequests = getAllShowRequestsController.listShowRequest();
        ShowRequest showRequest;
        if (listOfShowRequests.isPresent() && !listOfShowRequests.get().isEmpty()) {
            int index = Utils.showAndSelectIndexPartially(listOfShowRequests.get(), "Show Requests");

            if (index == EXIT) {
                Utils.printFailMessage("No show request selected...");
                return;
            }

            showRequest = listOfShowRequests.get().get(index);
        } else {
            Utils.printFailMessage("The are no show request to add to show proposal! Add them first and try again!");
            return;
        }

        Optional<List<ProposalTemplate>> listOfTemplates = getAllProposalTemplatesController.getAllProposalTemplates();
        ProposalTemplate template;
        if (listOfTemplates.isPresent() && !listOfTemplates.get().isEmpty()) {
            int index = Utils.showAndSelectIndexPartially(listOfTemplates.get(), "Proposal Templates");

            if (index == EXIT) {
                Utils.printFailMessage("No proposal templates selected...");
                return;
            }
            template = listOfTemplates.get().get(index);

        } else {
            Utils.printFailMessage("The are no proposal templates in the system! Add them first and try again!");
            return;
        }

        Utils.dropLines(2);
        Utils.printAlterMessage("Current number of drones: " + showRequest.getNumberOfDrones());
        int numberOfDrones;
        boolean changeNumberOfDrones = Utils.confirm("Do you wish to change the current number of drones? (y/n)");
        if (changeNumberOfDrones) {
            numberOfDrones = Utils.readIntegerFromConsole("Select the desired number: ");
            if (numberOfDrones == showRequest.getNumberOfDrones())
                Utils.printAlterMessage("You have selected the same number of drones, nothing was changed...");
        } else {
            numberOfDrones = showRequest.getNumberOfDrones();
        }


        Optional<Map<DroneModel, Integer>> inventory = getDroneModelsController.getDroneModelQuantity();

        if (inventory.isEmpty() || inventory.get().isEmpty()) {
            Utils.printFailMessage("No drone models in the system! Add some first");
            return;
        }

        DroneModelSelectorUI selector = new DroneModelSelectorUI(
                "Drone Model selection",
                "Choose a model",
                inventory.get(),
                numberOfDrones
        );

        Optional<Map<DroneModel, Integer>> selectedModels = selector.selectModels();
        if (selectedModels.isEmpty()) {
            Utils.printFailMessage("No model selected. Operation canceled.");
            return;
        }

        Map<DroneModel, Integer> modelsToBeUsed = selectedModels.get();
        int checkNumberOfDrones = 0;
        for (Integer i : modelsToBeUsed.values())
            checkNumberOfDrones += i;

        if (checkNumberOfDrones < numberOfDrones)
            numberOfDrones = checkNumberOfDrones;


        boolean option;

        Utils.dropLines(3);
        Utils.showDescriptionRules();
        option = Utils.confirm("Do you want to add a Description? (y/n)");
        Optional<Description> descriptionOpt = refurseOrAcceptValueObject(option, "Description", Description::new, Description.class);
        Description description = descriptionOpt.orElse(null);

        Utils.dropLines(3);
        Utils.showNameRules();
        Location location = FactoryProvider.getLocationFactoryImpl().createLocationObject();

        Utils.dropLines(3);
        //Utils.showDateRules();// (yyyy-MM-dd HH:mm)
        LocalDateTime showDate = Utils.readDateFromConsole("Enter the show date: ");

        Utils.dropLines(3);
        //Utils.showDurationRules();
        Duration showDuration = ofMinutes(Utils.readIntegerFromConsolePositive("Enter the show duration (minutes)"));

        Optional<ShowProposal> registeredShowProposal = null;
        try {
            registeredShowProposal = controller.registerShowProposal(
                    proposalName,
                    showRequest,
                    template,
                    description,
                    location,
                    showDate,
                    numberOfDrones,
                    showDuration,
                    proposalName.toString(),
                    modelsToBeUsed
            );
        } catch (IOException e) {
            Utils.printFailMessage("\n❌ Show proposal successfully registered!");
        }

        Utils.dropLines(10);
        Utils.printShowProposalResume(registeredShowProposal.get());
        Utils.printSuccessMessage("\n✅ Show proposal successfully registered!");
        Utils.waitForUser();
    }

    /**
     * Helper method to optionally accept or skip input for a value object.
     * If user chooses to skip, returns empty Optional.
     * Otherwise, prompts repeatedly until valid input is entered.
     *
     * @param option boolean indicating whether the user wants to provide the value
     * @param prompt label of the input requested
     * @param parser function that converts String input into the target value object
     * @param clazz class of the target value object (not used here but kept for signature consistency)
     * @param <T> type of value object
     * @return Optional containing the parsed value or empty if skipped
     */
    private <T> Optional<T> refurseOrAcceptValueObject(Boolean option, String prompt, Function<String, T> parser, Class<T> clazz) {
        if (!option) {
            Utils.printAlterMessage("Skipped...");
            return Optional.empty();
        } else {
            T value = Utils.rePromptWhileInvalid("Enter the "+ prompt, parser);
            return Optional.of(value);
        }
    }
}
