package ui.authz;

import authz.Email;
import authz.Password;

import controller.AuthenticationController;
import controller.RegisterUserController;
import utils.Utils;
import domain.valueObjects.Name;

public class RegisterShowDesignerUI implements Runnable {

    private final RegisterUserController controller = new RegisterUserController();

    @Override
    public void run() {

        Utils.printCenteredTitle("Show Designer Registration");

        Name name = Utils.rePromptWhileInvalid("Choose your user name: ", Name::new);
        Email email = Utils.rePromptWhileInvalid("Choose your email: ", Email::new);
        String password = Utils.readLineFromConsole("Choose a password: ");

        boolean success = controller.registerUser(name.name(), email.getEmail(), password, AuthenticationController.ROLE_SHOW_DESIGNER);

        if (success) {
            Utils.printSuccessMessage("User registered successfully!");
        } else {
            Utils.printFailMessage("Error registering user");
        }


    }
}
