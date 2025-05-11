package ui;

import authz.Email;
import controller.AddFigureCategoryController;
import domain.entity.FigureCategory;
import domain.valueObjects.Description;
import domain.valueObjects.Name;
import utils.AuthUtils;
import utils.Utils;

import java.util.Optional;


/**
 * UI for adding a new Figure Category.
 */
public class AddFigureCategoryUI implements Runnable {

    private final AddFigureCategoryController controller = new AddFigureCategoryController();

    /**
     * Runs the UI flow for adding a new figure category.
     */
    @Override
    public void run() {
        Utils.printCenteredTitle("Add Figure Category");

        Utils.printCenteredSubtitleV2("Name");
        Utils.showNameRules();
        Name name = Utils.rePromptWhileInvalid("Enter the Category name: ", Name::new);
        Description description;

        Utils.printCenteredSubtitleV2("Description");
        boolean option = Utils.confirm("Do you want to add a description? (y/n)");

        Optional<FigureCategory> result;

        Email createdBy = new Email(AuthUtils.getCurrentUserEmail());
        if (!option) {
            Utils.printAlterMessage("Description skipped...");
            result = controller.addFigureCategoryWithName(name, createdBy);

        } else {
            Utils.showDescriptionRules();
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
