package controller;

import domain.entity.Figure;
import persistence.RepositoryProvider;
import persistence.interfaces.FigureRepository;

import java.util.List;
import java.util.Optional;

public class GetAllActiveFiguresController {
    private final FigureRepository repository;

    public GetAllActiveFiguresController() {
        repository = RepositoryProvider.figureRepository();
    }

    public Optional<List<Figure>> getAllActiveFigures () {
        return Optional.ofNullable(repository.findAllActive());
    }
}