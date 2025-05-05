package domain.history;

import java.lang.reflect.Field;

public class EntityDiffer {

    public static <T> String generateDiff(T oldEntity, T newEntity) {
        StringBuilder diff = new StringBuilder();
        Class<?> clazz = oldEntity.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object oldVal = field.get(oldEntity);
                Object newVal = field.get(newEntity);
                if (!java.util.Objects.equals(oldVal, newVal)) {
                    diff.append(String.format("Field '%s' changed from '%s' to '%s'%n",
                            field.getName(), oldVal, newVal));
                }
            } catch (IllegalAccessException e) {
                diff.append("Could not access field: ").append(field.getName()).append("\n");
            }
        }
        return diff.toString();
    }
}
