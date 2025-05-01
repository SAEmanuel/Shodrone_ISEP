package ui;

import controller.ListAllFigureCategoriesController;
import domain.entity.FigureCategory;
import utils.Utils;

import java.util.List;
import java.util.Optional;

public class ListAllFigureCategoriesUI implements Runnable {
    private final ListAllFigureCategoriesController controller = new ListAllFigureCategoriesController();

    @Override
    public void run() {
        Utils.printCenteredTitle("All Figure Categories");

        Optional<List<FigureCategory>> allFigureCategoriesOptional = controller.getAllFigureCategories();

        if (allFigureCategoriesOptional.isEmpty()) {
            Utils.printFailMessage("No categories in the system yet...");
        } else {
            List<FigureCategory> allCategories = allFigureCategoriesOptional.get();
            Utils.showListElements(allCategories, "");
        }


    }
}
