package persistence.inmemory;

import domain.entity.Costumer;
import domain.entity.Figure;
import domain.entity.FigureCategory;
import domain.valueObjects.FigureAvailability;
import domain.valueObjects.FigureStatus;
import persistence.interfaces.FigureRepository;
import utils.Utils;

import java.util.*;

public class InMemoryFigureRepository implements FigureRepository {
    private final Map<Long, Figure> store = new HashMap<>();

    @Override
    public Optional<Figure> save(Figure figure) {
        Long key = figure.identity();
        if (store.containsKey(key)) {
            return Optional.empty();
        } else {
            store.put(key, figure);
            return Optional.of(figure);
        }
    }

    @Override
    public List<Figure> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Figure> findByID(Object id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Figure> findByStatus(FigureStatus status) { return Optional.ofNullable(store.get(status)); }

    @Override
    public List<Figure> findByCostumer(Costumer costumer) {
        ArrayList<Figure> figures = new ArrayList<Figure>();
        if(store.values().isEmpty())
            return figures;

        for (Figure figure : store.values()) {

            if (figure.costumer().equals(costumer)) {
                figures.add(figure);
            }
        }

        return figures;
    }

    @Override
    public List<Figure> findFigures(Long figureId, String name, FigureCategory category, FigureAvailability availability) {
        return new ArrayList<Figure>();
    }

    @Override
    public List<Figure> findAllPublicFigures() {
        ArrayList<Figure> figures = new ArrayList<Figure>();
        if(store.values().isEmpty())
            return figures;

        for (Figure figure : store.values()) {

            if (figure.status().equals(FigureStatus.ACTIVE) && figure.availability().equals(FigureAvailability.PUBLIC)) {
                figures.add(figure);
            }
        }

        return figures;
    }
}
