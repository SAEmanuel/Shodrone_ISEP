package controller.category;

import domain.entity.FigureCategory;
import domain.history.AuditLoggerService;
import persistence.RepositoryProvider;
import persistence.interfaces.FigureCategoryRepository;
import utils.AuthUtils;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Controller responsible for changing the status (active/inactive) of a figure category.
 */
public class ChangeFigureCategoryStatusController {
    private final FigureCategoryRepository repository;
    private final AuditLoggerService auditLoggerService;
    private static final Set<String> AUDIT_FIELDS = Set.of("name", "description", "available");

    /**
     * Constructs the controller and obtains the figure category repository.
     */
    public ChangeFigureCategoryStatusController() {
        repository = RepositoryProvider.figureCategoryRepository();
        auditLoggerService = RepositoryProvider.auditLoggerService();
    }

    /**
     * Changes the status (active/inactive) of the selected figure category.
     *
     * @param selectedCategory the category to change status
     * @return an Optional containing the updated FigureCategory, or empty if the operation failed
     */
    public Optional<FigureCategory> changeStatus(FigureCategory selectedCategory) {
        FigureCategory oldState = selectedCategory.copy();
        selectedCategory.toggleState();
        auditLoggerService.logChanges(oldState, selectedCategory, selectedCategory.identity(), AuthUtils.getCurrentUserEmail(), AUDIT_FIELDS);
        return repository.updateFigureCategory(selectedCategory);
    }
}
