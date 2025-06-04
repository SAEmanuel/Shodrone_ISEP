package network;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ListOfObjectDTO<T> implements Serializable {
    private List<T> objects;

    public ListOfObjectDTO(List<T> objects) {
        this.objects = objects;
    }

    public static <T> ListOfObjectDTO<T> of(List<T> objects) {
        return new ListOfObjectDTO<>(objects);
    }
}
