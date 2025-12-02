package ui.figure;

import controller.category.GetFigureCategoriesController;
import controller.drone.GetDroneModelsController;
import controller.figure.AddFigureController;
import controller.showrequest.ListCostumersController;
import domain.entity.Costumer;
import domain.entity.Figure;
import domain.entity.FigureCategory;
import domain.valueObjects.*;
import figure_dsl.validator.FigureValidationPlugin;
import utils.DSLTextBoxUtils;
import utils.DslMetadata;
import utils.Utils;

import java.util.*;

public class AddFigureUI implements Runnable {

    private final AddFigureController controller = new AddFigureController();
    private final GetFigureCategoriesController figureCategorycontroller = new GetFigureCategoriesController();
    private final ListCostumersController listCostumersController = new ListCostumersController();
    private final GetDroneModelsController getDroneModelsController = new GetDroneModelsController();
    private static final int EXIT = -1;

    @Override
    public void run() {
        Utils.printCenteredTitle("Add Figure");

        Name name = Utils.rePromptWhileInvalid("Enter the Name", Name::new);

        Utils.dropLines(3);
        Description description = null;
        if (Utils.confirm("Do you want to add a Description? (y/n)")) {
            description = Utils.rePromptWhileInvalid("Enter the Description", Description::new);
        } else {
            Utils.printAlterMessage("Skipped...");
        }

        Utils.dropLines(3);
        Optional<List<FigureCategory>> categoriesOpt = figureCategorycontroller.getActiveFigureCategories();
        if (categoriesOpt.isEmpty() || categoriesOpt.get().isEmpty()) {
            Utils.printFailMessage("No categories found. Please create one first.");
            return;
        }
        int catIndex = Utils.showAndSelectIndexPartially(categoriesOpt.get(), "Figure Category");
        if (catIndex == EXIT) {
            Utils.printFailMessage("No category selected.");
            return;
        }
        FigureCategory category = categoriesOpt.get().get(catIndex);

        Utils.dropLines(3);
        FigureAvailability availability = FigureAvailability.PUBLIC;
        if (Utils.confirm("Do you want to set Availability? (y/n)")) {
            availability = Utils.rePromptEnumWhileInvalid("Enter the Availability", FigureAvailability::valueOf);
        } else {
            Utils.printAlterMessage("Default set: PUBLIC");
        }

        Utils.dropLines(3);
        FigureStatus status = FigureStatus.ACTIVE;
        if (Utils.confirm("Do you want to set Status? (y/n)")) {
            status = Utils.rePromptEnumWhileInvalid("Enter the Status", FigureStatus::valueOf);
        } else {
            Utils.printAlterMessage("Default set: ACTIVE");
        }

        Utils.dropLines(3);
        Map<String, DslMetadata> dslVersions = new HashMap<>();
        boolean addMore = Utils.confirm("Do you want to add a DSL? (y/n)");
        while (addMore) {
            List<String> dslLines = DSLTextBoxUtils.getDslLinesFromTextBox("Enter DSL content");
            if (dslLines.isEmpty()) {
                Utils.printFailMessage("DSL content cannot be empty.");
                addMore = Utils.confirm("Try again? (y/n)");
                continue;
            }

            FigureValidationPlugin plugin = new FigureValidationPlugin();
            List<String> errors = plugin.validate(dslLines);

            if (!errors.isEmpty()) {
                Utils.printFailMessage("DSL has errors:");
                errors.forEach(Utils::printFailMessage);
                addMore = Utils.confirm("Try again? (y/n)");
                continue;
            }

            String droneModelName = plugin.getDroneModelName();
            if (droneModelName == null || droneModelName.isBlank()) {
                Utils.printFailMessage("Could not extract DroneType from DSL.");
                addMore = Utils.confirm("Try again? (y/n)");
                continue;
            }

            boolean modelExists = getDroneModelsController.getAllModels()
                    .map(list -> list.stream().anyMatch(m -> m.getDroneModelID().toString().equals(droneModelName)))
                    .orElse(false);

            if (!modelExists) {
                Utils.printFailMessage("DroneType '" + droneModelName + "' not found in system.");
                addMore = Utils.confirm("Try again? (y/n)");
                continue;
            }

            String version = plugin.getDslVersion();
            if (version == null || version.isBlank()) {
                Utils.printFailMessage("Could not extract DSL version.");
                addMore = Utils.confirm("Try again? (y/n)");
                continue;
            }

            if (dslVersions.containsKey(version)) {
                Utils.printFailMessage("Version '" + version + "' already added.");
                addMore = Utils.confirm("Try again? (y/n)");
                continue;
            }

            dslVersions.put(version, new DslMetadata(droneModelName, dslLines));
            Utils.printSuccessMessage("DSL version " + version + " added.");
            addMore = Utils.confirm("Do you want to add another DSL? (y/n)");
        }

        if (dslVersions.isEmpty()) {
            Utils.printFailMessage("At least one DSL is required.");
            return;
        }

        Utils.dropLines(3);
        Optional<Costumer> costumer = Optional.empty();
        Optional<List<Costumer>> costumers = listCostumersController.getAllCustomer();
        if (costumers.isPresent() && !costumers.get().isEmpty()) {
            int index = Utils.showAndSelectIndexPartially(costumers.get(), "Costumer");
            if (index != EXIT) {
                costumer = Optional.ofNullable(costumers.get().get(index));
            } else {
                Utils.printAlterMessage("Skipped...");
            }
        }

        try {
            Optional<Figure> result = controller.addFigure(
                    name, description, category, availability, status, dslVersions, costumer.orElse(null)
            );

            if (result.isPresent()) {
                Utils.printSuccessMessage("Figure added successfully!");
            } else {
                Utils.printFailMessage("Error: Figure already exists with same name/category/costumer.");
            }
        } catch (IllegalArgumentException ex) {
            Utils.printFailMessage("Error while saving figure: " + ex.getMessage());
        }
    }
}