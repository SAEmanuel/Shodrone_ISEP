package ui;

import authz.Email;
import controller.AddFigureCategoryController;
import domain.entity.FigureCategory;
import more.Description;
import more.Name;
import utils.AuthUtils;
import utils.Utils;

import java.util.Optional;

import static more.ColorfulOutput.*;

public class AddFigureCategoryUI implements Runnable {

    private final AddFigureCategoryController controller = new AddFigureCategoryController();

    @Override
    public void run() {
        Utils.printCenteredTitle("Add Figure Category");

        Name name = Utils.rePromptWhileInvalid("Enter the Category name: ", Name::new);
        Description description = null;

        boolean option = Utils.confirm("Do you want to add a description? (y/n)");

        Optional<FigureCategory> result;
        Email createdBy = new Email(AuthUtils.getCurrentUserEmail());
        if (!option) {
            System.out.println(ANSI_ORANGE + "Description skipped..." + ANSI_RESET);
            result = controller.addFigureCategoryWithName(name, createdBy);
        } else {
            description = Utils.rePromptWhileInvalid("Enter the Category description: ", Description::new);
            result = controller.addFigureCategoryWithNameAndDescription(name, description, createdBy);
        }

        if (result.isPresent()) {
            System.out.println(ANSI_BRIGHT_GREEN + "Category added successfully!" + ANSI_RESET);
        } else {
            System.out.println(ANSI_BRIGHT_RED + "A category with that name already exists!" + ANSI_RESET);
        }
    }

}
