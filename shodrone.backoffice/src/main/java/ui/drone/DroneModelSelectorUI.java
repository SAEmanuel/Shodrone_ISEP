package ui.drone;

import domain.entity.DroneModel;
import utils.Utils;

import java.util.*;

public class DroneModelSelectorUI {
    private static final int EXIT = -1;
    private final String title;
    private final String prompt;

    public DroneModelSelectorUI(String title, String prompt) {
        this.title = title;
        this.prompt = prompt;
    }

    public Optional<Map<DroneModel, Integer>> selectModels(List<DroneModel> availableModels) {
        Map<DroneModel, Integer> selectedModels = new HashMap<>();
        List<DroneModel> remainingModels = new ArrayList<>(availableModels);

        while (!remainingModels.isEmpty()) {
            Utils.printCenteredSubtitleV2(title);

            int index = Utils.showAndSelectIndexPartially(
                    remainingModels,
                    prompt + " (or 0 to finish)"
            );

            if (index == EXIT) break;

            DroneModel model = remainingModels.get(index);
            Integer quantity = getValidQuantity(model);

            if (quantity != null) {
                selectedModels.put(model, quantity);
                remainingModels.remove(index);
                Utils.printSuccessMessage(String.format(
                        "Added %dx %s", quantity, model.droneName()
                ));
            }
        }

        return selectedModels.isEmpty() ?
                Optional.empty() :
                Optional.of(selectedModels);
    }

    private Integer getValidQuantity(DroneModel model) {
        return Utils.readIntegerFromConsole(
                String.format("Quantity of %s (mín 1): ", model.droneName()),
                input -> input > 0 ? Optional.of(input) : Optional.empty()
        );
    }
}
