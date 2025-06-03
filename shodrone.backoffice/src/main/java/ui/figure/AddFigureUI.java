package ui.figure;

import controller.figure.AddFigureController;
import controller.category.GetFigureCategoriesController;
import controller.showrequest.ListCostumersController;
import domain.entity.Costumer;
import domain.entity.Figure;
import domain.entity.FigureCategory;
import domain.valueObjects.*;
import session.DatabaseSync;
import utils.Utils;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * User Interface class responsible for the interactive process of adding a new Figure.
 * Implements Runnable to be executed in a thread or as a standalone UI operation.
 * Uses controllers to retrieve necessary data and perform operations.
 */
public class AddFigureUI implements Runnable {

    private final AddFigureController controller = new AddFigureController();
    private final GetFigureCategoriesController figureCategorycontroller = new GetFigureCategoriesController();
    private final ListCostumersController listCostumersController = new ListCostumersController();
    private static final int EXIT = -1;

    /**
     * Runs the interactive console UI flow for adding a new Figure.
     * Guides the user through entering all necessary attributes with validation,
     * offers optional input for several attributes,
     * retrieves necessary reference data (categories, costumers),
     * and submits the figure for persistence through the controller.
     */
    @Override
    public void run() {
        Utils.printCenteredTitle("Add Figure");

        boolean option = false;

        Utils.showNameRules();
        Name name = Utils.rePromptWhileInvalid("Enter the Name", Name::new);

        Utils.dropLines(3);
        Utils.showDescriptionRules();
        option = Utils.confirm("Do you want to add a Description? (y/n)");
        Optional<Description> descriptionOpt = refurseOrAcceptValueObject(option, "Description", Description::new, Description.class);
        Description description = descriptionOpt.orElse(null);

        Utils.dropLines(3);
        option = Utils.confirm("Do you want to add a Version? (y/n)");
        Optional<Long> versionOpt = refurseOrAcceptValueObject(option,"Version", Long::valueOf, Long.class);
        Long version = versionOpt.orElse(null);

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

        }else{
            Utils.printFailMessage("The are no categories to add to figure! Added it first and try again!");
            return;
        }

        Utils.dropLines(3);
        Utils.showAvailabilityRules();
        option = Utils.confirm("Do you want to add a Availability? (y/n)");
        Optional<FigureAvailability> availabilityOpt = refurseOrAcceptValueObjectEnum(option,"Availability", FigureAvailability::valueOf, FigureAvailability.class);
        FigureAvailability availability = availabilityOpt.orElse(FigureAvailability.PUBLIC);

        Utils.dropLines(3);
        Utils.showStatusRules();
        option = Utils.confirm("Do you want to add a Status? (y/n)");
        Optional<FigureStatus> statusOpt = refurseOrAcceptValueObjectEnum(option,"Status", FigureStatus::valueOf, FigureStatus.class);
        FigureStatus status = statusOpt.orElse(FigureStatus.ACTIVE);

        Utils.dropLines(3);
        Utils.showDSLRules();
        option = Utils.confirm("Do you want to add a DSL File? (y/n)");
        Optional<DSL> DSLOpt = refurseOrAcceptValueObject(option,"DSL", DSL::new, DSL.class);
        DSL dsl = DSLOpt.orElse(null);

        Utils.dropLines(3);
        Optional<List<Costumer>> listOfCostumers = listCostumersController.getAllCustomer();
        Optional<Costumer> costumer = Optional.empty();
        if ( listOfCostumers.isPresent() && !listOfCostumers.get().isEmpty() ) {
            int index = Utils.showAndSelectIndexPartially(listOfCostumers.get(), "Costumer");

            if (index != EXIT) {
                costumer = Optional.ofNullable(listOfCostumers.get().get(index));
            }else{
                Utils.printAlterMessage("Skipped...");
            }
        }

        Optional<Figure> result;

        result = controller.addFigure(name, description, version, figureCategory.orElse(null), availability, status, dsl, costumer.orElse(null));

        if (result.isPresent() && !result.isEmpty()) {
            Utils.printSuccessMessage("Figure added successfully!");
            DatabaseSync.sync();
        } else {
            Utils.printFailMessage("Error Figure already exist! Same : (name, category and costumer not allowed)");
        }
    }

    /**
     * Helper method to optionally accept or skip input for a value object.
     * If user chooses to skip, returns empty Optional.
     * Otherwise, prompts repeatedly until valid input is entered.
     *
     * @param option boolean indicating whether the user wants to provide the value
     * @param prompt label of the input requested
     * @param parser function that converts String input into the target value object
     * @param clazz class of the target value object (not used here but kept for signature consistency)
     * @param <T> type of value object
     * @return Optional containing the parsed value or empty if skipped
     */
    private <T> Optional<T> refurseOrAcceptValueObject(Boolean option, String prompt, Function<String, T> parser, Class<T> clazz) {
        if (!option) {
            Utils.printAlterMessage("Skipped...");
            return Optional.empty();
        } else {
            T value = Utils.rePromptWhileInvalid("Enter the "+ prompt, parser);
            return Optional.of(value);
        }
    }

    /**
     * Helper method to optionally accept or skip input for an enum-based value object.
     * Similar to refurseOrAcceptValueObject but uses enum-specific validation.
     *
     * @param option boolean indicating whether the user wants to provide the value
     * @param prompt label of the input requested
     * @param parser function that converts String input into the target enum value
     * @param clazz class of the target enum
     * @param <T> type of enum
     * @return Optional containing the parsed enum value or empty if skipped
     */
    private <T> Optional<T> refurseOrAcceptValueObjectEnum(Boolean option, String prompt, Function<String, T> parser, Class<T> clazz) {
        if (!option) {
            Utils.printAlterMessage("Skipped...");
            return Optional.empty();
        } else {
            T value = Utils.rePromptEnumWhileInvalid("Enter the "+ prompt, parser);
            return Optional.of(value);
        }
    }
}
