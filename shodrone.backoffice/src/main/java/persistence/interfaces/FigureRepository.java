package persistence.interfaces;

import domain.entity.Figure;
import domain.entity.FigureCategory;
import domain.valueObjects.FigureAvailability;
import domain.valueObjects.FigureStatus;

import java.util.List;
import java.util.Optional;

public interface FigureRepository {

    Optional<Figure> save(Figure figure);

    Optional<Figure> findByID(Long figureId);

    Optional<Figure> findByStatus(FigureStatus status);

    List<Figure> findFigures(Long figureId, String name, FigureCategory category, FigureAvailability availability);

    List<Figure> findAllPublicFigures();

}
