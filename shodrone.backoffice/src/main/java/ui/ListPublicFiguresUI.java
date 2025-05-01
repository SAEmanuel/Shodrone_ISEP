package ui;

import controller.ListPublicFiguresController;
import domain.entity.Figure;
import domain.entity.FigureCategory;
import more.Description;
import more.Name;
import utils.Utils;

import java.util.List;
import java.util.Optional;

public class ListPublicFiguresUI implements Runnable {
    private final ListPublicFiguresController listPublicFiguresController = new ListPublicFiguresController();

    @Override
    public void run() {
         Utils.printCenteredTitle("Not implemented yet");
        /*

        Utils.printCenteredTitle("List Public Figures");

        List<Figure> allPublicFigures = listPublicFiguresController.listPublicFigures();
        if (!allPublicFigures.isEmpty()) {

            boolean page = Utils.confirm("Do you want to select a page number? (y/n)");
            int newpage = page ? Utils.readIntegerFromConsole("Enter the page number: ") : null;

            Utils.printAlterMessage("Current description: " + chosenCategory.description());
            boolean editDescription = Utils.confirm("Do you want to edit the category's description? (y/n)");
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
            Utils.printFailMessage("No categories in the system yet.");
        }*/
    }
}
