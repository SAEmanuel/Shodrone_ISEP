package ui;

import authz.Email;
import controller.AddFigureCategoryController;
import domain.entity.FigureCategory;
import domain.valueObjects.Description;
import domain.valueObjects.Name;
import utils.AuthUtils;
import utils.Utils;

import java.util.Optional;


public class AddFigureCategoryUI implements Runnable {

    private final AddFigureCategoryController controller = new AddFigureCategoryController();

    @Override
    public void run() {
        Utils.printCenteredTitle("Add Figure Category");

        Utils.silentWaring("""
                  The name must follow these rules:
                   • Minimum 3 and maximum 80 characters
                   • Only letters, spaces, apostrophes ('), commas, periods (.) and hyphens (-) are allowed
                   • Must not start with a space or special character
                """);
        Name name = Utils.rePromptWhileInvalid("Enter the Category name: ", Name::new);
        Description description;

        boolean option = Utils.confirm("Do you want to add a description? (y/n)");

        Optional<FigureCategory> result;

        Email createdBy = new Email(AuthUtils.getCurrentUserEmail());
        if (!option) {
            Utils.printAlterMessage("Description skipped...");
            result = controller.addFigureCategoryWithName(name, createdBy);
        } else {
            Utils.silentWaring("""
                      The description must follow these rules:
                       • Minimum 5 and maximum 300 characters
                       • Cannot be null, empty or only whitespace
                    """);
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
