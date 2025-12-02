package domain.entity;

import domain.valueObjects.Description;
import domain.valueObjects.Name;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProposalTemplateTest {

    private final Name name = new Name("Test Proposal");
    private final Description description = new Description("Template description");
    private final List<String> scriptLines = Arrays.asList("line1", "line2", "line3");

    @Test
    void testCreation() {
        ProposalTemplate template = new ProposalTemplate(name, description, scriptLines);
        assertEquals(name, template.name());
        assertEquals(description, template.description());
        assertEquals(scriptLines, template.text());
        assertNotNull(template.toString());
    }

    @Test
    void testChangeName() {
        ProposalTemplate template = new ProposalTemplate(name, description, scriptLines);
        Name newName = new Name("Updated Name");
        template.changeName(newName);
        assertEquals(newName, template.name());
    }

    @Test
    void testChangeDescription() {
        ProposalTemplate template = new ProposalTemplate(name, description, scriptLines);
        Description newDesc = new Description("Updated description");
        template.changeDescription(newDesc);
        assertEquals(newDesc, template.description());
    }

    @Test
    void testChangeContent() {
        ProposalTemplate template = new ProposalTemplate(name, description, scriptLines);
        List<String> newLines = Arrays.asList("new1", "new2");
        template.changeContent(newLines);
        assertEquals(newLines, template.text());
    }

    @Test
    void testHasIdentityWithValidId() {
        ProposalTemplate template = new ProposalTemplate(name, description, scriptLines);
        Long id = 42L;
        // Simulate persisted ID
        try {
            var idField = ProposalTemplate.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(template, id);
        } catch (Exception e) {
            fail("Failed to set ID for test");
        }
        assertTrue(template.hasIdentity("42"));
    }

    @Test
    void testHasIdentityWithInvalidId() {
        ProposalTemplate template = new ProposalTemplate(name, description, scriptLines);
        assertFalse(template.hasIdentity("notANumber"));
    }

    @Test
    void testSameAsTrue() {
        ProposalTemplate t1 = new ProposalTemplate(name, description, scriptLines);
        ProposalTemplate t2 = new ProposalTemplate(name, description, scriptLines);
        assertTrue(t1.sameAs(t2));
    }

    @Test
    void testSameAsFalse() {
        ProposalTemplate t1 = new ProposalTemplate(name, description, scriptLines);
        ProposalTemplate t2 = new ProposalTemplate(new Name("Different"), description, scriptLines);
        assertFalse(t1.sameAs(t2));
    }

    @Test
    void testEqualsAndHashCode() {
        ProposalTemplate t1 = new ProposalTemplate(name, description, scriptLines);
        ProposalTemplate t2 = new ProposalTemplate(name, description, scriptLines);
        assertEquals(t1, t2);
        assertEquals(t1.hashCode(), t2.hashCode());
    }

    @Test
    void testCompareTo() {
        ProposalTemplate template = new ProposalTemplate(new Name("Alpha"), description, scriptLines);
        assertTrue(template.compareTo("beta") < 0);
        assertTrue(template.compareTo("alpha") == 0);
    }

    @Test
    void testToStringFormatting() {
        ProposalTemplate template = new ProposalTemplate(new Name("Alpha"), new Description("Not provided!"), scriptLines);
        String str = template.toString();
        assertTrue(str.contains("Alpha"));
        assertTrue(str.contains("Not provided"));
    }
}