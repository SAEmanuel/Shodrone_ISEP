package history;

import java.lang.reflect.Field;

/**
 * Utility class for generating differences between two entities of the same type.
 * It compares the fields of the given entity objects and reports the changes.
 */
public class EntityDiffer {

    /**
     * Generates a string that describes the differences between two entities.
     * It compares all the fields of the given entities and reports which fields have changed,
     * along with their old and new values.
     *
     * @param oldEntity The original entity to compare.
     * @param newEntity The updated entity to compare.
     * @param <T> The type of the entity.
     * @return A string describing the differences between the two entities.
     *         If no differences are found, an empty string is returned.
     */
    public static <T> String generateDiff(T oldEntity, T newEntity) {
        StringBuilder diff = new StringBuilder();

        // Get the class of the entity to compare fields
        Class<?> clazz = oldEntity.getClass();

        // Iterate over each declared field in the class
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true); // Allow access to private fields
            try {
                // Get the old and new values of the field
                Object oldVal = field.get(oldEntity);
                Object newVal = field.get(newEntity);

                // Compare the old and new values and append the difference if they are not equal
                if (!java.util.Objects.equals(oldVal, newVal)) {
                    diff.append(String.format("Field '%s' changed from '%s' to '%s'%n",
                            field.getName(), oldVal, newVal));
                }
            } catch (IllegalAccessException e) {
                // Handle the exception if a field is not accessible
                diff.append("Could not access field: ").append(field.getName()).append("\n");
            }
        }
        // Return the generated differences, or an empty string if no changes
        return diff.toString();
    }
}
