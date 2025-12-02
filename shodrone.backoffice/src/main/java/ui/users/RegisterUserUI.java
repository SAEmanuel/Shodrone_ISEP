package ui.users;

import controller.user.RegisterUserController;
import utils.Utils;
import domain.valueObjects.Name;
import domain.entity.Email;
import domain.entity.Password;

/**
 * UI class responsible for handling user registration.
 * Prompts the user for a name, email, and password,
 * then delegates the registration to the {@link RegisterUserController}.
 */
public class RegisterUserUI implements Runnable {

    /** Controller used to perform user registration. */
    private final RegisterUserController controller = new RegisterUserController();

    /** Role to assign to the newly registered user. */
    private final String role;

    /**
     * Constructs the UI with a predefined role for the user being registered.
     *
     * @param role The role to assign to the user.
     */
    public RegisterUserUI(String role) {
        this.role = role;
    }

    /**
     * Runs the user registration workflow:
     * prompts for name, email, and password → validates input → registers user.
     */
    @Override
    public void run() {
        Utils.printCenteredTitle("Show Designer Registration");

        Utils.showNameRules();
        Name name = Utils.rePromptWhileInvalid("Choose your user name: ", Name::new);

        Utils.showEmailRules();
        Email email = Utils.rePromptWhileInvalid("Choose your email: ", Email::new);

        Utils.showPasswordRules();
        String password = Password.rePromptWhileInvalidPassword("Choose a password: ");

        boolean success = controller.registerUser(name.name(), email.getEmail(), password, role);

        if (success) {
            Utils.printSuccessMessage("User registered successfully!");
        } else {
            Utils.printFailMessage("Error registering user");
        }
    }
}
