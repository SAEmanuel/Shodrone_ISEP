package ui.showproposal;

import controller.drone.GetDroneModelsController;
import controller.showproposal.EditShowProposalController;
import controller.showproposal.GetAllProposalTemplatesController;
import domain.entity.*;
import domain.valueObjects.Description;
import factories.FactoryProvider;
import ui.drone.DroneModelSelectorUI;
import ui.showrequest.ListFiguresByCostumerUI;
import utils.Utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static more.ColorfulOutput.*;
import static more.ColorfulOutput.ANSI_RESET;
import static more.TextEffects.BOLD;

public class EditShowProposalUI implements Runnable {
    private final ShowProposal proposal;
    private final GetAllProposalTemplatesController getAllProposalTemplatesController;
    private final GetDroneModelsController getDroneModelsController;
    private final ListFiguresByCostumerUI listFiguresByCostumerUI;
    private final EditShowProposalController editShowProposalController;
    private static final int EXIT = -1;

    public EditShowProposalUI(ShowProposal previousProposal) {
        proposal = previousProposal;
        getAllProposalTemplatesController = new GetAllProposalTemplatesController();
        getDroneModelsController = new GetDroneModelsController();
        listFiguresByCostumerUI = new ListFiguresByCostumerUI();
        editShowProposalController = new EditShowProposalController();
    }

    @Override
    public void run() {
        Utils.printCenteredTitle("EDIT SHOW PROPOSAL");

        ShowProposal newProposal = proposal.cloneProposal();
        requestChanges(newProposal);
        Optional<ShowProposal> validEdit = editShowProposalController.editShowProposal(proposal, newProposal);
        if (validEdit.isPresent()) {
            Utils.printSuccessMessage("✅ Show request successfully edited!");
            Utils.waitForUser();
        } else {
            Utils.printFailMessage("Failed to edit show request.");
            Utils.waitForUser();
        }
    }


    public void requestChanges(ShowProposal newProposal) {
        int option;
        do {
            System.out.printf("\n%s%s──────── %sWhich field do you want to edit? %s────────\n", ANSI_BRIGHT_BLACK, BOLD, ANSI_BRIGHT_WHITE, ANSI_RESET);
            System.out.printf("    %s1 %s- %sDescription%s\n", ANSI_BRIGHT_BLACK, ANSI_RESET, BOLD, ANSI_RESET);
            System.out.printf("    %s2 %s- %sTemplate%s\n", ANSI_BRIGHT_BLACK, ANSI_RESET, BOLD, ANSI_RESET);
            System.out.printf("    %s3 %s- %sShow Date%s\n", ANSI_BRIGHT_BLACK, ANSI_RESET, BOLD, ANSI_RESET);
            System.out.printf("    %s4 %s- %sLocation%s\n", ANSI_BRIGHT_BLACK, ANSI_RESET, BOLD, ANSI_RESET);
            System.out.printf("    %s5 %s- %sDrone Models%s\n", ANSI_BRIGHT_BLACK, ANSI_RESET, BOLD, ANSI_RESET);
            System.out.printf("    %s6 %s- %sDuration%s\n", ANSI_BRIGHT_BLACK, ANSI_RESET, BOLD, ANSI_RESET);
            System.out.printf("    %s7 %s- %sFigures%s\n", ANSI_BRIGHT_BLACK, ANSI_RESET, BOLD, ANSI_RESET);
            System.out.printf("    %s0 %s- %sFinish editing%s\n", ANSI_BRIGHT_BLACK, ANSI_RESET, BOLD, ANSI_RESET);
            option = Utils.readIntegerFromConsole("• Option (0 to finish) ");
            switch (option) {
                case 1 -> {
                    Description desc = Utils.rePromptWhileInvalid("New description: ", Description::new);
                    newProposal.changeDescription(desc);
                }
                case 2 -> {
                    Optional<List<ProposalTemplate>> listOfTemplates = getAllProposalTemplatesController.getAllProposalTemplates();
                    ProposalTemplate newTemplate;
                    if (listOfTemplates.isPresent() && !listOfTemplates.get().isEmpty()) {
                        int index = Utils.showAndSelectIndexPartially(listOfTemplates.get(), "Proposal Templates");

                        if (index == EXIT) {
                            Utils.printWarningMessage("No proposal templates selected...");
                            return;
                        }
                        newTemplate = listOfTemplates.get().get(index);
                        if (newTemplate.equals(proposal.template())) {
                            Utils.printWarningMessage("Same template selected, nothing has changed in this field...");
                            return;
                        }

                    } else {
                        Utils.printFailMessage("The are no proposal templates in the system! Add them first and try again!");
                        return;
                    }

                    newProposal.changeTemplate(newTemplate);
                }
                case 3 -> {
                    LocalDateTime date = Utils.readDateFromConsole("New show date (yyyy-MM-dd HH:mm): ");
                    newProposal.setShowDate(date);
                }
                case 4 -> {
                    newProposal.setLocation(FactoryProvider.getLocationFactoryImpl().createLocationObject());
                }
                case 5 -> {
                    Optional<List<DroneModel>> listOfDroneModels = getDroneModelsController.getAllModels();

                    if (listOfDroneModels.isEmpty() || listOfDroneModels.get().isEmpty()) {
                        Utils.printFailMessage("No drone models in the system! Add some first");
                        return;
                    }

                    List<DroneModel> availableModels = new ArrayList<>(listOfDroneModels.get());
                    DroneModelSelectorUI selector = new DroneModelSelectorUI(
                            "New drone model selection",
                            "Select a model"
                    );

                    Optional<Map<DroneModel, Integer>> selectedModels = selector.selectModels(availableModels);
                    if (selectedModels.isEmpty()) {
                        Utils.printFailMessage("No model selected. Operation canceled.");
                        return;
                    }

                    Map<DroneModel, Integer> modelsToBeUsed = selectedModels.get();
                    newProposal.setModelsUsed(modelsToBeUsed);
                }
                case 6 -> {
                    int minutes = Utils.readIntegerFromConsole("New show duration in minutes: ");
                    do {
                        if (minutes < 1) {
                            Utils.printAlterMessage("Show duration must be greater than 0.");
                            minutes = Utils.readIntegerFromConsole("New show duration in minutes: ");
                        } else {
                            break;
                        }
                    } while (true);
                    newProposal.setShowDuration(Duration.ofMinutes(minutes));
                }
                case 7 -> {
                    List<Figure> newSelectedFigures = listFiguresByCostumerUI.getListFiguresUI(newProposal.getShowRequest().getCostumer());
                    newProposal.setSequenceFigues(newSelectedFigures);
                }
                case 0 -> Utils.printSuccessMessage("Finished editing.");

                default -> Utils.printAlterMessage("Invalid option.");
            }
        } while (option != 0);
    }

}
