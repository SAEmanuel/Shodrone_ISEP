package controller.category;

import domain.entity.FigureCategory;
import history.AuditLoggerService;
import domain.valueObjects.Description;
import domain.valueObjects.Name;
import persistence.RepositoryProvider;
import persistence.FigureCategoryRepository;
import utils.AuthUtils;

import java.util.Optional;
import java.util.Set;

/**
 * Controller responsible for editing an existing figure category.
 */
public class EditFigureCategoryController {

    private static final Set<String> AUDIT_FIELDS = Set.of("name", "description");

    private final FigureCategoryRepository repository;
    private final AuditLoggerService auditLoggerService;

    /**
     * Constructs the controller and obtains the figure category repository.
     */
    public EditFigureCategoryController() {
        repository = RepositoryProvider.figureCategoryRepository();
        this.auditLoggerService = RepositoryProvider.auditLoggerService();
    }

    /**
     * Edits the selected figure category, updating its name and/or description.
     *
     * @param category       the category to edit
     * @param newName        the new name, or null to keep current
     * @param newDescription the new description, or null to keep current
     * @return an Optional containing the updated FigureCategory, or empty if the operation failed
     */
    public Optional<FigureCategory> editChosenCategory(FigureCategory category, Name newName, Description newDescription) {
        FigureCategory oldState = category.copy();
        String oldValue = category.identity();
        if (newName != null)
            category.changeCategoryNameTo(newName);
        if (newDescription != null)
            category.changeDescriptionTo(newDescription);
        auditLoggerService.logChanges(oldState, category, category.identity(), AuthUtils.getCurrentUserEmail(), AUDIT_FIELDS);
        return repository.updateFigureCategory(category,oldValue);
    }
}
