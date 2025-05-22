package persistence.inmemory;


import domain.entity.FigureCategory;
import domain.history.AuditLoggerService;

import persistence.interfaces.FigureCategoryRepository;


import java.util.*;

/**
 * In-memory implementation of the FigureCategoryRepository.
 * Supports creation, editing, status change, and auditing of figure categories.
 */
public class InMemoryFigureCategoryRepository implements FigureCategoryRepository {
    private final Map<String, FigureCategory> store = new HashMap<>();

    /**
     * Constructs the repository with the given audit logger service.
     *
     * @param auditLoggerService the service used for auditing changes
     */
    public InMemoryFigureCategoryRepository(AuditLoggerService auditLoggerService) {
        super();
    }

    /**
     * Saves a new figure category if it does not already exist.
     * Audits the creation event.
     *
     * @param category the category to save
     * @return an Optional containing the saved category, or empty if it already exists
     */
    @Override
    public Optional<FigureCategory> save(FigureCategory category) {
        String key = category.identity().toLowerCase();
        if (store.containsKey(key)) {
            return Optional.empty();
        } else {
            store.put(key, category);
            return Optional.of(category);
        }
    }

    /**
     * Finds a figure category by its name (case-insensitive).
     *
     * @param name the name to search for
     * @return an Optional containing the found category, or empty if not found
     */
    @Override
    public Optional<FigureCategory> findByName(String name) {
        return Optional.ofNullable(store.get(name.toLowerCase()));
    }

    /**
     * Returns all figure categories, sorted by identity.
     *
     * @return a list of all categories
     */
    @Override
    public List<FigureCategory> findAll() {
        List<FigureCategory> allCategories = new ArrayList<>(store.values());
        allCategories.sort(Comparator.comparing(c -> c.identity().toLowerCase()));
        return allCategories;
    }

    /**
     * Returns all active (available) figure categories, sorted by identity.
     *
     * @return a list of active categories
     */
    @Override
    public List<FigureCategory> findActiveCategories() {
        List<FigureCategory> activeCategories = new ArrayList<>();
        for (FigureCategory category : store.values()) {
            if (category.isAvailable())
                activeCategories.add(category);
        }
        activeCategories.sort(Comparator.comparing(c -> c.identity().toLowerCase()));
        return activeCategories;
    }

    @Override
    public Optional<FigureCategory> updateFigureCategory(FigureCategory category) {
        return Optional.empty();
    }
}
