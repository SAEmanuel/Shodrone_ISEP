package ui;

import controller.GetFigureCategoriesController;
import domain.entity.FigureCategory;
import utils.Utils;

import java.util.List;
import java.util.Optional;

public class ListAllFigureCategoriesUI implements Runnable {
    private final GetFigureCategoriesController controller = new GetFigureCategoriesController();

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
