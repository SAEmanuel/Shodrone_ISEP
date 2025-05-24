package controller.category;

import authz.Email;
import domain.entity.FigureCategory;
import domain.history.AuditLoggerService;
import persistence.RepositoryProvider;
import domain.valueObjects.Description;
import domain.valueObjects.Name;
import persistence.interfaces.FigureCategoryRepository;
import utils.AuthUtils;

import java.util.Optional;
import java.util.Set;

/**
 * Controller responsible for adding new figure categories.
 * Provides methods to create categories with or without a description.
 */
public class AddFigureCategoryController {
    private final FigureCategoryRepository repository;
    private final AuditLoggerService auditLoggerService;
    private static final Set<String> AUDIT_FIELDS = Set.of("name", "description");

    /**
     * Constructs the controller and obtains the figure category repository.
     */
    public AddFigureCategoryController() {
        repository = RepositoryProvider.figureCategoryRepository();
        auditLoggerService = RepositoryProvider.auditLoggerService();
    }

    /**
     * Adds a new figure category with a name and description.
     *
     * @param name        the category name
     * @param description the category description
     * @param createdBy   the user creating the category
     * @return an Optional containing the created FigureCategory, or empty if the name is not unique
     */
    public Optional<FigureCategory> addFigureCategoryWithNameAndDescription(Name name, Description description, Email createdBy) {
        FigureCategory category = new FigureCategory(name, description, createdBy);
        auditLoggerService.logCreation(category, category.identity(), AuthUtils.getCurrentUserEmail(), AUDIT_FIELDS);
        return repository.save(category);
    }

    /**
     * Adds a new figure category with only a name.
     *
     * @param name      the category name
     * @param createdBy the user creating the category
     * @return an Optional containing the created FigureCategory, or empty if the name is not unique
     */
    public Optional<FigureCategory> addFigureCategoryWithName(Name name, Email createdBy) {
        FigureCategory category = new FigureCategory(name, createdBy);
        auditLoggerService.logCreation(category, category.identity(), AuthUtils.getCurrentUserEmail(), AUDIT_FIELDS);
        return repository.save(category);
    }
}
