package persistence.jpa.JPAImpl;

import authz.Email;
import domain.entity.FigureCategory;
import domain.history.AuditLoggerService;
import domain.valueObjects.Description;
import domain.valueObjects.Name;
import persistence.interfaces.FigureCategoryRepository;
import persistence.jpa.JpaBaseRepository;
import utils.AuthUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * JPA implementation of the FigureCategoryRepository.
 * Supports creation, editing, status change, and auditing of figure categories.
 */
public class FigureCategoryJPAImpl extends JpaBaseRepository<FigureCategory, Long>
        implements FigureCategoryRepository {

    private final AuditLoggerService auditLoggerService;
    private static final Set<String> AUDIT_FIELDS_CHANGE = Set.of("name", "description", "available");
    private static final Set<String> AUDIT_FIELDS_CREATION = Set.of("name", "description");

    /**
     * Constructs the repository with the given audit logger service.
     *
     * @param auditLoggerService the service used for auditing changes
     */
    public FigureCategoryJPAImpl(AuditLoggerService auditLoggerService) {
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
        Optional<FigureCategory> checkExistence = findByName(category.identity());
        if (checkExistence.isEmpty()) {
            FigureCategory saved = this.add(category);
            auditLoggerService.logCreation(saved, saved.identity(), AuthUtils.getCurrentUserEmail(), AUDIT_FIELDS_CREATION);
            return Optional.of(saved);
        }
        return Optional.empty();
    }

    /**
     * Finds a figure category by its name (case-insensitive).
     *
     * @param name the name to search for
     * @return an Optional containing the found category, or empty if not found
     */
    @Override
    public Optional<FigureCategory> findByName(String name) {
        List<FigureCategory> results = entityManager()
                .createQuery("SELECT f FROM FigureCategory f WHERE LOWER(f.name.name) = :name", FigureCategory.class)
                .setParameter("name", name.toLowerCase())
                .getResultList();

        if (results.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(results.get(0));
        }
    }

    /**
     * Returns all figure categories, sorted by name.
     *
     * @return a list of all categories
     */
    @Override
    public List<FigureCategory> findAll() {
        return entityManager()
                .createQuery("SELECT f FROM FigureCategory f ORDER BY LOWER(f.name.name) ASC", FigureCategory.class)
                .getResultList();
    }

    /**
     * Returns all active (available) figure categories, sorted by name.
     *
     * @return a list of active categories
     */
    @Override
    public List<FigureCategory> findActiveCategories() {
        return entityManager()
                .createQuery("SELECT f FROM FigureCategory f WHERE f.available = true ORDER BY LOWER(f.name.name) ASC", FigureCategory.class)
                .getResultList();
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
        try {
            FigureCategory managed = entityManager().find(FigureCategory.class, category.id());

            if (managed == null) {
                return Optional.empty();
            }

            FigureCategory oldState = new FigureCategory(managed.name(), managed.description(), managed.createdBy());
            oldState.setUpdatedBy(managed.updatedBy());

            entityManager().getTransaction().begin();
            if (newName != null) {
                managed.changeCategoryNameTo(newName);
            }
            if (newDescription != null) {
                managed.changeDescriptionTo(newDescription);
            }
            managed.updateTime();
            managed.setUpdatedBy(new Email(AuthUtils.getCurrentUserEmail()));
            entityManager().getTransaction().commit();

            auditLoggerService.logChanges(oldState, managed, managed.identity(), AuthUtils.getCurrentUserEmail(), AUDIT_FIELDS_CHANGE);

            return Optional.of(managed);
        } catch (Exception e) {
            if (entityManager().getTransaction().isActive()) {
                entityManager().getTransaction().rollback();
            }
            e.printStackTrace();
            return Optional.empty();
        }
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
        try {
            FigureCategory managed = entityManager().find(FigureCategory.class, category.id());

            if (managed == null) {
                return Optional.empty();
            }

            FigureCategory oldState = new FigureCategory(managed.name(), managed.description(), managed.createdBy());
            oldState.setUpdatedBy(managed.updatedBy());

            entityManager().getTransaction().begin();

            managed.updateTime();
            managed.setUpdatedBy(new Email(AuthUtils.getCurrentUserEmail()));
            managed.toggleState();
            entityManager().getTransaction().commit();

            auditLoggerService.logChanges(oldState, managed, managed.identity(), AuthUtils.getCurrentUserEmail(), AUDIT_FIELDS_CHANGE);

            return Optional.of(managed);
        } catch (Exception e) {
            if (entityManager().getTransaction().isActive()) {
                entityManager().getTransaction().rollback();
            }
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
