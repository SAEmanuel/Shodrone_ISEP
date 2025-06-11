package ui.figure;

import controller.drone.GetDroneModelsController;
import controller.figure.AddFigureController;
import controller.category.GetFigureCategoriesController;
import controller.showrequest.ListCostumersController;
import domain.entity.Costumer;
import domain.entity.DroneModel;
import domain.entity.Figure;
import domain.entity.FigureCategory;
import domain.valueObjects.*;
import figure_dsl.validator.FigureValidationPlugin;
import utils.Utils;
import utils.DSLTextBoxUtils;

import java.util.*;

import java.util.function.Function;

public class AddFigureUI implements Runnable {

    private final AddFigureController controller = new AddFigureController();
    private final GetFigureCategoriesController figureCategorycontroller = new GetFigureCategoriesController();
    private final ListCostumersController listCostumersController = new ListCostumersController();
    private final GetDroneModelsController getDroneModelsController = new GetDroneModelsController();
    private static final int EXIT = -1;

    @Override
    public void run() {
        Utils.printCenteredTitle("Add Figure");

        Utils.showNameRules();
        Name name = Utils.rePromptWhileInvalid("Enter the Name", Name::new);

        Utils.dropLines(3);
        Utils.showDescriptionRules();
        boolean option = Utils.confirm("Do you want to add a Description? (y/n)");
        Optional<Description> descriptionOpt = refurseOrAcceptValueObject(option, "Description", Description::new, Description.class);
        Description description = descriptionOpt.orElse(null);

        Utils.dropLines(3);
        Optional<List<FigureCategory>> listOfFigureCategories = figureCategorycontroller.getActiveFigureCategories();
        Optional<FigureCategory> figureCategory = Optional.empty();
        if (listOfFigureCategories.isPresent() && !listOfFigureCategories.get().isEmpty()) {
            int index = Utils.showAndSelectIndexPartially(listOfFigureCategories.get(), "Figure Category");
            if (index == EXIT) {
                Utils.printFailMessage("No figure category selected...");
                return;
            }
            figureCategory = Optional.ofNullable(listOfFigureCategories.get().get(index));
        } else {
            Utils.printFailMessage("There are no categories to add to figure! Add one first and try again!");
            return;
        }

        Utils.dropLines(3);
        Utils.showAvailabilityRules();
        option = Utils.confirm("Do you want to add an Availability? (y/n)");
        Optional<FigureAvailability> availabilityOpt = refurseOrAcceptValueObjectEnum(option, "Availability", FigureAvailability::valueOf, FigureAvailability.class);
        FigureAvailability availability = availabilityOpt.orElse(FigureAvailability.PUBLIC);

        Utils.dropLines(3);
        Utils.showStatusRules();
        option = Utils.confirm("Do you want to add a Status? (y/n)");
        Optional<FigureStatus> statusOpt = refurseOrAcceptValueObjectEnum(option, "Status", FigureStatus::valueOf, FigureStatus.class);
        FigureStatus status = statusOpt.orElse(FigureStatus.ACTIVE);

        Utils.dropLines(3);
        Utils.showDSLRules();
        Map<String, List<String>> dslVersions = new HashMap<>();
        boolean addMoreDsl = Utils.confirm("Do you want to add a DSL? (y/n)");
        while (addMoreDsl) {
            List<String> dslLines = DSLTextBoxUtils.getDslLinesFromTextBox("Enter DSL content");
            if (dslLines.isEmpty()) {
                Utils.printFailMessage("DSL content cannot be empty!");
                addMoreDsl = Utils.confirm("Do you want to add another DSL? (y/n)");
                continue;
            }

            String droneModelDSL = "";
            if (dslLines.size() > 1) {
                String line = dslLines.get(1).trim();
                if (line.startsWith("DroneType ")) {
                    droneModelDSL = line;
                }
            }

            if (droneModelDSL.isEmpty()) {
                Utils.printFailMessage("Failed to extract drone model from DSL!");
                addMoreDsl = Utils.confirm("Do you want to try entering DSL again? (y/n)");
                continue;
            }

            boolean foundDrone = false;

            Optional<List<DroneModel>> listDroneModels = getDroneModelsController.getAllModels();
            if(listDroneModels.isPresent() && !listDroneModels.get().isEmpty()) {
                for(DroneModel model : listDroneModels.get()) {
                    if(model.droneName().equals(droneModelDSL)) {
                        foundDrone = true;
                    }
                }
            }else{
                Utils.printFailMessage("No drone models found in the system.");
                return;
            }

            if (!foundDrone) {
                Utils.printFailMessage("Didn't found drone model");
                return;
            }

            FigureValidationPlugin plugin = new FigureValidationPlugin();
            List<String> errors = plugin.validate(dslLines);

            if (!errors.isEmpty()) {
                Utils.printFailMessage("Errors in DSL:");
                errors.forEach(Utils::printFailMessage);
                addMoreDsl = Utils.confirm("Do you want to try adding a DSL again? (y/n)");
                continue;
            }

            String version = plugin.getDslVersion();
            if (version == null || version.isEmpty()) {
                Utils.printFailMessage("Could not extract DSL version from the first line. Make sure it starts with 'DSL version X.Y.Z;'");
                addMoreDsl = Utils.confirm("Do you want to try adding a DSL again? (y/n)");
                continue;
            }

            if (dslVersions.containsKey(version)) {
                Utils.printFailMessage("A DSL with this version already exists in this figure.");
                addMoreDsl = Utils.confirm("Do you want to try adding a DSL again? (y/n)");
                continue;
            }

            dslVersions.put(version, dslLines);
            Utils.printSuccessMessage("DSL version " + version + " added successfully!");
            addMoreDsl = Utils.confirm("Do you want to add another DSL? (y/n)");
        }
        if (dslVersions.isEmpty()) {
            Utils.printFailMessage("At least one DSL is required!");
            return;
        }


        Utils.dropLines(3);
        Optional<List<Costumer>> listOfCostumers = listCostumersController.getAllCustomer();
        Optional<Costumer> costumer = Optional.empty();
        if (listOfCostumers.isPresent() && !listOfCostumers.get().isEmpty()) {
            int index = Utils.showAndSelectIndexPartially(listOfCostumers.get(), "Costumer");
            if (index != EXIT) {
                costumer = Optional.ofNullable(listOfCostumers.get().get(index));
            } else {
                Utils.printAlterMessage("Skipped...");
            }
        }

        Optional<Figure> result = controller.addFigure(
                name,
                description,
                figureCategory.orElse(null),
                availability,
                status,
                dslVersions,
                costumer.orElse(null)
        );

        if (result.isPresent()) {
            Utils.printSuccessMessage("Figure added successfully!");
        } else {
            Utils.printFailMessage("Error: Figure already exists! Same (name, category and costumer not allowed)");
        }
    }

    private <T> Optional<T> refurseOrAcceptValueObject(Boolean option, String prompt, Function<String, T> parser, Class<T> clazz) {
        if (!option) {
            Utils.printAlterMessage("Skipped...");
            return Optional.empty();
        } else {
            T value = Utils.rePromptWhileInvalid("Enter the " + prompt, parser);
            return Optional.of(value);
        }
    }

    private <T> Optional<T> refurseOrAcceptValueObjectEnum(Boolean option, String prompt, Function<String, T> parser, Class<T> clazz) {
        if (!option) {
            Utils.printAlterMessage("Skipped...");
            return Optional.empty();
        } else {
            T value = Utils.rePromptEnumWhileInvalid("Enter the " + prompt, parser);
            return Optional.of(value);
        }
    }
}
