package ui;


import controller.EditFigureCategoryController;

import controller.GetFigureCategoriesController;
import domain.entity.FigureCategory;
import domain.valueObjects.Description;
import domain.valueObjects.Name;
import utils.Utils;

import java.util.List;
import java.util.Optional;

public class EditFigureCategoryUI implements Runnable {

    private final EditFigureCategoryController editFigureCategoryController = new EditFigureCategoryController();
    private final GetFigureCategoriesController getFigureCategoriesController = new GetFigureCategoriesController();

    @Override
    public void run() {
        Utils.printCenteredTitle("Edit Figure Category");

        Optional<List<FigureCategory>> allCategoriesOptional = getFigureCategoriesController.getAllFigureCategories();
        if (allCategoriesOptional.isPresent()) {
            int index = Utils.showAndSelectIndexPartially(allCategoriesOptional.get(), "Select the desired category to edit");

            if (index < 0) {
                Utils.printFailMessage("No category selected.");
                return;
            }

            FigureCategory chosenCategory = allCategoriesOptional.get().get(index);

            Utils.printAlterMessage("Current name: " + chosenCategory.identity());
            boolean editName = Utils.confirm("Do you want to edit the category's name? (y/n)");

            Utils.showNameRules();
            Name newName = editName ? Utils.rePromptWhileInvalid("Enter the Category name: ", Name::new) : null;

            Utils.printAlterMessage("Current description: " + chosenCategory.description());
            boolean editDescription = Utils.confirm("Do you want to edit the category's description? (y/n)");

            Utils.showDescriptionRules();
            Description newDescription = editDescription ? Utils.rePromptWhileInvalid("Enter the Category description: ", Description::new) : null;

            if (!editName && !editDescription) {
                Utils.printFailMessage("Nothing has changed!");
                return;
            }

            Optional<FigureCategory> editedCategory = editFigureCategoryController.editChosenCategory(chosenCategory, newName, newDescription);
            editedCategory.ifPresentOrElse(
                    figureCategory -> Utils.printSuccessMessage("Category updated successfully!"),
                    () -> Utils.printFailMessage("Failed to update category.")
            );
        } else {
            Utils.printFailMessage("No categories in the system yet...");
        }
    }
}
