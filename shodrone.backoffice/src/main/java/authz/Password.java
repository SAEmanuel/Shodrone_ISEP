package authz;

import jakarta.persistence.Embeddable;
import at.favre.lib.crypto.bcrypt.BCrypt;
import utils.Utils;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Represents a hashed password within the Shodrone system.
 * Enforces a security policy requiring complexity (uppercase, digits, special characters),
 * and uses BCrypt to securely hash passwords before storing them.
 * This class is embeddable and designed to be used as a value object in user-related entities.
 */
@Embeddable
public class Password {

    /** Hashed password stored securely. */
    private String password;

    /**
     * Default constructor required by JPA.
     * Should not be used directly in business logic.
     */
    public Password() {
    }

    /**
     * Constructs a Password object after validating and hashing the raw password.
     *
     * @param password Raw password string entered by the user.
     * @throws IllegalArgumentException if the password does not meet complexity requirements.
     */
    public Password(String password) {
        if (!validate(password)) {
            throw new IllegalArgumentException("Invalid Password. It must contain at least 1 uppercase letter, 3 digits, and 1 special character.");
        }
        this.password = createHash(password);
    }

    /**
     * Validates a raw password against security constraints:
     * - At least one uppercase letter
     * - At least three digits
     * - At least one special character
     *
     * @param password The password string to validate.
     * @return True if valid, false otherwise.
     */
    public static boolean validate(String password) {
        if (password == null || password.isBlank()) return false;

        boolean hasUppercase = Pattern.compile("[A-Z]").matcher(password).find();
        boolean hasThreeDigits = Pattern.compile("(.*\\d.*){3,}").matcher(password).find();
        boolean hasSpecialChar = Pattern.compile("[^a-zA-Z0-9]").matcher(password).find();

        return hasUppercase && hasThreeDigits && hasSpecialChar;
    }

    /**
     * Creates a BCrypt hash from the provided password.
     *
     * @param password The raw password string.
     * @return A securely hashed version of the password.
     */
    private String createHash(String password) {
        return BCrypt.withDefaults().hashToString(4, password.toCharArray());
    }

    /**
     * Verifies whether a given raw password matches the stored hashed password.
     *
     * @param pwd The raw password to verify.
     * @return True if the password matches, false otherwise.
     */
    public boolean checkPassword(String pwd) {
        if (pwd == null || pwd.isBlank()) {
            return false;
        }
        BCrypt.Result result = BCrypt.verifyer().verify(pwd.toCharArray(), this.password.toCharArray());
        return result.verified;
    }

    /**
     * Retrieves the hashed password string.
     *
     * @return The hashed password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Checks for equality based on the hashed password string.
     *
     * @param o Object to compare.
     * @return True if the hashed passwords are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password1 = (Password) o;
        return Objects.equals(password, password1.password);
    }

    /**
     * Generates a hash code based on the stored password.
     *
     * @return Hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(password);
    }

    /**
     * Returns the hashed password as a string.
     *
     * @return The password hash.
     */
    @Override
    public String toString() {
        return this.password;
    }

    /**
     * Prompts the user repeatedly until a valid password is entered.
     *
     * @param promptMessage The prompt shown to the user.
     * @return A valid password string.
     */
    public static String rePromptWhileInvalidPassword(String promptMessage) {
        String input;
        do {
            input = Utils.readLineFromConsole(promptMessage);
            if (validate(input)) {
                return input;
            }
        } while (true);
    }
}
