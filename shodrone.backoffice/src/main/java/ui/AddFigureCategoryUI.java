package ui;

import authz.Email;
import controller.AddFigureCategoryController;
import domain.entity.FigureCategory;
import more.Description;
import more.Name;
import utils.Utils;

import java.util.Optional;


public class AddFigureCategoryUI implements Runnable {

    private final AddFigureCategoryController controller = new AddFigureCategoryController();

    @Override
    public void run() {
        Utils.printCenteredTitle("Add Figure Category");

        Name name = Utils.rePromptWhileInvalid("Enter the Category name: ", Name::new);
        Description description;

        boolean option = Utils.confirm("Do you want to add a description? (y/n)");

        Optional<FigureCategory> result;
        //todo (CreatedBy is not possible to find yet) - to be implemented by Xu
        Email createdBy = new Email("xu_vai_implementar@gmail.com");
        if (!option) {
            Utils.printAlterMessage("Description skipped...");
            result = controller.addFigureCategoryWithName(name, createdBy);
        } else {
            description = Utils.rePromptWhileInvalid("Enter the Category description: ", Description::new);
            result = controller.addFigureCategoryWithNameAndDescription(name, description, createdBy);
        }

        if (result.isPresent()) {
            Utils.printSuccessMessage("Category added successfully!");
        } else {
            Utils.printFailMessage("A category with that name already exists!");
        }
    }

}
