package persistence.inmemory;

import authz.Email;
import domain.entity.FigureCategory;
import domain.history.AuditLoggerService;
import domain.valueObjects.Description;
import domain.valueObjects.Name;
import persistence.interfaces.FigureCategoryRepository;
import utils.AuthUtils;

import java.util.*;

/**
 * In-memory implementation of the FigureCategoryRepository.
 * Supports creation, editing, status change, and auditing of figure categories.
 */
public class InMemoryFigureCategoryRepository implements FigureCategoryRepository {
    private final Map<String, FigureCategory> store = new HashMap<>();
    private final AuditLoggerService auditLoggerService;
    private static final Set<String> AUDIT_FIELDS_CHANGE = Set.of("name", "description", "available");
    private static final Set<String> AUDIT_FIELDS_CREATION = Set.of("name", "description");

    /**
     * Constructs the repository with the given audit logger service.
     *
     * @param auditLoggerService the service used for auditing changes
     */
    public InMemoryFigureCategoryRepository(AuditLoggerService auditLoggerService) {
        super();
        this.auditLoggerService = auditLoggerService;
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
            auditLoggerService.logCreation(category, category.identity(), AuthUtils.getCurrentUserEmail(), AUDIT_FIELDS_CREATION);
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

    /**
     * Edits the selected figure category, updating its name and/or description.
     * Audits the changes.
     *
     * @param category      the category to edit
     * @param newName       the new name, or null to keep current
     * @param newDescription the new description, or null to keep current
     * @return an Optional containing the updated category, or empty if not found
     */
    @Override
    public Optional<FigureCategory> editChosenCategory(FigureCategory category, Name newName, Description newDescription) {
        Optional<FigureCategory> categoryOptional = findByName(category.identity());

        if (categoryOptional.isEmpty()) {
            return Optional.empty();
        }

        FigureCategory existing = categoryOptional.get();

        FigureCategory oldState = new FigureCategory(existing.name(), existing.description(), existing.createdBy());
        oldState.setUpdatedBy(existing.updatedBy());

        existing.updateTime();
        existing.setUpdatedBy(new Email(AuthUtils.getCurrentUserEmail()));

        if (newName != null) {
            existing.changeCategoryNameTo(newName);
        }
        if (newDescription != null) {
            existing.changeDescriptionTo(newDescription);
        }

        auditLoggerService.logChanges(oldState, existing, existing.identity(), AuthUtils.getCurrentUserEmail(), AUDIT_FIELDS_CHANGE);

        return Optional.of(existing);
    }

    /**
     * Changes the status (available/unavailable) of the selected figure category.
     * Audits the change.
     *
     * @param category the category to change status
     * @return an Optional containing the updated category, or empty if not found
     */
    @Override
    public Optional<FigureCategory> changeStatus(FigureCategory category) {
        Optional<FigureCategory> categoryOptional = findByName(category.identity());

        if (categoryOptional.isEmpty()) {
            return Optional.empty();
        }

        FigureCategory existing = categoryOptional.get();

        FigureCategory oldState = new FigureCategory(existing.name(), existing.description(), existing.createdBy());
        oldState.setUpdatedBy(existing.updatedBy());

        existing.updateTime();
        existing.setUpdatedBy(new Email(AuthUtils.getCurrentUserEmail()));
        existing.toggleState();

        auditLoggerService.logChanges(oldState, existing, existing.identity(), AuthUtils.getCurrentUserEmail(), AUDIT_FIELDS_CHANGE);

        return Optional.of(existing);
    }
}
