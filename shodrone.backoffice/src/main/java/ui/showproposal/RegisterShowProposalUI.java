package ui.showproposal;

import controller.drone.GetDroneModelsController;
import controller.showproposal.GetAllProposalTemplatesController;
import controller.showproposal.GetAllShowRequestsController;
import controller.showproposal.RegisterShowProposalController;
import domain.entity.DroneModel;
import domain.entity.ProposalTemplate;
import domain.entity.ShowProposal;
import domain.entity.ShowRequest;
import ui.drone.DroneModelSelectorUI;
import utils.Utils;

import java.util.*;

public class RegisterShowProposalUI implements Runnable {
    private static final int EXIT = -1;
    private final GetAllShowRequestsController getAllShowRequestsController;
    private final RegisterShowProposalController registerShowProposalController;
    private final GetAllProposalTemplatesController getAllProposalTemplatesController;
    private final GetDroneModelsController getDroneModelsController;

    public RegisterShowProposalUI() {
        this.getAllShowRequestsController = new GetAllShowRequestsController();
        this.getAllProposalTemplatesController = new GetAllProposalTemplatesController();
        this.registerShowProposalController = new RegisterShowProposalController();
        this.getDroneModelsController = new GetDroneModelsController();
    }


    @Override
    public void run() {
        Utils.printCenteredTitle("Register Show Proposal");

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


        Optional<ShowProposal> proposal = registerShowProposalController.generateShowProposal(showRequest, template, modelsToBeUsed, numberOfDrones);

        if (proposal.isEmpty()) {
            Utils.printFailMessage("Error registering show proposal...");
        } else {

            Utils.printShowProposalResume(proposal.get());
            boolean edit = Utils.confirm("Do you wish to edit proposal? (y/n)");

            if (!edit) {
                Utils.printSuccessMessage("Show proposal registered!");
            } else {
                EditShowProposalUI editUI = new EditShowProposalUI(proposal.get());
                editUI.run();
            }
        }
    }
}
