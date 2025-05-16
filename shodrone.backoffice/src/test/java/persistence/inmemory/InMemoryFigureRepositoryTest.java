package persistence.inmemory;

import authz.Email;
import domain.entity.Costumer;
import domain.entity.Figure;
import domain.entity.FigureCategory;
import domain.history.AuditLoggerService;
import domain.valueObjects.Description;
import domain.valueObjects.FigureAvailability;
import domain.valueObjects.FigureStatus;
import domain.valueObjects.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.interfaces.FigureCategoryRepository;
import persistence.RepositoryProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for InMemoryFigureRepository, verifying CRUD operations and filtered queries.
 */
class InMemoryFigureRepositoryTest {

    private InMemoryFigureRepository figureRepository;
    private AuditLoggerService mockAuditLoggerService;
    private FigureCategory mockCategory;
    private Costumer mockCostumer;
    private FigureCategoryRepository mockFigureCategoryRepository;

    private final Email creator = new Email("test@shodrone.app");

    @BeforeEach
    void setUp() {
        mockFigureCategoryRepository = mock(FigureCategoryRepository.class);
        RepositoryProvider.injectFigureCategoryRepository(mockFigureCategoryRepository);

        mockAuditLoggerService = mock(AuditLoggerService.class);
        figureRepository = new InMemoryFigureRepository(mockAuditLoggerService);

        mockCategory = mock(FigureCategory.class);
        mockCostumer = mock(Costumer.class);
    }

    // ---------------------------
    // Save Tests
    // ---------------------------

    @Test
    void testSaveFigureWithValidData() {
        Figure figure = new Figure(new Name("Test Figure"), new Description("A test figure"), 1L,
                mockCategory, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null, mockCostumer);
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
        Figure figure = new Figure(new Name("Test Figure"), new Description("A test figure"), 1L,
                null, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null, mockCostumer);

        Optional<Figure> savedFigure = figureRepository.save(figure);

        assertTrue(savedFigure.isPresent());
    }

    @Test
    void testSaveFigureWithMockedCategory() {
        FigureCategory sharedCategory = new FigureCategory(
                new Name("Fantasy"),
                new Description("Fantasy category"),
                creator
        );

        when(mockFigureCategoryRepository.findByName("Fantasy"))
                .thenReturn(Optional.of(sharedCategory));

        Figure figure = new Figure(
                new Name("Test Figure"),
                new Description("A test figure"),
                1L,
                sharedCategory,
                FigureAvailability.PUBLIC,
                FigureStatus.ACTIVE,
                null,
                mockCostumer
        );

        Optional<Figure> savedFigure = figureRepository.save(figure);

        assertTrue(savedFigure.isPresent());
        assertEquals(sharedCategory, savedFigure.get().category());
    }

    // ---------------------------
    // Find All Tests
    // ---------------------------

    @Test
    void testFindAll() {
        Figure figure1 = new Figure(new Name("Figure One"), new Description("Description 1"), 1L,
                mockCategory, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null, mockCostumer);
        Figure figure2 = new Figure(new Name("Figure Two"), new Description("Description 2"), 2L,
                mockCategory, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null, mockCostumer);

        figureRepository.save(figure1);
        figureRepository.save(figure2);

        List<Figure> figures = figureRepository.findAll();
        assertEquals(2, figures.size());
    }

    @Test
    void testFindAllActive() {
        Figure activeFigure = new Figure(new Name("Active Figure"), new Description("Active"), 1L,
                mockCategory, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null, mockCostumer);
        Figure inactiveFigure = new Figure(new Name("Inactive Figure"), new Description("Inactive"), 2L,
                mockCategory, FigureAvailability.PUBLIC, FigureStatus.INACTIVE, null, mockCostumer);

        figureRepository.save(activeFigure);
        figureRepository.save(inactiveFigure);

        List<Figure> activeFigures = figureRepository.findAllActive();
        assertEquals(1, activeFigures.size());
        assertEquals(new Name("Active Figure"), activeFigures.get(0).name());
    }

    // ---------------------------
    // Find by Costumer
    // ---------------------------

    @Test
    void testFindByCostumer() {
        Costumer costumer = mock(Costumer.class);
        Figure figure1 = new Figure(new Name("Figure A"), new Description("Description A"), 1L,
                mockCategory, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null, costumer);
        Figure figure2 = new Figure(new Name("Figure B"), new Description("Description B"), 2L,
                mockCategory, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null, costumer);

        figureRepository.save(figure1);
        figureRepository.save(figure2);

        List<Figure> figures = figureRepository.findByCostumer(costumer);
        assertEquals(2, figures.size());
    }

    // ---------------------------
    // Filtered Search Tests
    // ---------------------------

    @Test
    void testFindFiguresByCategory() {
        Figure figure = new Figure(new Name("CatFigure"), new Description("Description"), 1L,
                mockCategory, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null, mockCostumer);
        figureRepository.save(figure);

        List<Figure> result = figureRepository
                .findFigures(null, null, null, null, mockCategory, null, null, null, null)
                .orElse(new ArrayList<>());

        assertEquals(1, result.size());
        assertEquals("CatFigure", result.get(0).name().toString());
    }

    @Test
    void testFindFiguresByStatus() {
        Figure active = new Figure(new Name("Active F"), new Description("Description"), 1L,
                mockCategory, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null, mockCostumer);
        Figure inactive = new Figure(new Name("Inactive F"), new Description("Description"), 2L,
                mockCategory, FigureAvailability.PUBLIC, FigureStatus.INACTIVE, null, mockCostumer);

        figureRepository.save(active);
        figureRepository.save(inactive);

        List<Figure> result = figureRepository
                .findFigures(null, null, null, null, null, null, FigureStatus.ACTIVE, null, null)
                .orElse(new ArrayList<>());

        assertEquals(1, result.size());
        assertEquals("Active F", result.get(0).name().toString());
    }

    // ---------------------------
    // Edit Tests
    // ---------------------------

    @Test
    void testEditChosenFigure() {
        Figure figure = new Figure(new Name("Editable"), new Description("Edit me"), 1L,
                mockCategory, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null, mockCostumer);
        figureRepository.save(figure);

        figure.decommissionFigureStatus();
        Optional<Figure> edited = figureRepository.editChosenFigure(figure);

        assertTrue(edited.isPresent());
        assertEquals(FigureStatus.INACTIVE, edited.get().status());
    }

    @Test
    void testEditNonExistentFigure() {
        Figure figure = new Figure(new Name("Ghost"), new Description("Description"), 999L,
                mockCategory, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null, mockCostumer);

        Optional<Figure> edited = figureRepository.editChosenFigure(figure);
        assertFalse(edited.isPresent());
    }

    // ---------------------------
    // Public Figures
    // ---------------------------

    @Test
    void testFindAllPublicFigures() {
        Figure publicF = new Figure(new Name("PublicOne"), new Description("Description"), 1L,
                mockCategory, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null, mockCostumer);
        Figure privateF = new Figure(new Name("PrivateOne"), new Description("Description"), 2L,
                mockCategory, FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE, null, mockCostumer);

        figureRepository.save(publicF);
        figureRepository.save(privateF);

        List<Figure> publicFigures = figureRepository.findAllPublicFigures();
        assertEquals(1, publicFigures.size());
        assertEquals(new Name("PublicOne"), publicFigures.get(0).name());
    }
}
