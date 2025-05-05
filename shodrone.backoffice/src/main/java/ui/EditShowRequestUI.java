package ui;

import controller.EditShowRequestController;
import controller.ListShowRequestByCostumerController;
import domain.entity.Figure;
import domain.entity.ShowRequest;
import domain.valueObjects.Description;
import factories.FactoryProvider;
import utils.Utils;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.Scanner;
import java.util.List;
import java.util.Optional;

import static more.ColorfulOutput.*;
import static more.TextEffects.BOLD;

public class EditShowRequestUI implements Runnable {

    private EditShowRequestController controller;
    private ListShowRequestByCostumerController listShowRequestByCostumerController;
    private ListFiguresByCostumerUI listFiguresByCostumerUI;

    public EditShowRequestUI() {
        controller = getEditShowRequestController();
        listShowRequestByCostumerController = new ListShowRequestByCostumerController();
        listFiguresByCostumerUI = new ListFiguresByCostumerUI();
    }

    private EditShowRequestController getEditShowRequestController() {
        if (controller == null) {
            controller = new EditShowRequestController();
        }
        return controller;
    }

    @Override
    public void run() {
        Utils.printCenteredTitle("EDIT SHOW REQUEST");
        try {
            List<ShowRequest> listShowRequestBySelectedCostumer = listShowRequestByCostumerController.listShowRequestByCostumer();
            int showIndexSelectedByUser = Utils.showAndSelectIndexCustomOptions(listShowRequestBySelectedCostumer, "Select the show you want to edit");

            ShowRequest oldShowRequest = listShowRequestBySelectedCostumer.get(showIndexSelectedByUser);

            if (oldShowRequest != null) {
                ShowRequest newShowRequest = oldShowRequest.clone();
                requestChanges(newShowRequest);
                Optional<ShowRequest> validEdit = getEditShowRequestController().editShowRequest(oldShowRequest, newShowRequest);
                if (validEdit.isPresent()) {
                    Utils.printSuccessMessage("✅ Show request successfully edited!");
                    Utils.waitForUser();
                } else {
                    Utils.printFailMessage("Failed to edit show request.");
                    Utils.waitForUser();
                }
            }

        } catch (Exception e) {
            Utils.printAlterMessage(e.getMessage());
        }
    }

    public void requestChanges(ShowRequest newRequest) {
        int option;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.printf("\n%s%s──────── %sWhich field do you want to edit? %s────────\n", ANSI_BRIGHT_BLACK, BOLD, ANSI_BRIGHT_WHITE, ANSI_RESET);
            System.out.printf("    %s1 %s- %sDescription%s\n", ANSI_BRIGHT_BLACK, ANSI_RESET, BOLD, ANSI_RESET);
            System.out.printf("    %s2 %s- %sShow Date%s\n", ANSI_BRIGHT_BLACK, ANSI_RESET, BOLD, ANSI_RESET);
            System.out.printf("    %s3 %s- %sLocation%s\n", ANSI_BRIGHT_BLACK, ANSI_RESET, BOLD, ANSI_RESET);
            System.out.printf("    %s4 %s- %sNumber of Drones%s\n", ANSI_BRIGHT_BLACK, ANSI_RESET, BOLD, ANSI_RESET);
            System.out.printf("    %s5 %s- %sDuration%s\n", ANSI_BRIGHT_BLACK, ANSI_RESET, BOLD, ANSI_RESET);
            System.out.printf("    %s6 %s- %sFigures%s\n", ANSI_BRIGHT_BLACK, ANSI_RESET, BOLD, ANSI_RESET);
            System.out.printf("    %s0 %s- %sFinish editing%s\n",ANSI_BRIGHT_BLACK , ANSI_RESET, BOLD, ANSI_RESET);
            option = Utils.readIntegerFromConsole("• Option (0 to finish): ");
            switch (option) {
                case 1 -> {
                    String desc = Utils.readLineFromConsole("New description: ");
                    newRequest.setDescription(Description.valueOf(desc));
                }
                case 2 -> {
                    LocalDateTime date = Utils.readDateFromConsole("New show date (yyyy-MM-dd HH:mm): ");
                    newRequest.setShowDate(date);
                }
                case 3 -> {
                    newRequest.setLocation(FactoryProvider.getLocationFactoryImpl().createLocationObject());
                }
                case 4 -> {
                    int drones = Utils.readIntegerFromConsole("New number of drones: ");
                    newRequest.setNumberOfDrones(drones);
                }
                case 5 -> {
                    int minutes = Utils.readIntegerFromConsole("New show duration in minutes: ");
                    newRequest.setShowDuration(Duration.ofMinutes(minutes));
                }
                case 6 -> {
                    List<Figure> newSelectedFigures = listFiguresByCostumerUI.getListFiguresUI(newRequest.getCostumer());
                    newRequest.setFigures(newSelectedFigures);
                }
                case 0 -> Utils.printSuccessMessage("Finished editing.");
                default -> Utils.printAlterMessage("Invalid option.");
            }
        } while (option != 0);

    }


}
