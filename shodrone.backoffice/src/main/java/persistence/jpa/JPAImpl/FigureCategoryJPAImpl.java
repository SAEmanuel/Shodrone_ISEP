package persistence.jpa.JPAImpl;

import authz.Email;
import domain.entity.FigureCategory;
import domain.valueObjects.Description;
import domain.valueObjects.Name;
import persistence.interfaces.FigureCategoryRepository;
import persistence.jpa.JpaBaseRepository;
import utils.AuthUtils;

import java.util.List;
import java.util.Optional;

public class FigureCategoryJPAImpl extends JpaBaseRepository<FigureCategory, Long>
        implements FigureCategoryRepository {


    @Override
    public Optional<FigureCategory> save(FigureCategory category) {
        Optional<FigureCategory> checkExistence = findByName(category.identity());
        if (checkExistence.isEmpty()) {
            FigureCategory saved = this.add(category);
            return Optional.ofNullable(saved);
        }
        return Optional.empty();
    }


    @Override
    public Optional<FigureCategory> findByName(String name) {
        List<FigureCategory> results = entityManager()
                .createQuery("SELECT f FROM FigureCategory f WHERE LOWER(f.name.name) = :name", FigureCategory.class)
                .setParameter("name", name.toLowerCase())
                .getResultList();

        if (results.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(results.get(0));
        }
    }

    @Override
    public List<FigureCategory> findActiveCategories() {
        return entityManager()
                .createQuery("SELECT f FROM FigureCategory f WHERE f.active = true", FigureCategory.class)
                .getResultList();
    }

    @Override
    public Optional<FigureCategory> editChosenCategory(FigureCategory category, Name newName, Description newDescription) {
        try {
            FigureCategory managed = entityManager().find(FigureCategory.class, category.id());

            if (managed == null) {
                return Optional.empty();
            }

            entityManager().getTransaction().begin();
            if(newName != null) {
                managed.changeCategoryNameTo(newName);
            }
            if(newDescription != null) {
                managed.changeDescriptionTo(newDescription);
            }
            managed.updateTime();
            managed.setUpdatedBy(new Email(AuthUtils.getCurrentUserEmail()));
            entityManager().getTransaction().commit();

            return Optional.of(managed);
        } catch (Exception e) {
            if (entityManager().getTransaction().isActive()) {
                entityManager().getTransaction().rollback();
            }
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<FigureCategory> changeStatus(FigureCategory category) {
        try {
            FigureCategory managed = entityManager().find(FigureCategory.class, category.id());

            if (managed == null) {
                return Optional.empty();
            }

            entityManager().getTransaction().begin();

            managed.updateTime();
            managed.setUpdatedBy(new Email(AuthUtils.getCurrentUserEmail()));
            managed.toggleState();
            entityManager().getTransaction().commit();

            return Optional.of(managed);
        } catch (Exception e) {
            if (entityManager().getTransaction().isActive()) {
                entityManager().getTransaction().rollback();
            }
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
