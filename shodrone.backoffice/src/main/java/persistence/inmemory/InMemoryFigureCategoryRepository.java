package persistence.inmemory;

import authz.Email;
import domain.entity.FigureCategory;
import domain.history.AuditLoggerService;
import domain.valueObjects.Description;
import domain.valueObjects.Name;
import persistence.interfaces.FigureCategoryRepository;
import utils.AuthUtils;

import java.util.*;

public class InMemoryFigureCategoryRepository implements FigureCategoryRepository {
    private final Map<String, FigureCategory> store = new HashMap<>();
    private final AuditLoggerService auditLoggerService;
    private static final Set<String> AUDIT_FIELDS = Set.of("name", "description");

    public InMemoryFigureCategoryRepository(AuditLoggerService auditLoggerService) {
        super();
        this.auditLoggerService = auditLoggerService;
    }


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

    @Override
    public Optional<FigureCategory> findByName(String name) {
        return Optional.ofNullable(store.get(name.toLowerCase()));
    }

    @Override
    public List<FigureCategory> findAll() {
        List<FigureCategory> allCategories = new ArrayList<>(store.values());
        allCategories.sort(Comparator.comparing(FigureCategory::identity));
        return allCategories;
    }

    @Override
    public List<FigureCategory> findActiveCategories() {
        List<FigureCategory> activeCategories = new ArrayList<>();
        for (FigureCategory category : store.values()) {
            if (category.isActive())
                activeCategories.add(category);
        }

        activeCategories.sort(Comparator.comparing(FigureCategory::identity));
        return activeCategories;
    }

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

        auditLoggerService.logChanges(oldState, existing, existing.identity(), AuthUtils.getCurrentUserEmail(), AUDIT_FIELDS);

        return Optional.of(existing);
    }

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

        auditLoggerService.logChanges(oldState, existing, existing.identity(), AuthUtils.getCurrentUserEmail(), AUDIT_FIELDS);

        return Optional.of(existing);
    }
}
