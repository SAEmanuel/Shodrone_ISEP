package ui.figure;

import controller.category.GetFigureCategoriesController;
import controller.showrequest.ListCostumersController;
import controller.figure.SearchFigureController;
import domain.entity.Costumer;
import domain.entity.Figure;
import domain.entity.FigureCategory;
import domain.valueObjects.*;
import utils.Utils;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * User Interface class responsible for searching Figures based on multiple optional criteria.
 * Implements Runnable for interactive console usage or threading.
 * Collects user inputs for various search filters such as Figure ID, Name, Description, Version,
 * Category, Availability, Status, DSL, and Costumer.
 * Uses the SearchFigureController to perform the search and displays the results.
 */
public class SearchFigureUI implements Runnable {
    private final SearchFigureController controller = new SearchFigureController();
    private final GetFigureCategoriesController figureCategorycontroller = new GetFigureCategoriesController();
    private final ListCostumersController listCostumersController = new ListCostumersController();
    private static final int EXIT = -1;

    /**
     * Runs the interactive UI to prompt user for search criteria,
     * validates and collects optional parameters,
     * invokes the controller to perform the search,
     * and displays the results or failure message.
     */
    @Override
    public void run() {
        Utils.printCenteredTitle("Search Figure");

        boolean option = false;

        Utils.showFigureIDRules();
        option = Utils.confirm("Do you want to add a Figure ID to the search? (y/n)");
        Optional<Long> figureIdOpt = Optional.empty();
        if (option) {
            while (figureIdOpt.isEmpty() || figureIdOpt.get() < 1) {
                figureIdOpt = Optional.ofNullable(Utils.rePromptWhileInvalid("Enter the Figure ID", Long::valueOf));
                if (figureIdOpt.get() < 1) {
                    Utils.printFailMessage("Figure ID lower than 1");
                }
            }
        }else{
            Utils.printAlterMessage("Skipped...");
        }
        Long figureID = figureIdOpt.orElse(null);

        Utils.dropLines(3);
        Utils.showNameRules();
        option = Utils.confirm("Do you want to add a name to the search? (y/n)");
        Optional<Name> nameOpt = refurseOrAcceptValueObject(option, "Name", Name::new, Name.class);
        Name name = nameOpt.orElse(null);

        Utils.dropLines(3);
        Utils.showDescriptionRules();
        option = Utils.confirm("Do you want to add a Description to the search? (y/n)");
        Optional<Description> descriptionOpt = refurseOrAcceptValueObject(option, "Description", Description::new, Description.class);
        Description description = descriptionOpt.orElse(null);

        Utils.dropLines(3);
        option = Utils.confirm("Do you want to add a Version to the search? (y/n)");
        Optional<Long> versionOpt = refurseOrAcceptValueObject(option,"Version", Long::valueOf, Long.class);
        Long version = versionOpt.orElse(null);

        Utils.dropLines(3);
        Optional<FigureCategory> figureCategory = Optional.empty();
        option = Utils.confirm("Do you want to add a Figure Category to the search? (y/n)");
        if(option) {
            System.out.println();
            Optional<List<FigureCategory>> listOfFigureCategories = figureCategorycontroller.getActiveFigureCategories();
            if (listOfFigureCategories.isPresent() && !listOfFigureCategories.get().isEmpty()) {
                int index = Utils.showAndSelectIndexPartially(listOfFigureCategories.get(), "Figure Category");

                if (index == EXIT) {
                    Utils.printFailMessage("No figure category selected...");
                    return;
                }

                figureCategory = Optional.ofNullable(listOfFigureCategories.get().get(index));
            }else{
                Utils.printAlterMessage("Didn't found any Figure Categories!\n");
            }
        }

        Utils.dropLines(3);
        Utils.showAvailabilityRules();
        option = Utils.confirm("Do you want to add a Availability to the search? (y/n)");
        Optional<FigureAvailability> availabilityOpt = refurseOrAcceptValueObjectEnum(option,"Availability", FigureAvailability::valueOf, FigureAvailability.class);
        FigureAvailability availability = availabilityOpt.orElse(null);

        Utils.dropLines(3);
        Utils.showStatusRules();
        option = Utils.confirm("Do you want to add a Status to the search? (y/n)");
        Optional<FigureStatus> statusOpt = refurseOrAcceptValueObjectEnum(option,"Status", FigureStatus::valueOf, FigureStatus.class);
        FigureStatus status = statusOpt.orElse(null);

        Utils.dropLines(3);
        Utils.showDSLRules();
        option = Utils.confirm("Do you want to add a DSL File? (y/n)");
        Optional<DSL> DSLOpt = refurseOrAcceptValueObject(option,"DSL", DSL::new, DSL.class);
        DSL dsl = DSLOpt.orElse(null);

        Utils.dropLines(3);
        option = Utils.confirm("Do you want to add a Costumer to the search? (y/n)");
        Optional<Costumer> costumer = Optional.empty();
        if(option) {
            Optional<List<Costumer>> listOfCostumers = listCostumersController.getAllCustomer();
            if (listOfCostumers.isPresent() && !listOfCostumers.get().isEmpty()) {
                int index = Utils.showAndSelectIndexPartially(listOfCostumers.get(), "Costumer");

                if (index == EXIT) {
                    Utils.printFailMessage("No costumer selected...");
                    return;
                }

                costumer = Optional.ofNullable(listOfCostumers.get().get(index));
            }else{
                Utils.printAlterMessage("Didn't found any Costumer!\n");
            }
        }

        Optional<List<Figure>> result = controller.searchFigure(figureID, name, description, version, figureCategory.orElse(null), availability, status, dsl, costumer.orElse(null));

        if (result.isPresent() && !result.get().isEmpty()) {
            Utils.showListElements(result.get(), "Figure List Found");
        } else {
            Utils.printFailMessage("\nSearch didn't found any Figures!");
        }
    }

    /**
     * Helper method to prompt user for input of a value object type if opted in.
     * Prints skipped message if user declines.
     *
     * @param option whether the user wants to enter the value
     * @param prompt text to prompt user
     * @param parser function to parse input String to type T
     * @param clazz Class object of type T (not used in this method but kept for signature consistency)
     * @param <T> the type of the value object
     * @return Optional of the parsed value or empty if skipped
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
     * Helper method to prompt user for input of an enum value if opted in.
     * Prints skipped message if user declines.
     *
     * @param option whether the user wants to enter the enum value
     * @param prompt text to prompt user
     * @param parser function to parse input String to enum type T
     * @param clazz Class object of enum type T (not used here but kept for signature)
     * @param <T> the enum type
     * @return Optional of the parsed enum value or empty if skipped
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
