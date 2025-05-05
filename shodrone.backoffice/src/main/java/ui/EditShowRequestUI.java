package ui;

import controller.EditShowRequestController;
import controller.ListShowRequestByCostumerController;
import domain.entity.ShowRequest;
import domain.valueObjects.Description;
import factories.FactoryProvider;
import utils.Utils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.util.Scanner;

import java.util.List;
import java.util.Optional;

public class EditShowRequestUI implements Runnable {

    private EditShowRequestController controller;
    private ListShowRequestByCostumerController listShowRequestByCostumerController;

    public EditShowRequestUI() {
        controller = getEditShowRequestController();
        listShowRequestByCostumerController = new ListShowRequestByCostumerController();
    }

    private EditShowRequestController getEditShowRequestController() {
        if(controller == null){
            controller = new EditShowRequestController();
        }
        return controller;
    }

    @Override
    public void run() {
        Utils.printCenteredTitle("EDIT SHOW REQUEST");
        try {
            List<ShowRequest> listShowRequestBySelectedCostumer= listShowRequestByCostumerController.listShowRequestByCostumer();
            int showIndexSelectedByUser = Utils.showAndSelectIndexCustomOptions(listShowRequestBySelectedCostumer, "Select the show you want to edit");

            ShowRequest oldShowRequest = listShowRequestBySelectedCostumer.get(showIndexSelectedByUser);

            if(oldShowRequest != null){
                ShowRequest newShowRequest = oldShowRequest.clone();
                requestChanges(newShowRequest);
                Optional<ShowRequest> validEdit = getEditShowRequestController().editShowRequest(oldShowRequest,newShowRequest);
                if(validEdit.isPresent()){
                    Utils.printSuccessMessage("Show request successfully edited!");
                    Utils.waitForUser();
                }else{
                    Utils.printFailMessage("Failed to edit show request.");
                    Utils.waitForUser();
                }
            }

        }catch(Exception e){
            Utils.printAlterMessage(e.getMessage());
        }
    }

    public void requestChanges(ShowRequest newRequest) {
        int option;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("\nWhich field do you want to edit?");
            System.out.println("1 - Description");
            System.out.println("2 - Show Date");
            System.out.println("3 - Location");
            System.out.println("4 - Number of Drones");
            System.out.println("5 - Duration");
            System.out.println("6 - Status");
            System.out.println("0 - Finish editing");

            option = Utils.readIntegerFromConsole("Option (0 to finish): ");
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
                case 0 -> Utils.printSuccessMessage("Finished editing.");
                default -> Utils.printAlterMessage("Invalid option.");
            }
        } while (option != 0);

    }


}
