package utils;

import java.util.Optional;

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
}
