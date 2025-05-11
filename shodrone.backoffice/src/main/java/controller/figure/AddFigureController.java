package controller.figure;

import domain.entity.Costumer;
import domain.entity.Figure;
import domain.entity.FigureCategory;
import domain.valueObjects.Description;
import domain.valueObjects.FigureAvailability;
import domain.valueObjects.FigureStatus;
import persistence.RepositoryProvider;
import persistence.interfaces.FigureRepository;

import java.util.Optional;

public class AddFigureController {
    private final FigureRepository repository;

    public AddFigureController() {
        repository = RepositoryProvider.figureRepository();
    }

    public Optional<Figure> addFigure(String name, Description description, Long version, FigureCategory category, FigureAvailability availability, FigureStatus status, Costumer costumer) {
        Figure figure = new Figure(name, description, version, category, availability, status, costumer);
        return repository.save(figure);
    }
}
