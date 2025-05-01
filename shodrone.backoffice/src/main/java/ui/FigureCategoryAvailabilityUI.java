package ui;

import controller.ChangeFigureCategoryStatusController;
import controller.GetAllFigureCategoriesController;
import domain.entity.FigureCategory;
import utils.Utils;

import java.util.List;
import java.util.Optional;

public class FigureCategoryAvailabilityUI implements Runnable{

    private final GetAllFigureCategoriesController getAllFigureCategoriesController = new GetAllFigureCategoriesController();
    private final ChangeFigureCategoryStatusController changeFigureCategoryStatusController = new ChangeFigureCategoryStatusController();


    @Override
    public void run() {
        Utils.printCenteredTitle("Inactivate/Activate a Figure Category");

        Optional<List<FigureCategory>> categoriesOptional = getAllFigureCategoriesController.getAllFigureCategories();

        if (categoriesOptional.isEmpty()) {
            Utils.printFailMessage("No categories in the system yet...");
        } else {
            Utils.printAlterMessage("The current status will change when selected");
            int index = Utils.showAndSelectIndexCustomOptions(categoriesOptional.get(), "Select the desired category to enable/disable");

            FigureCategory selectedCategory = categoriesOptional.get().get(index);
            Optional<FigureCategory> optionalCategory = changeFigureCategoryStatusController.changeStatus(selectedCategory);

            if(optionalCategory.isEmpty()) {
                Utils.printFailMessage("Failed to change category status");
            } else {
                Utils.printSuccessMessage("Category status changed");
            }

        }
    }
}
