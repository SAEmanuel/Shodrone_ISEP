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


    // ---- Save Tests ----
    @Test
    void testSaveFigureWithValidData() {
        Figure figure = new Figure(new Name("Test Figure"), new Description("A test figure"), 1L, mockCategory, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null, mockCostumer);
        figure.setFigureId(10L);

        Optional<Figure> savedFigure = figureRepository.save(figure);
        assertTrue(savedFigure.isPresent());

        assertEquals(10L, savedFigure.get().identity());
        assertEquals("Test Figure", savedFigure.get().name());
    }



    @Test
    void testSaveFigureWithNull() {
        Optional<Figure> savedFigure = figureRepository.save(null);
        assertFalse(savedFigure.isPresent());
    }

    @Test
    void testSaveFigureWithNullCategory() {
        Figure figure = new Figure(new Name("Test Figure"), new Description("A test figure"), 1L, null, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null, mockCostumer);
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



    // ---- Find All Tests ----

    @Test
    void testFindAll() {
        Figure figure1 = new Figure(new Name("Test Figure 1"), new Description("Description 1"), 1L, mockCategory, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null, mockCostumer);
        Figure figure2 = new Figure(new Name("Test Figure 2"), new Description("Description 2"), 2L, mockCategory, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null, mockCostumer);

        figureRepository.save(figure1);
        figureRepository.save(figure2);

        List<Figure> figures = figureRepository.findAll();
        assertEquals(2, figures.size());
    }

    @Test
    void testFindAllActive() {
        Figure activeFigure = new Figure(new Name("Active Figure"), new Description("Active status"), 1L, mockCategory, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null, mockCostumer);
        Figure inactiveFigure = new Figure(new Name("Inactive Figure"), new Description("Inactive status"), 2L, mockCategory, FigureAvailability.PUBLIC, FigureStatus.INACTIVE, null, mockCostumer);

        figureRepository.save(activeFigure);
        figureRepository.save(inactiveFigure);

        List<Figure> activeFigures = figureRepository.findAllActive();
        assertEquals(1, activeFigures.size());
        assertEquals("Active Figure", activeFigures.get(0).name());
    }

    // ---- Find By Costumer Tests ----

    @Test
    void testFindByCostumer() {
        Costumer costumer = mock(Costumer.class);
        Figure figure1 = new Figure(new Name("Test Figure 1"), new Description("Description 1"), 1L, mockCategory, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null,costumer);
        Figure figure2 = new Figure(new Name("Test Figure 2"), new Description("Description 2"), 2L, mockCategory, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null, costumer);

        figureRepository.save(figure1);
        figureRepository.save(figure2);

        List<Figure> figures = figureRepository.findByCostumer(costumer);
        assertEquals(2, figures.size());
    }

    // ---- Find Figures By Various Attributes Tests ----

    @Test
    void testFindFiguresByCategory() {
        Figure figure1 = new Figure(new Name("Test Figure 1"), new Description("Description 1"), 1L, mockCategory, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null, mockCostumer);
        figureRepository.save(figure1);

        List<Figure> figures = figureRepository.findFigures(null, null, null, null, mockCategory, null, null, null, null).orElse(new ArrayList<>());
        assertEquals(1, figures.size());
        assertEquals("Test Figure 1", figures.get(0).name().toString());
    }

    @Test
    void testFindFiguresByStatus() {
        Figure figure1 = new Figure(new Name("Active Figure"), new Description("Description 1"), 1L, mockCategory, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null, mockCostumer);
        Figure figure2 = new Figure(new Name("Inactive Figure"), new Description("Description 2"), 2L, mockCategory, FigureAvailability.PUBLIC, FigureStatus.INACTIVE, null, mockCostumer);

        figureRepository.save(figure1);
        figureRepository.save(figure2);

        List<Figure> activeFigures = figureRepository.findFigures(null, null, null, null, null, null, FigureStatus.ACTIVE, null, null).orElse(new ArrayList<>());
        assertEquals(1, activeFigures.size());
        assertEquals("Active Figure", activeFigures.get(0).name().toString());
    }

    // ---- Edit Figure Test ----

    @Test
    void testEditChosenFigure() {
        Figure figure = new Figure(new Name("Test Figure"), new Description("Description"), 1L, mockCategory, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null, mockCostumer);
        figureRepository.save(figure);

        figure.decommissionFigureStatus();
        Optional<Figure> editedFigure = figureRepository.editChosenFigure(figure);

        assertTrue(editedFigure.isPresent());
        assertEquals(FigureStatus.INACTIVE, editedFigure.get().status());
    }

    @Test
    void testEditNonExistentFigure() {
        Figure figure = new Figure(new Name("Non Existent"), new Description("Description"), 999L, mockCategory, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null, mockCostumer);

        Optional<Figure> editedFigure = figureRepository.editChosenFigure(figure);
        assertFalse(editedFigure.isPresent());
    }

    // ---- Find All Public Figures Tests ----

    @Test
    void testFindAllPublicFigures() {
        Figure publicFigure = new Figure(new Name("Public Figure"), new Description("Description"), 1L, mockCategory, FigureAvailability.PUBLIC, FigureStatus.ACTIVE, null, mockCostumer);
        Figure privateFigure = new Figure(new Name("Private Figure"), new Description("Description"), 2L, mockCategory, FigureAvailability.EXCLUSIVE, FigureStatus.ACTIVE, null, mockCostumer);

        figureRepository.save(publicFigure);
        figureRepository.save(privateFigure);

        List<Figure> publicFigures = figureRepository.findAllPublicFigures();
        assertEquals(1, publicFigures.size());
        assertEquals("Public Figure", publicFigures.get(0).name());
    }
}
