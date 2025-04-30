package persistence.jpa.JPAImpl;

import domain.entity.FigureCategory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import persistence.interfaces.FigureCategoryRepository;
import persistence.jpa.JpaBaseRepository;

import java.util.Optional;

public class FigureCategoryJPAImpl extends JpaBaseRepository<FigureCategory, Long>
        implements FigureCategoryRepository {


    @Override
    public Optional<FigureCategory> save(FigureCategory category) {
        FigureCategory saved = this.add(category);
        return Optional.ofNullable(saved);
    }


    @Override
    public Optional<FigureCategory> findByName(String name) {
        try {
            FigureCategory found = entityManager()
                    .createQuery("SELECT f FROM FigureCategory f WHERE LOWER(f.name) = LOWER(:name)", FigureCategory.class)
                    .setParameter("name", name)
                    .getSingleResult();
            return Optional.of(found);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }


}
