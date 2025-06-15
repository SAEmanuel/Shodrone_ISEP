package ui.showproposal;

import controller.drone.GetDroneModelsController;
import controller.showproposal.CreateShowProposalController;
import controller.showproposal.GetAllProposalTemplatesController;
import controller.showproposal.GetAllShowRequestsController;
import controller.showrequest.ListFiguresByCostumerController;
import domain.entity.*;
import domain.valueObjects.Name;
import ui.drone.DroneModelSelectorUI;
import utils.Utils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;


public class CreateShowProposalUI implements Runnable{
    private final CreateShowProposalController controller;
    private final GetAllProposalTemplatesController getAllProposalTemplatesController;
    private final GetAllShowRequestsController getAllShowRequestsController;
    private final GetDroneModelsController getDroneModelsController;
    private static final int EXIT = -1;

    public CreateShowProposalUI(){
        controller = new CreateShowProposalController();
        getAllProposalTemplatesController = new GetAllProposalTemplatesController();
        getAllShowRequestsController = new GetAllShowRequestsController();
        getDroneModelsController = new GetDroneModelsController();
    }

    @Override
    public void run(){
        Utils.printCenteredTitle("Register Show Proposal");

        Name proposalName = Utils.rePromptWhileInvalid("Enter the proposal name", Name::new);

        Optional<List<ShowRequest>> listOfShowRequests = getAllShowRequestsController.listShowRequest();
        ShowRequest showRequest;
        if (listOfShowRequests.isPresent() && !listOfShowRequests.get().isEmpty()) {
            Utils.showList(listOfShowRequests.get(), "Show Requests");
            String input;
            int index = 0;
            do {
                input = Utils.readLineFromConsole("Type your option");
                try {
                    index = Integer.valueOf(input);
                } catch (NumberFormatException ex) {
                    Utils.printCenteredTitle("Invalid option");
                }
                if(index < 0)
                    Utils.printAlterMessage("Value less than 0");

                if(index > listOfShowRequests.get().size())
                    Utils.printAlterMessage("Value greater than "+ listOfShowRequests.get().size());
            } while (index < 0 || index > listOfShowRequests.get().size());

            if (index == 0) {
                Utils.printFailMessage("No show request selected...");
                return;
            }

            showRequest = listOfShowRequests.get().get(index-1);
        } else {
            Utils.printFailMessage("The are no show request to add to show proposal! Add them first and try again!");
            return;
        }

        Optional<List<ProposalTemplate>> listOfTemplates = getAllProposalTemplatesController.getAllProposalTemplates();
        ProposalTemplate template;
        if (listOfTemplates.isPresent() && !listOfTemplates.get().isEmpty()) {
            Utils.showList(listOfTemplates.get(), "Show Requests");
            String input;
            int index = 0;
            do {
                input = Utils.readLineFromConsole("Type your option");
                try {
                    index = Integer.valueOf(input);
                } catch (NumberFormatException ex) {
                    Utils.printCenteredTitle("Invalid option");
                }
                if(index < 0)
                    Utils.printAlterMessage("Value less than 0");

                if(index > listOfTemplates.get().size())
                    Utils.printAlterMessage("Value greater than "+ listOfTemplates.get().size());
            } while (index < 0 || index > listOfTemplates.get().size());

            if (index == 0) {
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


        Optional<ShowProposal> registeredShowProposal = null;
        try {
            registeredShowProposal = controller.registerShowProposal(
                    proposalName,
                    showRequest,
                    template,
                    numberOfDrones,
                    modelsToBeUsed
            );
        } catch (IOException e) {
            Utils.printFailMessage("❌ Failed to register the show proposal. Please check the input data and try again.");
        }

        if (!registeredShowProposal.isEmpty()) {
            //    Utils.printFailMessage("❌ Failed to register the show proposal. Please check the input data and try again.");
            //} else {
            Utils.dropLines(10);
            Utils.printShowProposalResume(registeredShowProposal.get());
            Utils.dropLines(2);
            Utils.printSuccessMessage("\n✅ Show proposal successfully registered!");
            Utils.dropLines(1);
            boolean edit = Utils.confirm("Do you wish to edit proposal? (y/n)");

            if (edit) {
                EditShowProposalUI editUI = new EditShowProposalUI(registeredShowProposal.get());
                editUI.run();
            }
        }
    }
}
