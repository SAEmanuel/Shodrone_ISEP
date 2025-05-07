package controller;

import domain.entity.Figure;
import domain.valueObjects.FigureStatus;
import persistence.RepositoryProvider;
import persistence.interfaces.FigureRepository;

import java.util.Optional;

public class DecommissionFigureController {
    private final FigureRepository repository;

    public DecommissionFigureController() {
        repository = RepositoryProvider.figureRepository();
    }

    public Optional<Figure> editChosenFigure(Figure figure) {
        return repository.editChosenFigure(figure);
    }
}