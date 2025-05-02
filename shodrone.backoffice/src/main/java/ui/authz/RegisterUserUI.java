package ui.authz;

import authz.Email;

import authz.UserRole;
import controller.AuthenticationController;
import controller.RegisterUserController;
import utils.Utils;
import domain.valueObjects.Name;

public class RegisterUserUI implements Runnable {

    private final RegisterUserController controller = new RegisterUserController();

    private final String role;

    public RegisterUserUI(String role) {
        this.role = role;
    }

    @Override
    public void run() {

        Utils.printCenteredTitle("Show Designer Registration");

        Name name = Utils.rePromptWhileInvalid("Choose your user name: ", Name::new);
        Email email = Utils.rePromptWhileInvalid("Choose your email: ", Email::new);
        String password = Utils.readLineFromConsole("Choose a password: ");

        boolean success = controller.registerUser(name.name(), email.getEmail(), password, role);

        if (success) {
            Utils.printSuccessMessage("User registered successfully!");
        } else {
            Utils.printFailMessage("Error registering user");
        }


    }
}
