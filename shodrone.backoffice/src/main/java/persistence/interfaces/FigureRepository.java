package persistence.interfaces;

import domain.entity.Costumer;
import domain.entity.Figure;
import domain.entity.FigureCategory;
import domain.valueObjects.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface FigureRepository {

    Optional<Figure> save(Figure figure);

    List<Figure> findAll();
    
    List<Figure> findAllActive();

    Optional<Figure> editChosenFigure(Figure figure);

    List<Figure> findByCostumer(Costumer costumer);

    Optional<List<Figure>> findFigures(Long figureId, Name name, Description description, Long version, FigureCategory category, FigureAvailability availability, FigureStatus status, DSL dsl, Costumer costumer);

    List<Figure> findAllPublicFigures();

}
