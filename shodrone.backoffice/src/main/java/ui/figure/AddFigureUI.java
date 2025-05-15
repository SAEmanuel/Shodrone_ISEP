package ui.figure;

import controller.figure.AddFigureController;
import controller.category.GetFigureCategoriesController;
import controller.showrequest.ListCostumersController;
import domain.entity.Costumer;
import domain.entity.Figure;
import domain.entity.FigureCategory;
import domain.valueObjects.*;
import utils.Utils;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;


public class AddFigureUI implements Runnable {

    private final AddFigureController controller = new AddFigureController();
    private final GetFigureCategoriesController figureCategorycontroller = new GetFigureCategoriesController();
    private final ListCostumersController listCostumersController = new ListCostumersController();

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
            figureCategory = (Optional<FigureCategory>) Utils.showAndSelectObjectFromListStartingOnOne((Optional<List<?>>)(Optional<?>) listOfFigureCategories, "Figure Category");
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
            costumer = (Optional<Costumer>) Utils.showAndSelectObjectFromListStartingOnOne((Optional<List<?>>) (Optional<?>) listOfCostumers, "Costumer");
        }else{
            Utils.printFailMessage("The are no costumers to add to figure! Added it first and try again!");
            return;
        }

        Optional<Figure> result;

        result = controller.addFigure(name, description, version, figureCategory.get(), availability, status, dsl, costumer.get());

        if (result.isPresent()) {
            Utils.printSuccessMessage("Figure added successfully!");
        } else {
            Utils.printFailMessage("Error Figure already exist! Same : (name, category and costumer not allowed)");
        }
    }

    private <T> Optional<T> refurseOrAcceptValueObject(Boolean option, String prompt, Function<String, T> parser, Class<T> clazz) {
        if (!option) {
            Utils.printAlterMessage("Skipped...");
            return Optional.empty();
        } else {
            T value = Utils.rePromptWhileInvalid("Enter the "+ prompt, parser);
            return Optional.of(value);
        }
    }

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
