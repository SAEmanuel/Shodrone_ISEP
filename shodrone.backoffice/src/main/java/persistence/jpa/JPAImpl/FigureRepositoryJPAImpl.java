package persistence.jpa.JPAImpl;

import domain.entity.Costumer;
import domain.entity.Figure;
import domain.entity.FigureCategory;
import domain.valueObjects.Description;
import domain.valueObjects.FigureAvailability;
import domain.valueObjects.FigureStatus;
import jakarta.persistence.EntityTransaction;
import persistence.RepositoryProvider;
import persistence.interfaces.CostumerRepository;
import persistence.interfaces.FigureCategoryRepository;
import persistence.interfaces.FigureRepository;
import persistence.jpa.JpaBaseRepository;

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

        add(figure);
        System.out.println(figure);
        return Optional.of(figure);
    }


    @Override
    public Optional<Figure> findByID(Object id) {
        if (!(id instanceof Long)) return Optional.empty();
        return Optional.ofNullable(entityManager().find(Figure.class, (long) id));
    }

    @Override
    public Optional<Figure> findByStatus(FigureStatus status) {
        return Optional.empty();
    }

    @Override
    public List<Figure> findByCostumer(Costumer costumer) {
        return entityManager()
                .createQuery("SELECT f FROM Figure f WHERE f.costumer.id = costumer.id ORDER BY f.costumer.id ASC", Figure.class)
                .getResultList();
    }

    @Override
    public Optional<List<Figure>> findFigures(Long figureId, String name, Description description, Long version, FigureCategory category, FigureAvailability availability, FigureStatus status, Costumer costumer) {
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

        while (searching <= 7) {
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
                .createQuery("SELECT f FROM Figure f WHERE f.availability = :availability ORDER BY f.figureId ASC", Figure.class)
                .setParameter("availability", FigureAvailability.PUBLIC)
                .getResultList();
    }

}
