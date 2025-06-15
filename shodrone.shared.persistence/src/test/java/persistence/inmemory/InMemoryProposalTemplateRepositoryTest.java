package persistence.inmemory;

import domain.entity.ProposalTemplate;
import domain.valueObjects.Description;
import domain.valueObjects.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryProposalTemplateRepositoryTest {

    private InMemoryProposalTemplateRepository repo;

    @BeforeEach
    void setUp() {
        repo = new InMemoryProposalTemplateRepository();
    }

    @Test
    void testSaveSuccess() {
        ProposalTemplate template = new ProposalTemplate(
                new Name("Test Template"),
                new Description("A test template"),
                Arrays.asList("line1", "line2")
        );
        Optional<ProposalTemplate> saved = repo.save(template);
        assertTrue(saved.isPresent());
    }

    @Test
    void testSaveDuplicateReturnsEmpty() {
        ProposalTemplate template = new ProposalTemplate(
                new Name("Duplicate"),
                new Description("First"),
                Arrays.asList("lineA")
        );
        repo.save(template);
        ProposalTemplate duplicate = new ProposalTemplate(
                new Name("Duplicate"),
                new Description("Second"),
                Arrays.asList("lineB")
        );
        Optional<ProposalTemplate> result = repo.save(duplicate);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByNameSuccess() {
        ProposalTemplate template = new ProposalTemplate(
                new Name("FindMe"),
                new Description("descree"),
                Arrays.asList("L1")
        );
        repo.save(template);
        Optional<ProposalTemplate> found = repo.findByName("findme");
        assertTrue(found.isPresent());
        assertEquals("FindMe", found.get().name().toString());
    }

    @Test
    void testFindAllSortedAlphabetically() {
        repo.save(new ProposalTemplate(new Name("Zeta"), new Description("descdw"), List.of("z")));
        repo.save(new ProposalTemplate(new Name("Alpha"), new Description("descdw"), List.of("a")));
        repo.save(new ProposalTemplate(new Name("Beta"), new Description("descdw"), List.of("b")));

        List<ProposalTemplate> all = repo.findAll();
        assertEquals(3, all.size());
        assertEquals("Alpha", all.get(0).name().toString());
        assertEquals("Beta", all.get(1).name().toString());
        assertEquals("Zeta", all.get(2).name().toString());
    }

    @Test
    void testFindByNameNotFound() {
        Optional<ProposalTemplate> result = repo.findByName("nonexistent");
        assertTrue(result.isEmpty());
    }
}