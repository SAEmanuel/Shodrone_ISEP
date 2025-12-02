package domain.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Represents an email address within the Shodrone system.
 * Enforces domain-specific rules such as format validation and domain constraints.
 * Specifically, emails must end with "@shodrone.app" and follow a standard email format.
 * This class is used as a value object and is embeddable in JPA entities.
 */
@Embeddable
public class Email implements Serializable {

    /** Email address stored as a string. Must follow specific validation rules. */
    private String email;

    /**
     * Default constructor required by JPA.
     * Should not be used directly in business logic.
     */
    public Email() {}

    /**
     * Constructs a validated Email instance.
     *
     * @param email The email address to store.
     * @throws IllegalArgumentException if the email is null, blank, incorrectly formatted,
     *                                  or does not end with "@shodrone.app".
     */
    public Email(String email) {
        if (!validate(email)) {
            throw new IllegalArgumentException("Invalid Email Address. It must be a valid address ending with @shodrone.app.");
        }
        this.email = email;
    }

    /**
     * Validates the email address by checking format and domain.
     *
     * @param email The email string to validate.
     * @return True if valid, false otherwise.
     */
    private boolean validate(String email) {
        return email != null && !email.isBlank() && checkFormat(email) && checkDomain(email);
    }

    /**
     * Checks whether the email has a valid format using a regular expression.
     *
     * @param email The email string to check.
     * @return True if format is valid, false otherwise.
     */
    private boolean checkFormat(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }

    /**
     * Checks if the email ends with the required Shodrone domain.
     *
     * @param email The email string to check.
     * @return True if domain is valid, false otherwise.
     */
    private boolean checkDomain(String email) {
        return email.toLowerCase().endsWith("@shodrone.app");
    }

    /**
     * Retrieves the stored email string.
     *
     * @return The email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Compares this Email object with another for equality based on the stored email string.
     *
     * @param o The object to compare.
     * @return True if the emails are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email1 = (Email) o;
        return Objects.equals(email, email1.email);
    }

    /**
     * Generates a hash code based on the stored email string.
     *
     * @return Hash code of the email.
     */
    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    /**
     * Returns a string representation of the email address.
     *
     * @return The email string.
     */
    @Override
    public String toString() {
        return email;
    }
}
