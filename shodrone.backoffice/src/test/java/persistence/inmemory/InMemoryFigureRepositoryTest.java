package persistence.inmemory;

import domain.entity.*;
import domain.valueObjects.*;
import history.AuditLoggerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.AuditLogRepository;
import persistence.FigureCategoryRepository;
import persistence.RepositoryProvider;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryFigureRepositoryTest {

    private InMemoryFigureRepository figureRepository;
    private FigureCategoryRepository figureCategoryRepository;
    private FigureCategory defaultCategory;
    private Costumer defaultCostumer;

    @BeforeEach
    void setUp() {
        // Repositório de logs em memória
        AuditLogRepository auditLogRepo = new InMemoryAuditLogRepository();
        AuditLoggerService auditLoggerService = new AuditLoggerService(auditLogRepo);
        figureRepository = new InMemoryFigureRepository(auditLoggerService);

        figureCategoryRepository = RepositoryProvider.figureCategoryRepository();

        defaultCategory = new FigureCategory(
                new Name("Default Category"),
                new Description("Category for tests"),
                new Email("creator@shodrone.app")
        );

        defaultCostumer = new Costumer(
                Name.valueOf("Test Costumer"),
                eapli.framework.general.domain.model.EmailAddress.valueOf("test@customer.com"),
                new PhoneNumber("912345678"),
                new NIF("123456789"),
                new Address("Main Street", "City", "1111-111", "Country")
        );
    }

    @Test
    void testSaveFigureWithValidData() {
        Figure figure = new Figure(new Name("Test Figure"), new Description("A test figure"),
                defaultCategory, FigureAvailability.PUBLIC, FigureStatus.ACTIVE,
                new HashMap<>(), defaultCostumer);
        figure.setFigureId(10L);

        Optional<Figure> savedFigure = figureRepository.save(figure);

        assertTrue(savedFigure.isPresent());
        assertEquals(10L, savedFigure.get().identity());
        assertEquals(new Name("Test Figure"), savedFigure.get().name());
    }

    @Test
    void testSaveFigureWithNull() {
        Optional<Figure> savedFigure = figureRepository.save(null);
        assertFalse(savedFigure.isPresent());
    }

    @Test
    void testSaveFigureWithNullCategory() {
        Figure figure = new Figure(new Name("Test Figure"), new Description("A test figure"),
                null, FigureAvailability.PUBLIC, FigureStatus.ACTIVE,
                new HashMap<>(), defaultCostumer);

        Optional<Figure> savedFigure = figureRepository.save(figure);
        assertFalse(savedFigure.isPresent());
    }

    @Test
    void testFindAll() {
        figureRepository.save(new Figure(new Name("Figure One"), new Description("Desc 1"), defaultCategory, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, new HashMap<>(), defaultCostumer));
        figureRepository.save(new Figure(new Name("Figure Two"), new Description("Desc 2"), defaultCategory, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, new HashMap<>(), defaultCostumer));

        List<Figure> figures = figureRepository.findAll();
        assertEquals(2, figures.size());
    }

    @Test
    void testFindAllActive() {
        figureRepository.save(new Figure(new Name("Active"), new Description("Active"), defaultCategory, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, new HashMap<>(), defaultCostumer));
        figureRepository.save(new Figure(new Name("Inactive"), new Description("Inactive"), defaultCategory, FigureAvailability.PUBLIC, FigureStatus.INACTIVE, new HashMap<>(), defaultCostumer));

        List<Figure> activeFigures = figureRepository.findAllActive();
        assertEquals(1, activeFigures.size());
        assertEquals("Active", activeFigures.get(0).name().toString());
    }

    @Test
    void testFindByCostumer() {
        figureRepository.save(new Figure(new Name("Figure A"), new Description("Desc A"), defaultCategory, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, new HashMap<>(), defaultCostumer));
        figureRepository.save(new Figure(new Name("Figure B"), new Description("Desc B"), defaultCategory, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, new HashMap<>(), defaultCostumer));

        List<Figure> figures = figureRepository.findByCostumer(defaultCostumer);
        assertEquals(2, figures.size());
    }

    @Test
    void testFindFiguresByCategory() {
        Figure figure = new Figure(new Name("CatFigure"), new Description("Description"),
                defaultCategory, FigureAvailability.PUBLIC, FigureStatus.ACTIVE,
                new HashMap<>(), defaultCostumer);
        figureRepository.save(figure);

        List<Figure> result = figureRepository
                .findFigures(null, null, null, null, defaultCategory, null, null, null, null)
                .orElse(new ArrayList<>());

        assertEquals(1, result.size());
        assertEquals("CatFigure", result.get(0).name().toString());
    }

    @Test
    void testFindFiguresByStatus() {
        Figure active = new Figure(new Name("Active F"), new Description("Description"),
                defaultCategory, FigureAvailability.PUBLIC, FigureStatus.ACTIVE,
                new HashMap<>(), defaultCostumer);
        Figure inactive = new Figure(new Name("Inactive F"), new Description("Description"),
                defaultCategory, FigureAvailability.PUBLIC, FigureStatus.INACTIVE,
                new HashMap<>(), defaultCostumer);

        figureRepository.save(active);
        figureRepository.save(inactive);

        List<Figure> result = figureRepository
                .findFigures(null, null, null, null, null, null, FigureStatus.ACTIVE, null, null)
                .orElse(new ArrayList<>());

        assertEquals(1, result.size());
        assertEquals("Active F", result.get(0).name().toString());
    }

    @Test
    void testEditChosenFigure() {
        Figure figure = new Figure(new Name("Editable"), new Description("Edit me"),
                defaultCategory, FigureAvailability.PUBLIC, FigureStatus.ACTIVE,
                new HashMap<>(), defaultCostumer);
        figure.setFigureId(1L);
        figureRepository.save(figure);

        Optional<Figure> edited = figureRepository.editChosenFigure(figure);

        assertTrue(edited.isPresent());
        assertEquals(FigureStatus.INACTIVE, edited.get().status());
    }

    @Test
    void testEditNonExistentFigure() {
        Figure figure = new Figure(new Name("Ghost"), new Description("Ghost figure"),
                defaultCategory, FigureAvailability.PUBLIC, FigureStatus.ACTIVE,
                new HashMap<>(), defaultCostumer);
        figure.setFigureId(999L);

        Optional<Figure> edited = figureRepository.editChosenFigure(figure);
        assertFalse(edited.isPresent());
    }

    @Test
    void testFindAllPublicFigures() {
        Figure publicF = new Figure(new Name("PublicOne"), new Description("Visible figure"),
                defaultCategory, FigureAvailability.PUBLIC, FigureStatus.ACTIVE,
                new HashMap<>(), defaultCostumer);
        Figure privateF = new Figure(new Name("PrivateOne"), new Description("Hidden figure"),
                defaultCategory, FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE,
                new HashMap<>(), defaultCostumer);

        figureRepository.save(publicF);
        figureRepository.save(privateF);

        List<Figure> publicFigures = figureRepository.findAllPublicFigures();
        assertEquals(1, publicFigures.size());
        assertEquals("PublicOne", publicFigures.get(0).name().toString());
    }
}