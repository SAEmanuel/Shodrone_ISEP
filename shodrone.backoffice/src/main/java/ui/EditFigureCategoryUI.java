package ui;

import controller.EditFigureCategoryController;
import controller.GetFigureCategoriesController;
import domain.entity.FigureCategory;
import domain.valueObjects.Description;
import domain.valueObjects.Name;
import utils.Utils;

import java.util.List;
import java.util.Optional;

/**
 * UI for editing an existing Figure Category.
 * Allows the user to select an active category and update its name and/or description.
 */
public class EditFigureCategoryUI implements Runnable {

    private final EditFigureCategoryController editFigureCategoryController = new EditFigureCategoryController();
    private final GetFigureCategoriesController getFigureCategoriesController = new GetFigureCategoriesController();
    private static final int EXIT = -1;

    /**
     * Runs the UI flow for editing a figure category.
     * Guides the user through selecting a category and updating its fields.
     */
    @Override
    public void run() {
        Utils.printCenteredTitle("Edit Figure Category");

        Optional<List<FigureCategory>> activeCategoriesOptional = getFigureCategoriesController.getActiveFigureCategories();
        if (activeCategoriesOptional.isPresent()) {
            Utils.printCenteredSubtitleV2("Figure Category selection");
            int index = Utils.showAndSelectIndexPartially(activeCategoriesOptional.get(), "Select the desired category to edit");

            if (index == EXIT) {
                Utils.printFailMessage("No category selected...");
                return;
            }

            FigureCategory chosenCategory = activeCategoriesOptional.get().get(index);

            Utils.printCenteredSubtitleV2("Name");
            Utils.printAlterMessage("Current name: " + chosenCategory.identity());
            boolean editName = Utils.confirm("Do you want to edit the category's name? (y/n)");
            Name newName = null;
            if (editName) {
                Utils.showNameRules();
                newName = Utils.rePromptWhileInvalid("Enter the Category name: ", Name::new);
            } else {
                Utils.silentWarning("Name maintained...");
                Utils.dropLines(2);
            }

            Utils.printCenteredSubtitleV2("Description");
            Utils.printAlterMessage("Current description: " + chosenCategory.description());
            boolean editDescription = Utils.confirm("Do you want to edit the category's description? (y/n)");
            Description newDescription = null;

            if (editDescription) {
                Utils.printCenteredSubtitle("Description");
                Utils.showDescriptionRules();
                newDescription = Utils.rePromptWhileInvalid("Enter the Category description: ", Description::new);
            } else {
                Utils.silentWarning("Description maintained...");
                Utils.dropLines(2);
            }

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
