package ui.figure;

import controller.figure.AddFigureController;
import controller.category.GetFigureCategoriesController;
import controller.showrequest.ListCostumersController;
import domain.entity.Costumer;
import domain.entity.Figure;
import domain.entity.FigureCategory;
import domain.valueObjects.Description;
import domain.valueObjects.FigureAvailability;
import domain.valueObjects.FigureStatus;
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
        String name = Utils.rePromptWhileInvalid("Enter the Name", String::new);

        Utils.dropLines(1);

        option = Utils.confirm("Do you want to add a Description? (y/n)");
        Utils.showDescriptionRules();
        Optional<Description> descriptionOpt = refurseOrAcceptValueObject(option, "Description", Description::new, Description.class);
        Description description = descriptionOpt.orElse(null);

        Utils.dropLines(1);
        option = Utils.confirm("Do you want to add a Version? (y/n)");
        Optional<Long> versionOpt = refurseOrAcceptValueObject(option,"Version", Long::valueOf, Long.class);
        Long version = versionOpt.orElse(null);

        System.out.println();
        Optional<List<FigureCategory>> listOfFigureCategories = figureCategorycontroller.getActiveFigureCategories();
        Optional<FigureCategory> figureCategory = Optional.empty();
        if (listOfFigureCategories.isPresent() && !listOfFigureCategories.get().isEmpty()) {
            figureCategory = (Optional<FigureCategory>) Utils.showAndSelectObjectFromList((Optional<List<?>>)(Optional<?>) listOfFigureCategories, "Figure Category");
        }

        option = Utils.confirm("Do you want to add a Availability? (y/n)");
        Utils.showAvailabilityRules();
        Optional<FigureAvailability> availabilityOpt = refurseOrAcceptValueObjectEnum(option,"Availability", FigureAvailability::valueOf, FigureAvailability.class);
        FigureAvailability availability = availabilityOpt.orElse(FigureAvailability.PUBLIC);

        option = Utils.confirm("Do you want to add a Status? (y/n)");
        Utils.showStatusRules();
        Optional<FigureStatus> statusOpt = refurseOrAcceptValueObjectEnum(option,"Status", FigureStatus::valueOf, FigureStatus.class);
        FigureStatus status = statusOpt.orElse(FigureStatus.ACTIVE);

        Optional<Figure> result;

        System.out.println();
        Optional<List<Costumer>> listOfCostumers = listCostumersController.getAllCustomer();
        Optional<Costumer> costumer = Optional.empty();
        if (listOfCostumers.isPresent() && !listOfCostumers.get().isEmpty()) {
            costumer = (Optional<Costumer>) Utils.showAndSelectObjectFromList((Optional<List<?>>) (Optional<?>) listOfCostumers, "Costumer");

            result = controller.addFigure(name, description, version, figureCategory.get(), availability, status, costumer.get());
        }else{
            result = Optional.empty();
            Utils.printFailMessage("The are no costumers to add to figure!");
        }

        if (result.isPresent()) {
            Utils.printSuccessMessage("Figure added successfully!");
        } else {
            Utils.printFailMessage("Error Figure not added!");
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
