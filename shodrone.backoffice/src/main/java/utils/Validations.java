package utils;

import java.util.Optional;
import java.util.Map;

public class Validations {

    public static boolean isNullOrEmpty(String str){
        return str == null || str.isEmpty();
    }

    public static boolean isNullOrEmpty(Object obj){
        return obj == null;
    }

    public static boolean isNotEmptyOptional(Optional<?> obj){
        return obj.isPresent();
    }

    public static <K, V> boolean containsKey(Map<K, V> map, K key) {
        if (map == null) {
            throw new IllegalArgumentException("The map cannot be null.");
        }
        return map.containsKey(key);
    }
}

