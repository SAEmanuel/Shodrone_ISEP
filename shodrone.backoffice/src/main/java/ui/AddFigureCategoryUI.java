package ui;

import controller.AddFigureCategoryController;
import eapli.framework.presentation.console.AbstractUI;
import more.Description;
import more.Name;
import utils.Utils;

import static more.ColorfulOutput.ANSI_ORANGE;
import static more.ColorfulOutput.ANSI_RESET;


public class AddFigureCategoryUI extends AbstractUI {

    private final AddFigureCategoryController controller = new AddFigureCategoryController();

    @Override
    protected boolean doShow() {

        Name name = Utils.rePromptWhileInvalid("Enter the Category name: ", Name::new);
        Description description;

        boolean option = Utils.confirm("Do you want to add a description? ");

        if (!option) {
            System.out.println(ANSI_ORANGE + "Description skipped..." + ANSI_RESET);
        } else {
            description = Utils.rePromptWhileInvalid("Enter the Category description: ", Description::new);
        }

       // controller.addFigureCategory(name, description);

            return false;
        }

        @Override
        public String headline () {
            return "Add Figure Category";
        }

    }
