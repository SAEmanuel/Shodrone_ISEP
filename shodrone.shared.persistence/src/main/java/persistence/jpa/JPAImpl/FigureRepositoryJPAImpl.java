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
import utils.DslMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FigureRepositoryJPAImpl extends JpaBaseRepository<Figure, Long>
        implements FigureRepository {

    @Override
    public Optional<Figure> save(Figure figure) {
        if (figure.identity() != null && findById(figure.identity()) != null) {
            return Optional.empty();
        }

        Optional<FigureCategory> category = Optional.ofNullable(figure.category());
        if (category.isPresent() && category.get().identity() != null) {
            FigureCategoryRepository categoryRepository = RepositoryProvider.figureCategoryRepository();
            Optional<FigureCategory> existingCategory = categoryRepository.findByName(category.get().identity());

            if (existingCategory.isEmpty()) {
                category = categoryRepository.save(category.get());
            } else {
                category = Optional.of(existingCategory.get());
            }
            figure.updateFigureCategory(category.get());
        } else {
            return Optional.empty();
        }

        Optional<Costumer> costumer = Optional.ofNullable(figure.customer());
        if (costumer.isPresent() && costumer.get().nif() != null) {
            CostumerRepository costumerRepository = RepositoryProvider.costumerRepository();
            Optional<Costumer> existingCostumer = costumerRepository.findByNIF(costumer.get().nif());

            if (existingCostumer.isEmpty()) {
                costumer = costumerRepository.saveInStore(costumer.get(), costumer.get().nif());
            } else {
                costumer = Optional.of(existingCostumer.get());
            }
            figure.updateCustomer(costumer.get());
        }

        // Verifica duplicados
        Optional<List<Figure>> findExistentFigure = findFigures(
                null, figure.name(), null, null, figure.category(),
                null, null, null, figure.customer());
        if (findExistentFigure.isPresent() && !findExistentFigure.get().isEmpty()) {
            for (Figure f : findExistentFigure.get()) {
                if (f.name().equals(figure.name())
                        && f.category().equals(figure.category())
                        && ((f.customer() == null && figure.customer() == null)
                        || (f.customer() != null && f.customer().equals(figure.customer())))) {
                    for (Map.Entry<String, DslMetadata> entry : f.dslVersions().entrySet()) {
                        for (DslMetadata newMetadata : figure.dslVersions().values()) {
                            if (newMetadata.getDslLines().equals(entry.getValue().getDslLines())) {
                                return Optional.empty();
                            }
                        }
                    }
                }
            }
        }

        add(figure);
        return Optional.of(figure);
    }

    @Override
    public List<Figure> findByCostumer(Costumer costumer) {
        return entityManager()
                .createQuery("SELECT f FROM Figure f WHERE (f.customer.id = :costumerid and f.status = :status) or (f.status = :status and f.availability = :availability) ORDER BY f.customer.id ASC", Figure.class)
                .setParameter("costumerid", costumer.identity())
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
    public Optional<List<Figure>> findFigures(Long figureId, Name name, Description description, Long version,
                                              FigureCategory category, FigureAvailability availability,
                                              FigureStatus status, DSL dsl, Costumer costumer) {
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
                        figures.add(figure);
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
                        if (dsl == null) {
                            figures.add(figure);
                        } else {
                            for (DslMetadata dslMetadata : figure.dslVersions().values()) {
                                if (dslMetadata.getDslLines().equals(List.of(dsl.toString()))) {
                                    figures.add(figure);
                                    break;
                                }
                            }
                        }
                        break;
                    case 8:
                        if (costumer == null || (figure.customer() != null && figure.customer().equals(costumer))) {
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

        figure.decommission();
        Figure updated = update(figure);
        return Optional.ofNullable(updated);
    }

    @Override
    public Optional<Figure> findFigure(Long figureId) {
        Figure figure = findById(figureId);
        if (figure == null) {
            return Optional.empty();
        }
        return Optional.of(figure);
    }
}