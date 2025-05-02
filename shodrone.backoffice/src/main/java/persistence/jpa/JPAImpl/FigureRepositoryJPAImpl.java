package persistence.jpa.JPAImpl;

import domain.entity.Costumer;
import domain.entity.Figure;
import domain.entity.FigureCategory;
import domain.valueObjects.FigureAvailability;
import domain.valueObjects.FigureStatus;
import persistence.interfaces.FigureRepository;
import persistence.jpa.JpaBaseRepository;

import java.util.List;
import java.util.Optional;

public class FigureRepositoryJPAImpl extends JpaBaseRepository<Figure, Long> implements FigureRepository {

    @Override
    public Optional<Figure> save(Figure figure, Long identity) {
        Optional<Figure> checkExistence = findByID(figure.identity());
        if (checkExistence.isEmpty()) {
            Figure saved = this.add(figure);
            return Optional.ofNullable(saved);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Figure> findByID(Long figureId) {
        return Optional.empty();
    }

    @Override
    public Optional<Figure> findByStatus(FigureStatus status) {
        return Optional.empty();
    }

    @Override
    public List<Figure> findByCostumer(Costumer costumer) {
        return List.of();
    }

    @Override
    public List<Figure> findFigures(Long figureId, String name, FigureCategory category, FigureAvailability availability) {
        return List.of();
    }

    @Override
    public List<Figure> findAllPublicFigures() {
        return List.of();
    }
}
