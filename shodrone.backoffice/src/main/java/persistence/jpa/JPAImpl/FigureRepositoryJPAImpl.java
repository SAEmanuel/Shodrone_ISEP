package persistence.jpa.JPAImpl;

import authz.Email;
import domain.entity.Costumer;
import domain.entity.Figure;
import domain.entity.FigureCategory;
import domain.entity.ShowRequest;
import domain.valueObjects.*;
import jakarta.persistence.EntityTransaction;
import persistence.RepositoryProvider;
import persistence.interfaces.CostumerRepository;
import persistence.interfaces.FigureCategoryRepository;
import persistence.interfaces.FigureRepository;
import persistence.jpa.JpaBaseRepository;
import utils.AuthUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        if(findFigures(null, figure.name, null, null, figure.category(), null, null, null, figure.costumer()) != null) {}

        add(figure);
        //System.out.println(figure);
        return Optional.of(figure);
    }

    @Override
    public List<Figure> findByCostumer(Costumer costumer) {
        return entityManager()
                .createQuery("SELECT f FROM Figure f WHERE (f.costumer.id = :costumerid and f.status = :status) or (f.status = :status and f.availability = :availability) ORDER BY f.costumer.id ASC", Figure.class)
                .setParameter("costumerid",costumer.identity())
                .setParameter("status", FigureStatus.ACTIVE)
                .setParameter("availability", FigureAvailability.PUBLIC)
                .getResultList();
    }

    @Override
    public List<Figure> findAllActive() {
        return entityManager()
                .createQuery("SELECT f FROM Figure f WHERE f.status = :status ORDER BY f.figureId ASC", Figure.class)
                .setParameter("status", FigureStatus.ACTIVE)
                .getResultList();
    }

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
                        if (name != null) {
                            if (figure.name().equals(name))
                                figures.add(figure);
                        } else {
                            figures.add(figure);
                        }
                        break;
                    case 2:
                        if (description != null) {
                            if (figure.description() != null && figure.description().equals(description))
                                figures.add(figure);
                        } else {
                            figures.add(figure);
                        }
                        break;
                    case 3:
                        if (version != null) {
                            if (figure.version() != null && figure.version().equals(version))
                                figures.add(figure);
                        } else {
                            figures.add(figure);
                        }
                        break;
                    case 4:
                        if (category != null) {
                            if (figure.category().equals(category))
                                figures.add(figure);
                        } else {
                            figures.add(figure);
                        }
                        break;
                    case 5:
                        if (availability != null) {
                            if (figure.availability().equals(availability))
                                figures.add(figure);
                        } else {
                            figures.add(figure);
                        }
                        break;
                    case 6:
                        if (status != null) {
                            if (figure.status().equals(status))
                                figures.add(figure);
                        } else {
                            figures.add(figure);
                        }
                        break;
                    case 7:
                        if (dsl != null) {
                            if (figure.dsl().equals(dsl))
                                figures.add(figure);
                        } else {
                            figures.add(figure);
                        }
                        break;
                    case 8:
                        if (costumer != null) {
                            if (figure.costumer().equals(costumer))
                                figures.add(figure);
                        } else {
                            figures.add(figure);
                        }
                        break;
                }
            }
            searchFigures = figures;
            searching++;
        }

        return Optional.of(figures);
    }


    @Override
    public List<Figure> findAllPublicFigures() {
        return entityManager()
                .createQuery("SELECT f FROM Figure f WHERE f.status = :status and f.availability = :availability ORDER BY f.figureId ASC", Figure.class)
                .setParameter("status", FigureStatus.ACTIVE)
                .setParameter("availability", FigureAvailability.PUBLIC)
                .getResultList();
    }

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
}
