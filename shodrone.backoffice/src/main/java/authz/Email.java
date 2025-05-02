package authz;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public class Email implements Serializable {

    private String email;

    public Email() {}

    public Email(String email) {
        if (!validate(email)) {
            throw new IllegalArgumentException("Invalid Email Address. It must be a valid address ending with @shodrone.app.");
        }
        this.email = email;
    }

    private boolean validate(String email) {
        return email != null && !email.isBlank() && checkFormat(email) && checkDomain(email);
    }

    private boolean checkFormat(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }

    private boolean checkDomain(String email) {
        return email.toLowerCase().endsWith("@shodrone.app");
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email1 = (Email) o;
        return Objects.equals(email, email1.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return email;
    }
}