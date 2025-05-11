package ui.figure;

import controller.category.GetFigureCategoriesController;
import controller.showrequest.ListCostumersController;
import controller.figure.SearchFigureController;
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

public class SearchFigureUI implements Runnable {
    private final SearchFigureController controller = new SearchFigureController();
    private final GetFigureCategoriesController figureCategorycontroller = new GetFigureCategoriesController();
    private final ListCostumersController listCostumersController = new ListCostumersController();

    @Override
    public void run() {
        Utils.printCenteredTitle("Search Figure");

        boolean option = false;

        option = Utils.confirm("Do you want to add a Figure ID to the search? (y/n)");
        Utils.showDescriptionRules();
        Optional<Long> figureIdOpt = refurseOrAcceptValueObject(option, "Figure ID", Long::valueOf, Long.class);
        Long figureId = figureIdOpt.orElse(null);

        option = Utils.confirm("Do you want to add a name to the search? (y/n)");
        String name = null;
        if(option) {
            Utils.showNameRules();
            name = Utils.rePromptWhileInvalid("Enter the Name", String::new);
        }

        Utils.dropLines(1);

        option = Utils.confirm("Do you want to add a Description to the search? (y/n)");
        Utils.showDescriptionRules();
        Optional<Description> descriptionOpt = refurseOrAcceptValueObject(option, "Description", Description::new, Description.class);
        Description description = descriptionOpt.orElse(null);

        Utils.dropLines(1);
        option = Utils.confirm("Do you want to add a Version to the search? (y/n)");
        Optional<Long> versionOpt = refurseOrAcceptValueObject(option,"Version", Long::valueOf, Long.class);
        Long version = versionOpt.orElse(null);

        option = Utils.confirm("Do you want to add a Figure Category to the search? (y/n)");
        Optional<FigureCategory> figureCategory = Optional.empty();
        if(option) {
            System.out.println();
            Optional<List<FigureCategory>> listOfFigureCategories = figureCategorycontroller.getActiveFigureCategories();
            if (listOfFigureCategories.isPresent() && !listOfFigureCategories.get().isEmpty()) {
                figureCategory = (Optional<FigureCategory>) Utils.showAndSelectObjectFromList((Optional<List<?>>) (Optional<?>) listOfFigureCategories, "Figure Category");
            }else{
                Utils.printAlterMessage("Didn't found any Figure Categories!");
            }
        }

        option = Utils.confirm("Do you want to add a Availability to the search? (y/n)");
        Utils.showAvailabilityRules();
        Optional<FigureAvailability> availabilityOpt = refurseOrAcceptValueObjectEnum(option,"Availability", FigureAvailability::valueOf, FigureAvailability.class);
        FigureAvailability availability = availabilityOpt.orElse(null);

        option = Utils.confirm("Do you want to add a Status to the search? (y/n)");
        Utils.showStatusRules();
        Optional<FigureStatus> statusOpt = refurseOrAcceptValueObjectEnum(option,"Status", FigureStatus::valueOf, FigureStatus.class);
        FigureStatus status = statusOpt.orElse(null);

        System.out.println();
        option = Utils.confirm("Do you want to add a Costumer to the search? (y/n)");
        Optional<Costumer> costumer = Optional.empty();
        if(option) {
            Optional<List<Costumer>> listOfCostumers = listCostumersController.getAllCustomer();
            if (listOfCostumers.isPresent() && !listOfCostumers.get().isEmpty()) {
                costumer = (Optional<Costumer>) Utils.showAndSelectObjectFromList((Optional<List<?>>) (Optional<?>) listOfCostumers, "Costumer");
            }else{
                Utils.printAlterMessage("Didn't found any Costumer!");
            }
        }

        Optional<List<Figure>> result = controller.searchFigure(figureId, name, description, version, figureCategory.orElse(null), availability, status, costumer.orElse(null));
        
        if (result.isPresent() && !result.get().isEmpty()) {
            Utils.showListElements(result.get(), "Figure List Found");
        } else {
            Utils.printFailMessage("Search didn't found any Figures!");
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
