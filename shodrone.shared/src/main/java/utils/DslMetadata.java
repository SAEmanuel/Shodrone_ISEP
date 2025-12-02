package utils;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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

    @Column(name = "droneModel", columnDefinition = "TEXT")
    private String droneModel;

    @Convert(converter = StringListConverter.class)
    @Column(name = "dslLines", columnDefinition = "TEXT")
    private List<String> dslLines = new ArrayList<>();

    public DslMetadata(String droneModel, List<String> dslLines) {
        this.droneModel = droneModel;
        this.dslLines = new ArrayList<>(dslLines);
    }
}