package authz;

import jakarta.persistence.Embeddable;
import at.favre.lib.crypto.bcrypt.BCrypt;
import utils.Utils;

import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public class Password {

    private String password;

    public Password() {
    }

    public Password(String password) {
        if (!validate(password)) {
            throw new IllegalArgumentException("Invalid Password. It must contain at least 1 uppercase letter, 3 digits, and 1 special character.");
        }
        this.password = createHash(password);
    }

    public static boolean validate(String password) {
        if (password == null || password.isBlank()) return false;

        boolean hasUppercase = Pattern.compile("[A-Z]").matcher(password).find();
        boolean hasThreeDigits = Pattern.compile("(.*\\d.*){3,}").matcher(password).find();
        boolean hasSpecialChar = Pattern.compile("[^a-zA-Z0-9]").matcher(password).find();

        return hasUppercase && hasThreeDigits && hasSpecialChar;
    }

    private String createHash(String password) {
        return BCrypt.withDefaults().hashToString(4, password.toCharArray());
    }

    public boolean checkPassword(String pwd) {
        if (pwd == null || pwd.isBlank()) {
            return false;
        }
        BCrypt.Result result = BCrypt.verifyer().verify(pwd.toCharArray(), this.password.toCharArray());
        return result.verified;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password1 = (Password) o;
        return Objects.equals(password, password1.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }

    @Override
    public String toString() {
        return this.password;
    }

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