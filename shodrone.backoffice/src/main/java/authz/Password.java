package authz;

import jakarta.persistence.Embeddable;
import at.favre.lib.crypto.bcrypt.BCrypt;
import java.util.Objects;

@Embeddable
public class Password {

    private String password;

    public Password() {}

    public Password(String password) {
        if (!validate(password)) {
            throw new IllegalArgumentException("Invalid Password.");
        }
        this.password = createHash(password);
    }

    private boolean validate(String password) {
        return password != null && !password.isBlank();
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

    public int hashCode() {
        int hash = 7;
        hash = 7 * hash + this.password.hashCode();
        return hash;
    }
}
