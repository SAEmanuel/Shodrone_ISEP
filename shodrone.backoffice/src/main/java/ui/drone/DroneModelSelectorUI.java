package ui.drone;

import domain.entity.DroneModel;
import utils.Utils;

import java.util.*;
import java.util.stream.Collectors;

public class DroneModelSelectorUI {
    private static final int EXIT = -1;
    private final String title;
    private final String prompt;
    private final Map<DroneModel, Integer> inventory;
    private final int maxDrones;

    public DroneModelSelectorUI(String title, String prompt, Map<DroneModel, Integer> inventory, int maxDrones) {
        this.title = title;
        this.prompt = prompt;
        this.inventory = new HashMap<>(inventory);
        this.maxDrones = maxDrones;
    }

    public Optional<Map<DroneModel, Integer>> selectModels() {
        Map<DroneModel, Integer> selectedModels = new HashMap<>();
        List<DroneModel> availableModels = filterAvailableModels();
        System.out.println(availableModels.size());
        int remainingDrones = maxDrones;

        while (!availableModels.isEmpty() && remainingDrones > 0) {
            Utils.printCenteredSubtitleV2(title);
            System.out.println("Remaining drones to select: " + remainingDrones);


            int index = Utils.showAndSelectIndexPartially(
                    availableModels,
                    prompt + " (or 0 to finish)"
            );

            if (index == EXIT) break;

            if (index < 0 || index >= availableModels.size()) {
                System.out.println("Invalid selection. Please try again.");
                continue;
            }

            DroneModel model = availableModels.get(index);
            int maxAllowed = Math.min(inventory.get(model), remainingDrones);

            Integer quantity = getValidQuantity(model, maxAllowed);
            if (quantity == null) continue;

            updateSelection(selectedModels, availableModels, model, quantity, remainingDrones);
            remainingDrones -= quantity;
        }

        return selectedModels.isEmpty() ?
                Optional.empty() :
                Optional.of(selectedModels);
    }

    private List<DroneModel> filterAvailableModels() {
        return inventory.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private Integer getValidQuantity(DroneModel model, int maxAllowed) {
        return Utils.readPositiveIntegerFromConsole(
                String.format("Quantity for %s (1-%d): ", model.droneName(), maxAllowed),
                input -> (input > 0 && input <= maxAllowed) ? Optional.of(input) : Optional.empty()
        );
    }

    private void updateSelection(
            Map<DroneModel, Integer> selectedModels,
            List<DroneModel> availableModels,
            DroneModel model,
            int quantity,
            int remainingDrones
    ) {
        selectedModels.put(model, quantity);
        inventory.put(model, inventory.get(model) - quantity);

        if (inventory.get(model) == 0) {
            availableModels.remove(model);
        }

        Utils.printSuccessMessage("Added " + quantity + "x " + model.droneName());
    }
}
