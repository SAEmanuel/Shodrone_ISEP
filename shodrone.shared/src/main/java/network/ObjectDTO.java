package network;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ObjectDTO<T> implements Serializable {
    private T object;

    public ObjectDTO(T object) {
        this.object = object;
    }

    public static <T> ObjectDTO<T> of(T object) {
        return new ObjectDTO<>(object);
    }
}
