package persistence.inmemory;

import domain.entity.Costumer;
import domain.entity.Figure;
import domain.entity.FigureCategory;
import history.AuditLoggerService;
import domain.valueObjects.*;
import persistence.RepositoryProvider;
import persistence.CostumerRepository;
import persistence.FigureCategoryRepository;
import persistence.FigureRepository;

import java.util.*;

public class InMemoryFigureRepository implements FigureRepository {
    private final Map<Long, Figure> store = new HashMap<>();
    private final AuditLoggerService auditLoggerService;
    private static long LAST_FIGURE_ID = 1L;

    public InMemoryFigureRepository(AuditLoggerService auditLoggerService) {
        this.auditLoggerService = auditLoggerService;
    }

    @Override
    public Optional<Figure> save(Figure figure) {
        if (figure == null) return Optional.empty();

        if (figure.identity() == null) {
            figure.setFigureId(LAST_FIGURE_ID++);
        }

        Optional<FigureCategory> category = Optional.ofNullable(figure.category());
        FigureCategoryRepository categoryRepo = RepositoryProvider.figureCategoryRepository();
        if (category.isPresent()) {
            Optional<FigureCategory> existing = categoryRepo.findByName(category.get().identity());
            figure.updateFigureCategory(existing.orElseGet(() -> categoryRepo.save(category.get()).get()));
        } else {
            return Optional.empty();
        }

        Optional<Costumer> customer = Optional.ofNullable(figure.customer());
        CostumerRepository customerRepo = RepositoryProvider.costumerRepository();
        if (customer.isPresent()) {
            Optional<Costumer> existing = customerRepo.findByNIF(customer.get().nif());
            figure.updateCustomer(existing.orElseGet(() -> customerRepo.saveInStore(customer.get(), customer.get().nif()).get()));
        }

        Optional<List<Figure>> duplicates = findFigures(
                null, figure.name(), null, null, figure.category(),
                null, null, null, figure.customer()
        );

        if (duplicates.isPresent()) {
            for (Figure existing : duplicates.get()) {
                if (existing.dslVersions().values().stream()
                        .anyMatch(existingDsl ->
                                figure.dslVersions().values().stream()
                                        .anyMatch(newDsl -> newDsl.equals(existingDsl))
                        )) {
                    return Optional.empty();
                }
            }
        }

        store.put(figure.identity(), figure);
        return Optional.of(figure);
    }

    @Override
    public Optional<List<Figure>> findFigures(Long figureId, Name name, Description description, Long version,
                                              FigureCategory category, FigureAvailability availability,
                                              FigureStatus status, DSL dsl, Costumer customer) {
        List<Figure> figures = new ArrayList<>(store.values());

        figures.removeIf(f ->
                (figureId != null && !f.identity().equals(figureId)) ||
                        (name != null && !f.name().equals(name)) ||
                        (description != null && !f.description().equals(description)) ||
                        (category != null && !f.category().equals(category)) ||
                        (availability != null && !f.availability().equals(availability)) ||
                        (status != null && !f.status().equals(status)) ||
                        (customer != null && (f.customer() == null || !f.customer().equals(customer))) ||
                        (dsl != null && !containsDsl(f, dsl))
        );

        return Optional.of(figures);
    }

    private boolean containsDsl(Figure figure, DSL dsl) {
        List<String> targetDsl = Arrays.asList(dsl.toString().split("\n"));
        return figure.dslVersions().values().stream()
                .anyMatch(versionDsl -> versionDsl.equals(targetDsl));
    }

    @Override
    public List<Figure> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<Figure> findAllActive() {
        return filterFigures(f -> f.status() == FigureStatus.ACTIVE);
    }

    @Override
    public List<Figure> findByCostumer(Costumer customer) {
        return filterFigures(f ->
                (f.customer() != null && f.customer().equals(customer)) ||
                        f.availability() == FigureAvailability.PUBLIC
        );
    }

    @Override
    public List<Figure> findAllPublicFigures() {
        return filterFigures(f ->
                f.availability() == FigureAvailability.PUBLIC &&
                        f.status() == FigureStatus.ACTIVE
        );
    }

    @Override
    public Optional<Figure> editChosenFigure(Figure figure) {
        if (figure == null || figure.identity() == null) return Optional.empty();

        Figure existing = store.get(figure.identity());
        if (existing != null) {
            existing.decommission();
            return Optional.of(existing);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Figure> findFigure(Long figureId) {
        return Optional.ofNullable(store.get(figureId));
    }

    private List<Figure> filterFigures(java.util.function.Predicate<Figure> predicate) {
        return store.values().stream()
                .filter(predicate)
                .toList();
    }
}
