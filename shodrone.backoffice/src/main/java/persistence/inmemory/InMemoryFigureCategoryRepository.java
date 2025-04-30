package persistence.inmemory;

import domain.entity.FigureCategory;
import persistence.interfaces.FigureCategoryRepository;

import java.util.*;

public class InMemoryFigureCategoryRepository implements FigureCategoryRepository {
    private final Map<String, FigureCategory> store = new HashMap<>();

    @Override
    public Optional<FigureCategory> save(FigureCategory category) {
        String key = category.identity().toLowerCase();
        if (store.containsKey(key)) {
            return Optional.empty();
        } else {
            store.put(key, category);
            return Optional.of(category);
        }
    }

    @Override
    public Optional<FigureCategory> findByName(String name) {
        return Optional.ofNullable(store.get(name));
    }

    @Override
    public List<FigureCategory> findAll() {
        return new ArrayList<>(store.values());
    }
}
