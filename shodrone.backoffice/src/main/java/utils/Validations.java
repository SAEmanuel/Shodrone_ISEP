package utils;

import java.util.Optional;
import java.util.Map;

/**
 * Utility class containing static validation methods.
 *
 * <p>These methods are commonly used for checking nullability, emptiness,
 * and key existence in collections.</p>
 */
public class Validations {

    /**
     * Checks if a {@link String} is null or empty.
     *
     * @param str the string to validate
     * @return {@code true} if the string is null or empty, {@code false} otherwise
     */
    public static boolean isNullOrEmpty(String str){
        return str == null || str.isEmpty();
    }

    /**
     * Checks if an {@link Object} is null.
     *
     * @param obj the object to validate
     * @return {@code true} if the object is null, {@code false} otherwise
     */
    public static boolean isNullOrEmpty(Object obj){
        return obj == null;
    }

    /**
     * Checks if an {@link Optional} contains a value.
     *
     * @param obj the optional to validate
     * @return {@code true} if the optional has a value, {@code false} otherwise
     */
    public static boolean isNotEmptyOptional(Optional<?> obj){
        return obj.isPresent();
    }

    /**
     * Checks if a {@link Map} contains a given key.
     *
     * @param map the map to check
     * @param key the key to look for
     * @param <K> the type of keys in the map
     * @param <V> the type of values in the map
     * @return {@code true} if the map contains the specified key, {@code false} otherwise
     * @throws IllegalArgumentException if the map is null
     */
    public static <K, V> boolean containsKey(Map<K, V> map, K key) {
        if (map == null) {
            throw new IllegalArgumentException("The map cannot be null.");
        }
        return map.containsKey(key);
    }
}
