package controller.showproposal;

import controller.drone.GetDroneModelsController;
import domain.entity.DroneModel;
import domain.entity.ShowProposal;
import drone.DroneModelInformation;
import persistence.RepositoryProvider;

import java.util.*;
import java.util.stream.Collectors;

public class AddDronesShowProposalController {

    private final GetDroneModelsController getDroneModelsController = new GetDroneModelsController();

    public AddDronesShowProposalController() {
    }

    public List<DroneModelInformation> getCurrentDroneModels(ShowProposal proposal) {
        Optional<Map<DroneModel, Integer>> inventoryOpt = getDroneModelsController.getDroneModelQuantity();
        Map<DroneModel, Integer> totalInventory = inventoryOpt.orElse(new HashMap<>());

        return proposal.getModelsUsed().entrySet().stream()
                .map(entry -> {
                    DroneModel model = entry.getKey();
                    int usedQty = entry.getValue();
                    int maxAvailable = totalInventory.getOrDefault(model, 0);
                    return new DroneModelInformation(model, usedQty, maxAvailable);
                })
                .collect(Collectors.toList());
    }

    public List<DroneModelInformation> getAvailableDroneModelsToAdd(ShowProposal proposal) {
        Optional<Map<DroneModel, Integer>> inventoryOpt = getDroneModelsController.getDroneModelQuantity();
        Map<DroneModel, Integer> totalInventory = inventoryOpt.orElse(new HashMap<>());

        Map<DroneModel, Integer> usedInProposal = proposal.getModelsUsed();

        return totalInventory.entrySet().stream()
                .map(entry -> {
                    DroneModel model = entry.getKey();
                    int totalAvailable = entry.getValue();
                    int usedQty = usedInProposal.getOrDefault(model, 0);
                    int available = Math.max(totalAvailable - usedQty, 0);
                    return new DroneModelInformation(model, usedQty, available + usedQty); // maxAvailable = initial total
                })
                .filter(info -> (info.maxAvailable() - info.usedQty()) > 0)
                .collect(Collectors.toList());
    }

    public Optional<String> changeDroneModelQuantity(ShowProposal proposal, DroneModel model, int newQty) {
        int maxAvailable = getMaxAvailableForModel(proposal, model);

        if (newQty < 0 || newQty > maxAvailable) {
            return Optional.of("Invalid quantity. Must be between 0 and " + maxAvailable);
        }

        if (newQty == 0) {
            proposal.getModelsUsed().remove(model);
        } else {
            proposal.getModelsUsed().put(model, newQty);
        }

        return Optional.empty();
    }

    public Optional<String> addDroneModels(ShowProposal proposal, Map<DroneModel, Integer> modelsToAdd) {
        Optional<Map<DroneModel, Integer>> inventoryOpt = getDroneModelsController.getDroneModelQuantity();
        Map<DroneModel, Integer> totalInventory = inventoryOpt.orElse(new HashMap<>());

        Map<DroneModel, Integer> usedInProposal = proposal.getModelsUsed();

        for (Map.Entry<DroneModel, Integer> entry : modelsToAdd.entrySet()) {
            DroneModel model = entry.getKey();
            int qtyToAdd = entry.getValue();

            int maxAvailable = totalInventory.getOrDefault(model, 0);
            int usedQty = usedInProposal.getOrDefault(model, 0);
            int available = Math.max(maxAvailable - usedQty, 0);

            if (qtyToAdd > available) {
                return Optional.of("Not enough drones available for model: " + model.droneName());
            }

            proposal.getModelsUsed().put(model, usedQty + qtyToAdd);
        }

        return Optional.empty();
    }

    public Optional<String> removeDroneModel(ShowProposal proposal, DroneModel model) {
        if (!proposal.getModelsUsed().containsKey(model)) {
            return Optional.of("Drone model is not in the proposal.");
        }

        proposal.getModelsUsed().remove(model);
        return Optional.empty();
    }

    public Optional<ShowProposal> saveProposal(ShowProposal proposal) {
        int newTotal = proposal.getModelsUsed().values().stream().mapToInt(Integer::intValue).sum();
        proposal.setNumberOfDrones(newTotal);

        return RepositoryProvider.showProposalRepository().saveInStore(proposal);
    }

    private int getMaxAvailableForModel(ShowProposal proposal, DroneModel model) {
        Optional<Map<DroneModel, Integer>> inventoryOpt = getDroneModelsController.getDroneModelQuantity();
        return inventoryOpt.orElse(new HashMap<>()).getOrDefault(model, 0);
    }
}
