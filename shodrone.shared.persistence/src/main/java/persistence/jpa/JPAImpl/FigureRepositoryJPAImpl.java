package persistence.jpa.JPAImpl;

import domain.entity.Costumer;
import domain.entity.Figure;
import domain.entity.FigureCategory;
import domain.valueObjects.*;
import persistence.RepositoryProvider;
import persistence.CostumerRepository;
import persistence.FigureCategoryRepository;
import persistence.FigureRepository;
import persistence.jpa.JpaBaseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JPA implementation of the FigureRepository interface.
 * Provides persistence and query operations for Figure entities using JPA.
 */
public class FigureRepositoryJPAImpl extends JpaBaseRepository<Figure, Long>
        implements FigureRepository {

    // FIX TABLE BY DROPPING
    /*public Optional<Void> dropFigureTableAndSequence() {
        EntityTransaction tx = entityManager().getTransaction();

        try {
            tx.begin();
            entityManager().createNativeQuery("DROP TABLE IF EXISTS figure").executeUpdate();
            entityManager().createNativeQuery("DROP SEQUENCE IF EXISTS figure_SEQ").executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }

        return Optional.empty();
    }*/

    /**
     * Saves a new Figure entity if it does not already exist.
     * Also persists or reuses the associated category and costumer entities.
     * Checks for duplicate figures before saving.
     *
     * @param figure The Figure entity to save.
     * @return Optional containing the saved Figure if successful, or empty if duplicate or already exists.
     */
    @Override
    public Optional<Figure> save(Figure figure) {
        if (figure.identity() != null && findById(figure.identity()) != null) {
            return Optional.empty();
        }

        // Persist category if not already persisted
        Optional<FigureCategory> category = Optional.ofNullable(figure.category());
        if (category.isPresent() && category.get().identity() != null) {
            FigureCategoryRepository categoryRepository = RepositoryProvider.figureCategoryRepository();
            Optional<FigureCategory> existingCategory = categoryRepository.findByName(category.get().identity());

            if (existingCategory.isEmpty()) {
                category = categoryRepository.save(category.orElse(null));
            } else {
                category = Optional.of(existingCategory.get());
            }
            figure.UpdateFigureCategory(category.get());
        }else{
            return Optional.empty();
        }


        // Persist or reuse costumer
        Optional<Costumer> costumer = Optional.ofNullable(figure.costumer());
        if (costumer.isPresent() && costumer.get().nif() != null) {
            CostumerRepository costumerRepository = RepositoryProvider.costumerRepository();
            Optional<Costumer> existingCostumer = costumerRepository.findByNIF(costumer.get().nif());

            if (existingCostumer.isEmpty()) {
                costumer = costumerRepository.saveInStore(costumer.get(), costumer.get().nif());
            } else {
                costumer = Optional.of(existingCostumer.get());
            }
            figure.UpdateFigureCostumer(costumer.get());
        }

        Optional<List<Figure>> findExistentFigure = findFigures(null, figure.name, null, null, figure.category(),
                null, null, null, figure.costumer());
        if( findExistentFigure.isPresent() && !findExistentFigure.get().isEmpty()) {
            return Optional.empty();
        }

        add(figure);
        //System.out.println(figure);
        return Optional.of(figure);
    }

    /**
     * Retrieves all active figures owned by the specified costumer or publicly available figures.
     *
     * @param costumer The costumer to filter by.
     * @return List of figures matching the criteria.
     */
    @Override
    public List<Figure> findByCostumer(Costumer costumer) {
        return entityManager()
                .createQuery("SELECT f FROM Figure f WHERE (f.costumer.id = :costumerid and f.status = :status) or (f.status = :status and f.availability = :availability) ORDER BY f.costumer.id ASC", Figure.class)
                .setParameter("costumerid",costumer.identity())
                .setParameter("status", FigureStatus.ACTIVE)
                .setParameter("availability", FigureAvailability.PUBLIC)
                .getResultList();
    }

    /**
     * Retrieves all figures with status ACTIVE.
     *
     * @return List of active figures.
     */
    @Override
    public List<Figure> findAllActive() {
        return entityManager()
                .createQuery("SELECT f FROM Figure f WHERE f.status = :status ORDER BY f.figureId ASC", Figure.class)
                .setParameter("status", FigureStatus.ACTIVE)
                .getResultList();
    }

    /**
     * Searches for figures matching a combination of optional filters.
     * Applies filters successively by each attribute.
     *
     * @param figureId     Filter by figure ID.
     * @param name         Filter by figure name.
     * @param description  Filter by description.
     * @param version      Filter by version.
     * @param category     Filter by figure category.
     * @param availability Filter by availability status.
     * @param status       Filter by figure status.
     * @param dsl          Filter by DSL.
     * @param costumer     Filter by costumer.
     * @return Optional containing list of matching figures or empty if none found.
     */
    @Override
    public Optional<List<Figure>> findFigures(Long figureId, Name name, Description description, Long version, FigureCategory category, FigureAvailability availability, FigureStatus status, DSL dsl, Costumer costumer) {
        List<Figure> figures = entityManager()
                .createQuery("SELECT f FROM Figure f", Figure.class)
                .getResultList();

        if (figures.isEmpty()) {
            return Optional.empty();
        }

        if (figureId != null) {
            List<Figure> result = entityManager()
                    .createQuery("SELECT f FROM Figure f WHERE f.figureId = :figureId", Figure.class)
                    .setParameter("figureId", figureId)
                    .getResultList();

            if (!result.isEmpty()) {
                return Optional.of(result);
            }
        }

        int searching = 1;
        List<Figure> searchFigures = new ArrayList<>(figures);

        while (searching <= 8) {
            figures = new ArrayList<>();

            for (Figure figure : searchFigures) {
                switch (searching) {
                    case 1:
                        if (name == null || figure.name().equals(name)) {
                            figures.add(figure);
                        }
                        break;
                    case 2:
                        if (description == null || figure.description().equals(description)) {
                            figures.add(figure);
                        }
                        break;
                    case 3:
                        if (version == null || figure.version().equals(version)) {
                            figures.add(figure);
                        }
                        break;
                    case 4:
                        if (category == null || figure.category().equals(category)) {
                            figures.add(figure);
                        }
                        break;
                    case 5:
                        if (availability == null || figure.availability().toString().equals(availability.toString())) {
                            figures.add(figure);
                        }
                        break;
                    case 6:
                        if (status == null || figure.status().toString().equals(status.toString())) {
                            figures.add(figure);
                        }
                        break;
                    case 7:
                        if (dsl == null || figure.dsl().toString().equals(dsl.toString())) {
                            figures.add(figure);
                        }
                        break;
                    case 8:
                        if (costumer == null || figure.costumer().equals(costumer)) {
                            figures.add(figure);
                        }
                        break;
                }
            }

            if (figures.isEmpty()) {
                break;
            }

            searchFigures = figures;
            searching++;
        }

        return Optional.of(figures);
    }


    /**
     * Retrieves all figures that are active and publicly available.
     *
     * @return List of public active figures.
     */
    @Override
    public List<Figure> findAllPublicFigures() {
        return entityManager()
                .createQuery("SELECT f FROM Figure f WHERE f.status = :status and f.availability = :availability ORDER BY f.figureId ASC", Figure.class)
                .setParameter("status", FigureStatus.ACTIVE)
                .setParameter("availability", FigureAvailability.PUBLIC)
                .getResultList();
    }

    /**
     * Marks a given figure as inactive (decommissioned) and updates it in the database.
     *
     * @param figure The figure to edit.
     * @return Optional containing the updated figure if successful, or empty if the figure does not exist.
     */
    @Override
    public Optional<Figure> editChosenFigure(Figure figure) {
        if (figure == null || figure.identity() == null) {
            return Optional.empty();
        }

        Optional<Figure> existing = Optional.ofNullable(findById(figure.identity()));
        if (existing.isEmpty()) {
            return Optional.empty();
        }

        figure.decommissionFigureStatus();

        Figure updated = update(figure);
        return Optional.ofNullable(updated);
    }

    @Override
    public Optional<Figure> findFigure(Long figureId){
        Figure figure = findById(figureId);
        if( figure == null){
            return Optional.empty();
        }
        return Optional.of(figure);
    }

}
