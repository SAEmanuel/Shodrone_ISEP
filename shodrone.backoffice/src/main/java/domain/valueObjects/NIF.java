package domain.valueObjects;

import eapli.framework.domain.model.ValueObject;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class NIF implements ValueObject {

    private String nif;

    protected NIF() {
    }

    public NIF(String number) {
        if (!isValidNIF(number)) {
            throw new IllegalArgumentException("Invalid Portuguese NIF: " + number);
        }
        this.nif = number;
    }

    private boolean isValidNIF(String nif) {
        if (nif == null || !nif.matches("\\d{9}")) return false;

        int firstDigit = Character.getNumericValue(nif.charAt(0));
        if (!(firstDigit == 1 || firstDigit == 2 || firstDigit == 3 || firstDigit == 5 || firstDigit == 6 || firstDigit == 8 || firstDigit == 9)) {
            return false;
        }

        int sum = 0;
        for (int i = 0; i < 8; i++) {
            sum += Character.getNumericValue(nif.charAt(i)) * (9 - i);
        }

        int checkDigit = 11 - (sum % 11);
        if (checkDigit >= 10) checkDigit = 0;

        return checkDigit == Character.getNumericValue(nif.charAt(8));
    }

    public String getNif() {
        return nif;
    }

    @Override
    public String toString() {
        return nif;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NIF)) return false;
        NIF nif = (NIF) o;
        return this.nif.equals(nif.nif);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nif);
    }
}

