package controller.figure;

import domain.entity.Costumer;
import domain.entity.Figure;
import domain.entity.FigureCategory;
import domain.valueObjects.*;
import persistence.RepositoryProvider;
import persistence.interfaces.FigureRepository;

import java.util.Optional;

public class AddFigureController {
    private final FigureRepository repository;

    public AddFigureController() {
        repository = RepositoryProvider.figureRepository();
    }

    public Optional<Figure> addFigure(Name name, Description description, Long version, FigureCategory category, FigureAvailability availability, FigureStatus status, DSL dsl, Costumer costumer) {
        Figure figure = new Figure(name, description, version, category, availability, status, dsl, costumer);
        return repository.save(figure);
    }
}
