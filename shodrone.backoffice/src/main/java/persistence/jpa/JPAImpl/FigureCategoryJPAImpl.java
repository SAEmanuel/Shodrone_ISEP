package persistence.jpa.JPAImpl;


import domain.entity.FigureCategory;
import domain.entity.ShowRequest;
import persistence.interfaces.FigureCategoryRepository;
import persistence.jpa.JpaBaseRepository;


import java.util.List;
import java.util.Optional;


/**
 * JPA implementation of the FigureCategoryRepository.
 * Supports creation, editing, status change, and auditing of figure categories.
 */
public class FigureCategoryJPAImpl extends JpaBaseRepository<FigureCategory, Long>
        implements FigureCategoryRepository {


    /**
     * Constructs the repository .
     */
    public FigureCategoryJPAImpl() {
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
        Optional<FigureCategory> checkExistence = findByName(category.identity());
        if (checkExistence.isEmpty()) {
            FigureCategory saved = this.add(category);
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

    @Override
    public Optional<FigureCategory> updateFigureCategory(FigureCategory category) {
        if (category == null || category.identity() == null) {
            return Optional.empty();
        }

        Optional<FigureCategory> existing = Optional.ofNullable(findById(category.id()));
        if (existing.isEmpty()) {
            return Optional.empty();
        }

        FigureCategory updated = update(category);
        return Optional.ofNullable(updated);
    }
}


