package ui.showproposal;

import controller.drone.GetDroneModelsController;
import controller.showproposal.EditShowProposalController;
import controller.showproposal.GetAllProposalTemplatesController;
import domain.entity.*;
import domain.valueObjects.Description;
import factories.FactoryProvider;
import ui.drone.DroneModelSelectorUI;
import utils.Utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static more.ColorfulOutput.*;
import static more.TextEffects.BOLD;

public class EditShowProposalUI implements Runnable {
    private final ShowProposal proposal;
    private final GetAllProposalTemplatesController getAllProposalTemplatesController;
    private final GetDroneModelsController getDroneModelsController;
    private final EditShowProposalController editShowProposalController;
    private static final int EXIT = -1;

    public EditShowProposalUI(ShowProposal previousProposal) {
        proposal = previousProposal;
        getAllProposalTemplatesController = new GetAllProposalTemplatesController();
        getDroneModelsController = new GetDroneModelsController();
        editShowProposalController = new EditShowProposalController();
    }

    @Override
    public void run() {
        Utils.printCenteredTitle("EDIT SHOW PROPOSAL");

        requestChanges(proposal);
        Optional<ShowProposal> validEdit = editShowProposalController.editShowProposal(proposal);
        if (validEdit.isPresent()) {
            Utils.printSuccessMessage("Show proposal successfully edited!");
            Utils.printShowProposalResume(proposal);
            Utils.waitForUser();
        } else {
            Utils.printFailMessage("Failed to edit show proposal.");
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
            System.out.printf("    %s5 %s- %sDuration%s\n", ANSI_BRIGHT_BLACK, ANSI_RESET, BOLD, ANSI_RESET);
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
                        if (newTemplate.equals(newProposal.template())) {
                            Utils.printWarningMessage("Same template selected, nothing has changed in this field...");
                            return;
                        }

                        newProposal.changeTemplate(newTemplate);
                    } else {
                        Utils.printFailMessage("There are no proposal templates in the system! Add them first and try again!");
                        return;
                    }
                }
                case 3 -> {
                    LocalDateTime date = Utils.readDateFromConsole("New show date (yyyy-MM-dd HH:mm): ");
                    newProposal.setShowDate(date);
                }
                case 4 -> {
                    newProposal.setLocation(FactoryProvider.getLocationFactoryImpl().createLocationObject());
                }
                case 5 -> {
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

                case 0 -> Utils.printSuccessMessage("Finished editing.");

                default -> Utils.printAlterMessage("Invalid option.");
            }
        } while (option != 0);
    }

}