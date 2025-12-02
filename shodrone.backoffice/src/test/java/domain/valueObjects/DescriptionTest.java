package domain.valueObjects;

import static org.junit.jupiter.api.Assertions.*;

import eapli.framework.functional.Either;
import org.junit.jupiter.api.Test;

public class DescriptionTest {

    @Test
    public void validDescriptionShouldCreateInstance() {
        String input = "This is a valid description";
        Either<String, Description> result = Description.tryValueOf(input);
        Description description = result.right().orElse(null);
        assertNotNull(description);
        assertEquals(input, description.toString());
    }

    @Test
    public void nullDescriptionShouldReturnLeft() {
        Either<String, Description> result = Description.tryValueOf(null);
        assertTrue(result.left().orElse("").contains("cannot be null"));
    }

    @Test
    public void emptyDescriptionShouldReturnLeft() {
        Either<String, Description> result = Description.tryValueOf("");
        assertTrue(result.left().orElse("").contains("cannot be empty"));
    }

    @Test
    public void whitespaceDescriptionShouldReturnLeft() {
        Either<String, Description> result = Description.tryValueOf("    ");
        assertTrue(result.left().orElse("").contains("cannot be empty"));
    }

    @Test
    public void tooShortDescriptionShouldReturnLeft() {
        Either<String, Description> result = Description.tryValueOf("abcd");
        assertTrue(result.left().orElse("").contains("at least"));
    }

    @Test
    public void tooLongDescriptionShouldReturnLeft() {
        String longInput = "a".repeat(301);
        Either<String, Description> result = Description.tryValueOf(longInput);
        assertTrue(result.left().orElse("").contains("at most"));
    }

    @Test
    public void validDescriptionAtMaxLength() {
        String input = "a".repeat(300);
        Either<String, Description> result = Description.tryValueOf(input);
        assertNotNull(result.right().orElse(null));
    }

    @Test
    public void validDescriptionAtMinLength() {
        String input = "abcde";
        Either<String, Description> result = Description.tryValueOf(input);
        assertNotNull(result.right().orElse(null));
    }

    @Test
    public void equalsAndHashCodeShouldWork() {
        Description d1 = new Description("Valid description");
        Description d2 = new Description("Valid description");
        assertEquals(d1, d2);
        assertEquals(d1.hashCode(), d2.hashCode());
    }

    @Test
    public void toStringReturnsValue() {
        String input = "Some description";
        Description d = new Description(input);
        assertEquals(input, d.toString());
    }

    @Test
    public void lengthReturnsCorrectValue() {
        Description d = new Description("12345");
        assertEquals(5, d.length());
    }

}



