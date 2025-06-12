package utils;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class DslMetadata {

    private String droneModel;

    @Lob
    private List<String> dslLines = new ArrayList<>();

    public DslMetadata(String droneModel, List<String> dslLines) {
        this.droneModel = droneModel;
        this.dslLines = new ArrayList<>(dslLines);
    }
}