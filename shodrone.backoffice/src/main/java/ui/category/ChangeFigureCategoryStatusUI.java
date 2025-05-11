package ui.category;

import controller.category.ChangeFigureCategoryStatusController;
import controller.category.GetFigureCategoriesController;
import domain.entity.FigureCategory;
import utils.Utils;

import java.util.List;
import java.util.Optional;

/**
 * UI for inactivating or activating a Figure Category.
 * Allows the user to select a category and toggle its status.
 */
public class ChangeFigureCategoryStatusUI implements Runnable {

    private final GetFigureCategoriesController getFigureCategoriesController = new GetFigureCategoriesController();
    private final ChangeFigureCategoryStatusController changeFigureCategoryStatusController = new ChangeFigureCategoryStatusController();
    private static final int EXIT = -1;

    /**
     * Runs the UI flow for changing the status of a figure category.
     * Displays all categories, lets the user select one, and toggles its status.
     */
    @Override
    public void run() {
        Utils.printCenteredTitle("Inactivate/Activate a Figure Category");

        Optional<List<FigureCategory>> categoriesOptional = getFigureCategoriesController.getAllFigureCategories();

        Utils.printCenteredSubtitleV2("Figure Category selection");
        if (categoriesOptional.isEmpty()) {
            Utils.printFailMessage("No categories in the system yet...");
        } else {
            Utils.printAlterMessage("The current status will change when selected");
            int index = Utils.showAndSelectIndexPartially(categoriesOptional.get(), "Select the desired category to enable/disable");

            if (index != EXIT) {
                FigureCategory selectedCategory = categoriesOptional.get().get(index);
                Optional<FigureCategory> optionalCategory = changeFigureCategoryStatusController.changeStatus(selectedCategory);

                if (optionalCategory.isEmpty()) {
                    Utils.printFailMessage("Failed to change category status");
                } else {
                    Utils.printSuccessMessage("Category status changed");
                }

            } else {
                Utils.printFailMessage("Operation canceled...");
            }

        }

    }
}
