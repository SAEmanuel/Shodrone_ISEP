package ui.showproposal;

import controller.showproposal.AddDronesShowProposalController;
import domain.entity.DroneModel;
import domain.entity.ShowProposal;
import drone.DroneModelInformation;
import persistence.RepositoryProvider;
import ui.drone.DroneModelSelectorUI;
import utils.Utils;

import java.util.*;

public class AddDronesShowProposalUI implements Runnable {

    private final AddDronesShowProposalController addDronesController;

    public AddDronesShowProposalUI() {
        this.addDronesController = new AddDronesShowProposalController();
    }

    @Override
    public void run() {
        Utils.printCenteredTitle("Configure Drones for Show Proposal");

        Optional<List<ShowProposal>> standbyProposals = RepositoryProvider.showProposalRepository().getStandbyProposals();

        if (standbyProposals.isEmpty() || standbyProposals.get().isEmpty()) {
            Utils.printFailMessage("No STAND_BY show proposals found in the system!");
            return;
        }

        List<ShowProposal> proposals = standbyProposals.get();

        int index = Utils.showAndSelectIndexPartially(proposals, "Show Proposals");

        if (index == -1) {
            Utils.printFailMessage("No show proposal selected. Operation canceled.");
            return;
        }

        ShowProposal proposal = proposals.get(index);

        boolean running = true;
        while (running) {
            printCurrentDrones(proposal);

            Utils.showListCustom(List.of(
                    "Add drones",
                    "Change quantity of existing drones",
                    "Remove drone model"
            ), "");

            int choice = Utils.readIntegerFromConsole("Type your option: ");

            switch (choice) {
                case 1:
                    addDrones(proposal);
                    break;
                case 2:
                    changeDroneQuantity(proposal);
                    break;
                case 3:
                    removeDroneModel(proposal);
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    Utils.printWarningMessage("Invalid option. Please try again.");
                    break;
            }
        }

        Optional<ShowProposal> success = addDronesController.saveProposal(proposal);

        if (success.isPresent()) {
            Utils.printSuccessMessage("Drones successfully configured in the proposal!");
            Utils.printShowProposalResume(success.get());
        } else {
            Utils.printFailMessage("Failed to update drones.");
        }
    }

    private void printCurrentDrones(ShowProposal proposal) {
        Utils.printAlterMessage("Current drones in the proposal:");

        List<DroneModelInformation> currentModels = addDronesController.getCurrentDroneModels(proposal);

        if (currentModels.isEmpty()) {
            System.out.println("    (No drones configured yet)");
        } else {
            currentModels.forEach(info ->
                    System.out.printf("    -> [%dx] %s\n", info.usedQty(), info.model().toString()));
        }
    }

    private void addDrones(ShowProposal proposal) {
        List<DroneModelInformation> available = addDronesController.getAvailableDroneModelsToAdd(proposal);

        if (available.isEmpty()) {
            Utils.printWarningMessage("No available drone models to add.");
            return;
        }

        Map<DroneModel, Integer> inventoryToAdd = new HashMap<>();
        for (DroneModelInformation info : available) {
            int availableQty = info.maxAvailable() - info.usedQty();
            if (availableQty > 0) {
                inventoryToAdd.put(info.model(), availableQty);
            }
        }

        int totalRemaining = inventoryToAdd.values().stream().mapToInt(Integer::intValue).sum();

        DroneModelSelectorUI selector = new DroneModelSelectorUI(
                "Drone Model selection",
                "Choose drone models to add",
                inventoryToAdd,
                totalRemaining
        );

        Optional<Map<DroneModel, Integer>> selectedModels = selector.selectModels();
        selectedModels.ifPresent(models -> {
            Optional<String> result = addDronesController.addDroneModels(proposal, models);
            if (result.isPresent()) {
                Utils.printWarningMessage(result.get());
            } else {
                Utils.printSuccessMessage("Drones added successfully.");
            }
        });
    }

    private void changeDroneQuantity(ShowProposal proposal) {
        List<DroneModelInformation> currentList = addDronesController.getCurrentDroneModels(proposal);

        if (currentList.isEmpty()) {
            Utils.printWarningMessage("No drones to change.");
            return;
        }

        System.out.println("Choose which drone model to change (or 0 to cancel):");
        for (int i = 0; i < currentList.size(); i++) {
            DroneModelInformation info = currentList.get(i);
            System.out.printf("    (%d) -> [%dx] %s\n", i + 1, info.usedQty(), info.model().toString());
        }
        System.out.println("    (0) Cancel");

        int option = Utils.readIntegerFromConsole("Type your option: ");
        if (option < 1 || option > currentList.size()) {
            Utils.printAlterMessage("No change performed.");
            return;
        }

        DroneModelInformation selectedInfo = currentList.get(option - 1);

        while (true) {
            int newQty = Utils.readIntegerFromConsole(
                    String.format("New quantity for model %s (max %d, 0 = remove): ",
                            selectedInfo.model().droneName(), selectedInfo.maxAvailable())
            );

            Optional<String> result = addDronesController.changeDroneModelQuantity(proposal, selectedInfo.model(), newQty);

            if (result.isPresent()) {
                Utils.printWarningMessage(result.get());
            } else {
                Utils.printSuccessMessage("Drone model quantity updated.");
                break;
            }
        }
    }

    private void removeDroneModel(ShowProposal proposal) {
        List<DroneModelInformation> currentList = addDronesController.getCurrentDroneModels(proposal);

        if (currentList.isEmpty()) {
            Utils.printWarningMessage("No drones to remove.");
            return;
        }

        System.out.println("Choose which drone model to remove (or 0 to cancel):");
        for (int i = 0; i < currentList.size(); i++) {
            DroneModelInformation info = currentList.get(i);
            System.out.printf("    (%d) -> [%dx] %s\n", i + 1, info.usedQty(), info.model().toString());
        }
        System.out.println("    (0) Cancel");

        int option = Utils.readIntegerFromConsole("Type your option: ");
        if (option < 1 || option > currentList.size()) {
            Utils.printAlterMessage("No removal performed.");
            return;
        }

        DroneModelInformation selectedInfo = currentList.get(option - 1);
        Optional<String> result = addDronesController.removeDroneModel(proposal, selectedInfo.model());

        if (result.isPresent()) {
            Utils.printWarningMessage(result.get());
        } else {
            Utils.printSuccessMessage("Drone model removed.");
        }
    }
}
